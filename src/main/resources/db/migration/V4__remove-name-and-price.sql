DROP TABLE camunda_order_task;

ALTER TABLE order_item
DROP
COLUMN name;

ALTER TABLE order_item
DROP
COLUMN price;

ALTER TABLE `order`
DROP
COLUMN status;

ALTER TABLE `order`
    ADD status VARCHAR(255) NULL;