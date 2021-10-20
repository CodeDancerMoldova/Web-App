CREATE TABLE department
(
    id       NUMBER(6) PRIMARY KEY,
    name     VARCHAR2(20) NOT NULL,
    location VARCHAR2(20) NOT NULL
);

INSERT INTO department
VALUES (100
           , 'IT-dep',
        'Chisinau');
INSERT INTO department
VALUES (101
           , 'IT-dep',
        'Bucuresti');