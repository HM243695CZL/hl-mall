package cn.hl.admin.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 后台用户和角色关系表
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_admin_role")
@ApiModel(value="UmsAdminRole对象", description="后台用户和角色关系表")
public class UmsAdminRole implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "管理员id")
    private String adminId;

    @ApiModelProperty(value = "角色id")
    private String roleId;


}
