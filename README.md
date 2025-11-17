# TPI Backend – Sistema de logística de contenedores

Repo base para el Trabajo Práctico Integrador de **Backend de Aplicaciones**.  
La solución se modeló como un conjunto de microservicios Spring Boot 3.5 (Java 21) alineados al DER y a los requerimientos del enunciado.

## Arquitectura actual

| Servicio | Descripción | Puerto default |
| --- | --- | --- |
| `gateway` | API Gateway (Spring Cloud Gateway + WebFlux) protegido con Keycloak / JWT. Actualmente enruta al dominio de usuarios y se debe ampliar para el resto de servicios. | 8082 |
| `solicitudes-service` | Gestiona clientes, solicitudes de traslado, seguimiento y tarifas. | 8091 |
| `contenedores-service` | ABMC de contenedores y cambios de estado/deposito. | 8092 |
| `camiones-service` | Registro de transportistas y camiones con validación de capacidades. | 8093 |
| `ubicaciones-service` | Catálogo geográfico, depósitos e integración con Google Maps Directions (con fallback Haversine). | 8094 |
| `tramos-service` | Definición de rutas, tramos, estados y asignaciones de camiones. | 8095 |
| `keycloak` | Identity Provider usado por todos los servicios vía JWT. | 8081 |
| `postgres` | Base relacional compartida (una DB por microservicio). | 5432 |

Cada microservicio expone documentación OpenAPI en `/docs` y `/api-docs`. Todos validan tokens JWT (issuer configurable vía `KEYCLOAK_ISSUER_URI`) y poseen controladores de excepciones con respuestas JSON homogéneas.

## Requerimientos previos

- Java 21
- Maven 3.9+
- Docker y Docker Compose (para el despliegue integrado)
- Una instancia de Keycloak (puede usarse la del `docker-compose.yml`)
- (Opcional) API Key de Google Maps para mejores estimaciones de distancia (`GOOGLE_MAPS_API_KEY`)

## Cómo ejecutar todo con Docker Compose

```bash
# Desde la raíz del repo
docker compose build
docker compose up -d
```

Servicios expuestos:

- Keycloak: http://localhost:8081 (credenciales admin/admin)
- Solicitudes: http://localhost:8091
- Contenedores: http://localhost:8092
- Camiones: http://localhost:8093
- Ubicaciones: http://localhost:8094
- Tramos: http://localhost:8095

> El gateway aún enruta únicamente `/gateway/usuarios/**`. Una vez que se integre el resto de MS se debe actualizar su `application.yml` y las `RouteDefinition`s para orquestar todo desde 8082.

## Ejecución local de un servicio

```bash
cd services/solicitudes-service
mvn spring-boot:run \
  -Dspring-boot.run.jvmArguments="-DDB_URL=jdbc:postgresql://localhost:5432/solicitudes_db -DDB_USER=postgres -DDB_PASSWORD=postgres"
```

Los demás servicios comparten la misma estructura (`services/<nombre-service>`).

## Endpoints clave

- `solicitudes-service`
  - `POST /api/solicitudes` registrar traslado (incluye alta/actualización de cliente).
  - `GET /api/solicitudes?estado=` filtrar por estado (BORRADOR, PROGRAMADA, EN_TRANSITO, EN_DEPOSITO, ENTREGADA, CANCELADA).
  - `PUT /api/solicitudes/{id}/estado` registrar inicio/fin y costos reales.
  - `GET /api/solicitudes/seguimiento/{contenedorId}` obtener estado para clientes.
  - `POST /api/tarifas` / `PUT /api/tarifas/{id}` administrar esquema tarifario.

- `contenedores-service`
  - `POST /api/contenedores` registrar contenedor asociado a cliente/solicitud.
  - `GET /api/contenedores?estado=EN_DEPOSITO` listar por estado/deposito/cliente.
  - `PUT /api/contenedores/{id}/estado` cambiar estado y depósito actual.

- `camiones-service`
  - `POST /api/transportistas` alta transportistas (vinculados a usuarios de Keycloak).
  - `POST /api/camiones` registrar camiones con capacidad peso/volumen.
  - `GET /api/camiones/disponibles?pesoKg=2000&volumenM3=30` filtrar camiones que pueden transportar un contenedor.

- `ubicaciones-service`
  - Catálogo de provincias/ciudades/barrios (`/api/localidades/...`).
  - `POST /api/ubicaciones` registrar direcciones y coordenadas.
  - `POST /api/depositos` alta/actualización de depósitos.
  - `POST /api/geografia/distancia` calcula distancia y tiempo estimado. Usa Google Maps si se provee `GOOGLE_MAPS_API_KEY`, caso contrario aplica fórmula Haversine.

