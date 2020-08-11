package com.pks.demo.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pks.demo.exception.ProductNotFoundException;
import com.pks.demo.exception.RestRequestException;
import com.pks.demo.model.Product;
import com.pks.demo.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@RequestMapping(value="/products",  method = RequestMethod.GET )
	public ResponseEntity<List<Product>> getProducts() throws ClassNotFoundException, SQLException {

		List<Product> products = null;
		products = productService.getProducts();

		if (products.isEmpty()) {
			throw new ProductNotFoundException("No Product found ");
		}

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

	}

	@RequestMapping(value = "/products", params = { "orderBy", "searchString", "maxRecord",
			"startAt" }, method = RequestMethod.GET)
	public Page<Product> getProductuse(@RequestParam("searchString") String searchString,
			@RequestParam("orderBy") String orderBy, @RequestParam("maxRecord") String maxRecord,
			@RequestParam("startAt") String startAt) throws Exception  {
		Page<Product> products=null;
		try {
		products = productService.getProductsUsingParameter(searchString, orderBy, maxRecord, startAt);
		}
		catch (RestRequestException e) {
			throw new RestRequestException("Invalid value for maxRecord or startAt");
		}

		return products;
	}

	@RequestMapping(value="/products/{id}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable int id) throws ClassNotFoundException, SQLException {
		Product product = productService.getProduct(id);
		if (product == null) {
			throw new ProductNotFoundException("No Product found for the id: " + id);
		}
		return product;
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<Product> createProduct(@RequestBody Product product) throws Exception {

		boolean bool = productService.createProduct(product);

		if (bool) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			throw new RestRequestException("Product adding failed for the id : " + "!!");

		}

	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable int id)
			throws ClassNotFoundException, SQLException {

		boolean bool = productService.updateProduct(product, id);
		if (bool) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}

		throw new ProductNotFoundException("Product updating failed for the  id : " + id);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public String deleteProduct(@PathVariable int id) {

		boolean bool = productService.deleteProduct(id);
		if (bool) {
			return "Product deleted with id : " + id;
		} else {
			throw new ProductNotFoundException(
					"Product deletion failed for the  id : " + id + "!!  No Product found with id : " + id);
		}
	}

/*	@RequestMapping("/")
	public void exception() {
		throw new RestRequestException("No mapping/resource found for the end point {/}");
	}
*/
	
}
