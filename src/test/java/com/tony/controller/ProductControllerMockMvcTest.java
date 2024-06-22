package com.tony.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProducts() {
    }

    @Test
    void getProductById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                  .get("/products/1");
                    .get("/products/{productId}", 1) // 也可以用這種寫法
                    .header("headerName", "headerValue")
                    .queryParam("price", "100");
//                  .param("price", "100") // 也可以這樣寫


//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("$.productId", equalTo(1)))
//                .andExpect(jsonPath("$.productName", notNullValue()));

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productId", equalTo(1)))
                .andExpect(jsonPath("$.productName", notNullValue()))
                .andReturn();

        // 這樣就可以取得這 API 所返回的 response body 了
        String body = mvcResult.getResponse().getContentAsString();
        System.out.println("返回的 response body 為: " + body);

    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)// 這行很重要，等同於 request headers，才可以在 request body 裡面 傳遞 json 字串
                .content(" {\n" +
                        "     \"productName\" : \"C++\",\n" +
                        "     \"category\" : \"E_BOOK\",\n" +
                        "     \"imageUrl\" : \"https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/068/87/0010688757.jpg&v=55e42cd7k&w=280&h=280\",\n" +
                        "     \"price\" : 1000,\n" +
                        "     \"stock\" : 5000,\n" +
                        "     \"description\" : \"出版社：碁峰，作者：Stephen Prata，譯者：蔡明志\"\n" +
                        " }");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(201));

    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}