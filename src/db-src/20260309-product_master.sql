CREATE TABLE product_master(
    product_id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    attached_file_id int NOT NULL,
    amount int NOT NULL,
    status int NOT NULL,
    entry_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL
);
INSERT INTO product_master 
(product_name, attached_file_id, amount, status, entry_date, update_date)
VALUES
('Red Rose Bouquet', 1, 100, 1, NOW(), NOW()),
('White Lily Bouquet', 2, 80, 1, NOW(), NOW()),
('Sunflower Basket', 3, 60, 1, NOW(), NOW()),
('Tulip Gift Box', 4, 50, 1, NOW(), NOW()),
('Carnation Arrangement', 5, 120, 1, NOW(), NOW());

INSERT INTO product_master 
(product_name, attached_file_id, amount, status, entry_date, update_date)
VALUES
('Pink Rose Basket', 6, 90, 2, NOW(), NOW()),
('Lavender Flower Box', 7, 110, 2, NOW(), NOW()),
('Mixed Spring Bouquet', 8, 75, 2, NOW(), NOW());

select
            product_master.product_id,
            product_master.product_name,
            CASE
                WHEN attached_file_master.attached_file_id IS NULL THEN 0
                ELSE attached_file_master.attached_file_id
            END AS attached_file_id,
            CASE
                WHEN attached_file_master.attached_file_path IS NULL THEN ""
                ELSE attached_file_master.attached_file_path
            END AS attached_file_path,
            product_master.amount,
            stock_master.stock_count
        from
            product_master
            INNER JOIN
                stock_master
            ON  product_master.product_id = stock_master.product_id
            LEFT JOIN
                attached_file_master
            ON  attached_file_master.attached_file_id = product_master.attached_file_id
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
INSERT INTO stock_master VALUES
(2,50),
(3,20),
(4,80),
(5,10);

INSERT INTO stock_master (product_id, stock_count)
VALUES
(6, 45),
(7, 20),
(8, 75);
select * from stock_master
