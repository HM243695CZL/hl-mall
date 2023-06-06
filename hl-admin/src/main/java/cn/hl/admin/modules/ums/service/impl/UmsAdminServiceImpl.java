package cn.hl.admin.modules.ums.service.impl;

import cn.hl.admin.modules.domain.AdminUserDetails;
import cn.hl.admin.modules.ums.dto.AdminPageDTO;
import cn.hl.admin.modules.ums.dto.AllocationRoleDTO;
import cn.hl.admin.modules.ums.dto.LoginParamDTO;
import cn.hl.admin.modules.ums.mapper.UmsAdminMapper;
import cn.hl.admin.modules.ums.model.UmsAdmin;
import cn.hl.admin.modules.ums.model.UmsAdminRole;
import cn.hl.admin.modules.ums.service.UmsAdminRoleService;
import cn.hl.admin.modules.ums.service.UmsAdminService;
import cn.hl.admin.security.utils.JwtTokenUtil;
import cn.hl.common.constants.Constants;
import cn.hl.common.exception.ApiException;
import cn.hl.common.exception.Asserts;
import cn.hl.common.filter.FilterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@Service
@Slf4j
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Autowired
    private UmsAdminRoleService adminRoleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional
    @Override
    public Boolean create(UmsAdmin umsAdmin) {
        // 设置用户初始密码
        umsAdmin.setPassword(Constants.INIT_PASSWORD);
        return save(umsAdmin);
    }

    @Override
    public Page<UmsAdmin> pageList(AdminPageDTO pageDTO) throws IllegalAccessException {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        FilterUtil.convertQuery(queryWrapper, pageDTO);
        Page<UmsAdmin> page = new Page<>(pageDTO.getPageIndex(), pageDTO.getPageSize());
        Page<UmsAdmin> adminList = page(page, queryWrapper);
        adminList.setTotal(count(queryWrapper));
        return adminList;
    }

    /**
     * 分配角色
     * @param allocationRoleDTO
     * @return
     */
    @Override
    public Boolean allocationRole(AllocationRoleDTO allocationRoleDTO) {
        List<UmsAdminRole> adminRoleList = setAdminAndRole(allocationRoleDTO.getRoleIds(), allocationRoleDTO.getId());
        return adminRoleService.saveBatch(adminRoleList);
    }

    /**
     * 登录
     * @param loginParamDTO
     * @param request
     * @return
     */
    @Override
    public String login(LoginParamDTO loginParamDTO, HttpServletRequest request) {
        String token = null;
        try {
            AdminUserDetails userDetails = loadUserByUsername(loginParamDTO.getUsername());
            if (userDetails == null) {
                Asserts.fail("用户名不存在");
            }
            if (!userDetails.getPassword().equals(loginParamDTO.getPassword())) {
                Asserts.fail("密码错误");
            }
            // 生成springSecurity的通过认证标识
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            // 更新上次登录时间和登录ip
//            UmsAdmin currentAdmin = getCurrentAdmin();
//            UpdateWrapper<UmsAdmin> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.lambda().eq(UmsAdmin::getId, currentAdmin.getId());
//            updateWrapper.set("last_login_ip", IpUtil.getIpAddr(request));
//            updateWrapper.set("last_login_time", new Date());
//            update(updateWrapper);
        } catch (AuthenticationException e) {
            log.warn("登录异常：{}", e.getMessage());
        }
        return token;
    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    public AdminUserDetails loadUserByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            return new AdminUserDetails(admin);
        }
        throw new ApiException("用户不存在");
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    private UmsAdmin getAdminByUsername(String username) {
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername, username);
        return getOne(wrapper);
    }

    /**
     * 设置用户和角色的关联关系
     * @param roleIds
     * @param userId
     * @return
     */
    private List<UmsAdminRole> setAdminAndRole(List<String> roleIds, String userId) {
        List<UmsAdminRole> adminRoleList = new ArrayList<>();
        for (String roleId : roleIds) {
            UmsAdminRole adminRole = new UmsAdminRole();
            adminRole.setAdminId(userId);
            adminRole.setRoleId(roleId);
            adminRoleList.add(adminRole);
        }
        return adminRoleList;
    }
}
