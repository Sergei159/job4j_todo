CREATE TABLE IF NOT EXISTS items
(
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    isDone BOOLEAN,
    user_id int not null references users(id)
);

CREATE TABLE IF NOT EXISTS users (
id serial primary key,
email text,
password text,
CONSTRAINT email_unique UNIQUE (email)

);