CREATE TABLE USER_MASTER(
    user_id int AUTO_INCREMENT PRIMARY KEY comment '用户名',
    openid VARCHAR(128) not null UNIQUE comment '微信用的openid',
    nickname VARCHAR(200) not null comment '姓名',
    is_admin int COMMENT '是否是管理员',
    birthday DATETIME,
    tel varchar(13),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间'
)

select * from `USER_MASTER`
UPDATE USER_MASTER 
SET birthday = '1995-03-12', tel = '13845218763'
WHERE user_id = 11;