## Introduction

A shopping mall backend developed using SpringBoot and employing Redis for caching, along with RabbitMQ for message queuing, is designed to deliver high-performance and scalable services.

## Environment

1. Java JDK 8
2. Spring Boot: 2.3.7.RELEASE
3. MySQL: latest


## Technologies

1. Unit Test: JUnit 5 & H2 Database
2. Spring JDBC
4. Spring Validation
5. Lombok
6. Encryption with MD5 Hash
7. Spring RabbitMQ
8. Spring Redis

## API

* Product Features
   - Add/Query/Update/Delete Products (CRUD)
   - Query Product List
* Account Features
   - Register New Account
   - Login
* Order Features
   - Create Order
   - Query Order List

### Product Features:

1. Add/Query/Update/Delete Products (CRUD)

   |*method*|*url*|*description*|
   |--|--|--|
   |POST|`localhost:8080/products`|Add|
   |GET|`localhost:8080/products/{productId}`|Query|
   |PUT|`localhost:8080/products/{productId}`|Update|
   |DELETE|`localhost:8080/products/{productId}`|Delete|
   |GET|`localhost:8080/products`|Query All|
   |POST|`localhost:8080/products/enqueue`|EnqueueProductToRedis|

   POST & PUT request body
     ```
      {
          "productName" : "C++",
          "category" : "E_BOOK",
          "imageUrl" : "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/068/87/0010688757.jpg&v=55e42cd7k&w=280&h=280",
          "price" : 1000,
          "stock" : 5000,
          "description" : "Publisher: Qi Feng, Author: Stephen Prata, Translator: Tsai Ming-chih"
      }
      ```

2. Query Product List<br>
   `GET:   localhost:8080/products?category=X&search=X&orderBy=X&sort=X&limit=X&offset=X`<br>

   |**Feature**|**Parameter Name**|**Description**|**Note**|
   |--|--|--|--|
   |Query Condition|category|Product category||
   |Query Condition|search|Search and compare product names with fuzzy search||
   |Sorting|orderBy|Sort by which field of the product|Default: created_date|
   |Sorting|sort|Ascending or descending|Default: Descending|
   |Pagination|limit|Limit the number of returns|Default: 5|
   |Pagination|offset|Skip a few records|Default: 0|


### Account Features

1. Register New Account<br>
   `POST:   localhost:8080/users/register`

2. Login<br>
   `POST:   localhost:8080/users/login`<br>
   request body
    ```
    {
      "email" : "test@gmail.com",
      "password" : "123"
    }
    ```

### Order Features

1. Create Order<br>
   `POST:   localhost:8080/users/{userId}/orders`<br>
   request body
    ```
    {
      "buyItemList" : [
        {
          "productId" : 1,
          "quantity" : 1
        },
        {
          "productId" : 2,
          "quantity" : 2
        }
      ]
    }
    ```
2. Query Order List<br>
   `GET:   localhost:8080/users/{userId}/orders?limit=X&offset=X`<br>
   |**Feature**|**Parameter Name**|**Description**|**Note**|
   |--|--|--|--|
   |Pagination|limit|Limit the number of returns|Default: 10|
   |Pagination|offset|Skip a few records|Default: 0|

## Unit Testing

Currently using MockMvc to test each functionality of the Controller layer.

## Dockerfile

### MySQL:

 ```
FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_CHARSET=utf8mb4
ENV MYSQL_COLLATION=utf8mb4_unicode_ci

EXPOSE 3306

CMD ["mysqld"]
 ```
```
$ mkdir -p ~/mysql/data ~/mysql/logs ~/mysql/conf
$ docker build -t my-mysql .
$ sudo docker run --name my-mysql -p 3306:3306 -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -d my-mysql
$ docker exec -it my-mysql bash
```

### Redis:

redis.config:
https://redis.io/docs/management/config/

```
FROM redis:latest

ENV MY_PORT=6379

COPY ./redis.conf /usr/local/etc/redis/redis.conf

EXPOSE $MY_PORT

CMD ["redis-server"]
```

```
$ docker build -t my-redis .
$ docker run --name my-redis -p 6379:6379 -d my-redis redis-server --appendonly yes
$ docker exec -it my-redis bash
$ redis-cli --raw
$ ping
$ exit
```

### RabbitMQ:
```
FROM rabbitmq:management

ENV RABBITMQ_DEFAULT_USER=root
ENV RABBITMQ_DEFAULT_PASS=1234

EXPOSE 15672 5672
```

```
$ docker build -t my-rabbitmq .
$ docker run --name my-rabbitmq -d -p 15672:15672 -p 5672:5672 my-rabbitmq
```

## Database

Database schema and data are located in [docs](https://github.com/ea103java28/spring-boot-shopping-mall/tree/main/docs).
