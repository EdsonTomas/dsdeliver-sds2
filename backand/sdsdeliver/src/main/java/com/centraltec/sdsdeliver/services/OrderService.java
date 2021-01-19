package com.centraltec.sdsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centraltec.sdsdeliver.dto.OrderDTO;
import com.centraltec.sdsdeliver.dto.ProductDTO;
import com.centraltec.sdsdeliver.entities.Order;
import com.centraltec.sdsdeliver.entities.OrderStatus;
import com.centraltec.sdsdeliver.entities.Product;
import com.centraltec.sdsdeliver.repositories.OrderRepository;
import com.centraltec.sdsdeliver.repositories.ProductRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findAll(){
		List<Order> list = repo.findAllWithProducts();
		return list.stream().map(x-> new OrderDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(), OrderStatus.PENDING);
		for(ProductDTO p: dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		order = repo.save(order);
		return new OrderDTO(order);
	}
	@Transactional
	public OrderDTO setDelivered(Long id) {
		Order order = repo.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = repo.save(order);
		return new OrderDTO(order);

	}
}
