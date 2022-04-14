CREATE TABLE IF NOT EXISTS items
(
    id SERIAL PRIMARY KEY,
    name varchar(100),
    description varchar(500),
    created TIMESTAMP,
    isDone BOOLEAN,
    user_id int not null references users(id)
);

CREATE TABLE IF NOT EXISTS users (
id serial primary key,
name varchar(100),
email varchar(50),
password varchar(50),
CONSTRAINT email_unique UNIQUE (email)

);