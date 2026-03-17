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

select * from stock_master
select * from product_master
select * from attached_file_master;
select * from category_master;
select * from stock_master;
delete from stock_master;
delete from product_master;
delete from attached_file_master;
delete from product_category;

select * from attached_file_master

select * from product_category;

select
            cm1.category_id,
            cm1.category_name
        from
            category_master cm1
        WHERE cm1.category_id = 1


select * from product_master

select * from stock_master


ALTER TABLE stock_master
ADD INDEX idx_stock_count (stock_count);

ALTER TABLE product_master
ADD INDEX idx_status (status);

