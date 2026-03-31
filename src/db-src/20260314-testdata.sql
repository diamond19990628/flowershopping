create table delivery_address_master(
    delivery_address_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    delivery_address VARCHAR(255) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES USER_MASTER(user_id)
);
INSERT INTO card_master values(1,'祝福卡',now(),now());
INSERT INTO card_master values(2,'生日卡',now(),now());

INSERT INTO card_master values(3,'无卡',now(),now());