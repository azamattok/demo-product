CREATE TABLE if not exist product (
                           id BIGSERIAL PRIMARY KEY,
                           name varchar(255),
                           description varchar(255),
                           quantity integer,
                           price numeric(19,2)
                           photo varchar
);
