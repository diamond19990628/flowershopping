CREATE TABLE product_master(
    product_id int NOT NULL PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    attached_file_id int NOT NULL,
    amount int NOT NULL,
    entry_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL
);

CREATE TABLE attached_file_master(
    attached_file_id int NOT NULL PRIMARY KEY,
    attached_file_path VARCHAR(256) NOT NULL,
    entry_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL
);
CREATE TABLE stock_master(
    product_id int not null PRIMARY KEY,
    stock_count int not null
)

