-- Peuplement de la base de données avec des données de test

-- Utilisateurs (mots de passe encodés en BCrypt pour "password123")
INSERT IGNORE INTO users (pseudo, nom, prenom, age, adresse, mot_de_passe, role) VALUES
('admin', 'Admin', 'Super', 30, '1 rue de Brest, 29200 Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN'),
('jdupont', 'Dupont', 'Jean', 25, '12 avenue de la Liberté, 29200 Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('mmartin', 'Martin', 'Marie', 22, '5 rue du Port, 29000 Quimper', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER');

-- Note : Les posters sont gérés dans MongoDB via le PosterServlet.
-- Les données de test MongoDB sont initialisées par MongoDataInitializer.
