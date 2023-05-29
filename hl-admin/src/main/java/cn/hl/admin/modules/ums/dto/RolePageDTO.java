package cn.hl.admin.modules.ums.dto;

import cn.hl.admin.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RolePageDTO extends BaseDTO {

    @ApiModelProperty(value = "角色名称")
    private String name;
}
