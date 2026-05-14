# Hospital Management System

A small **Java console application** for managing patients, listing doctors, and booking appointments. It uses **JDBC** with **MySQL** and **Maven** for dependencies.

## Features

- Register patients (name, age, gender)
- List all patients and doctors
- Book an appointment with availability checks (one appointment per doctor per day)
- Database schema and sample doctor rows included for a quick demo

## Tech stack

- Java 17
- Maven
- MySQL 8.x
- MySQL Connector/J (via Maven)

## Prerequisites

- [JDK 17](https://adoptium.net/) or newer
- [Maven](https://maven.apache.org/install.html)
- [MySQL](https://dev.mysql.com/downloads/mysql/) server running locally (or any host you point `HOSPITAL_DB_URL` to)

## Database setup

1. Create the schema and seed sample doctors (from the project root):

   ```bash
   mysql -u root -p < database/schema.sql
   ```

2. Adjust MySQL user permissions if you use a non-root account.

## Configuration (environment variables)

The app does **not** store passwords in source control. Set these before running:

| Variable | Required | Description |
|----------|----------|-------------|
| `HOSPITAL_DB_PASSWORD` | **Yes** | MySQL password for the configured user |
| `HOSPITAL_DB_URL` | No | JDBC URL (default: `jdbc:mysql://localhost:3306/hospital`) |
| `HOSPITAL_DB_USER` | No | MySQL user (default: `root`) |

**Windows (PowerShell, current session):**

```powershell
$env:HOSPITAL_DB_PASSWORD = "your-mysql-password"
```

**macOS / Linux:**

```bash
export HOSPITAL_DB_PASSWORD="your-mysql-password"
```

## Run

From the project directory:

```bash
mvn compile exec:java
```

The MySQL JDBC driver is pulled in by Maven; you do not need to download a JAR manually.

## Project layout

- `src/main/java/hospital_management_system/` — application source
- `database/schema.sql` — MySQL DDL and sample data
- `pom.xml` — Maven build and `mysql-connector-j` dependency

## Ideas for your resume / portfolio

You can adapt these bullets to your CV or LinkedIn:

- Built a console-based hospital management prototype using **Java**, **JDBC**, and **MySQL** with **prepared statements** for safer queries.
- Implemented appointment booking with **server-side availability** rules (doctor/date uniqueness in SQL and application logic).
- Used **Maven** for dependency management and **environment-based configuration** so secrets are not committed to version control.

## License

This project is provided as sample portfolio work; add a `LICENSE` file if you want a formal open-source license.
