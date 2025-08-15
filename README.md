# 📝 Notes App

A full-stack **Notes Management Application** built with **Java Spring Boot** (backend) and **React** (frontend).  
The app allows users to **register, log in, create, edit, and delete notes**, with secure authentication via **JWT**.  
All notes are stored in a **PostgreSQL** database. The project is fully containerized using **Docker**.

---

## 🚀 Features

- **User Authentication**  
  - Register & Login  
  - JWT-based authentication  
  - Logout support

- **Notes Management**  
  - Create a new note  
  - Edit existing notes using a rich note editor  
  - Delete notes  
  - View all notes for the logged-in user on the home page

- **Secure & Persistent**  
  - Passwords securely stored (BCrypt)  
  - JWT tokens for request authorization  
  - PostgreSQL database for persistent storage

---

## 🛠️ Tech Stack

**Backend:**  
- Java Spring Boot  
- Spring Security (JWT)  
- PostgreSQL (via JPA/Hibernate)  

**Frontend:**  
- React (with Hooks & Context)  
- Axios for API calls  

**Deployment & Environment:**  
- Docker & Docker Compose  
- PostgreSQL container  

---
## Project Structure

```
notes-application/
├── frontend/                 # React.js frontend application
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── App.js
│   │   └── index.js
│   └── package.json
├── backend/                  # Spring Boot backend application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── notes/
│   │   │   │           ├── controller/
│   │   │   │           ├── service/
│   │   │   │           ├── repository/
│   │   │   │           ├── model/
│   │   │   │           └── NotesApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
└── README.md                 # Main project documentation
```

## Technologies Used

### Frontend
- **React.js** - JavaScript library for building user interfaces
- **HTML5 & Tailwind CSS** - Markup and styling
- **JavaScript (ES6+)** - Programming language
- **Axios/Fetch** - HTTP client for API calls

### Backend
- **Spring Boot** - Java framework for building REST APIs
- **Spring Data JPA** - Data persistence layer
- **Spring Data Security** - Authentication and Authorization
- **H2/MySQL/PostgreSQL** - Database (configurable)
- **Maven** - Dependency management and build tool

## API Documentation

## Getting Started

### Prerequisites
- **Node.js** (v14 or higher)
- **npm** or **yarn**
- **Java** (JDK 11 or higher)
- **Maven** (v3.6 or higher)

### Running the Application

#### Backend (Spring Boot)
1. Navigate to the backend directory:
   ```bash
   cd notes-application/backend
   ```

2. Install dependencies and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. Backend will start at: `http://localhost:8080`

#### Frontend (React)
1. Navigate to the frontend directory:
   ```bash
   cd notes-application/frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Frontend will start at: `http://localhost:5173`

---
# Notes API Documentation

## Base URL
```
{backendurl}
```

## Endpoints

### Get All Notes
Retrieve all notes from the database.

**Endpoint:**
```
GET /notes
```

**Response:**
```json
[
  {
    "id": "1",
    "title": "Sample Note",
    "content": "This is a sample note content"
  }
]
```

### Create New Note
Create a new note in the database.

**Endpoint:**
```
POST /notes
```

**Request Body:**
```json
{
  "title": "New Note Title",
  "content": "Note content goes here"
}
```

**Response:**
```json
{
  "id": "2",
  "title": "New Note Title",
  "content": "Note content goes here"
}
```

### Update Existing Note
Update a specific note by its ID.

**Endpoint:**
```
PUT /notes/{id}
```

**Parameters:**
- `{id}` - The unique identifier of the note to update

**Request Body:**
```json
{
  "title": "Updated Note Title",
  "content": "Updated note content"
}
```

**Response:**
```json
{
  "id": "2",
  "title": "Updated Note Title", 
  "content": "Updated note content"
}
```

### Delete Note
Delete a specific note by its ID.

**Endpoint:**
```
DELETE /notes/{id}
```

**Parameters:**
- `{id}` - The unique identifier of the note to delete

**Response:**
```
HTTP Status: 204

```

## Example Usage

### Using cURL

**Get all notes:**
```bash
curl -X GET {backendurl}/notes
```

**Create a new note:**
```bash
curl -X POST {backendurl}/notes \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My New Note",
    "content": "This is the content of my new note"
  }'
```

**Update a note:**
```bash
curl -X PUT {backendurl}/notes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Title",
    "content": "Updated content"
  }'
```

**Delete a note:**
```bash
curl -X DELETE {backendurl}/notes/1
```

## Notes
- Replace `{backendurl}` with your actual backend URL
- Replace `{id}` with the actual note ID when making requests
- All requests and responses use JSON format
- Ensure proper Content-Type headers are set for POST and PUT requests
