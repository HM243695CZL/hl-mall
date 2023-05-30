package cn.hl.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class BaseDTO {

    @ApiModelProperty(value = "页码")
    private Integer pageIndex;

    @ApiModelProperty(value = "条数")
    private Integer pageSize;
}
