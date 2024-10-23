package com.tony.model;

import com.tony.constant.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(
                name = "Product.findAll",
                query = "SELECT p FROM Product p"
        ),
        @NamedQuery(
                name = "Product.findByCategory",
                query = "SELECT p FROM Product p WHERE p.category = :category"
        )
})
public class Product implements Serializable {


    private static final long serialVersionUID = 11234877563926L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "category")
    @Enumerated(EnumType.STRING) // check enum type is correct
    private ProductCategory category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "description")
    private String description;

//    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

//    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date", insertable = false, updatable = false)
    private Date lastModifiedDate;



    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category=" + category +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
