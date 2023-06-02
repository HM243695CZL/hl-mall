package cn.hl.admin.modules.ums.service;

import cn.hl.admin.modules.ums.dto.AuthMenuDTO;
import cn.hl.admin.modules.ums.dto.RolePageDTO;
import cn.hl.admin.modules.ums.model.UmsRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
public interface UmsRoleService extends IService<UmsRole> {

    Page<UmsRole> pageList(RolePageDTO roleDTO) throws IllegalAccessException;

    /**
     * 根据角色id查询已分配的权限
     * @param id
     * @return
     */
    List<String> viewAuth(String id);

    /**
     * 分配权限
     * @param authMenuDTO
     * @return
     */
    Boolean authMenu(AuthMenuDTO authMenuDTO);
}
