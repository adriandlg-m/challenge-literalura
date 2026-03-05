# 📚 Challenge Literalura

¡Bienvenido al Challenge **Literalura**! Este proyecto es un catálogo de libros interactivo desarrollado como parte del programa **Oracle Next Education (ONE)**. La aplicación consume datos de la API de **Gutendex**, permitiendo buscar libros, persistirlos en una base de datos relacional y realizar consultas avanzadas mediante una interfaz de consola.



## 🛠️ Funcionalidades
El sistema cuenta con un menú interactivo que permite realizar las siguientes acciones:

1.  **Buscar libro por título:** Obtiene información detallada desde la API y la guarda en la base de datos MySQL, asociando automáticamente al autor y evitando registros duplicados.
2.  **Listar libros registrados:** Muestra todos los títulos almacenados en la base de datos con sus respectivos autores, idiomas y número de descargas.
3.  **Listar autores registrados:** Lista los autores almacenados y detalla los libros de cada uno que están presentes en la biblioteca local.
4.  **Listar autores vivos en un año determinado:** Consulta autores cuya vida coincida con el año ingresado por el usuario (usando consultas JPQL personalizadas).
5.  **Listar libros por idioma:** Filtra la biblioteca local según las siglas de idioma (es, en, fr, pt).
6.  **Mostrar estadisticas de los libros registrados.**
## 🚀 Tecnologías Utilizadas
* **Java 21:** Lenguaje de programación principal.
* **Spring Boot 4.0.x:** Framework para la gestión de dependencias y configuración.
* **Spring Data JPA:** Para la persistencia de datos y mapeo objeto-relacional (ORM).
* **MySQL:** Base de datos relacional para el almacenamiento persistente.
* **Jackson:** Para el procesamiento de datos JSON de la API.
* **API Gutendex:** Fuente de datos externa para la búsqueda de libros.

## 📋 Configuración y Ejecución

### 1. Base de Datos
Es necesario crear la base de datos en tu entorno de MySQL antes de iniciar la aplicación:
```sql
CREATE DATABASE literalura_db;
```
### 2. Configuración del Entorno
En el archivo `src/main/resources/application.properties`. Asegúrate de configurar tus credenciales de acceso a MySQL:
```java
# Configuración de la Base de Datos (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/literalura_db
spring.datasource.username=Tu usuario
spring.datasource.password=Tu contraseña
```
### 3. Ejecución

- Clona este repositorio en tu máquina local.
- Importa el proyecto en IntelliJ IDEA como un proyecto Maven.
- Ejecuta la clase `LiteraluraApplication`.
- Utiliza la consola para navegar por las 6 opciones principales del menú.

### 👤 Desarrollado por:
<br>

**Adrian Delgado** <br>*Participante del programa Oracle Next Education (ONE) en alianza con Alura Latam.*

<br>

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/adriandlg-m)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/TU_USUARIO_LINKEDIN)