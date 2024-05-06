package com.ecommerce.sportscenter.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BasketItem {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Long price;
    private String pictureUrl;
    private String productBrand;
    private String productType;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id") // Changed from 'id' to 'basket_id'
    private Basket basket;
}