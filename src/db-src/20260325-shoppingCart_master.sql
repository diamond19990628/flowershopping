CREATE TABLE shopping_cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    card_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    is_anonymous INT NOT NULL,
    comment TEXT,
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES USER_MASTER(user_id),
    FOREIGN KEY (product_id) REFERENCES product_master(product_id),
    FOREIGN KEY (card_id) REFERENCES card_master(card_id),
    unique (user_id, product_id, card_id)
);
