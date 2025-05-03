package product_app.model.dto.brand_dto.projections;

import java.util.List;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.dto.category_dto.CategoryResponse;

public interface BrandProjectionGlobal {

    Long getId();

    String getName();

    String getImage_Url();

    Boolean getIs_Feature();

    Integer getProduct_count();

    List<CategoriesView> getCategory_names();

    interface CategoriesView {
        String getCategory_name();

        default CategoryResponse mapToCategoryResponse() {
            return new CategoryResponse(null, getCategory_name(), null, null, null, null);
        }
    }
    ;

    default BrandResponse mapToBrndResponse() {
        return new BrandResponse(
                getId(),
                getName(),
                getImage_Url(),
                getIs_Feature(),
                getProduct_count(),
                getCategory_names() != null
                        ? getCategory_names().stream()
                                .map(t -> t.mapToCategoryResponse())
                                .toList()
                        : null);
    }
}
