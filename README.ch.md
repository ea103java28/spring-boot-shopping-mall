## 簡介

使用Spring Boot 開發的電商網站。

## 環境

1. Java JDK 11
2. Spring Boot: 2.3.7.RELEASE
3. MySQL: 8.X


## 技術

1. Unit Test: JUnit 5 & H2 Database
2. Spring JDBC
3. Spring validation
4. Lombok
5. Encrypt with MD5 Hash

## API

* 商品功能
   - 新增／查詢／修改／刪除商品 (CRUD)
   - 查詢商品列表
* 帳號功能
   - 註冊新帳號
   - 登入
* 訂單功能
   - 創建訂單
   - 查詢訂單列表

### 商品功能：
1. 新增／查詢／修改／刪除商品(CRUD)

   |*method*|*url*|*description*|
   |--|--|--|
   |POST|`localhost:8080/products`|新增|
   |GET|`localhost:8080/products/{productId}`|查詢|
   |PUT|`localhost:8080/products/{productId}`|修改|
   |DELETE|`localhost:8080/products/{productId}`|刪除|

   POST & PUT request body
     ```
      {
          "productName" : "C++",
          "category" : "E_BOOK",
          "imageUrl" : "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/068/87/0010688757.jpg&v=55e42cd7k&w=280&h=280",
          "price" : 1000,
          "stock" : 5000,
          "description" : "出版社：碁峰，作者：Stephen Prata，譯者：蔡明志"
      }
      ```

2. 查詢商品列表<br>
   `GET:   localhost:8080/products?category=X&search=X&orderBy=X&sort=X&limit=X&offset=X`<br>

   |**功能**|**參數名稱**|**說明**|**備註**|
   |--|--|--|--|
   |查詢條件|category|商品類別||
   |查詢條件|search|用模糊搜尋對比商品名稱||
   |排序|orderBy|以商品的何種欄位排序|預設：created_date|
   |排序|sort|升冪或降冪|預設：降冪|
   |分頁|limit|限制回傳筆數|預設：5|
   |分頁|offset|跳過幾筆|預設：0|


### 帳號功能

1. 註冊新帳號<br>
   `POST:   localhost:8080/users/register`

2. 登入<br>
   `POST:   localhost:8080/users/login`<br>
   request body
    ```
    {
      "email" : "test@gmail.com",
      "password" : "123"
    }
    ```

### 訂單功能
1. 創建訂單<br>
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
2. 查詢訂單列表<br>
   `GET:   localhost:8080/users/{userId}/orders?limit=X&offset=X`<br>
   |**功能**|**參數名稱**|**說明**|**備註**|
   |--|--|--|--|
   |分頁|limit|限制回傳筆數|預設：10|
   |分頁|offset|跳過幾筆|預設：0|


## 單元測試

目前針對每個功能的Controller層去使用MockMvc去做測試


## 資料庫

database schema以及data放在[docs](https://github.com/ea103java28/shopping-mall/tree/master/docs)

