# TPI Backend - Sistema de logistica de contenedores

Repositorio base del Trabajo Practico Integrador de **Backend de Aplicaciones**. La solucion esta compuesta por microservicios Spring Boot 3.5 (Java 21) alineados al DER y a los casos de uso del enunciado.

## Arquitectura

| Servicio | Descripcion | Puerto |
| --- | --- | --- |
| gateway | Spring Cloud Gateway + WebFlux + Keycloak. | 8082 |
| solicitudes-service | Clientes, solicitudes, seguimiento y tarifas. | 8091 |
| contenedores-service | ABMC de contenedores y estados/depositos. | 8092 |
| camiones-service | Transportistas y camiones con validaciones de capacidad. | 8093 |
| ubicaciones-service | Catalogo geografico e integracion con Google Maps. | 8094 |
| tramos-service | Rutas, tramos y catalogos de tipos/estados. | 8095 |
| keycloak | Identity Provider. | 8080 |
| postgres | Base relacional (una DB por MS). | 5432 |

Cada microservicio expone documentacion OpenAPI en `/docs` y `/api-docs` y valida JWT emitidos por Keycloak (`KEYCLOAK_ISSUER_URI`).

## Requisitos

- Java 21
- Maven 3.9+
- Docker + Docker Compose
- Keycloak (incluido en el compose)
- (Opcional) `GOOGLE_MAPS_API_KEY` para distancias reales

## Ejecucion con Docker Compose

```bash
# Desde la raiz del repo
docker compose build
docker compose up -d
```

Servicios directos:

- Keycloak: http://keycloak:8080 (admin/admin)
- Gateway: http://localhost:8082 (entrypoint unico)
- Solicitudes: http://localhost:8091
- Contenedores: http://localhost:8092
- Camiones: http://localhost:8093
- Ubicaciones: http://localhost:8094
- Tramos: http://localhost:8095

> Todo consumo funcional se realiza a traves del Gateway: `http://localhost:8082/gateway/<dominio>/<recurso>`.

## Rutas expuestas por el Gateway

| Dominio | Path externo | Destino (puerto) |
| --- | --- | --- |
| Usuarios | `/gateway/usuarios/**` | usuarios-service (8090) |
| Clientes | `/gateway/clientes/**` | solicitudes-service (8091) |
| Solicitudes | `/gateway/solicitudes/**` | solicitudes-service (8091) |
| Tarifas | `/gateway/tarifas/**` | solicitudes-service (8091) |
| Contenedores | `/gateway/contenedores/**` | contenedores-service (8092) |
| Transportistas | `/gateway/transportistas/**` | camiones-service (8093) |
| Camiones | `/gateway/camiones/**` | camiones-service (8093) |
| Ubicaciones | `/gateway/ubicaciones/**` | ubicaciones-service (8094) |
| Localidades | `/gateway/localidades/**` | ubicaciones-service (8094) |
| Geografia | `/gateway/geografia/**` | ubicaciones-service (8094) |
| Depositos | `/gateway/depositos/**` | ubicaciones-service (8094) |
| Catalogo de tramos | `/gateway/catalogo-tramos/**` | tramos-service (8095) |
| Rutas | `/gateway/rutas/**` | tramos-service (8095) |
| Tramos | `/gateway/tramos/**` | tramos-service (8095) |

Cada ruta aplica un `RewritePath` a `/api/...` y mantiene autenticacion y auditoria en un unico entrypoint.

## Ejecucion local de un microservicio

```bash
cd services/solicitudes-service
mvn spring-boot:run \
  -Dspring-boot.run.jvmArguments="-DDB_URL=jdbc:postgresql://localhost:5432/solicitudes_db -DDB_USER=postgres -DDB_PASSWORD=postgres"
```

Los demas servicios siguen la misma estructura (`services/<nombre-service>`). Ajusta las variables `DB_*`, `KEYCLOAK_ISSUER_URI`, `GOOGLE_MAPS_API_KEY` y `SERVER_PORT` segun sea necesario.

## Variables y configuracion

- `KEYCLOAK_ISSUER_URI`: issuer para validar tokens.
- `DB_URL`, `DB_USER`, `DB_PASSWORD`: conexion postgres por servicio.
- `GOOGLE_MAPS_API_KEY`: habilita el uso de Directions API (sino usa Haversine).
- `SPRING_PROFILES_ACTIVE`: perfiles `docker`, `local`, etc.
- `USUARIOS_SERVICE_URI`, `SOLICITUDES_SERVICE_URI`, etc.: permiten apuntar el gateway a otra instancia.
- Keycloak corre en Docker con `KC_HOSTNAME=keycloak`, por lo que los JWT traen `iss=http://keycloak:8080/...`. Desde el host accedé a `http://keycloak:8080` (agregá `127.0.0.1 keycloak` en tu archivo `hosts`) y todos los llamados funcionales deben ser `http://localhost:8082/gateway/**`.

## Credenciales y token JWT

