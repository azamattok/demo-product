package kz.test.good.repository.filter;

import kz.test.good.domain.Product;
import kz.test.good.dto.ProductFilterDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.EscapeCharacter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static kz.test.good.config.Constants.CONTAINING_LIKE;
import static kz.test.good.config.Constants.ESCAPE_CHAR;

public class ProductSpecifications {
    public static Specification<Product> filterGood(ProductFilterDto dto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(dto)) {
                if (nonNull(dto.getUid())) {
                    predicates.add(cb.equal(root.get("uid"), dto.getUid()));
                }
                if (nonNull(dto.getName())) {
                    String value = dto.getName();
                    predicates.add(cb.like(cb.lower(root.get("name")), String.format(CONTAINING_LIKE, EscapeCharacter.of(ESCAPE_CHAR).escape(value.toLowerCase())), ESCAPE_CHAR));
                }

            }
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
