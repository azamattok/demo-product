package kz.test.good.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "FileInfoDto", description = "Модель файла")
public class FileInfoDto {
    @ApiModelProperty(value = "Гуид файла")
    private String uuid;
    @ApiModelProperty(value = "Наименование файла")
    private String name;
    @ApiModelProperty(value = "Тип файла")
    private String type;
    @ApiModelProperty(value = "Ссылка на файл")
    private String url;
    @ApiModelProperty(value = "Размер файла")
    private long size;
    @ApiModelProperty(value = "Данные файла")
    @JsonIgnore
    private byte[] data;
}
