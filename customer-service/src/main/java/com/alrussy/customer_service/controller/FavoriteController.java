package com.alrussy.customer_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alrussy.customer_service.entity.Favorite;
import com.alrussy.customer_service.service.FavoritesServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers/favorites")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FavoriteController {

	final FavoritesServiceImpl service;

	@GetMapping("/{username}")
	public ResponseEntity<List<Favorite>>  findByUsername(@PathVariable String username){
		return ResponseEntity.ok(service.findByUsername(username));
	}
	
	@PostMapping
	public ResponseEntity<Long> save(@RequestBody Favorite request) {
		return ResponseEntity.ok(service.save(request));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestParam String username,@RequestParam String skuCode) {
		service.deleteByIdAndUsername(skuCode,username);
		return ResponseEntity.status(HttpStatus.OK).build();

	}
}
