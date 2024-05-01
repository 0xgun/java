package com.ecommerce.sportscenter.repository;

import com.ecommerce.sportscenter.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("SELECT p FROM Product where p.name LIKE %:string%")
    List<Product> searchByName(@Param("string") String string);
}
