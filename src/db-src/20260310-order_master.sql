CREATE TABLE order_master (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    order_no VARCHAR(64) NOT NULL UNIQUE COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    status_id TINYINT NOT NULL COMMENT '订单状态 0待支付 1已支付 2已完成 3已取消',

    delivery_type_id TINYINT NOT NULL COMMENT '配送方式 1自取 2配送',
    Delivery_address_id TEXT COMMENT '配送地址ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_status_id (status_id),
    INDEX idx_delivery_type_id (delivery_type_id)
);
CREATE TABLE order_item (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    card_id BIGINT NOT NULL,
    is_anonymous INT NOT NULL,
    comment TEXT,
    quantity INT NOT NULL,
    PRIMARY KEY(order_id,product_id,card_id)
);
create table card_master(
    card_id BIGINT NOT NULL PRIMARY KEY,
    card_name VARCHAR(200) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);
INSERT INTO card_master(card_id,card_name) values(1,"空白卡");
INSERT INTO card_master(card_id,card_name) values(1,"代写卡");
create table order_status_master(
    status_id int NOT NULL PRIMARY KEY,
    status_name varchar(40) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)
INSERT INTO order_status_master(status_id,status_name) VALUES(1,"已支付");
INSERT INTO order_status_master(status_id,status_name) VALUES(2,"发货中");
INSERT INTO order_status_master(status_id,status_name) VALUES(3,"已收货");
INSERT INTO order_status_master(status_id,status_name) VALUES(4,"申请退款");
INSERT INTO order_status_master(status_id,status_name) VALUES(5,"退款已到账");

create table delivery_type_master(
    delivery_type_id INT NOT NULL PRIMARY KEY,
    delivery_type_name VARCHAR(20) NOT NULL
)

INSERT INTO delivery_type_master values(1,'自取');
INSERT INTO delivery_type_master values(2,'配送');