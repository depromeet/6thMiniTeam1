package com.conveniencestore.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conveniencestore.dao.ProductDao;
import com.conveniencestore.model.Product;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductDao productDao;

	@GetMapping("/products")
	public List<Product> getproducts(){
		List<Product> products = productDao.findAll();
		return products;
	}

}
