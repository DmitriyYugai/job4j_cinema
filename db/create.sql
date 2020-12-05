DROP TABLE IF EXISTS halls;
DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts(
    id serial primary key,
    name TEXT,
    phone TEXT
);

CREATE TABLE IF NOT EXISTS halls(
   id serial primary key,
   row_column INTEGER UNIQUE,
   account_id INTEGER REFERENCES accounts(id)
);

INSERT INTO halls(row_column) VALUES(11);
INSERT INTO halls(row_column) VALUES(12);
INSERT INTO halls(row_column) VALUES(13);
INSERT INTO halls(row_column) VALUES(21);
INSERT INTO halls(row_column) VALUES(22);
INSERT INTO halls(row_column) VALUES(23);
INSERT INTO halls(row_column) VALUES(31);
INSERT INTO halls(row_column) VALUES(32);
INSERT INTO halls(row_column) VALUES(33);

SELECT * FROM accounts;

SELECT * FROM halls;
