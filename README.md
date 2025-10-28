This project is a full-stack task management system built with a Spring Boot backend Which uses Postgre Sql and an Android Studio frontend.
It provides secure user authentication, project management, and team collaboration features.

Frontend Rep : https://github.com/Priynshu2341/realtimetaskmanagementfrontend

🧠 Tech Stack

Backend: Spring Boot, Spring Security, JPA, Redis, JWT
Database: MySQL, PostgreSQL, MongoDB, SQLiTe (android room)
Frontend: Android Studio (Kotlin)
Tools: Postman, Git, Gradle, IntelliJ IDEA
Language : Kotlin, Java

🚀 Key Features

🔐 JWT Authentication & Authorization:
Secure login and role-based access control using access and refresh tokens.
Users receive a default USER role upon registration, and admins can promote users when needed.

🧱 Role-Based Project Management:
Only admins can create new projects and assign members using their usernames.
Each project contains a list of tasks that admins can fully manage.

✅ Task Control & Comments:
Tasks include properties such as priority and status, which can be updated.
Every task supports a comment section, allowing all members to collaborate.

⚙️ Pagination Support:
All major entities (Projects, Tasks, Comments) support both normal and paginated data retrieval for flexible client performance optimization.

⚡ Redis Caching:
Integrated Redis caching to reduce database load and deliver faster response times.

🔁 JWT Refresh Token Flow:
When an access token expires, a new one is automatically generated after validating the refresh token.

🧩  DTOS 
All Response and Request uses DTO For better response and requests.

💡 Highlights

Clean RESTful API design integrated with Android frontend.

Secure, scalable, and optimized backend architecture.

Designed for real-world use cases with caching, pagination, and role-based control.

It Follows Clean Architecture 
