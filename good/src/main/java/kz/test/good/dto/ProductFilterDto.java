package kz.test.good.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "GoodFilterDto", description = "Фильтрация товаров")
public class ProductFilterDto {
    @ApiModelProperty(value = "ID товара")
    private Long uid;

    @ApiModelProperty(value = "Наименование товара")
    private String name;
}
