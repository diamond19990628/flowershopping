CREATE TABLE USER_MASTER(
    user_id int AUTO_INCREMENT PRIMARY KEY comment '用户名',
    openid VARCHAR(64) not null UNIQUE comment '微信用的openid',
    nickname VARCHAR(200) not null comment '姓名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间'
)