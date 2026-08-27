CREATE TABLE IF NOT EXISTS type_produit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL UNIQUE
);

-- Table des produits
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL,
    prix DOUBLE NOT NULL,
    type_produit_id BIGINT,
    CONSTRAINT fk_type_produit FOREIGN KEY (type_produit_id) REFERENCES type_produit(id)
);

-- Index pour améliorer les performances
CREATE INDEX idx_products_type_produit_id ON products(type_produit_id);
CREATE INDEX idx_type_produit_libelle ON type_produit(libelle);
