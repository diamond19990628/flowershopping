CREATE TABLE order_master (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    order_no VARCHAR(64) NOT NULL UNIQUE COMMENT '系统订单编号',
    wx_transaction_id VARCHAR(64) DEFAULT NULL COMMENT '微信支付订单号',

    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',

    status_id TINYINT NOT NULL COMMENT '订单状态 0待支付 1已支付 2已完成 3已取消',
    pay_confirm_source TINYINT DEFAULT NULL COMMENT '支付确认来源 1微信回调 2主动查单 3人工处理',

    notify_status TINYINT NOT NULL DEFAULT 0 COMMENT '回调处理状态 0未收到有效回调 1已收到有效回调',
    notify_count INT NOT NULL DEFAULT 0 COMMENT '微信回调次数',

    delivery_type_id TINYINT NOT NULL COMMENT '配送方式 1自取 2配送',
    delivery_address_id BIGINT DEFAULT NULL COMMENT '配送地址ID',
    delivery_date DATETIME DEFAULT NULL COMMENT '配送时间或者自取时间',

    pay_time DATETIME DEFAULT NULL COMMENT '支付成功时间',
    pay_status INT NOT NULL DEFAULT 0 COMMENT '支付状态 0未支付 1支付成功 2支付失败',
    cancel_time DATETIME DEFAULT NULL COMMENT '取消时间',
    close_reason VARCHAR(255) DEFAULT NULL COMMENT '取消/关闭原因',

    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    requestNo VARCHAR(64) DEFAULT NULL COMMENT '请求号，幂等控制' unique,

    INDEX idx_user_id (user_id),
    INDEX idx_status_id (status_id),
    INDEX idx_delivery_type_id (delivery_type_id),
    INDEX idx_wx_transaction_id (wx_transaction_id)
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
create table order_status_master(
    status_id int NOT NULL PRIMARY KEY,
    status_name varchar(40) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);
INSERT INTO order_status_master(status_id,status_name) VALUES(0,"待支付");
INSERT INTO order_status_master(status_id,status_name) VALUES(1,"已支付");
INSERT INTO order_status_master(status_id,status_name) VALUES(2,"发货中");
INSERT INTO order_status_master(status_id,status_name) VALUES(3,"已收货");
INSERT INTO order_status_master(status_id,status_name) VALUES(4,"申请退款");
INSERT INTO order_status_master(status_id,status_name) VALUES(5,"退款已到账");

create table delivery_type_master(
    delivery_type_id INT NOT NULL PRIMARY KEY,
    delivery_type_name VARCHAR(20) NOT NULL
);

INSERT INTO delivery_type_master values(1,'自取');
INSERT INTO delivery_type_master values(2,'配送');
