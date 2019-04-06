package com.conveniencestore.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conveniencestore.dao.ProductDao;
import com.conveniencestore.model.Product;

@RestController
public class ProductController {

	@Autowired
	private ProductDao productDao;

	@GetMapping("/products")
	public List<Product> getProducts(@RequestParam(value="page", required=false) Integer page, @RequestParam(value="count", required=false) Integer count) {
		List<Product> products = productDao.findAll();
		if (page == null) page = 1;
		if (count == null) count = 30;

		System.out.println(page);
		System.out.println(count);

		return products;
	}
}
