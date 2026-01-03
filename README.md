# 📚 LiterAlura

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)
![Oracle ONE](https://img.shields.io/badge/Oracle%20ONE-Challenge-red.svg)
![Alura Latam](https://img.shields.io/badge/Alura%20Latam-Approved-blueviolet.svg)

**Challenge Backend de Alura Latam & Oracle ONE**

[Características](#-características) • [Tecnologías](#-tecnologías) • [Instalación](#-instalación) • [Uso](#-uso) • [Arquitectura](#-arquitectura)

</div>

---

## 📖 Descripción

**LiterAlura** es una aplicación de catálogo de libros desarrollada como parte del **Oracle ONE Backend Challenge II** de Alura Latam. La aplicación consume la API de **Gutendex** (Project Gutenberg) para buscar y almacenar información de libros y autores en una base de datos PostgreSQL, permitiendo consultas avanzadas y generación de estadísticas.

Este proyecto demuestra dominio en:
- ✅ Consumo de APIs REST externas
- ✅ Persistencia con JPA/Hibernate
- ✅ Relaciones bidireccionales en bases de datos
- ✅ Consultas personalizadas con JPQL
- ✅ Manejo de datos con Streams de Java
- ✅ Aplicación de consola interactiva

---

## ✨ Características

### 🔍 Funcionalidades Principales

1. **Búsqueda de Libros**
   - Consulta en tiempo real a la API de Gutendex
   - Almacenamiento automático en base de datos
   - Prevención de duplicados

2. **Gestión de Autores**
   - Registro automático de autores
   - Relación bidireccional con libros
   - Búsqueda por nombre

3. **Consultas Avanzadas**
   - Listar libros por idioma
   - Filtrar autores vivos en un año específico
   - Top 10 libros más descargados
   - Generación de estadísticas de descargas

4. **Estadísticas**
   - Media de descargas
   - Máximo y mínimo de descargas
   - Identificación de libros más y menos populares

---

## 🛠️ Tecnologías

### Framework y Lenguaje
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.5.9** - Framework base
- **Spring Data JPA** - Capa de persistencia
- **Spring Web** - Para consumo de API externa

### Base de Datos
- **PostgreSQL** - Base de datos relacional
- **Hibernate** - ORM para mapeo objeto-relacional

### Procesamiento de Datos
- **Jackson Databind 2.16.0** - Deserialización de JSON
- **Java Streams** - Procesamiento funcional de datos

### Herramientas
- **Maven** - Gestor de dependencias
- **Spring Boot DevTools** - Desarrollo rápido

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue el patrón **Package by Layer**:

```
com.example.literalura
├── model/              # Entidades JPA, Records y Enums
│   ├── Autor.java
│   ├── Libro.java
│   ├── DatosAutor.java
│   ├── DatosLibros.java
│   ├── DatosResults.java
│   └── Idiomas.java
├── repository/         # Repositorios JPA
│   ├── AutoresRepository.java
│   └── LibrosRepository.java
├── service/           # Servicios de negocio
│   ├── ConsumoAPI.java
│   ├── ConvierteDatos.java
│   └── IConvertirDatos.java
└── principal/         # Lógica de la aplicación
    └── Principal.java
```

---

## 📦 Estructura Detallada

### 📁 Model - Capa de Modelo

#### **Entidades JPA** 🗄️

**`Autor`**
- Entidad principal para autores
- Relación **OneToMany** con Libro
- Atributos: nombre (único), nacimiento, fallecimiento
- Método `setLibros()` que actualiza la FK manteniendo coherencia

**`Libro`**
- Entidad para libros
- Relación **ManyToOne** con Autor
- Atributos: título (único), idioma (Enum), descargas

#### **Records (DTOs)** 🔖

- **`DatosAutor`**: Mapea información de autores desde la API
- **`DatosLibros`**: Mapea información de libros desde la API
- **`DatosResults`**: Mapea la respuesta completa de la API (lista de libros)

#### **Enum** 🌍

**`Idiomas`**
- Gestiona idiomas de forma segura
- Valores: ENGLISH, SPANISH, FRENCH, PORTUGUESE, ITALIAN
- Método `fromAPI()` para convertir código de idioma → Enum

---

### 📁 Repository - Capa de Persistencia

**`AutoresRepository`**
```java
// Derived Query
Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutores);

// JPQL - Autores vivos en un año específico
@Query("SELECT a FROM Autor a WHERE a.nacimiento <= :año 
        AND (a.fallecimiento IS NULL OR a.fallecimiento >= :año)")
List<Autor> autorPorNacimiento(Integer año);
```

**`LibrosRepository`**
```java
// Derived Queries
Optional<Libro> findByTituloContainsIgnoreCase(String tituloLibro);
List<Libro> findByIdiomas(Idiomas idiomas);
```

---

### 📁 Service - Capa de Servicio

**`ConsumoAPI`**
- Realiza peticiones HTTP a la API de Gutendex
- Usa `HttpClient` de Java 11+
- Retorna el JSON como String

**`ConvierteDatos`**
- Implementa `IConvertirDatos`
- Deserialización genérica con Jackson
- Manejo de excepciones `JsonProcessingException`

---

### 📁 Principal - Lógica de Aplicación

**`Principal.java`**

Menú interactivo de consola con 8 opciones:

1. ✅ **Buscar libro por título**
   - Consulta API de Gutendex
   - Verifica duplicados
   - Crea/asocia autor automáticamente
   - Guarda en base de datos

2. ✅ **Listar libros registrados**
   - Muestra todos los libros en BD

3. ✅ **Listar autores registrados**
   - Muestra todos los autores con sus libros

4. ✅ **Autores vivos en año específico**
   - Usa consulta JPQL personalizada

5. ✅ **Libros por idioma**
   - Filtra usando Enum de idiomas

6. ✅ **Estadísticas de descargas**
   - Usa `DoubleSummaryStatistics`
   - Calcula media, máximo, mínimo

7. ✅ **Top 10 más descargados**
   - Ordenamiento con Streams
   - Límite de 10 resultados

8. ✅ **Buscar autor por nombre**
   - Búsqueda flexible (contiene, ignora mayúsculas)

---

## ⚙️ Configuración

### Variables de Entorno

El proyecto utiliza variables de entorno para proteger datos sensibles:

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

### Configuración Requerida

```bash
# Base de datos PostgreSQL
DB_HOST=localhost:5432
DB_NAME=literalura
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

### Configuración de Hibernate

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🚀 Instalación

### Prerrequisitos

- ☕ **Java 17** o superior
- 📦 **Maven 3.6+**
- 🐘 **PostgreSQL 12+**
- 🌐 Conexión a Internet (para API de Gutendex)

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/literalura.git
   cd literalura
   ```

2. **Crear base de datos PostgreSQL**
   ```sql
   CREATE DATABASE literalura;
   ```

3. **Configurar variables de entorno**

   **Linux/Mac:**
   ```bash
   export DB_HOST=localhost:5432
   export DB_NAME=literalura
   export DB_USER=tu_usuario
   export DB_PASSWORD=tu_contraseña
   ```

   **Windows (CMD):**
   ```cmd
   set DB_HOST=localhost:5432
   set DB_NAME=literalura
   set DB_USER=tu_usuario
   set DB_PASSWORD=tu_contraseña
   ```

   **Windows (PowerShell):**
   ```powershell
   $env:DB_HOST="localhost:5432"
   $env:DB_NAME="literalura"
   $env:DB_USER="tu_usuario"
   $env:DB_PASSWORD="tu_contraseña"
   ```

4. **Instalar dependencias**
   ```bash
   ./mvnw clean install
   ```
   O en Windows:
   ```cmd
   mvnw.cmd clean install
   ```

5. **Ejecutar la aplicación**
   ```bash
   ./mvnw spring-boot:run
   ```
   O en Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

---

## 💻 Uso

### Interfaz de Consola

Al ejecutar la aplicación, verás el siguiente menú:

```
1 - buscar libro por título
2 - listar libros registrados en la base de datos
3 - listar autores registrados
4 - listar autores vivos en un determinado año
5 - listar libros por idioma
6 - genera estadísticas de las descargas de todos los libros
7 - top 10 libros más descargados
8 - buscar autor por nombre

0 - Salir
```

### Ejemplos de Uso

#### 📖 Buscar un Libro

```
Selecciona: 1
Ingrese el nombre del libro que desea buscar
> don quijote

=== Libro guardado exitosamente ===
----- LIBRO -----
Título: Don Quijote
Autor: Cervantes Saavedra, Miguel de
Idioma: SPANISH
Descargas: 15234
-----------------
```

#### 👤 Listar Autores

```
Selecciona: 3

=================
Autor: Cervantes Saavedra, Miguel de
Nacimiento: 1547
Fallecimiento: 1616
Libros: [Don Quijote]
=================
```

#### 📊 Estadísticas

```
Selecciona: 6

---------- Estadísticas ----------
Media de descargas: 5234.50
Máxima cantidad de descargas: 15234.00 [Don Quijote]
Mínima cantidad de descargas: 234.00 [Libro menos conocido]
Cantidad de libros en la base de datos: 25
----------------------------------
```

#### 🌍 Libros por Idioma

```
Selecciona: 5

Escriba el idioma que le interesa leer:
1) en (Inglés)
2) es (Español)
3) fr (Francés)
4) pt (Portugués)
5) it (Italiano)

> es

---- Resultados ----
[Lista de libros en español]
```

---

## 🗃️ Modelo de Base de Datos

### Diagrama ER

```
┌─────────────────┐       ┌─────────────────┐
│     AUTORES     │       │     LIBROS      │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │◄──┐   │ id (PK)         │
│ nombre (UNIQUE) │   └───│ autor_id (FK)   │
│ nacimiento      │       │ titulo (UNIQUE) │
│ fallecimiento   │       │ idiomas (ENUM)  │
└─────────────────┘       │ descargas       │
                          └─────────────────┘
        1                          N
```

### Relaciones

- **Autor → Libro**: OneToMany (Un autor puede tener varios libros)
- **Libro → Autor**: ManyToOne (Cada libro tiene un autor)

---

## 🔍 Características Técnicas Destacadas

### 1. Prevención de Duplicados

```java
// Verifica si el libro ya existe antes de guardarlo
Optional<Libro> libroExistente = 
    librosRepository.findByTituloContainsIgnoreCase(datosLibro.titulo());

if (libroExistente.isPresent()) {
    System.out.println("El libro ya existe en la base de datos");
    return;
}
```

### 2. Gestión Inteligente de Autores

```java
// Busca o crea el autor según sea necesario
Optional<Autor> autorExistente = 
    autoresRepository.findByNombreContainsIgnoreCase(datosAutor.nombre());

Autor autor;
if (autorExistente.isPresent()) {
    autor = autorExistente.get(); // Reutiliza el autor existente
} else {
    autor = new Autor(datosAutor); // Crea nuevo autor
    autor = autoresRepository.save(autor);
}
```

### 3. Consultas JPQL Avanzadas

```java
// Encuentra autores vivos en un año específico
@Query("SELECT a FROM Autor a WHERE a.nacimiento <= :año 
        AND (a.fallecimiento IS NULL OR a.fallecimiento >= :año)")
List<Autor> autorPorNacimiento(Integer año);
```

### 4. Estadísticas con Streams

```java
DoubleSummaryStatistics stats = libros.stream()
    .filter(book -> book.getDescargas() > 0)
    .mapToDouble(Libro::getDescargas)
    .summaryStatistics();

System.out.printf("Media: %.2f\n", stats.getAverage());
System.out.printf("Máximo: %.2f\n", stats.getMax());
System.out.printf("Mínimo: %.2f\n", stats.getMin());
```

### 5. Relaciones Bidireccionales Coherentes

```java
public void setLibros(List<Libro> libros) {
    libros.forEach(book -> book.setAutor(this)); // Actualiza FK
    this.libros = libros; // Sincroniza en memoria
}
```

---

## 🎓 Conceptos Aplicados

Este proyecto demuestra comprensión de:

| Concepto | Implementación |
|----------|----------------|
| **APIs REST** | Consumo de Gutendex API con HttpClient |
| **JPA/Hibernate** | Mapeo objeto-relacional con anotaciones |
| **Relaciones Bidireccionales** | OneToMany / ManyToOne entre Autor y Libro |
| **JPQL** | Consultas personalizadas para filtros complejos |
| **Derived Queries** | Métodos de repositorio generados automáticamente |
| **Enums** | Gestión segura de idiomas |
| **DTOs (Records)** | Mapeo limpio de respuestas de API |
| **Streams de Java** | Procesamiento funcional y estadísticas |
| **Variables de Entorno** | Seguridad de credenciales |
| **Validación de Datos** | Prevención de duplicados y validaciones |

---

## 🌟 Desafíos Resueltos

### 1. Relaciones Bidireccionales
**Problema**: Mantener coherencia entre `Autor` y sus `Libros`

**Solución**: Método `setLibros()` que actualiza automáticamente la FK del lado propietario

### 2. Duplicados en Base de Datos
**Problema**: Intentar guardar el mismo libro/autor múltiples veces

**Solución**: Consultas `findBy...IgnoreCase` antes de cada inserción

### 3. Autores sin Fecha de Fallecimiento
**Problema**: Algunos autores aún viven (campo null)

**Solución**: Consulta JPQL con condición `IS NULL OR fallecimiento >= año`

### 4. API con Respuestas Anidadas
**Problema**: JSON complejo con múltiples niveles

**Solución**: Records anidados (`DatosResults` → `DatosLibros` → `DatosAutor`)

---

## 📊 Flujo de Datos

```
┌─────────────┐
│   Usuario   │
└──────┬──────┘
       │ 1. Busca libro
       ▼
┌─────────────┐
│  Principal  │
└──────┬──────┘
       │ 2. Consulta API
       ▼
┌─────────────┐
│ ConsumoAPI  │───────► Gutendex API
└──────┬──────┘
       │ 3. JSON response
       ▼
┌──────────────┐
│ConvierteDatos│
└──────┬───────┘
       │ 4. Objeto Java
       ▼
┌─────────────┐
│ Repository  │
└──────┬──────┘
       │ 5. Persistencia
       ▼
┌─────────────┐
│ PostgreSQL  │
└─────────────┘
```

---

## 🐛 Manejo de Errores

La aplicación maneja los siguientes casos:

- ✅ Libro no encontrado en la API
- ✅ Libro ya existe en la base de datos
- ✅ Autor sin información completa
- ✅ Idioma no válido
- ✅ Año fuera de rango
- ✅ Base de datos vacía
- ✅ Errores de conexión a la API

---

## 🚧 Mejoras Futuras

- [ ] Implementar paginación para listados largos
- [ ] Agregar búsqueda por rango de descargas
- [ ] Integrar Spring Security para autenticación
- [ ] Crear API REST para exponer funcionalidades
- [ ] Implementar caché con Redis
- [ ] Agregar tests unitarios con JUnit y Mockito
- [ ] Dockerizar la aplicación
- [ ] Implementar frontend con React/Angular
- [ ] Agregar filtros combinados (idioma + año + descargas)
- [ ] Sistema de favoritos por usuario

---

## 📝 Notas del Challenge

Este proyecto fue desarrollado como parte del **Oracle ONE Backend Challenge II** de Alura Latam, enfocándose en:

- ✅ Consumo de APIs REST externas
- ✅ Persistencia con JPA/Hibernate
- ✅ Consultas personalizadas
- ✅ Manejo de relaciones entre entidades
- ✅ Aplicación de buenas prácticas

**Objetivos cumplidos:**
- [x] Búsqueda de libros en API externa
- [x] Almacenamiento en base de datos
- [x] Listado de libros y autores
- [x] Consultas por idioma
- [x] Autores vivos en año específico
- [x] Generación de estadísticas
- [x] Top 10 libros más descargados
- [x] Búsqueda de autores

---

## 🏆 Badge del Challenge

<div align="center">

![Oracle ONE](https://img.shields.io/badge/Oracle%20ONE-Challenge%20Completed-FF0000?style=for-the-badge&logo=oracle)
![Alura](https://img.shields.io/badge/Alura%20Latam-Backend%20Developer-0055FF?style=for-the-badge)

</div>

---

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos como parte del programa Oracle ONE de Alura Latam.

---

## 👤 Autor

Desarrollado con 💙 como parte de mi formación en desarrollo backend con **Java** y **Spring Boot** en el programa **Oracle ONE** de **Alura Latam**.

---

## 🙏 Agradecimientos

- **[Alura Latam](https://www.aluracursos.com/)** - Por la formación de calidad
- **[Oracle](https://www.oracle.com/)** - Por el programa Oracle ONE
- **[Gutendex API](https://gutendex.com/)** - Por proporcionar acceso a los libros del Project Gutenberg
- **Spring Community** - Por la excelente documentación

---

## 🔗 Enlaces Útiles

- 📚 [Documentación de Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- 📖 [API de Gutendex](https://gutendex.com/)
- 🎓 [Oracle ONE Program](https://www.oracle.com/education/oracle-next-education/)
- 💻 [Alura Latam](https://www.aluracursos.com/)

---

<div align="center">

⭐ Si este proyecto te fue útil, no olvides darle una estrella en GitHub

**#OracleONE #AluraLatam #Backend #Java #SpringBoot**

</div>
