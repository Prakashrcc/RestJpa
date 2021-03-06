package com.pks.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pks.demo.exception.ProductNotFoundException;
import com.pks.demo.model.Category;

import com.pks.demo.repository.CategoryRepository;
import com.pks.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Override
	public List<Category> getAllCategory() {
		logger.info("getAllCategory() method accessed");
		List<Category> categories = new ArrayList<>();
		try {
			categoryRepository.findAll().forEach(categories::add);
		} catch (Exception e) {
			logger.warn(e + " Exception occured in getAllCategory() method");
			throw new ProductNotFoundException("");
		}
		logger.info("getAllCategory() method completed");
		return categories;
	}

	@Override
	public Category getCategory(Long id) {
		logger.info("getCategory() method accessed for categoryId: " + id);
		Category category = null;
		try {
			category = categoryRepository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.warn(e + " Exception occured in getCategory() method for categoryId: " + id);
			throw new ProductNotFoundException("");
		}
		logger.info("getCategory() method completed for categoryId: " + id);
		return category;
	}

	@Override
	public boolean saveCategory(Category category) {
		logger.info("saveCategory() method accessed for categoryId: " + category.getCategoryId());
		try {
			categoryRepository.save(category);
		} catch (Exception e) {
			logger.warn(e + " Exception occured in saveCategory() method for categoryId: " + category.getCategoryId());
			return false;

		}
		logger.info("saveCategory() method completed for categoryId: " + category.getCategoryId());
		return true;
	}

	@Override
	public boolean updateCategory(Long id, Category category) {
		logger.info("updateCategory() method accessed for categoryId: " + id);
		try {
			categoryRepository.save(category);
		} catch (Exception e) {
			logger.warn(e + " Exception occured in updateCategory() method for categoryId: " + id);
			return false;

		}
		logger.info("updateCategory() method completed for categoryId: " + id);
		return true;
	}

	@Override
	public boolean deleteCategory(Long id) {
		logger.info("deleteCategory() method accessed for categoryId: " + id);
		try {
			categoryRepository.deleteById(id);
		} catch (Exception e) {
			logger.warn(e + " Exception occured in deleteCategory() method for categoryId: " + id);
			return false;

		}
		logger.info("deleteCategory() method completed for categoryId: " + id);
		return true;
	}

}
