package cn.hl.admin.modules.ums.service;

import cn.hl.admin.modules.ums.dto.AdminPageDTO;
import cn.hl.admin.modules.ums.dto.AllocationRoleDTO;
import cn.hl.admin.modules.ums.dto.LoginParamDTO;
import cn.hl.admin.modules.ums.model.UmsAdmin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    Boolean create(UmsAdmin umsAdmin);

    Page<UmsAdmin> pageList(AdminPageDTO pageDTO) throws IllegalAccessException;

    /**
     * 分配角色
     * @param allocationRoleDTO
     * @return
     */
    Boolean allocationRole(AllocationRoleDTO allocationRoleDTO);

    /**
     * 登录
     * @param loginParamDTO
     * @param request
     * @return
     */
    String login(LoginParamDTO loginParamDTO, HttpServletRequest request);

    UserDetails loadUserByUsername(String username);

    UmsAdmin getCurrentAdmin();

    Boolean updateAdmin(UmsAdmin umsAdmin);

    UmsAdmin view(String id);

    Boolean delete(String id);
}
