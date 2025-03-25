CREATE TABLE author (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL ,
                        email VARCHAR(100) NOT NULL UNIQUE ,
                        created_at DATETIME NOT NULL ,
                        modified_at DATETIME NOT NULL
);
CREATE TABLE schedule (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          author_id BIGINT NOT NULL ,
                          exercise_date VARCHAR(100) NOT NULL,
                          exercises TEXT NOT NULL,
                          password VARCHAR(100) NOT NULL,
                          created_at DATETIME NOT NULL,
                          modified_at DATETIME NOT NULL,
                          CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author(id)
);
