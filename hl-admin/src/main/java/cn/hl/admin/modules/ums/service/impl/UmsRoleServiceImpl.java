package cn.hl.admin.modules.ums.service.impl;

import cn.hl.admin.modules.ums.dto.PageRoleDTO;
import cn.hl.admin.modules.ums.mapper.UmsRoleMapper;
import cn.hl.admin.modules.ums.model.UmsRole;
import cn.hl.admin.modules.ums.service.UmsRoleService;
import cn.hl.common.filter.FilterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


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

    @Override
    public Page<UmsRole> pageList(PageRoleDTO roleDTO) throws IllegalAccessException {
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        FilterUtil.convertQuery(queryWrapper, roleDTO);
        Page<UmsRole> page = new Page<>(roleDTO.getPageIndex(), roleDTO.getPageSize());
//        int count = count(queryWrapper);
        Page<UmsRole> roleList = page(page, queryWrapper);
//        roleList.setTotal(count);
        return roleList;
    }
}
