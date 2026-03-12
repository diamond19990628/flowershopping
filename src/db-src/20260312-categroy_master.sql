create table product_category(
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    entry_date DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    PRIMARY KEY(product_id,category_id)
);

create table category_master(
    category_id INT NOT NULL PRIMARY KEY,
    category_name VARCHAR(200) NOT NULL,
    child_category_id INT NOT NULL,
    entry_date DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    update_date DATETIME NOT NULL
);




