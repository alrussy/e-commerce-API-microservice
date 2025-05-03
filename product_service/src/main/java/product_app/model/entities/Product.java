package product_app.model.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.model.entities.id.ProductId;
import product_app.model.entities.table.BrandCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@EntityListeners(AuditingEntityListener.class)
public class Product extends Audition {

    @EmbeddedId
    private ProductId id;

    private String name;
    private Boolean isFeature;
    private String imageUrl;
    private String about;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToOne
    @JoinColumnsOrFormulas(
            value = {
                @JoinColumnOrFormula(formula = @JoinFormula(value = "category_id")),
                @JoinColumnOrFormula(column = @JoinColumn(name = "departmentId"))
            })
    private Department department;

    @ManyToOne(targetEntity = BrandCategory.class, fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas(
            value = {
                @JoinColumnOrFormula(formula = @JoinFormula(value = "category_id")),
                @JoinColumnOrFormula(formula = @JoinFormula(value = "brand_id"))
            })
    private BrandCategory brandCategory;

    public ProductResponse mapToproductResponseWithCategoryBrandAndDepartment() {
        return new ProductResponse(
                id.getProductId(),
                name,
                isFeature,
                imageUrl,
                department == null ? null : department.getCategory().mapToCategoryResponseWithDetailsNameOutBrand(),
                department == null ? null : department.mapToDepartmentResponseOutCategory(),
                brand.mapToBrandResponseOutCategory(),
                about);
    }

    public ProductResponse mapToproductResponseOutCategory() {
        return new ProductResponse(
                id.getProductId(),
                name,
                isFeature,
                imageUrl,
                null,
                department.mapToDepartmentResponseOutCategory(),
                brand.mapToBrandResponseOutCategory(),
                about);
    }

    public ProductResponse mapToproductResponseOutCategoryBrandDepartmentAndDetails() {
        return new ProductResponse(
                id.getProductId(),
                name,
                isFeature,
                imageUrl,
                null, // category
                null,
                null,
                about);
    }

    public ProductResponse mapToproductResponseOutBrand() {
        return new ProductResponse(
                id.getProductId(),
                name,
                isFeature,
                imageUrl,
                department.getCategory().mapToCategoryResponseOutDetailsNameAndBrand(), // category
                department.mapToDepartmentResponseOutCategory(), // department
                null,
                about);
    }

    public ProductResponse mapToproductResponseOutDepartment() {
        return new ProductResponse(
                id.getProductId(),
                name,
                isFeature,
                imageUrl,
                department.getCategory().mapToCategoryResponseOutDetailsNameAndBrand(),
                null,
                brand.mapToBrandResponseOutCategory(),
                about);
    }

    public ProductResponse mapToproductResponseWithDepartment() {
        return new ProductResponse(
                id.getProductId(),
                name,
                isFeature,
                imageUrl,
                null,
                department.mapToDepartmentResponseOutCategory(),
                null,
                about);
    }

    public ProductResponse mapToproductResponseWithBrand() {
        return new ProductResponse(
                id.getProductId(), name, isFeature, imageUrl, null, null, brand.mapToBrandResponseOutCategory(), about);
    }

    public ProductResponse mapToproductResponseWithCategory() {
        return new ProductResponse(
                id.getProductId(),
                name,
                isFeature,
                imageUrl,
                department.getCategory().mapToCategoryResponseWithDetailsNameOutBrand(),
                null,
                null,
                about);
    }
}
