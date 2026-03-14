create table delivery_address_master(
    delivery_address_id INT NOT NULL,
    delivery_address TEXT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY(delivery_address_id,user_id)
)
INSERT INTO delivery_address_master VALUES (1,'南京市玄武区中山东路1号',11);
INSERT INTO delivery_address_master VALUES (2,'南京市鼓楼区湖南路88号',11);
INSERT INTO delivery_address_master VALUES (3,'南京市建邺区江东中路222号',11);

INSERT INTO delivery_address_master VALUES (1,'南京市秦淮区夫子庙贡院街99号',12);
INSERT INTO delivery_address_master VALUES (2,'南京市雨花台区软件大道168号',12);
INSERT INTO delivery_address_master VALUES (3,'南京市栖霞区仙林大道163号',12);

INSERT INTO delivery_address_master VALUES (1,'南京市江宁区胜太路88号',13);
INSERT INTO delivery_address_master VALUES (2,'南京市浦口区江浦街道文德路66号',13);
INSERT INTO delivery_address_master VALUES (3,'南京市六合区雄州东路100号',13);

INSERT INTO delivery_address_master VALUES (1,'南京市建邺区河西大街101号',14);
INSERT INTO delivery_address_master VALUES (2,'南京市玄武区珠江路699号',14);
INSERT INTO delivery_address_master VALUES (3,'南京市鼓楼区中央路399号',14);

INSERT INTO delivery_address_master VALUES (1,'南京市雨花台区铁心桥大街88号',15);
INSERT INTO delivery_address_master VALUES (2,'南京市秦淮区中华路305号',15);
INSERT INTO delivery_address_master VALUES (3,'南京市江宁区天元东路388号',15);


select
            om.order_id,
            om.order_no,
            UM.user_id,
            UM.nickname,
            om.total_amount,
            dym.delivery_type_name,
            osm.status_id,
            osm.status_name,
            dam.delivery_address_id,
            dam.delivery_address,
            UM.tel,
            UM.birthday,
            om.create_time
        from
            order_master om
            INNER JOIN
                USER_MASTER UM
            ON  um.user_id = om.user_id
            INNER JOIN
                delivery_type_master dym
            ON  dym.delivery_type_id = om.delivery_type_id
            INNER JOIN
                order_status_master osm
            ON  osm.status_id = om.status_id
            INNER JOIN
                delivery_address_master dam
            ON  dam.user_id = UM.user_id
            AND dam.delivery_address_id = om.Delivery_address_id


select pm.product_id,pm.product_name,cm.card_id,cm.card_name,oi.comment,oi.is_anonymous,oi.quantity from order_item oi
INNER JOIN product_master pm ON pm.product_id = oi.product_id
INNER JOIN card_master cm ON cm.card_id = oi.card_id
where order_id = 1

select * from card_master
INSERT INTO card_master values(1,'祝福卡',now(),now());
INSERT INTO card_master values(2,'生日卡',now(),now());

INSERT INTO card_master values(3,'无卡',now(),now());


select
            oi.order_id,
            pm.product_id,
            pm.product_name,
            oi.quantity,
            cm.card_id,
            cm.card_name,
            oi.comment,
            oi.is_anonymous
        from
            order_item oi
            INNER JOIN
                product_master pm
            ON  pm.product_id = oi.product_id
            INNER JOIN
                card_master cm
            ON  cm.card_id = oi.card_id
        WHERE oi.order_id = 2

INSERT INTO order_item
(order_id, product_id, card_id, is_anonymous, comment, quantity)
VALUES
(2, 55, 1, 0, '生日快乐', 2),
(3, 56, 2, 1, '匿名祝福', 1),
(6, 59, 3, 0, '节日快乐', 3),
(9, 60, 1, 1, '好运常在', 2);

select * from order_item

select
            om.order_id,
            om.order_no,
            UM.user_id,
            UM.nickname,
            om.total_amount,
            dym.delivery_type_name,
            osm.status_id,
            osm.status_name,
            dam.delivery_address_id,
            dam.delivery_address,
            UM.tel,
            UM.birthday,
            om.create_time
        from
            order_master om
            INNER JOIN
                USER_MASTER UM
            ON  um.user_id = om.user_id
            INNER JOIN
                delivery_type_master dym
            ON  dym.delivery_type_id = om.delivery_type_id
            INNER JOIN
                order_status_master osm
            ON  osm.status_id = om.status_id
            INNER JOIN
                delivery_address_master dam
            ON  dam.user_id = UM.user_id
            AND dam.delivery_address_id = om.Delivery_address_id

