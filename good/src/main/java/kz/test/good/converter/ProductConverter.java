package kz.test.good.converter;

import kz.test.good.domain.Product;
import kz.test.good.dto.ProductDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ProductConverter {
    public abstract ProductDto toDTO(Product source);

    public abstract Product toEntity(ProductDto source);
}
