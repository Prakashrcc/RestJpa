package com.pks.demo.service;

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

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	Logger logger = LoggerFactory.getLogger(ProductService.class);

	public List<Product> getProducts() {
		logger.info("getProducts() method accessed");

		List<Product> products = new ArrayList<>();
		try {
			productRepository.findAll().forEach(products::add);
		} catch (Exception e) {
			logger.warn(e + " Exception occured in getProducts() method");
		}

		if (products.isEmpty()) {
			logger.warn("No Product found");
		}
		logger.info("getProduct() method completed ");
		return products;

	}

	public Product getProduct(int id) throws ClassNotFoundException, SQLException {
		logger.info("getProduct() method accessed for productId: " + id);

		Product product = null;
		try {
			product = productRepository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.warn(e + " Exception occured in getProducts() method");
		}

		if (product == null) {
			logger.warn("No Product found for productId: " + id);
		}
		logger.info("getProduct() method completed for productId: " + id);
		return product;
	}

	public boolean createProduct(Product product) throws SQLIntegrityConstraintViolationException {
		logger.info("createProduct() method accessed ");
		try {
			productRepository.save(product);

		} catch (Exception e) {
			logger.error(e + " Exception occured while creating new product with productId: ");
			return false;
		}
		logger.info("createProduct() method completed");
		return true;

	}

	public boolean updateProduct(Product product, int id) throws ClassNotFoundException, SQLException {
		logger.info("updateProduct() method accessed with productId: " + id);
		try {
			productRepository.save(product);

		} catch (Exception e) {
			logger.error(e + " Exception occured while updating product with productId: " + product.getProductId());
			return false;
		}
		logger.info("updateProduct() method completed  with productId: " + id);
		return true;
	}

	public boolean deleteProduct(int id) {
		logger.info("deleteProduct() method accessed with productId: " + id);

		try {
			productRepository.deleteById(id);
			

		} catch (Exception e) {
			logger.error("Exception occured while deleting product with productId: " + id);
			return false;
		}

		logger.info("deleteProduct() method completed with productId: " + id);
		return true;
	}

	public Page<Product> getProductsUsingParameter(String searchString, String orderBy, String maxRecord,
			String startAt) throws Exception {
		logger.info("getProductsUsingParameter() method accessed in ProductUtil class");

		Page<Product> products = null;
		if (searchString != null && orderBy != null && maxRecord != null && startAt != null) {
			Pageable paging = null;

			if (maxRecord.length() != 0 && startAt.length() != 0 && Integer.parseInt(maxRecord) > -1
					&& Integer.parseInt(startAt) > -1) {
				if (orderBy.toLowerCase().equals("desc")) {
					paging = PageRequest.of(Integer.parseInt(startAt), Integer.parseInt(maxRecord),
							Sort.by("productName").descending());
				} else {
					paging = PageRequest.of(Integer.parseInt(startAt), Integer.parseInt(maxRecord),
							Sort.by("productName").ascending());
				}
				products = productRepository.findByProductName(searchString, paging);
			} else {
				throw new RestRequestException("Invalid value for maxRecord or startAt");
			}
		}

		logger.info("method completed");

		return products;
	}

}
