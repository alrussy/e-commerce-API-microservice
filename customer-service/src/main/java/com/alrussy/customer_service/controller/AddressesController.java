package com.alrussy.customer_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alrussy.customer_service.entity.Address;
import com.alrussy.customer_service.service.AddressesServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers/addresses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AddressesController {

	private final AddressesServiceImpl service;

	
	@GetMapping("/{username}")
	public ResponseEntity<List<Address>>  findAddressesByUsername(@PathVariable String username){
		return ResponseEntity.ok(service.findByUsername(username));
	}
	
	@PostMapping
	public ResponseEntity<Long> save(@RequestBody Address request) {
		return ResponseEntity.ok(service.save(request));
	}
	public ResponseEntity<Void> delete(@RequestParam String username,@RequestParam Long id) {
		service.deleteByIdAndUsername(id,username);
		return ResponseEntity.status(HttpStatus.OK).build();

	}
	

}
