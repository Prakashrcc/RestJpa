package com.pks.demo.service;

import java.util.List;

import com.pks.demo.model.Category;

public interface CategoryService {
	public List<Category> getAllCategory();

	public Category getCategory(Long id);

	public boolean saveCategory(Category category);

	public boolean updateCategory(Long id, Category category);

	public boolean deleteCategory(Long id);
}
