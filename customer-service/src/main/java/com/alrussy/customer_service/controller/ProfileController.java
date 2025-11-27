package com.alrussy.customer_service.controller;

import com.alrussy.customer_service.entity.Profile;
import com.alrussy.customer_service.service.ProfileService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/customers/profile")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProfileController {

    final ProfileService service;

    @GetMapping("/{username}")
    public ResponseEntity<Profile> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.findById(username));
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Profile request) {
        return ResponseEntity.ok(service.save(request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String username) {
        service.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
