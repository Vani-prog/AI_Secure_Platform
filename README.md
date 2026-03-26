# AI Secure Platform 

## Project Description
AI Secure Platform is a full-stack web application that analyzes text and multiple file formats such as LOG, TXT, CSV, JSON, and XML to detect security threats, suspicious patterns, and generate insights. The system helps in identifying errors, warnings, unauthorized access attempts, and unusual activities from system logs or uploaded files.


## Features
- Analyze raw text
- Upload and analyze files
- Supports multiple file formats (.log, .txt, .csv, .json, .xml)
- Detects security threats and suspicious activities
- Generates findings and insights
- Threat level detection
- Rate limiting (Max 5 uploads per minute)
- Dashboard UI
- Full-stack implementation (React + Spring Boot)

## Technologies Used
### Frontend
- React.js
- HTML
- CSS
- JavaScript
- Axios

### Backend
- Spring Boot
- Java
- REST API
- Multipart File Upload
- Pattern-based Analysis

## Project Structure

AI-Secure-Platform
│
├── frontend
├── backend
└── README.md

## Setup Instructions

### Backend Setup (Spring Boot)
1. Open backend folder in IntelliJ / Eclipse
2. Make sure Java 17+ is installed
3. Run the Spring Boot application
4. Server will start on:     http://localhost:8080


### Frontend Setup (React)
1. Open frontend folder in VS Code
2. Run: npm install
        npm start

3. Frontend will start on:   http://localhost:3000

## API Endpoints
| Method | Endpoint | Description |
|-------|---------|-------------|
| GET | /analyze/ | Check server |
| POST | /analyze/text | Analyze text |
| POST | /analyze/file | Upload and analyze file |

## Configuration

### application.properties
server.port=8080
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

### Frontend API URL
http://localhost:8080/analyze/

## How to Use
1. Enter text in the text area and click **Analyze Text**
2. Upload a file and click **Upload File**
3. The system will display:
   - Threats
   - Findings
   - Insights

## Future Improvements
- AI/ML based threat detection
- User authentication
- Database storage
- Report generation
- Visualization dashboard

## Author
P Vani
