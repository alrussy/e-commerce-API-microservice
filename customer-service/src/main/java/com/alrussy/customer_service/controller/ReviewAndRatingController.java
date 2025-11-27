package com.alrussy.customer_service.controller;

import com.alrussy.customer_service.dto.RatingSummaryProjiction;
import com.alrussy.customer_service.entity.ReviewAndRating;
import com.alrussy.customer_service.service.ReviewAndRatingServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/customers/review-rating")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReviewAndRatingController {

    final ReviewAndRatingServiceImpl service;

    @GetMapping("/{username}")
    public ResponseEntity<List<ReviewAndRating>> findByUsernameAndSkuCode(@PathVariable String username) {
        return ResponseEntity.ok(service.findByUsername(username));
    }

    @GetMapping("/one-review")
    public ResponseEntity<ReviewAndRating> findByUsernameAndSkuCode(
            @RequestParam String username, @RequestParam String skuCode) {
        System.out.println(username + " " + skuCode);
        return ResponseEntity.ok(service.findByUsernameAndSkuCode(username, skuCode));
    }

    @GetMapping("/sku-code/{skuCode}")
    public ResponseEntity<Page<ReviewAndRating>> findReviewsAndRatingsBySkuCode(
            @PathVariable String skuCode, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(service.findBySkuCode(skuCode, pageNumber, pageSize));
    }

    @GetMapping("/summary-ratings/sku-code/{skuCode}")
    public ResponseEntity<RatingSummaryProjiction> findRatingsSummarysBySkuCode(@PathVariable String skuCode) {
        return ResponseEntity.ok(service.findRatingsSummary(skuCode));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody ReviewAndRating request) {
        System.out.println("requset is controller");
        return ResponseEntity.ok(service.save(request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String username, @RequestParam Long id) {
        service.deleteByIdAndUsername(id, username);
        ;
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
