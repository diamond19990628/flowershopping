create table discount_master(
	discount_id int not null primary key AUTO_INCREMENT,
    discount_scope int not null,
    discount_type_id int not null,
    status int not null,
    discount_title varchar(200) not null,
    discount_rate DECIMAL(4,2) not null,
    threshold_amount DECIMAL(10,2) default 0,
    reduction_amount DECIMAL(10,2) default 0,
    booking_time datetime,
    advance_days int default 0,
    start_date datetime not null,
    end_date datetime not null
);
create index idx_discount_scope ON discount_master(discount_scope);
create index idx_discount_type_id ON discount_master(discount_type_id);

create table discount_type_master(
	discount_type_id int not null primary key,
    discount_type_name varchar(20) not null
);
insert into discount_type_master values(1,"全场打折折扣");
insert into discount_type_master values(2,"满减折扣");
insert into discount_type_master values(3,"预约折扣");

create table discount_scope_master(
	discount_scope_id int not null primary key,
    discount_scope_name varchar(20) not null
);
insert into discount_scope_master values(1,"系统折扣");
insert into discount_scope_master values(2,"个人折扣");