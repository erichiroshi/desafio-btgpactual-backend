-- Baseline schema for Order/OrderItem.
-- orderId is NOT auto-generated: OrderEntity has no @GeneratedValue, the order orderId
-- always comes from "orderId" in the RabbitMQ message.

CREATE TABLE tb_order (
                          order_id    BIGINT PRIMARY KEY,
                          customer_id BIGINT NOT NULL,
                          total       NUMERIC(19, 2) NOT NULL

);

-- Every read path (list orders, count orders, sum order value) filters by
-- customer_id, so this index is what keeps those queries off a full scan.
CREATE INDEX idx_tb_order_customer_id ON tb_order (customer_id);

CREATE TABLE tb_order_order_item (
                                     order_id  BIGINT         NOT NULL REFERENCES tb_order (order_id) ON DELETE CASCADE,
                                     product   VARCHAR(255)   NOT NULL,
                                     quantity  INTEGER        NOT NULL,
                                     price     NUMERIC(19, 2) NOT NULL,
                                     total     NUMERIC(19, 2) NOT NULL

);

CREATE INDEX idx_tb_order_order_item_order_id ON tb_order_order_item (order_id);