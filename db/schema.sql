-- PostgreSQL schema for PDF Saver
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) DEFAULT 'user' -- 'admin' or 'user'
);

CREATE TABLE pdf_files (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(1024) NOT NULL,
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    downloads INT DEFAULT 0
);

-- Default admin (change password after first run)
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');
