package product_app;

import com.alrussy.product_service.ProductServiceApplication;
import org.springframework.boot.SpringApplication;

public class TestProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
