package cn.hl.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "oss", description = "oss")
public class OssOssController {

    @ApiOperation("hello测试")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello world";
    }
}
