package com.centraltec.sdsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centraltec.sdsdeliver.dto.ProductDTO;
import com.centraltec.sdsdeliver.entities.Product;
import com.centraltec.sdsdeliver.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		List<Product> list = repo.findAllByOrderByNameAsc();
		return list.stream().map(x-> new ProductDTO(x)).collect(Collectors.toList());
	}
	
	
}
