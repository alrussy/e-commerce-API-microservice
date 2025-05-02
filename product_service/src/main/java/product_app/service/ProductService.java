package product_app.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import product_app.mapper.ProductMapper;
import product_app.model.dto.PageResponse;
import product_app.model.dto.product.ProductResponse;
import product_app.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

	final ProductRepository repository;
	 int pageSize=10;
	
	
	
	public PageResponse<ProductResponse> findAll(Integer pageNumber) {
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		var page= repository.findAll(pageable).map(ProductMapper::fromEntity);
		return ProductMapper.fromPage(page);
	}
}
