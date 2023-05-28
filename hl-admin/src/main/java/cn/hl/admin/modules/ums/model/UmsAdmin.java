package cn.hl.admin.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_admin")
@ApiModel(value="UmsAdmin对象", description="管理员表")
public class UmsAdmin implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "管理员名称")
    private String username;

    @ApiModelProperty(value = "管理员密码")
    private String password;

    @ApiModelProperty(value = "0：男 1：女")
    private Integer sex;

    @ApiModelProperty(value = "wxopenid")
    private String wxOpenId;

    @ApiModelProperty(value = "最近一次登录IP地址")
    private String lastLoginIp;

    @ApiModelProperty(value = "最近一次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "头像图片")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;


}
