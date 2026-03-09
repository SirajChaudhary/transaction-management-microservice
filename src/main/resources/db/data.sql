INSERT INTO customers(name, credit_score, account_balance, version)
VALUES ('Rahul Trivedi', 750, 50000, 0);

INSERT INTO customers(name, credit_score, account_balance, version)
VALUES ('Sameer Chaudhary', 650, 30000, 0);

INSERT INTO accounts(customer_id, balance)
VALUES (1, 50000);

INSERT INTO accounts(customer_id, balance)
VALUES (2, 30000);