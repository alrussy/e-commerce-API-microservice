package com.alrussy.customer_service.dto;

import java.util.List;

public record ReviewAndRatingWithCountAndAvrgRating(
		Long countReviews,Double avrgRating,List<ReviewAndRatingResponse> reviews) {

	
}
