INSERT INTO customer (id, name, email, username, password, role) VALUES (1111, 'Kutay incirci', 'kutay.incirci@gmail.com', 'kutay', '1', 'ADMIN');
INSERT INTO customer (id, name, email, username, password, role) VALUES (2111, 'hebelek dubelek', 'hebelek@dubelek.com', 'hebelek', '1', 'ADMIN');

INSERT INTO asset (id, customer_id, asset_name, size, usable_size) VALUES (1, 1111, 'TRY', 1000.00, 1000.00);
INSERT INTO asset (id, customer_id, asset_name, size, usable_size) VALUES (2, 2111, 'TRY', 1500.00, 1500.00);

-- INSERT INTO orders (id, customer_id, asset_name, order_type, size, price, status, create_date) VALUES (1, 1, 'TRY', 'BUY', 10, 1000.00, 'PENDING', '2024-09-09 10:00:00');
-- INSERT INTO orders (id, customer_id, asset_name, order_type, size, price, status, create_date) VALUES (2, 2, 'TRY', 'SELL', 5, 3000.00, 'PENDING', '2024-09-09 10:00:00');
