Leave Management System (Backend)

The Leave Management System (Backend) is a microservice-based application built using Spring Boot. It provides all the necessary API endpoints to handle leave requests, approval workflows, and employee leave balance management.

Features

Leave Request Management: Employees can submit leave requests which are then processed by the system.

Leave Approval Workflow: Admins or managers can approve or reject leave requests.

Microservices Architecture: Built using a modular structure with different services like authentication, email notification, etc.

API Gateway: A unified entry point to interact with various services.

Authentication: Secured using authentication services like JWT or OAuth.

Database: Stores employee leave data securely, supporting scalability and efficient data management.

Services Included

AdminService: Handles administrative tasks and controls leave management actions.

ApiGateway: The entry point for client requests, routing them to the appropriate service.

AuthService: Manages user authentication and authorization.

EmailService: Sends email notifications for leave approvals or rejections.

EmployeeService: Manages employee-related operations, such as leave requests and leave balances.

ServiceRegistry: Manages the service discovery and registration in the microservices architecture.

