package cn.hl.admin.modules.ums.dto;

import cn.hl.common.dto.BasePageDTO;
import cn.hl.common.filter.FilterEnum;
import cn.hl.common.filter.FilterQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminPageDTO extends BasePageDTO {

    @ApiModelProperty(value = "用户名称")
    @FilterQuery(field = "username", filterEnum = FilterEnum.LIKE)
    private String username;
}
