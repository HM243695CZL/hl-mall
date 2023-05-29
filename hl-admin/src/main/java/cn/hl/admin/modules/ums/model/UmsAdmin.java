package cn.hl.admin.modules.ums.model;

import cn.hl.admin.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_admin")
@ApiModel(value="UmsAdmin对象", description="管理员表")
public class UmsAdmin extends BaseModel {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "管理员名称")
    @NotBlank(message = "用户名称不能为空")
    private String username;

    @ApiModelProperty(value = "管理员密码", hidden = true)
    private String password;

    @ApiModelProperty(value = "0：男 1：女")
    private Integer sex;

    @ApiModelProperty(value = "wxopenid", hidden = true)
    private String wxOpenId;

    @ApiModelProperty(value = "最近一次登录IP地址", hidden = true)
    private String lastLoginIp;

    @ApiModelProperty(value = "最近一次登录时间", hidden = true)
    private Date lastLoginTime;

    @ApiModelProperty(value = "头像图片")
    private String avatar;

}
