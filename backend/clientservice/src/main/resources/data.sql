-- Admin seed user (password: "password")
INSERT INTO clients (id, email, first_name, last_name, password, phone, address, status, last_login, created_at, updated_at)
VALUES (1, 'admin@example.com', 'Admin', 'User', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi5R.Y8Z3p9Uy7F2x..G8V8X7p8iUzK', NULL, NULL, 'ACTIVE', NULL, NOW(), NOW())
ON CONFLICT (email) DO NOTHING;

INSERT INTO client_roles (client_id, role) VALUES (1, 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO client_roles (client_id, role) VALUES (1, 'ROLE_USER') ON CONFLICT DO NOTHING;

SELECT setval('clients_seq', GREATEST((SELECT COALESCE(MAX(id), 1) FROM clients) + 1, 2), false);
