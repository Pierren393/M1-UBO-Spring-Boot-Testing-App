-- Peuplement de la base de données avec des données de test

-- Utilisateurs (mots de passe encodés en BCrypt pour "password123")
INSERT IGNORE INTO users (pseudo, nom, prenom, age, adresse, mot_de_passe, role) VALUES
('admin', 'Admin', 'Super', 30, '1 rue de Brest, 29200 Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN'),
('jdupont', 'Dupont', 'Jean', 25, '12 avenue de la Liberté, 29200 Brest', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('mmartin', 'Martin', 'Marie', 22, '5 rue du Port, 29000 Quimper', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER');

-- Posters
INSERT IGNORE INTO poster (id, title, image_url, movie_id, description, price, width, height) VALUES
(1, 'Inception', 'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEhniW0khKSBa.jpg', 27205, 'Poster officiel du film Inception de Christopher Nolan', 19.99, 60.0, 90.0),
(2, 'The Dark Knight', 'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911BTUNoM8JTol0.jpg', 155, 'Poster du film The Dark Knight avec Heath Ledger', 24.99, 60.0, 90.0),
(3, 'Interstellar', 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg', 157336, 'Poster du film Interstellar de Christopher Nolan', 22.99, 40.0, 60.0),
(4, 'Pulp Fiction', 'https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg', 680, 'Poster culte de Pulp Fiction par Quentin Tarantino', 29.99, 80.0, 120.0),
(5, 'The Matrix', 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg', 603, 'Poster emblématique de The Matrix', 21.99, 60.0, 90.0);