1. Levantá Keycloak (`docker compose up -d keycloak`) y accedé a `http://keycloak:8080` con admin/admin.
2. Si es la primera vez, importá `gatewaywa/gateway/keycloak/realms/realm-export.json` desde la consola (Realm Settings → Import) para crear el realm `TPI-Backend`.
3. Pedí tokens con `POST http://keycloak:8080/realms/TPI-Backend/protocol/openid-connect/token` enviando `grant_type=password`, `client_id=tpi-backend-client`, `client_secret` (si corresponde), `username` y `password`.
4. Usá ese `access_token` en los llamados a `http://localhost:8082/gateway/**`. Si Keycloak se reinicia, repetí el proceso de importación antes de pedir tokens.

## Plan de pruebas paso a paso (via Gateway)

1. **Autenticacion**
   1. Levantar Keycloak desde Docker Compose.
   2. Crear roles Cliente/Operador/Transportista y usuarios de prueba.
   3. Registrar un nuevo cliente/usuario con `POST /gateway/clientes/registro` (ver ejemplo abajo) para demostrar el alta desde la API.
   4. Obtener un JWT valido (Realm `TPI-Backend`).
2. **Ubicaciones y catalogos**
   1. `POST /gateway/localidades/provincias` -> guardar `provinciaId`.
   2. `POST /gateway/localidades/ciudades` y `/gateway/localidades/barrios` -> `ciudadId` / `barrioId`.
   3. `POST /gateway/ubicaciones` para cliente y deposito -> `ubicacionClienteId` / `ubicacionDepositoId`.
   4. `POST /gateway/depositos` usando `ubicacionDepositoId`.
3. **Camiones**
   1. `POST /gateway/transportistas` (vincular usuario Keycloak).
   2. `POST /gateway/camiones` usando `transportistaId` y `depositoId`, validar rechazo por capacidad.
4. **Contenedores**
   1. `POST /gateway/contenedores` con datos del contenedor.
   2. `GET /gateway/contenedores?estado=EN_DEPOSITO` para verificar disponibilidad.
5. **Tarifas y solicitudes**
   1. `POST /gateway/tarifas` con las reglas vigentes.
   2. `POST /gateway/solicitudes` (puede dar de alta al cliente). Guardar `solicitudId`.
   3. `PUT /gateway/contenedores/{id}` para asociar el `solicitudId` real.
6. **Rutas y tramos**
   1. `POST /gateway/catalogo-tramos/estados` y `/gateway/catalogo-tramos/tipos` (solo una vez).
   2. `POST /gateway/rutas` con el trayecto completo. Guardar `tramoId`.
   3. `PUT /gateway/tramos/{tramoId}` para iniciar/finalizar y asignar camion/costos reales.
7. **Seguimiento y cierre**
   1. `PUT /gateway/solicitudes/{id}/estado` -> pasar a EN_TRANSITO y luego ENTREGADA.
   2. `GET /gateway/solicitudes/seguimiento/{contenedorId}` para mostrar el estado al cliente.
8. **Pruebas negativas**
   - `POST /gateway/camiones` con menos capacidad -> debe rechazar.
   - `POST /gateway/solicitudes` sin tarifa vigente -> debe fallar.
   - Cualquier `/gateway/**` sin JWT -> respuesta 401.

Con este flujo se recorre todo el circuito exigido en el enunciado y se demuestra que cada servicio opera detras del gateway.

### Registro de clientes y usuarios via API

- Endpoint: `POST http://localhost:8082/gateway/clientes/registro`
- Ejemplo de body:

```json
{
  "dni": "38111222",
  "nombre": "Lucia",
  "apellido": "Gomez",
  "telefono": "1122334455",
  "email": "lucia@example.com",
  "username": "lucia.gomez",
  "password": "ClaveSegura123"
}
```

El servicio crea el usuario en Keycloak (rol `CLIENTE`) y almacena al cliente en `solicitudes-service`, dejando el `usuarioId` listo para futuras solicitudes.

## Google Maps API Key

1. Ingresar a https://console.cloud.google.com/ y crear (o elegir) un proyecto.
2. Habilitar **Directions API** dentro de "APIs & Services" > "Library".
3. Generar una **API Key** en "APIs & Services" > "Credentials" y restringirla a HTTPS + Directions API.
4. Exportar la clave como variable de entorno:
   - Docker Compose: agregar `GOOGLE_MAPS_API_KEY=<clave>` o usar `.env`.
   - Local: `export GOOGLE_MAPS_API_KEY=<clave>` antes de `mvn spring-boot:run`.
5. Si no se define la variable, el servicio de ubicaciones usa Haversine como fallback.

## Proximos pasos

1. Extender el gateway con rate limiting, tracing y health-checks centralizados.
2. Automatizar pruebas criticas (JUnit + Testcontainers) y colecciones Postman.
3. Documentar el realm/roles de Keycloak en `gatewaywa/gateway/keycloak/realms`.
4. Agregar observabilidad basica (logs estructurados, Prometheus/Grafana).
