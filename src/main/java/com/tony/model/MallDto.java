package com.tony.model;

import lombok.Data;

import java.util.Date;
@Data
public class MallDto {

    private Integer product_id;
    private String category;
    private Integer amount;
    private Date last_modified_date;

}
