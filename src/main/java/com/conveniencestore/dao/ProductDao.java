package com.conveniencestore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.conveniencestore.model.Product;

public interface ProductDao extends JpaRepository<Product, Long> {
	List<Product> findByNameContaining(String name);
}
