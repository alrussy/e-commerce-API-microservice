package com.alrussy.customer_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alrussy.customer_service.dto.RatingSummaryProjiction;
import com.alrussy.customer_service.entity.ReviewAndRating;

public interface ReviewAndRatingRepository extends JpaRepository<ReviewAndRating, Long> {

	@Query(value = "SELECT AVG(e.rating)avgRatings,count(*) countReviews FROM customer_service.review_and_rating e where e.sku_code = ?1 " , nativeQuery = true)
	RatingSummaryProjiction getRatingsSummary(String skuCode);
	
	Page<ReviewAndRating> findBySkuCode(String skuCode,Pageable page);
	Optional<ReviewAndRating> findByUsernameAndSkuCode(String username,String skuCode) ;
	List<ReviewAndRating> findByUsername(String username);
	int deleteByIdAndUsername(Long id,String username);
}
