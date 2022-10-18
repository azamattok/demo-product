package kz.test.good.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.test.good.dto.ProductDto;
import kz.test.good.dto.ProductFilterDto;
import kz.test.good.dto.PageDto;
import kz.test.good.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/products")
@ApiModel(description = "Контроллер для работы с товарами")
@CrossOrigin
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение товара по id")
    public ResponseEntity<ProductDto> get(@ApiParam(value = "ID товара")  @PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(this.service.get(id));
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping()
    @ApiOperation(value = "Создание товара")
    public ResponseEntity<ProductDto> create(@ApiParam(value = "Dto товара")  @RequestBody ProductDto dto) {
        return ResponseEntity.ok().body(service.create(dto));
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Обновление товара по id")
    public ResponseEntity<ProductDto> update(@ApiParam(value = "ID товара") @PathVariable Long id,
                                             @ApiParam(value = "Dto товара")   @RequestBody ProductDto dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @PostMapping("/addPhoto/{id}")
    @ApiOperation(value = "Сохранение идентификатора фотографии по айди товара")
    public ResponseEntity<Long> addPhoto(@PathVariable("id") Long productId,
                                         @RequestParam String photoUUid) {
        Long result = service.addPhoto(productId, photoUUid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление товара по id")
    public ResponseEntity<Void> delete(@ApiParam(value = "ID товара") @PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/filter")
    public ResponseEntity<PageDto<ProductDto>> findByFilter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(value = "size", defaultValue = "3") Integer size,
                                                            @RequestBody ProductFilterDto dto) {
        PageRequest request = PageRequest.of(page - 1, size);
        return ResponseEntity.ok().body(service.findByFilter(dto, request));
    }

    @GetMapping("/all")
    public ResponseEntity<PageDto<ProductDto>> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return ResponseEntity.ok().body(service.findAll(request));
    }
}
