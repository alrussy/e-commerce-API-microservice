package product_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import product_app.model.entities.Product;
import product_app.model.entities.id.ProductId;

public interface ProductRepository extends JpaRepository<Product, ProductId> {

}
