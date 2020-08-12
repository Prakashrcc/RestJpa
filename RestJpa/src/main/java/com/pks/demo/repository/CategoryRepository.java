package com.pks.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.pks.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
