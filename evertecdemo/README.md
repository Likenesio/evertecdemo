
# API REST PARA EVERTEC 

Un projecto que muestra el desarrollo de una API REST hecha con Java 23, Maven 3.3.5 y Mysql.



## Variables de entorno

| Parameter  | Description                |
| :--------  | :------------------------- |
| `SPRING_APPLICATION_NAME`| Nombre de la aplicación|
| `SPRING_DATASOURCE_URL`| URL de la base de datos|
| `SPRING_DATASOURCE_USERNAME`  | Nombre de usuario de la base de datos|
| `SPRING_DATASOURCE_PASSWORD` | contraseña de la base de datos|



## Correr proyecto de forma local.

1- Descargar JDK 23 [aquí](https://www.oracle.com/cl/java/technologies/downloads/) e instalar

2- Descargar Mysql [aquí](https://www.mysql.com/downloads/) e instalar.

3- Crear una base de datos (No crear tablas).

4- Para entorno de pruebas, instalar Postman o Insomnia

5- Clonar el proyecto

```bash
  git clone https://github.com/Likenesio/evertecdemo
```

6- Ir al directorio del proyecto

```bash
  cd evertecdemo
```
7- Cambiar valores a las variable de entorno

8- Iniciar sesión en tu BD

8- Iniciar Server

```bash
  mvnw.cmd spring-boot:run
```


## Referencias de la API (Testeado en Postman)

#### Registrar un Cliente

```http
  POST http://localhost:8080/api/clientes/registro
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `nombre` | `string` | **Required**. nombre del cliente|
| `apellido` | `string` | **Required**. apellido del cliente|
| `email` | `string` | **Required**. email del cliente|
| `password` | `string` | **Required**. password del cliente|
| `telefono` | `string` | **Required**. telefono del cliente|
| `direccion` | `string` | **Required**. direccion del cliente|
| `comuna` | `string` | **Required**. comuna del cliente|

#### Listar Clientes

```http
  GET http://localhost:8080/api/clientes/listar
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **No Required**. Id de cliente |

#### Autenticación de Cliente

```http
  POST http://localhost:8080/api/clientes/login
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` | **Required**. email de cliente |
| `password`      | `string` | **Required**. password de cliente |
