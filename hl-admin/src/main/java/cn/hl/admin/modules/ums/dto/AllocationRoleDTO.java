package cn.hl.admin.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分配角色
 */
@Data
public class AllocationRoleDTO {

    @ApiModelProperty(value = "角色id数组")
    private List<String> roleIds;

    @ApiModelProperty(value = "用户id")
    private String id;
}
