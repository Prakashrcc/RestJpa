package com.pks.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pks.demo.model.Product;

public interface ProductService {
	public List<Product> getProducts();

	public Product getProduct(Long id);

	public Product createProduct(Product product);

	public Product updateProduct(Product product, Long id);

	public boolean deleteProduct(Long id);

	public Page<Product> getProductsUsingParameter(String searchString, String orderBy, String maxRecord,String startAt);
													
}