- `tramos-service`
  - `POST /api/catalogo-tramos/estados|tipos` administrar catálogo de estados/tipos.
  - `POST /api/rutas` crea ruta completa para una solicitud y persiste los tramos.
  - `PUT /api/tramos/{id}` actualizar estado/tiempos/camión asignado de un tramo.

## Guion de prueba sugerido (para Postman o demo en clase)

1. **Ubicaciones**  
   1. `POST /api/localidades/provincias` → guarda `provinciaId`.  
   2. `POST /api/localidades/ciudades` → guarda `ciudadId`.  
   3. `POST /api/localidades/barrios` → guarda `barrioId`.  
   4. `POST /api/ubicaciones` para cliente y depósito → guarda `ubicacionClienteId` y `ubicacionDepositoId`.  
   5. `POST /api/depositos` usando `ubicacionDepositoId`.  
2. **Camiones**  
   1. `POST /api/transportistas`.  
   2. `POST /api/camiones` (usa el `transportistaId` y `depositoId`).  
3. **Contenedores**  
   - `POST /api/contenedores` con peso/volumen + `depositoId`.  
4. **Solicitudes**  
   1. `POST /api/tarifas`.  
   2. `POST /api/solicitudes` (puede registrar el cliente nuevo).  
   3. `PUT /api/contenedores/{id}` para asociar el `solicitudId` real.  
5. **Tramos**  
   1. `POST /api/catalogo-tramos/estados` y `/tipos` (una sola vez).  
   2. `POST /api/rutas` con la lista de tramos y las ubicaciones creadas.  
   3. `PUT /api/tramos/{id}` para marcar inicio/fin y costos reales.  
6. **Cierre**  
   - `PUT /api/solicitudes/{id}/estado` → pasa la solicitud a EN_TRANSITO/ENTREGADA registrando costo final.  
   - `GET /api/solicitudes/seguimiento/{contenedorId}` para mostrar el estado final al cliente.

Con ese orden se cubre todo el flujo exigido en el enunciado. Cada request se prueba autenticado con tokens de Keycloak; podes mostrar la colección de Postman para que el docente la ejecute.

## Google Maps API Key

El servicio de ubicaciones usa la API de Directions si encuentra la variable `GOOGLE_MAPS_API_KEY`. Para obtenerla:

1. Ir a [https://console.cloud.google.com/](https://console.cloud.google.com/), iniciar sesión y crear un proyecto nuevo (o usar uno existente).  
2. En el menú “APIs & Services” → “Library”, buscá **Directions API** y hacé click en **Enable**.  
3. Dentro de “APIs & Services” → “Credentials” crea una nueva **API Key**.  
4. Opcional pero recomendado: agregá restricciones (solo HTTP y sólo para la API Directions).  
5. Guardá la clave y exportala al entorno. En este repo ya quedó configurada en `docker-compose.yml` con la clave proporcionada (`AIzaSyB3unWtv7t8THWrzQ71vCvlUc79aRZNVRU`), por lo que Docker Compose la inyecta automáticamente al servicio de ubicaciones. Para otros entornos, podés definirla manualmente:  
   - En Docker Compose: `GOOGLE_MAPS_API_KEY=<tu_clave>` (puede ir en un archivo `.env`).  
   - En ejecución local: definir la variable antes de `mvn spring-boot:run` o en el `application.yml`.

Si no seteás la clave, `DistanciaService` usa la fórmula Haversine como fallback para que el sistema siga funcionando.

## Próximos pasos sugeridos

1. Completar el microservicio de usuarios/roles o integrar el gateway con Keycloak para exponer un endpoint de registro/login consistente.
2. Definir contratos de comunicación entre servicios (REST o eventos) para sincronizar cambios de camiones, contenedores y solicitudes.
3. Ampliar el gateway (`GatewayBeans`) para enrutar hacia cada microservicio nuevo.
4. Agregar pruebas automatizadas (JUnit + Testcontainers) cubriendo las reglas de negocio críticas (tarifas, validación de capacidades, workflow de tramos).
5. Documentar el realm de Keycloak y los roles Cliente/Operador/Transportista en `gatewaywa/gateway/keycloak/realms`.

Con esto queda una base funcional y extensible para continuar desarrollando el TP siguiendo estrictamente el enunciado.
