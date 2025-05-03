package product_app.model.dto.brand_dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product_app.model.entities.Brand;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {

    private String name;
    private String imageUrl;
    private List<Long> categoryIds;
    private Boolean isFeature;

    public Brand mapToBrand() {
        return Brand.builder()
                .name(name)
                .imageUrl(imageUrl)
                .isFeature(isFeature)
                .build();
    }
}
