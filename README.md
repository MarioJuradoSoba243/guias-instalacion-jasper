# Codex – Generador de informe (JasperReport) a partir de plantilla fija

Aplicación web basada en **Vue.js (frontend)** y **Java 21 (backend)** cuyo objetivo final es generar un **informe en JasperReports** a partir de los datos introducidos en un formulario web.

> ⚠️ Importante:  
> En este proyecto **no se proporciona inicialmente la plantilla Jasper** (`.jrxml`/`.jasper`).  
> Se proporciona un **PDF hecho a mano** como referencia del diseño del informe. A partir de ese PDF, Codex deberá **crear la plantilla Jasper** que después utilizará el backend para generar el informe.

---

## Índice

1. [Arquitectura](#arquitectura)
2. [Tecnologías](#tecnologías)
3. [Estructura del proyecto](#estructura-del-proyecto)
4. [Requisitos previos](#requisitos-previos)
5. [Fases del proyecto](#fases-del-proyecto)
   - [Fase 1 – Creación de la plantilla Jasper](#fase-1--creación-de-la-plantilla-jasper)
   - [Fase 2 – Generación del informe vía web](#fase-2--generación-del-informe-vía-web)
6. [Puesta en marcha](#puesta-en-marcha)
   - [Backend (Java 21 + Maven)](#backend-java-21--maven)
   - [Frontend (Vue)](#frontend-vue)
7. [Flujo funcional (Fase 2)](#flujo-funcional-fase-2)
8. [Endpoints principales](#endpoints-principales)
9. [Ubicación de recursos](#ubicación-de-recursos)
10. [Personalización y evolución](#personalización-y-evolución)
11. [Pruebas](#pruebas)

---

## Arquitectura

La solución se compone de dos capas claramente separadas:

- **Frontend (Vue.js)**  
  - Muestra un **formulario** con los campos necesarios para rellenar el informe.
  - Envía los datos al backend mediante HTTP (JSON).
  - Gestiona la descarga del informe generado (por ejemplo, PDF).

- **Backend (Java 21)**  
  - Desarrollado usando **Maven** (uso obligatorio).
  - Expone un API REST que:
    - Recibe los datos enviados desde el frontend.
    - Carga la **plantilla Jasper fija** (una vez creada a partir del PDF).
    - Inyecta los datos en el informe Jasper.
    - Devuelve el informe generado (normalmente PDF) al frontend.

---

## Tecnologías

- **Frontend**
  - [Vue.js](https://vuejs.org/) (2 o 3, según se defina en el proyecto).
  - Axios (u otra librería similar) para las peticiones HTTP.
  - Node.js / npm o yarn para gestión de dependencias.

- **Backend**
  - **Java 21**.
  - **Maven** (obligatorio) como gestor de dependencias y construcción.
  - Framework web Java (por ejemplo **Spring Boot**, o el que se defina).
  - Biblioteca **JasperReports**.
  - (Opcional) Lombok, MapStruct, etc.

---

## Estructura del proyecto

Ejemplo de estructura de carpetas:

```text
codex/
├─ backend/
│  ├─ src/main/java/...           # Código Java (controladores, servicios, etc.)
│  ├─ src/main/resources/
│  │  ├─ reports/
│  │  │  └─ plantilla.jrxml       # Plantilla Jasper (a crear en Fase 1)
│  │  └─ application.yml|properties
│  ├─ pom.xml                     # Maven (obligatorio)
│
├─ frontend/
│  ├─ src/
│  │  ├─ components/
│  │  │  └─ ReportForm.vue        # Formulario con los campos del informe
│  │  └─ main.js
│  ├─ package.json
│  └─ vue.config.js               # Proxy hacia backend, etc.
│
├─ docs/
│  └─ informe-referencia.pdf      # PDF original hecho a mano (diseño objetivo)
│
└─ README.md                      # Este archivo
```

> La ruta y el nombre del PDF de referencia pueden variar, pero debe existir un documento similar bajo `docs/`.

---

## Requisitos previos

- **Java JDK 21**.
- **Maven 3+** (obligatorio).
- **Node.js** (versión LTS recomendada).
- **npm** o **yarn**.
- Git (para clonar el repositorio).

---

## Fases del proyecto

### Fase 1 – Creación de la plantilla Jasper

En esta fase, a partir del **PDF de referencia** (`docs/informe-referencia.pdf`):

1. Analizar el diseño del informe:
   - Cabeceras, pies, tablas, estilos, tipografías.
   - Campos variables (datos que vendrán del formulario).
2. Crear la plantilla Jasper (`.jrxml`) usando la herramienta elegida (p.ej. **Jaspersoft Studio**).
3. Definir:
   - Parámetros/fields del informe (`$P{...}`, `$F{...}`, etc.).
   - Cualquier subinforme o dataset si fuera necesario.
4. Guardar la plantilla en:

   ```text
   backend/src/main/resources/reports/plantilla.jrxml
   ```

5. (Opcional) Compilar la plantilla a `.jasper` en tiempo de build o en runtime, según se decida.

> Hasta que esta fase no esté completada, el backend no podrá generar el informe final.

---

### Fase 2 – Generación del informe vía web

Una vez creada la plantilla Jasper:

1. Implementar el **modelo de datos** en el backend (DTO) con los mismos campos que se definieron en la plantilla (`ReportRequest`, por ejemplo).
2. Implementar el **formulario Vue** para recoger dichos campos.
3. Implementar el **servicio Java** que:
   - Carga `plantilla.jrxml` / `plantilla.jasper`.
   - Transforma los datos del DTO al mapa de parámetros de Jasper.
   - Genera el informe (PDF).
4. Implementar el **endpoint REST** que:
   - Recibe el JSON con los datos.
   - Llama al servicio de generación de informe.
   - Devuelve el binario del PDF.
5. Implementar en el frontend la descarga del archivo al recibir la respuesta.

---

## Puesta en marcha

### Backend (Java 21 + Maven)

1. Ir al directorio del backend:

   ```bash
   cd backend
   ```

2. Compilar y descargar dependencias:

   ```bash
   mvn clean install
   ```

3. Ejecutar la aplicación (ejemplo con Spring Boot):

   ```bash
   mvn spring-boot:run
   ```

4. El backend quedará disponible (por defecto) en:

   - `http://localhost:8080`

#### Configuración básica de Maven para Java 21 (ejemplo)

```xml
<properties>
    <java.version>21</java.version>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
</properties>

<dependencies>
    <!-- Spring Boot Web (ejemplo) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JasperReports -->
    <dependency>
        <groupId>net.sf.jasperreports</groupId>
        <artifactId>jasperreports</artifactId>
        <version>REEMPLAZAR_POR_VERSIÓN</version>
    </dependency>

    <!-- Otros (Lombok, etc.) -->
</dependencies>
```

> La versión de JasperReports debe ajustarse a la elegida en el proyecto.

---

### Frontend (Vue)

1. Ir al directorio del frontend:

   ```bash
   cd frontend
   ```

2. Instalar dependencias:

   ```bash
   npm install
   # o
   yarn install
   ```

3. Ejecutar servidor de desarrollo:

   ```bash
   npm run serve
   # o
   yarn serve
   ```

4. El frontend suele estar disponible en:

   - `http://localhost:8081` (o el puerto configurado).

5. Configurar un proxy en `vue.config.js` para redirigir `/api` al backend:

   ```js
   // vue.config.js
   module.exports = {
     devServer: {
       proxy: {
         '/api': {
           target: 'http://localhost:8080',
           changeOrigin: true
         }
       }
     }
   }
   ```

---

## Flujo funcional (Fase 2)

1. El usuario accede a la web y ve el **formulario**.
2. Introduce los datos que corresponden a los campos del informe (por ejemplo: nombre, fecha, importes, etc.).
3. Pulsa en **“Generar informe”**.
4. El frontend envía un `POST` con JSON al endpoint del backend.
5. El backend:
   - Carga la plantilla Jasper fija (`plantilla.jrxml` / `plantilla.jasper`).
   - Rellena los parámetros con los datos del JSON.
   - Genera el PDF.
6. El backend devuelve el PDF.
7. El frontend:
   - Recibe el binario.
   - Lanza la descarga del archivo (por ejemplo, creando un `Blob` y un enlace temporal).
   - Opcionalmente, abre el PDF en una nueva pestaña.

---

## Endpoints principales

> Los nombres definitivos de los endpoints pueden adaptarse según el diseño del backend.

- `POST /api/report`
  - **Descripción:** genera el informe PDF a partir de los datos de entrada.
  - **Request body (JSON):**  
    Objeto con los campos necesarios para el informe Jasper.  
    Ejemplo (simplificado):

    ```json
    {
      "nombreCliente": "Juan Pérez",
      "fechaInforme": "2025-01-01",
      "importeTotal": 1234.56
    }
    ```

  - **Response:**
    - `200 OK` con contenido `application/pdf` (binario del informe).
    - `400 Bad Request` si faltan datos obligatorios o son inválidos.
    - `500 Internal Server Error` si se produce un error generando el informe.

Ejemplo de controlador (esquemático) en Java:

```java
@PostMapping(value = "/api/report", produces = MediaType.APPLICATION_PDF_VALUE)
public ResponseEntity<byte[]> generateReport(@RequestBody ReportRequest request) {
    byte[] pdfBytes = reportService.generateReport(request);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("attachment", "informe.pdf");

    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
}
```

---

## Ubicación de recursos

- **PDF de referencia** (diseño original hecho a mano):
  - `docs/informe-referencia.pdf`

- **Plantilla Jasper (a generar en Fase 1):**
  - `backend/src/main/resources/reports/plantilla.jrxml`
  - (Opcional) versión compilada `.jasper` en la misma carpeta.

---

## Personalización y evolución

- **Cambiar el diseño del informe**
  - Modificar el PDF de referencia para documentar el nuevo diseño.
  - Actualizar la plantilla Jasper (`.jrxml`) usando Jaspersoft Studio (o similar).
  - Alinear los campos del formulario y del DTO con los nuevos parámetros de Jasper.

- **Añadir nuevos campos**
  1. Actualizar la plantilla Jasper (nuevos fields/parameters).
  2. Añadir esos campos en el DTO del backend.
  3. Adaptar el mapeo DTO → parámetros Jasper en el servicio.
  4. Añadir/ajustar campos en el formulario Vue.

- **Soportar otros formatos de salida**
  - Extender el servicio de Jasper para generar, además de PDF:
    - XLS/XLSX
    - HTML
    - DOCX
    - etc.
  - Exponer endpoints adicionales o parámetros para elegir formato.

- **Seguridad**
  - Si se requiere autenticación/autorización, integrar:
    - Spring Security, OAuth2, JWT, o el mecanismo que se decida.
  - Proteger el endpoint `/api/report` para que solo usuarios autenticados puedan generar informes.

---

## Pruebas

### Backend

- **Tests unitarios**
  - Servicio de generación de informes:
    - Mapeo correcto de `ReportRequest` a parámetros Jasper.
    - Manejo de valores nulos u opcionales.
  - Lógica de validación (si la hay).

- **Tests de integración**
  - Endpoint `/api/report`:
    - Envío de JSON válido y verificación de que se devuelve un PDF.
    - Manejo de errores (datos inválidos, plantilla no encontrada, etc.).

### Frontend

- **Tests de componentes**
  - Validaciones del formulario:
    - Campos obligatorios.
    - Formato de fechas.
    - Rango de importes, etc.
  - Comportamiento de los botones (limpiar formulario, enviar, etc.).

- **Tests E2E (end-to-end)**
  - Flujo completo:
    1. Rellenar formulario.
    2. Pulsar “Generar informe”.
    3. Verificar que se descarga un archivo PDF.

---

Este README puede ampliarse con:

- La lista exacta de campos del formulario según la plantilla Jasper real.
- Ejemplos de JSON más detallados.
- Capturas de pantalla del PDF de referencia y de la aplicación web.
