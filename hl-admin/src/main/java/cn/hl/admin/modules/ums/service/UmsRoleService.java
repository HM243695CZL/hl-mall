package cn.hl.admin.modules.ums.service;

import cn.hl.admin.modules.ums.dto.PageRoleDTO;
import cn.hl.admin.modules.ums.model.UmsRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
public interface UmsRoleService extends IService<UmsRole> {

    Page<UmsRole> pageList(PageRoleDTO roleDTO) throws IllegalAccessException;
}
