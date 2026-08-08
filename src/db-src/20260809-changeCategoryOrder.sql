Alter table category_master add order_no int not null;
UPDATE category_master c
JOIN (
    SELECT
        category_id,
        ROW_NUMBER() OVER (ORDER BY category_id) -1 AS new_order_no
    FROM category_master where parent_category_id = 0
) t ON c.category_id = t.category_id
SET c.order_no = t.new_order_no;