package cn.hl.admin.modules.ums.service.impl;

import cn.hl.admin.modules.ums.dto.AuthMenuDTO;
import cn.hl.admin.modules.ums.dto.PageRoleDTO;
import cn.hl.admin.modules.ums.mapper.UmsRoleMapper;
import cn.hl.admin.modules.ums.model.UmsRole;
import cn.hl.admin.modules.ums.model.UmsRoleMenu;
import cn.hl.admin.modules.ums.service.UmsRoleMenuService;
import cn.hl.admin.modules.ums.service.UmsRoleService;
import cn.hl.common.filter.FilterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    private UmsRoleMenuService roleMenuService;

    @Override
    public Page<UmsRole> pageList(PageRoleDTO roleDTO) throws IllegalAccessException {
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        FilterUtil.convertQuery(queryWrapper, roleDTO);
        Page<UmsRole> page = new Page<>(roleDTO.getPageIndex(), roleDTO.getPageSize());
        Page<UmsRole> roleList = page(page, queryWrapper);
        int count = count(queryWrapper);
        roleList.setTotal(count);
        return roleList;
    }

    /**
     * 根据角色id查询已分配的权限
     * @param id
     * @return
     */
    @Override
    public List<String> viewAuth(String id) {
        QueryWrapper<UmsRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsRoleMenu::getRoleId, id);
        return roleMenuService.list(queryWrapper.select("menu_id")).stream().map(UmsRoleMenu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 分配权限
     * @param authMenuDTO
     * @return
     */
    @Transactional
    @Override
    public Boolean authMenu(AuthMenuDTO authMenuDTO) {
        // 先清空之前的授权
        QueryWrapper<UmsRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsRoleMenu::getRoleId, authMenuDTO.getId());
        roleMenuService.remove(queryWrapper);

        List<UmsRoleMenu> roleMenuList = setRoleAndMenuRelation(authMenuDTO.getMenuIds(), authMenuDTO.getId());
        return roleMenuService.saveBatch(roleMenuList);
    }

    /**
     * 设置角色和菜单的关联关系
     * @param menuIds
     * @param roleId
     * @return
     */
    private List<UmsRoleMenu> setRoleAndMenuRelation(List<String> menuIds, String roleId) {
        ArrayList<UmsRoleMenu> roleMenuList = new ArrayList<>();
        for (String menuId : menuIds) {
            UmsRoleMenu roleMenu = new UmsRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        return roleMenuList;
    }
}
