package cn.hl.admin.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 设置角色对应菜单权限的DTO
 */
@Data
public class AuthMenuDTO {

    @ApiModelProperty(value = "菜单id数组")
    private List<String> menuIds;

    @ApiModelProperty(value = "角色id")
    private String id;
}
