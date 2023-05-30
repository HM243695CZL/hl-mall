package cn.hl.admin.modules.ums.dto;

import cn.hl.common.dto.BaseDTO;
import cn.hl.common.filter.FilterEnum;
import cn.hl.common.filter.FilterQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageRoleDTO extends BaseDTO {

    @ApiModelProperty(value = "角色名称")
    @FilterQuery(field = "`name`", filterEnum = FilterEnum.LIKE)
    private String name;
}
