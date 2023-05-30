package cn.hl.admin.modules.ums.model;

import cn.hl.common.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_role")
@ApiModel(value="UmsRole对象", description="角色表")
public class UmsRole extends BaseModel {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "角色名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "角色key")
    @TableField("`key`")
    private String key;

    @ApiModelProperty(value = "角色描述")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;
}
