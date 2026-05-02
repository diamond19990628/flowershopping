alter table order_master
add is_refunding int default 0 after order_status;