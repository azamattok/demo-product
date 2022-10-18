package kz.test.good.service;

import kz.test.good.dto.ProductDto;
import kz.test.good.dto.ProductFilterDto;
import kz.test.good.dto.PageDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto dto);

    ProductDto get(Long id);

    ProductDto update(Long id, ProductDto dto);

    void delete(Long id);

    PageDto<ProductDto> findByFilter(ProductFilterDto dto, PageRequest request);

    PageDto<ProductDto> findAll(PageRequest request);

    Long addPhoto(Long productId, String photoUUid);
}
