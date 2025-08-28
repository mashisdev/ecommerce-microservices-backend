CREATE TABLE brands (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255),
    stock INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    brand_id BIGINT,
    category_id BIGINT,

    CONSTRAINT fk_brand
        FOREIGN KEY (brand_id)
        REFERENCES brands(id),

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
);