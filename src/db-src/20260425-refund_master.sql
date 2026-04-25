CREATE TABLE refund_master (
    refund_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    out_refund_no VARCHAR(64) NOT NULL UNIQUE,
    order_no VARCHAR(64) NOT NULL,
    status INT DEFAULT 0, -- 0:退款中 1:成功 2:失败
    create_time DATETIME,
    update_time DATETIME
);