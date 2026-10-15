# Red de Huertos Urbanos - Microservicios

Sistema de gestión para una red de huertos urbanos utilizando arquitectura de microservicios con Spring Boot, PostgreSQL (Supabase) y Gitflow.

## Arquitectura
El sistema se compone de dos microservicios:
1. **Parcel Service (Puerto 8081):** Servicio base que gestiona las parcelas directamente en la base de datos PostgreSQL.
2. **Garden Composite Service (Puerto 8082):** Servicio compuesto que consume a Parcel Service y enriquece las respuestas añadiendo recomendaciones agrícolas según el tipo de suelo de la parcela.

## Requisitos previos
- Java 17 o superior
- Maven
- Base de datos en Supabase (PostgreSQL)

## Variables de Entorno (Parcel Service)
Para ejecutar el servicio `parcel`, necesitas configurar las siguientes variables de entorno (puedes usar un archivo `launch.json` en VS Code):
- `DB_HOST`: Host de Supabase (ej. aws-0-us-east-1.pooler.supabase.com)
- `DB_USER`: Usuario de base de datos
- `DB_PASSWORD`: Contraseña de la base de datos

## Cómo ejecutar localmente
1. Levantar **Parcel Service**:
   Navega a la carpeta `/parcel` y ejecuta la clase `ParcelApplication.java`. (Puerto 8081).
2. Levantar **Garden Service**:
   Navega a la carpeta `/garden` y ejecuta la clase `GardenApplication.java`. (Puerto 8082). 
   *Nota: Garden Service requiere que Parcel Service esté corriendo.*

## Endpoints Principales
### Parcel Service (8081)
- `POST /api/v1/parcels` : Crear una nueva parcela.
- `GET /api/v1/parcels` : Listar todas las parcelas.
- `GET /api/v1/parcels/{id}` : Obtener detalle de una parcela específica.

### Garden Service (8082)
- `GET /api/v1/garden/parcels` : Listar todas las parcelas con recomendaciones agrícolas.
- `GET /api/v1/garden/parcels?soilType={tipo}` : Filtrar parcelas por tipo de suelo (ARCILLOSO, LIMOSO, ARENOSO).
- `GET /api/v1/garden/parcels/{id}/overview` : Obtener parcela específica con recomendaciones.