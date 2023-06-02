package cn.hl.admin.modules.ums.service.impl;

import cn.hl.admin.modules.ums.dto.AdminPageDTO;
import cn.hl.admin.modules.ums.dto.AllocationRoleDTO;
import cn.hl.admin.modules.ums.model.UmsAdmin;
import cn.hl.admin.modules.ums.mapper.UmsAdminMapper;
import cn.hl.admin.modules.ums.model.UmsAdminRole;
import cn.hl.admin.modules.ums.service.UmsAdminRoleService;
import cn.hl.admin.modules.ums.service.UmsAdminService;
import cn.hl.common.constants.Constants;
import cn.hl.common.filter.FilterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Autowired
    private UmsAdminRoleService adminRoleService;

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
