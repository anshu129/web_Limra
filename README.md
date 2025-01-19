# Limra Infracon Website

This repository contains the source code for the Limra Infracon website. The website is built using the Maven Spring Boot framework and serves as the online platform for Limra Infracon, a company specializing in the sale and purchase of properties. It provides users with a seamless interface to browse, manage, and inquire about properties.

# Important Notice

Due to unforeseen circumstances, Limra Infracon has been acquired, and the website is no longer operational. This repository is now archived and will no longer receive updates or maintenance.

## Features

1. **Property Listings**: Display a wide variety of properties for sale and purchase.
2. **Advanced Search**: Filter properties based on location, price, type, and other criteria.
3. **User Authentication**: Secure login and registration functionality for users.
4. **Admin Dashboard**: Manage property listings, inquiries, and user accounts.
5. **Contact Forms**: Enable users to send inquiries about properties.
6. **Responsive Design**: Optimized for viewing on different devices.

## Technologies Used

- **Backend**: Spring Boot (Java)
- **Frontend**: HTML, CSS, JavaScript
- **Database**: MySQL
- **Build Tool**: Maven
- **Server**: Embedded Tomcat

## Prerequisites

To run the project locally, ensure you have the following installed:

1. Java Development Kit (JDK) 11 or later
2. Apache Maven
3. MySQL Server
4. An IDE such as IntelliJ IDEA, Eclipse, or Visual Studio Code (optional but recommended)

## Getting Started

Follow these steps to set up the project:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/limra-infracon.git
   cd limra-infracon
   ```

2. Configure the database:
   - Create a MySQL database named `limra_infracon`.
   - Update the database connection details in `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/limra_infracon
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```

3. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. Access the application:
   Open a web browser and navigate to `http://localhost:8080`.

## Project Structure

- `src/main/java` - Contains the Java source code for the application.
  - `controller` - Handles HTTP requests and responses.
  - `service` - Contains the business logic.
  - `repository` - Interfaces for database access.
  - `model` - Entity classes representing the database tables.

- `src/main/resources`
  - `templates` - HTML files for the frontend.
  - `application.properties` - Configuration file for the application.

## Deployment

The application can be deployed to any server that supports Java, such as AWS, Azure, or DigitalOcean. For production, it is recommended to use a reverse proxy (e.g., Nginx) and configure SSL for secure communication.


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For inquiries or support, please contact us:

- **Email**: 129deepanshusharma@gmail.com
- **Phone**: +91-9289824060


