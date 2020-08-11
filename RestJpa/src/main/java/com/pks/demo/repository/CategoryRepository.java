package com.pks.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.pks.demo.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
