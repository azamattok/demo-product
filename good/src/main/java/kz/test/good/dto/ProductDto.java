package kz.test.good.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductDto {
    @ApiModelProperty(value = "ID товара")
    private Long uid;

    @ApiModelProperty(value = "Наименование товара")
    private String name;

    @ApiModelProperty(value = "Описание товара")
    private String description;

    @ApiModelProperty(value = "Количество товара")
    private int quantity;

    @ApiModelProperty(value = "Цена товара")
    private BigDecimal price;

    @ApiModelProperty(value = "Фото товара")
    private String photo;
}
