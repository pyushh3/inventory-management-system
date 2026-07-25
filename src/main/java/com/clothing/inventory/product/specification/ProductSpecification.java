package com.clothing.inventory.product.specification;

import com.clothing.inventory.product.enums.Gender;
import com.clothing.inventory.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;
public class ProductSpecification {

    public static Specification<Product> notDeleted() {

        return (root, query, cb) ->
                cb.isFalse(root.get("deleted"));
    }

    public static Specification<Product> hasCategory(Long categoryId) {

        return (root, query, cb) -> {

            if (categoryId == null) {
                return null;
            }

            return cb.equal(
                    root.get("category").get("id"),
                    categoryId);
        };
    }

    public static Specification<Product> hasBrand(Long brandId) {

        return (root, query, cb) -> {

            if (brandId == null) {
                return null;
            }

            return cb.equal(
                    root.get("brand").get("id"),
                    brandId);
        };
    }

    public static Specification<Product> hasGender(Gender gender) {

        return (root, query, cb) -> {

            if (gender == null) {
                return null;
            }

            return cb.equal(root.get("gender"), gender);
        };
    }

    public static Specification<Product> search(String search) {

        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {
                return null;
            }

            String value = "%" + search.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), value),
                    cb.like(cb.lower(root.get("sku")), value)
            );
        };
    }
}

