package cn.hl.admin.modules.ums.service.impl;

import cn.hl.admin.modules.ums.dto.InitMenuDTO;
import cn.hl.admin.modules.ums.dto.MenuMataDTO;
import cn.hl.admin.modules.ums.model.UmsAdminRole;
import cn.hl.admin.modules.ums.model.UmsMenu;
import cn.hl.admin.modules.ums.mapper.UmsMenuMapper;
import cn.hl.admin.modules.ums.model.UmsRole;
import cn.hl.admin.modules.ums.model.UmsRoleMenu;
import cn.hl.admin.modules.ums.service.UmsAdminRoleService;
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
import java.util.Objects;
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

    @Autowired
    private UmsAdminRoleService adminRoleService;


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
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    @Override
    public List<InitMenuDTO> getMenuListByUserId(String id) {
        List<InitMenuDTO> dataList = new ArrayList<>();
        List<InitMenuDTO> menus = new ArrayList<>();
        // 获取用户对应的角色id
        List<String> roleIds = adminRoleService.list(new QueryWrapper<UmsAdminRole>().eq("admin_id", id).select("role_id"))
                .stream().map(UmsAdminRole::getRoleId).collect(Collectors.toList());
        // 根据角色id查询对应的菜单id
        List<String> menuIds = roleMenuService.list(new QueryWrapper<UmsRoleMenu>().in("role_id", roleIds).select("menu_id"))
                .stream().map(UmsRoleMenu::getMenuId).collect(Collectors.toList());
        // 根据菜单id获取对应的菜单父id(需要对数据进行空值判断【objects::nonNull】)
        List<String> pIds = list(new QueryWrapper<UmsMenu>().in("id", menuIds).select("pid"))
                .stream().filter(Objects::nonNull).map(UmsMenu::getPid).collect(Collectors.toList());
        // 将菜单父id添加到menuIds中，以便查出父级菜单信息
        menuIds.addAll(pIds);
        // 根据菜单id查询出菜单
        list(new QueryWrapper<UmsMenu>().in("id", menuIds).lambda().orderBy(true, true, UmsMenu::getSort))
                .stream().forEach(menuItem -> {
                    // 根据角色表查询对应的角色key
            List<String> roleKey = roleService.listByIds(roleIds).stream().map(UmsRole::getKey).collect(Collectors.toList());
            InitMenuDTO initMenuDTO = new InitMenuDTO();
            MenuMataDTO menuMataDTO = new MenuMataDTO();
            // 设置meta
            menuMataDTO.setTitle(menuItem.getTitle());
            menuMataDTO.setIsLink(menuItem.getIsLink());
            menuMataDTO.setIsHide(menuItem.getIsHide());
            menuMataDTO.setIsKeepAlive(menuItem.getIsKeepAlive());
            menuMataDTO.setIsAffix(menuItem.getIsAffix());
            menuMataDTO.setIsIframe(menuItem.getIsIframe());
            menuMataDTO.setRoles(roleKey);
            menuMataDTO.setIcon(menuItem.getIcon());

            initMenuDTO.setId(menuItem.getId());
            initMenuDTO.setPid(menuItem.getPid());
            initMenuDTO.setPath(menuItem.getPath());
            initMenuDTO.setName(menuItem.getName());
            initMenuDTO.setComponent(menuItem.getComponent());
            initMenuDTO.setMeta(menuMataDTO);
            menus.add(initMenuDTO);
        });
        // 找到父节点
        for (InitMenuDTO menu : menus) {
            if (ObjectUtil.isEmpty(menu.getPid())) {
                menu.setChildren(new ArrayList<InitMenuDTO>());
                dataList.add(menu);
            }
        }
        // 根据父节点找到子节点
        for (InitMenuDTO menu : dataList) {
            menu.getChildren().add(findInitMenuChildren(menu, menus));
        }
        return dataList;
    }

    private InitMenuDTO findInitMenuChildren(InitMenuDTO menu, List<InitMenuDTO> menus) {
        menu.setChildren(new ArrayList<>());
        for (InitMenuDTO item : menus) {
            if (menu.getId().equals(item.getPid())) {
                menu.getChildren().add(findInitMenuChildren(item, menus));
            }
        }
        return menu;
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
