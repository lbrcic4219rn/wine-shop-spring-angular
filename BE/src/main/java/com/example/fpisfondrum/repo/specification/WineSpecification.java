package com.example.fpisfondrum.repo.specification;

import com.example.fpisfondrum.model.Wine;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class WineSpecification {
    public static Specification<Wine> hasVarietyIn(List<Long> varietyIds) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("variety").get("id")).value(varietyIds);
    }

    public static Specification<Wine> hasStyleIn(List<Long> styleIds) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("style").get("id")).value(styleIds);
    }
}
