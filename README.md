# JavaPrueba

# 🧠 JavaPrueba

Official repository for **JavaPrueba**, a practice application developed in **Java**.  
This project aims to strengthen knowledge in object-oriented programming, package management, layered architecture, and the use of modern tools such as **Maven**.

---

## 📋 Table of Contents

- [Description](#description)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage / Examples](#usage--examples)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)
- [Acknowledgments](#acknowledgments)

---

## 🧐 Description

**JavaPrueba** is an academic and practice-oriented project developed in **Java**, designed to understand the structure of a modular project using **Maven**, and following the **MVC** (Model–View–Controller) pattern.

Its main goals include:

- Reinforcing the use of classes, objects, and encapsulation.
- Learning how to organize a project using Maven.
- Applying good programming practices.
- Practicing error and exception handling.
- Laying the foundation for more complex projects with database integration.

---

## ✨ Features

- Project organized with **Maven**.
- Package separation according to the **MVC** pattern.
- Practical example of flow control and data validation.
- Can be executed directly from console or IDE.
- Clean, well-commented, and easy-to-understand code.

---

## 🛠 Technologies Used

- **Language:** Java 17 or higher
- **Dependency Manager:** Maven
- **Recommended IDEs:** IntelliJ IDEA / VSCode / Eclipse
- **Supported Operating Systems:** Ubuntu, Windows, or macOS

---

## 📋 Prerequisites

Before running the project, make sure you have the following installed:

- ☕ **Java JDK 17+**
- 🧩 **Apache Maven** (if you don’t have it yet, install it using `sudo apt install maven` on Ubuntu)
- 💻 An IDE or code editor compatible with Maven projects (IntelliJ IDEA, Eclipse, etc.)

You can verify your installed versions with:




```bash
java -version
mvn -version

#For clone de project
git clone https://github.com/IsabellaPulgarin/JavaPrueba.git

cd JavaPrueba

mvn clean install
```

```
JavaPrueba/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.bookNova.app/
│   │   │       ├── config/       # Configuración general del proyecto (DB, constantes, etc.)
│   │   │       ├── controller/   # Controladores: gestionan la comunicación entre vista y servicios
│   │   │       ├── dao/          # Acceso a datos: simula o conecta con la base de datos
│   │   │       ├── domain/       # Clases de modelo o entidades (User, Course, etc.)
│   │   │       ├── errors/       # Excepciones personalizadas y manejo de errores
│   │   │       ├── services/     # Lógica de negocio y validaciones
│   │   │       ├── util/         # Funciones auxiliares, validadores o utilidades generales
│   │   │       ├── view/         # Interfaz de usuario o consola
│   │   │       └── Main.java     # Punto de entrada de la aplicación
│   │   └── resources/            # Archivos de configuración, propiedades o plantillas
│   └── test/
│       └── java/                 # Clases de prueba (JUnit u otras)
├── pom.xml                       # Configuración de Maven
└── README.md                     # Documentación del proyecto
```

### Author: Isabella Pulgarin Muñoz

# Clan: Berneslee

# Correo: isabellapul7@gmail.com

# Documento: 1023525693
