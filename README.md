# Login/Signup Demo

A full-stack demonstration application showcasing user authentication with login and signup functionality built with **Spring Boot** and **Next.js**, using **SQLite** for persistence.

## 📋 Table of Contents

- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Features](#features)
- [Security Notes](#security-notes)

**How to use:** Click any link above to jump to that section. Works in GitHub, GitLab, and most markdown viewers.

## Overview

This project demonstrates a complete user authentication flow with:

- User registration (signup) with email and password
- User login with JWT token-based authentication
- Secure password handling
- Protected API endpoints

## Technology Stack

### Backend

- **Java 21** - Programming language
- **Spring Boot 4.0.3** - Framework
- **Spring Data JPA** - ORM and database abstraction
- **SQLite** - Lightweight relational database
- **JWT (JSON Web Tokens)** - For authentication
- **Maven** - Dependency management and build tool

### Frontend

- **Next.js 16.1.6** - React framework with SSR
- **React 19.2.3** - UI library
- **TypeScript** - Type safety
- **Tailwind CSS** - Utility-first CSS framework
- **Shadcn UI** - Component library
- **Next Themes** - Dark mode support
- **Sonner** - Toast notifications

## Project Structure

```
login_signup/
├── README.md (this file)
├── backend/                    # Spring Boot application
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/demo/
│   │   │   │   ├── DemoApplication.java
│   │   │   │   ├── config/            # Spring configurations
│   │   │   │   │   ├── WebConfig.java
│   │   │   │   │   └── WebSecurityConfig.java
│   │   │   │   ├── exception/         # Custom exceptions
│   │   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   │   ├── ErrorResponse.java
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   └── ResourceNotFoundException.java
│   │   │   │   ├── security/          # Authentication & JWT
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── AuthEntryPointJwt.java
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   ├── AuthTokenFilter.java
│   │   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   │   ├── JwtUtil.java
│   │   │   │   │   └── UserPrincipal.java
│   │   │   │   └── user/              # User domain
│   │   │   │       ├── User.java
│   │   │   │       ├── UserDto.java
│   │   │   │       ├── UserRepository.java
│   │   │   │       └── UserService.java
│   │   │   └── resources/
│   │   │       └── application.yaml   # Configuration file
│   │   └── test/
│   └── target/                # Build output
├── frontend/                   # Next.js application
│   ├── package.json
│   ├── tsconfig.json
│   ├── next.config.ts
│   ├── tailwind.config.js
│   ├── app/                   # App directory (Next.js 13+)
│   │   ├── globals.css
│   │   ├── layout.tsx
│   │   └── page.tsx
│   ├── components/            # React components
│   │   ├── (landing)/         # Landing page components
│   │   │   ├── login-badge.tsx
│   │   │   ├── login-dialog.tsx
│   │   │   ├── logout-button.tsx
│   │   │   └── signup-dialog.tsx
│   │   ├── ui/                # Shadcn UI components
│   │   ├── theme-provider.tsx
│   │   └── mode-toggle.tsx
│   ├── context/               # React Context
│   │   └── AuthContext.tsx
│   ├── lib/
│   │   └── utils.ts
│   └── public/
```

## Prerequisites

Ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6.0** or higher (for building the backend)
- **Node.js 18.17** or higher
- **pnpm** (or npm/yarn)
- **Git** (optional, for version control)

## Installation & Setup

### Backend Setup

1. **Navigate to the backend directory:**

   ```bash
   cd backend
   ```

2. **Set up the database and build the project:**

   ```bash
   ./mvnw clean install
   ```

   (On Windows, use `mvnw.cmd` instead of `./mvnw`)

3. **Configuration:**
   - The SQLite database file (`user.db`) will be created automatically in the backend root directory
   - Default configuration is in `src/main/resources/application.yaml`
   - **Important:** For production, update the JWT secret key in `application.yaml`:
     ```yaml
     jwt:
       secret: your-very-secure-secret-key-at-least-256-bits-long
     ```

### Frontend Setup

1. **Navigate to the frontend directory:**

   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   pnpm install
   ```

## Running the Application

### Start the Backend

```bash
cd backend
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`

### Start the Frontend

In a new terminal:

```bash
cd frontend
pnpm run dev
```

The frontend will start on `http://localhost:3000`

Visit `http://localhost:3000` in your browser to access the application.

## API Endpoints

All endpoints are prefixed with `http://localhost:8080/api/auth`

### Authentication Endpoints

#### Signup

- **POST** `/signup`
- Request body:
  ```json
  {
    "email": "user@example.com",
    "password": "securePassword123"
  }
  ```
- Response:
  - Status: `201 Created`
  - Headers: `Set-Cookie` with JWT token in httpOnly cookie (24 hour expiration)
  - Body: Empty
  - ⚠️ **Demo Note**: In production, should return a DTO with user data or similar (e.g., `AuthResponse` containing user ID, email, success message)

#### Login

- **POST** `/login`
- Request body:
  ```json
  {
    "email": "user@example.com",
    "password": "securePassword123"
  }
  ```
- Response:
  - Status: `200 OK`
  - Headers: `Set-Cookie` with JWT token in httpOnly cookie (24 hour expiration)
  - Body: Empty
  - ⚠️ **Demo Note**: In production, should return a DTO with user data or similar (e.g., `AuthResponse` containing user ID, email, token details)

#### Logout

- **POST** `/logout`
- Response:
  - Status: `200 OK`
  - Headers: `Set-Cookie` with expired token (maxAge=0)
  - Body: `"Logged out successfully"`

#### Get Current User

- **GET** `/me`
- Headers: Requires valid JWT cookie (or Authorization header)
- Response:
  - Status: `200 OK`
  - Body: Empty
  - ⚠️ **Demo Note**: In production, should return a DTO with user details or similar (e.g., `UserResponse` with user ID, email, roles)

## Features

- ✅ **User Registration** - Create new user accounts with email and password
- ✅ **User Login** - Authenticate with JWT tokens
- ✅ **Password Security** - Passwords are hashed before storage
- ✅ **Duplicate Prevention** - Email uniqueness validation
- ✅ **Error Handling** - Comprehensive error responses
- ✅ **Dark Mode Support** - Toggle between light and dark themes
- ✅ **Toast Notifications** - User-friendly feedback messages
- ✅ **Responsive Design** - Mobile-friendly UI with Tailwind CSS
- ✅ **Type Safety** - Full TypeScript support in frontend

## Security Notes

⚠️ **This is a demonstration project and should not be used in production without additional security measures:**

1. **JWT Secret Key**: The default secret key is weak. Change it to a strong, random string at least 256 bits long.
2. **Password Hashing**: Ensure Spring Security's password encoder is properly configured in production.
3. **CORS Configuration**: Review and configure CORS settings in `WebConfig.java` for your specific domain.
4. **HTTPS**: Always use HTTPS in production.
5. **Token Expiration**: The default token expiration is 24 hours. Adjust as needed.
6. **Environment Variables**: Store sensitive configuration (JWT secret, database credentials) in environment variables, not in code.
7. **Input Validation**: Additional validation should be added for email format and password strength.

## 📝 Notes

- The SQLite database is file-based and stored in the backend root directory as `user.db`
- The application uses JWT tokens for stateless authentication
- The frontend uses React Context for state management

## 📄 License

This project is provided as a demonstration for educational purposes.
