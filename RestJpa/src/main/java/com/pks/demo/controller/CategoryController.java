package com.pks.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pks.demo.exception.ProductNotFoundException;
import com.pks.demo.model.Category;
import com.pks.demo.service.impl.CategoryServiceImpl;

@RestController
public class CategoryController {
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@RequestMapping(value="/categories", method=RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> categories = new ArrayList<>();
		try {
			categories = categoryServiceImpl.getAllCategory();
		} catch (ProductNotFoundException e) {
			throw new ProductNotFoundException("No Categories Found");
		}
		if (categories.isEmpty()) {
			throw new ProductNotFoundException("No Categories Found");
		}
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories/{id}", method=RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable Long id) {
		Category category = null;
		try {
			category = categoryServiceImpl.getCategory(id);
		} catch (ProductNotFoundException e) {
			throw new ProductNotFoundException("No Category found for the categoryId: " + id);
		}
		if (category == null) {
			throw new ProductNotFoundException("No Category found for the categoryId: " + id);
		}
		return new ResponseEntity<Category>(category, HttpStatus.OK);

	}

	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
		if (categoryServiceImpl.saveCategory(category)) {
			return new ResponseEntity<Category>(category, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Category>(category, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable Long id) {
		if (categoryServiceImpl.updateCategory(id, category)) {
			return new ResponseEntity<Category>(category, HttpStatus.OK);
		} else {
			return new ResponseEntity<Category>(category, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
		if (categoryServiceImpl.deleteCategory(id)) {
			return new ResponseEntity<Object>("Category deleted for categoryId: "+id, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Category not deleted for categoryId: "+id, HttpStatus.BAD_REQUEST);
		}
	}
	
}
