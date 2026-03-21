create table information_master(
    information_id int not null primary key auto_increment,
    information_title varchar(255) not null,
    information_content text not null,
    publish_start_date datetime not null,
    publish_end_date datetime not null,
    created_date datetime default current_timestamp
);