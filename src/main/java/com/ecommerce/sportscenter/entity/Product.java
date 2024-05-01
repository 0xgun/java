package com.ecommerce.sportscenter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name="Name")
    private String name;
    @Column(name="Description")
    private String description;
    @Column(name="Price")
    private Long price;

    //many products can have one brand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductBrandId",referencedColumnName = "id")
    private Brand brand;

    //many products can have one type
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductTypeId",referencedColumnName = "id")
    private Type type;;
}
