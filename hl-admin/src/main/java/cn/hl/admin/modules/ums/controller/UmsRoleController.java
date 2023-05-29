package cn.hl.admin.modules.ums.controller;

import cn.hl.admin.modules.ums.dto.RolePageDTO;
import cn.hl.common.api.CommonPage;
import cn.hl.common.api.CommonResult;
import cn.hl.common.log.LogAnnotation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

import cn.hl.admin.modules.ums.service.UmsRoleService;
import cn.hl.admin.modules.ums.model.UmsRole;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/admin/role")
@Api(tags = "角色管理", description = "角色管理")
public class UmsRoleController {

    @Autowired
    private UmsRoleService umsRoleService;

    // 分页
    @LogAnnotation
    @ApiOperation("分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public CommonResult page(@RequestBody RolePageDTO pageDTO) {
       Page<UmsRole> roleList = umsRoleService.pageList(pageDTO);
       return CommonResult.success(CommonPage.restPage(roleList));
    }

    // 新增
    @LogAnnotation
    @ApiOperation("新增角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult save(@RequestBody UmsRole umsRole){
        return CommonResult.success(umsRoleService.save(umsRole));
    }

    // 更新
    @LogAnnotation
    @ApiOperation("更新角色")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestBody UmsRole umsRole){
        return CommonResult.success(umsRoleService.updateById(umsRole));
    }

    // 删除
    @LogAnnotation
    @ApiOperation("删除角色")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable String id){
        return CommonResult.success( umsRoleService.removeById(id));
    }

    // 获取全部
    @LogAnnotation
    @ApiOperation("获取全部角色")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(){
        return CommonResult.success(umsRoleService.list());
    }

    // 查看
    @LogAnnotation
    @ApiOperation("查看角色")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public CommonResult findOne(@PathVariable String id){
        return CommonResult.success(umsRoleService.getById(id));
    }

}

