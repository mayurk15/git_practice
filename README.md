# PDF Saver - Java JSP & Servlet Project

## What you get
A ready-to-import sample Java web application that uses JSP + Servlets + PostgreSQL.
- Admin can upload/manage PDFs
- Users can register/login and download PDFs
- All metadata stored in PostgreSQL

## Quick setup
1. Install Java (JDK 11+), Apache Tomcat 9/10, and PostgreSQL.
2. Create a PostgreSQL DB and run `db/schema.sql`.
3. Update DB credentials in `src/com/pdfapp/utils/DBConnection.java`.
4. Import this folder as a Maven project in IntelliJ/Eclipse (or create a Dynamic Web Project).
5. Build and deploy to Tomcat. Place `uploads/` folder under webapp root if needed.

## Notes
- Passwords are stored in plaintext in this sample for simplicity. Use hashing (BCrypt) for production.
- Adjust `uploadPath` in `UploadServlet.java` if you prefer a different storage path.
