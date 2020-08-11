package com.pks.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.pks.demo.model.RestUser;

public interface RestUserRepository extends CrudRepository<RestUser, String> {

}
