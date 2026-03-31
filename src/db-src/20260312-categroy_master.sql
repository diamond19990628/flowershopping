create table product_category(
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    entry_date DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    PRIMARY KEY(product_id,category_id)
);

create table category_master(
    category_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    category_name VARCHAR(200) NOT NULL,
    parent_category_id INT NOT NULL,
    entry_date DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    update_date DATETIME NOT NULL
);
ALTER TABLE stock_master
ADD INDEX idx_stock_count (stock_count);
ALTER TABLE product_master
ADD INDEX idx_status (status);

