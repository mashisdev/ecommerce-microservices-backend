CREATE TABLE IF NOT EXISTS orders (
    id VARCHAR(36) PRIMARY KEY,
    order_tracking_number VARCHAR(255) UNIQUE,
    total_quantity INT NOT NULL,
    total_price DECIMAL(19, 2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(255) UNIQUE NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    unit_price DECIMAL(19, 2) NOT NULL,
    quantity INT NOT NULL,
    order_id VARCHAR(36),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders (id)
);