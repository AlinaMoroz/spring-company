--liquibase formatted sql

--changeset belotserkovskia:1
ALTER TABLE users
    ADD column created_at TIMESTAMP;


ALTER TABLE users
    ADD column modified_at TIMESTAMP;


ALTER TABLE users
    ADD column created_by varchar(32);


ALTER TABLE users
    ADD column modified_by varchar(32);