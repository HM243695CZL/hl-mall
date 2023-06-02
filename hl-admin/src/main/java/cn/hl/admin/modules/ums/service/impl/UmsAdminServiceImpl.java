package cn.hl.admin.modules.ums.service.impl;

import cn.hl.admin.modules.ums.dto.AdminPageDTO;
import cn.hl.admin.modules.ums.model.UmsAdmin;
import cn.hl.admin.modules.ums.mapper.UmsAdminMapper;
import cn.hl.admin.modules.ums.service.UmsAdminService;
import cn.hl.common.constants.Constants;
import cn.hl.common.filter.FilterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
