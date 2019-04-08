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
		if (page == null) page = 1;
		if (count == null) count = 30;

		List<Product> products = productDao.findAll();

		int baseIndex = (page - 1) * count;
		if (baseIndex >= products.size()) return null;
		int lastIndex = Math.min(baseIndex + count - 1, products.size() - 1);
		return products.subList(baseIndex, lastIndex);
	}
}
