-- Peuplement de la base de données avec des données de test
-- Toutes les insertions utilisent IGNORE pour éviter les doublons sur les colonnes UNIQUE (email, username)
-- Mot de passe pour tous : password123
-- Hash BCrypt : $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

REPLACE INTO users (email, username, nom, prenom, age, adresse, password, role) VALUES
('admin@univ-brest.fr', 'admin', 'Admin', 'Super', 30, '1 rue de Brest, 29200 Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN'),
('pierre_ubo@univ-brest.fr', 'pierre_ubo', 'Le Goff', 'Pierre', 21, 'Faculté des Sciences, Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN'),
('jean.dupont@example.com', 'jdupont', 'Dupont', 'Jean', 25, '12 avenue de la Liberté, 29200 Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('marie.martin@example.com', 'mmartin', 'Martin', 'Marie', 22, '5 rue du Port, 29000 Quimper', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('sophie.lavoie@example.com', 'sophie22', 'Lavoie', 'Sophie', 28, '42 Place de la Liberté, Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('thomas.moreau@example.com', 'thomas35', 'Moreau', 'Thomas', 35, 'Quai de la Douane, Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('julie.petit@example.com', 'julie_bzh', 'Petit', 'Julie', 24, '15 rue de Siam, Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('nicolas.roux@example.com', 'nico_test', 'Roux', 'Nicolas', 29, '7 avenue Foch, Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER');
