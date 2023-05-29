package cn.hl.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"cn.hl.*.modules.*.mapper"}) // 多模块项目必须有配置扫描
@ComponentScan(basePackages = "cn.hl") // swagger扫描配置
public class HlAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HlAdminApplication.class, args);
    }

}
