package com.alrussy.customer_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alrussy.customer_service.dto.RatingSummaryProjiction;
import com.alrussy.customer_service.entity.ReviewAndRating;
import com.alrussy.customer_service.repository.ReviewAndRatingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewAndRatingServiceImpl implements ReviewAndRatingService {

	private final ReviewAndRatingRepository repository;	

	
	@Override
	public RatingSummaryProjiction findRatingsSummary(String skuCode)  {
		return repository.getRatingsSummary(skuCode);
	}
	
	@Override
	public ReviewAndRating findByUsernameAndSkuCode(String username, String skuCode)  {
		return repository.findByUsernameAndSkuCode(username, skuCode).orElse(ReviewAndRating.builder().username(username).skuCode(skuCode).rating(0.0).review("").build());
	}
	
	@Override
	public Page<ReviewAndRating> findBySkuCode(String skuCode,Integer pageNumber,Integer pageSize)  {
		Pageable pageable=PageRequest.of(pageNumber,pageSize,Sort.by("craetedDate").ascending());
		return repository.findBySkuCode(skuCode,pageable);
	}

	@Override
	public List<ReviewAndRating> findByUsername(String username) {
		return repository.findByUsername(username);
	}


	@Override
	public void deleteByIdAndUsername(Long id, String userName) {
		deleteByIdAndUsername(id, userName);
	}


	@Override
	public Long save(ReviewAndRating request) {
		return repository.save(request).getId();
	}
	
		
}
