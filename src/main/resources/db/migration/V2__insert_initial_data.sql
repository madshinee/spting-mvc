INSERT INTO type_produit (libelle) VALUES
    ('Électronique'),
    ('Vêtements'),
    ('Alimentation')
ON DUPLICATE KEY UPDATE libelle = libelle;

-- Insertion de quelques produits exemples
INSERT INTO products (libelle, prix, type_produit_id) VALUES
    ('Smartphone', 69999, 1),
    ('Laptop', 89999, 1),
    ('T-shirt', 2999, 2),
    ('Jean', 5999, 2),
    ('Pâtes', 249, 3),
    ('Huile', 899, 3)
ON DUPLICATE KEY UPDATE libelle = libelle;
