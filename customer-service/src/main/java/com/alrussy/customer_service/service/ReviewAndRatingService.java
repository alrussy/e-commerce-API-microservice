package com.alrussy.customer_service.service;

import org.springframework.data.domain.Page;

import com.alrussy.customer_service.dto.RatingSummaryProjiction;
import com.alrussy.customer_service.entity.ReviewAndRating;

public interface ReviewAndRatingService extends BaseService<Long, ReviewAndRating, ReviewAndRating>{

	RatingSummaryProjiction findRatingsSummary(String skuCode);

	ReviewAndRating findByUsernameAndSkuCode(String username, String skuCode);


	Page<ReviewAndRating> findBySkuCode(String skuCode, Integer pageNumber, Integer pageSize);

}
