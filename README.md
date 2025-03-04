# Energy management system

Web-based system designed to monitor and analyze energy consumption.

It features:

- **Microservices Architecture** with separate services for **users, devices, monitoring and communication**.
- **RabbitMQ** for message queuing and data processing from smart metering devices.
- **MySQL Database** to store energy measurements.
- **Smart Meter Simulator** that:
  - Reads energy consumption data from a CSV file.
  - Sends data to RabbitMQ every 10 seconds.
- **WebSocket Notifications** to alert users of excessive energy usage.
- **Frontend Web App (Angular)** to visualize energy consumption data with interactive charts.
- **Dockerized Deployment** with containers for:
  - Microservices
  - Database
  - Frontend


