CREATE DATABASE mall;

USE mall;

-- product
DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128) NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    default CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP    default CURRENT_TIMESTAMP NOT NULL on update CURRENT_TIMESTAMP
);

-- user
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE KEY,
    password           VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    default CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP    default CURRENT_TIMESTAMP NOT NULL on update CURRENT_TIMESTAMP
);


-- order
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    order_id           INT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id            INT       NOT NULL,
    total_amount       INT       NOT NULL, -- 訂單總花費
    created_date       TIMESTAMP    default CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP    default CURRENT_TIMESTAMP NOT NULL on update CURRENT_TIMESTAMP
);

-- order_item
DROP TABLE IF EXISTS order_item;
CREATE TABLE order_item
(
    order_item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id      INT NOT NULL,
    product_id    INT NOT NULL,
    quantity      INT NOT NULL, -- 商品數量
    amount        INT NOT NULL  -- 商品花費
);
