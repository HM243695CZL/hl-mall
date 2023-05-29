package cn.hl.admin.modules.ums.controller;

import cn.hl.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

import cn.hl.admin.modules.ums.service.UmsAdminService;
import cn.hl.admin.modules.ums.model.UmsAdmin;

import org.springframework.web.bind.annotation.RestController;

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
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;

    // 新增
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult save(@RequestBody UmsAdmin umsAdmin){
        return CommonResult.success(umsAdminService.save(umsAdmin));
    }

    // 更新
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestBody UmsAdmin umsAdmin){
        return CommonResult.success(umsAdminService.updateById(umsAdmin));
    }

    // 删除
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable String id){
        return CommonResult.success( umsAdminService.removeById(id));
    }

    // 获取全部
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(){
        return CommonResult.success(umsAdminService.list());
    }

    // 查看
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public CommonResult findOne(@PathVariable String id){
        return CommonResult.success(umsAdminService.getById(id));
    }

}

