package cn.hl.admin.modules.ums.dto;

import cn.hl.common.dto.BasePageDTO;
import cn.hl.common.filter.FilterEnum;
import cn.hl.common.filter.FilterQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RolePageDTO extends BasePageDTO {

    @ApiModelProperty(value = "角色名称")
    @FilterQuery(field = "`name`", filterEnum = FilterEnum.LIKE)
    private String name;
}
