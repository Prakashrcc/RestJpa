package com.pks.demo.service.impl;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pks.demo.exception.RestRequestException;

import com.pks.demo.model.Product;

import com.pks.demo.repository.ProductRepository;
import com.pks.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public List<Product> getProducts() {
		logger.info("getProducts() method accessed");

		List<Product> products = new ArrayList<>();
		try {
			productRepository.findAll().forEach(products::add);
		} catch (Exception e) {
			logger.error(e + " Exception occured in getProducts() method");
			throw e;
		} finally {
			logger.info("getProduct() method completed ");
		}

		if (products.isEmpty()) {
			logger.warn("No Product found");
		}

		return products;

	}

	@Override
	public Product getProduct(Long id) {
		logger.info("getProduct() method accessed for productId: " + id);

		Product product = null;
		try {
			product = productRepository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error(e + " Exception occured in getProducts() method");
			throw e;
		}
		finally {
			logger.info("getProduct() method completed for productId: " + id);
		}
		
		return product;
	}

	@Override
	public Product createProduct(Product product) {
		logger.info("createProduct() method accessed ");
		Product savedProduct = null;
		try {
			savedProduct = productRepository.save(product);

		} catch (Exception e) {
			logger.error(
					e + " Exception occured while creating new product with productName: " + product.getProductName());
			throw e;
		} finally {
			logger.info("createProduct() method completed for productId " + savedProduct.getProductId());
		}
		return savedProduct;
	}

	@Override
	public Product updateProduct(Product product, Long id) {
		logger.info("updateProduct() method accessed with productId: " + id);
		Product updatedProduct = null;
		try {
			updatedProduct = productRepository.save(product);

		} catch (IllegalArgumentException e) {
			logger.error(e + " Exception occured while updating product with productId: " + product.getProductId());
			throw e;
		} finally {
			logger.info("updateProduct() method completed  with productId: " + id);
		}

		return updatedProduct;
	}

	@Override
	public boolean deleteProduct(Long id) {
		logger.info("deleteProduct() method accessed with productId: " + id);

		try {
			productRepository.deleteById(id);

		} catch (IllegalArgumentException e) {
			logger.error(e + " Exception occured while deleting product with productId: " + id);
			throw e;
		}
		finally {
			logger.info("deleteProduct() method completed with productId: " + id);
		}	
		return true;
	}

	@Override
	public Page<Product> getProductsUsingParameter(String searchString, String orderBy, String maxRecord,
			String startAt) {
		logger.info("getProductsUsingParameter() method accessed in ProductUtil class");

		Page<Product> products = null;

		Pageable paging = null;
		try {
			if (orderBy.toLowerCase().equals("desc")) {
				paging = PageRequest.of(Integer.parseInt(startAt), Integer.parseInt(maxRecord),
						Sort.by("productName").descending());
			} else {
				paging = PageRequest.of(Integer.parseInt(startAt), Integer.parseInt(maxRecord),
						Sort.by("productName").ascending());
			}
			products = productRepository.findByProductName(searchString, paging);
		} catch (Exception e) {
			logger.error(
					e + " Exception occured in getProductsUsingParameter() method for searchString: " + searchString);
			throw e;
		} finally {
			logger.info("getProductsUsingParameter() method completed");
		}

		return products;
	}

}
