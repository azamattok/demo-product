package kz.test.good.service.impl;

import kz.test.good.converter.ProductConverter;
import kz.test.good.domain.Product;
import kz.test.good.dto.ProductDto;
import kz.test.good.dto.ProductFilterDto;
import kz.test.good.dto.PageDto;
import kz.test.good.repository.ProductRepository;
import kz.test.good.repository.filter.ProductSpecifications;
import kz.test.good.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductConverter converter;
    private final ProductRepository repository;

    public ProductServiceImpl(ProductConverter converter, ProductRepository repository) {
        this.converter = converter;
        this.repository = repository;
    }

    @Override
    public ProductDto create(ProductDto dto) {
        Product product = repository.save(converter.toEntity(dto));
        return converter.toDTO(product);
    }

    @Override
    public ProductDto get(Long id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Element not found"));
        return converter.toDTO(product);
    }

    @Override
    public PageDto<ProductDto> findByFilter(ProductFilterDto dto, PageRequest request) {
        Specification<Product> specification = ProductSpecifications.filterGood(dto);
        Page<Product> goods = repository.findAll(specification, request);

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : goods) {
            productDtos.add(converter.toDTO(product));
        }
        return new PageDto<>(productDtos, request.getPageNumber(),  goods.getTotalElements(), request.getPageSize());
    }

    @Override
    public PageDto<ProductDto> findAll(PageRequest request) {
        Page<Product> goods = repository.findAll(request);

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : goods) {
            productDtos.add(converter.toDTO(product));
        }
        return new PageDto<>(productDtos, request.getPageNumber(),  goods.getTotalElements(), request.getPageSize());
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product product = repository.save(converter.toEntity(dto));
        return converter.toDTO(product);
    }

    @Override
    public void delete(Long id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Element not found"));
        repository.delete(product);
    }

    @Override
    public Long addPhoto(Long productId, String photoUUid) {
        Product product = repository.findById(productId).orElseThrow(
                () -> new NoSuchElementException("Element not found"));
        product.setPhoto(photoUUid);
        return product.getUid();
    }
}
