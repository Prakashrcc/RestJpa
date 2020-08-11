package com.pks.demo.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pks.demo.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	@Query("select p from Product p where product_name like %?1%")
	Page<Product> findByProductName(String productName, Pageable pageable );

}
