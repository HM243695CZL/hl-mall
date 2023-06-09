package cn.hl.admin.modules.ums.service;

import cn.hl.admin.modules.ums.dto.InitMenuDTO;
import cn.hl.admin.modules.ums.model.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
public interface UmsMenuService extends IService<UmsMenu> {

    /**
     * 获取全部菜单
     * @return
     */
    List<UmsMenu> getMenuList();

    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    List<InitMenuDTO> getMenuListByUserId(String id);
}
