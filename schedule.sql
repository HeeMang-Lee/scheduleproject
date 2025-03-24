CREATE TABLE schedule (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          exercise_date VARCHAR(100) NOT NULL,
                          exercises TEXT NOT NULL,
                          writer VARCHAR(50) NOT NULL,
                          password VARCHAR(100) NOT NULL,
                          created_at DATETIME NOT NULL,
                          modified_at DATETIME NOT NULL
);
