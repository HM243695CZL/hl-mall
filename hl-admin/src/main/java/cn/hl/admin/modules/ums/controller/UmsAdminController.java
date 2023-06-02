package cn.hl.admin.modules.ums.controller;

import cn.hl.admin.modules.ums.dto.AdminPageDTO;
import cn.hl.admin.modules.ums.dto.AllocationRoleDTO;
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

import cn.hl.admin.modules.ums.service.UmsAdminService;
import cn.hl.admin.modules.ums.model.UmsAdmin;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/admin/admin")
@Api(tags = "用户管理", description = "用户管理")
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;

    // 分页
    @LogAnnotation
    @ApiOperation("分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public CommonResult page(@RequestBody AdminPageDTO pageDTO) throws IllegalAccessException {
        Page<UmsAdmin> adminList = umsAdminService.pageList(pageDTO);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    // 新增
    @LogAnnotation
    @ApiOperation("新增用户")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult save(@Valid @RequestBody UmsAdmin umsAdmin){
        return CommonResult.success(umsAdminService.create(umsAdmin));
    }

    // 更新
    @LogAnnotation
    @ApiOperation("更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestBody UmsAdmin umsAdmin){
        return CommonResult.success(umsAdminService.updateById(umsAdmin));
    }

    // 删除
    @LogAnnotation
    @ApiOperation("删除用户")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable String id){
        return CommonResult.success( umsAdminService.removeById(id));
    }

    // 获取全部
    @LogAnnotation
    @ApiOperation("获取全部用户")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(){
        return CommonResult.success(umsAdminService.list());
    }

    // 查看
    @LogAnnotation
    @ApiOperation("查看用户")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public CommonResult findOne(@PathVariable String id){
        return CommonResult.success(umsAdminService.getById(id));
    }

    // 分配角色
    @LogAnnotation
    @ApiOperation("分配角色")
    @RequestMapping(value = "/allocationRole", method = RequestMethod.POST)
    public CommonResult allocationRole(@RequestBody AllocationRoleDTO allocationRoleDTO) {
        return CommonResult.success(umsAdminService.allocationRole(allocationRoleDTO));
    }

}

