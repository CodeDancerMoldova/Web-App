CREATE TABLE employees
(     id    NUMBER(6)
          CONSTRAINT emp_emp_id_pk
              PRIMARY KEY
    , first_name     VARCHAR2(20) UNIQUE
    , last_name      VARCHAR2(25) UNIQUE
          CONSTRAINT emp_last_name_nn  NOT NULL
    , email          VARCHAR2(25)
          CONSTRAINT emp_email_nn  NOT NULL UNIQUE
    , phone_number   VARCHAR2(20) NOT NULL UNIQUE
    , salary         NUMBER(8,2)
          CONSTRAINT emp_salary_min
              CHECK (salary > 0)
    , department_id  NUMBER(4) NOT NULL
);

INSERT INTO employees VALUES
    ( 105
    , 'David'
    , 'Austin'
    , 'david@gmail.com'
    , '590.423.4569'
    , 4800
    , 60
    );
INSERT INTO employees VALUES
    ( 100
    , 'Alex'
    , 'Gigel'
    , 'alex@gmail.com'
    , '590.423.4570'
    , 4500
    , 60
    );