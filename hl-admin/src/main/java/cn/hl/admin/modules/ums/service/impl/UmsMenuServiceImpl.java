package cn.hl.admin.modules.ums.service.impl;

import cn.hl.admin.modules.ums.model.UmsMenu;
import cn.hl.admin.modules.ums.mapper.UmsMenuMapper;
import cn.hl.admin.modules.ums.model.UmsRole;
import cn.hl.admin.modules.ums.model.UmsRoleMenu;
import cn.hl.admin.modules.ums.service.UmsMenuService;
import cn.hl.admin.modules.ums.service.UmsRoleMenuService;
import cn.hl.admin.modules.ums.service.UmsRoleService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Autowired
    private UmsRoleMenuService roleMenuService;

    @Autowired
    private UmsRoleService roleService;

    /**
     * 获取全部菜单
     * @return
     */
    @Override
    public List<UmsMenu> getMenuList() {
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByAsc(true, UmsMenu::getSort);
        // 获取所有菜单
        List<UmsMenu> menuList = list(wrapper);
        // 给菜单设置关联的角色
        menuList.stream().forEach(menu -> {
            // 根据菜单id获取角色id
            List<String> roleIds = roleMenuService.list(new QueryWrapper<UmsRoleMenu>().eq("menu_id", menu.getId()).select("role_id"))
                    .stream().map(UmsRoleMenu::getRoleId).collect(Collectors.toList());
            if(!ObjectUtil.isEmpty(roleIds)) {
                // 根据角色表查询对应的角色key和角色id
                List<String> roleKey = roleService.listByIds(roleIds).stream().map(UmsRole::getKey).collect(Collectors.toList());
                menu.setRoles(roleKey);
                menu.setRoleIds(roleIds);
            }
        });
        return buildMenuTree(menuList);
    }

    /**
     * 构建菜单树
     * @param menuList
     * @return
     */
    private List<UmsMenu> buildMenuTree(List<UmsMenu> menuList) {
        ArrayList<UmsMenu> dataList = new ArrayList<>();
        // 找到父节点
        for (UmsMenu menu : menuList) {
            if (ObjectUtil.isEmpty(menu.getPid())) {
                menu.setChildren(new ArrayList<>());
                dataList.add(menu);
            }
        }
        // 根据父节点找到子节点
        for (UmsMenu menu : dataList) {
            menu.getChildren().add(findMenuChildren(menu, menuList));
        }
        return dataList;
    }

    /**
     * 递归菜单
     * @param menu 父级菜单
     * @param menuList 菜单列表
     * @return
     */
    private UmsMenu findMenuChildren(UmsMenu menu, List<UmsMenu> menuList) {
        menu.setChildren(new ArrayList<>());
        for (UmsMenu item : menuList) {
            if (menu.getId().equals(item.getPid())) {
                menu.getChildren().add(findMenuChildren(item, menuList));
            }
        }
        return menu;
    }
}
