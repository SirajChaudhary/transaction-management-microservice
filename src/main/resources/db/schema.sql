-- CREATE DATABASE transactiondb;

CREATE TABLE customers (
 id BIGSERIAL PRIMARY KEY,
 name VARCHAR(255),
 credit_score NUMERIC,
 account_balance NUMERIC,
 version BIGINT
);

CREATE TABLE accounts (
 id BIGSERIAL PRIMARY KEY,
 customer_id BIGINT,
 balance NUMERIC
);

CREATE TABLE loans (
 id BIGSERIAL PRIMARY KEY,
 customer_id BIGINT,
 amount NUMERIC,
 status VARCHAR(50)
);

CREATE TABLE audit_logs (
 id BIGSERIAL PRIMARY KEY,
 action VARCHAR(255),
 status VARCHAR(50),
 created_at TIMESTAMP
);