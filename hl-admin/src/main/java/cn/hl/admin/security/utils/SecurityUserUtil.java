package cn.hl.admin.security.utils;

import cn.hl.admin.domain.AdminUserDetails;
import cn.hl.admin.modules.ums.model.UmsAdmin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserUtil {

    /**
     * 获取当前登录用户
     * @return
     */
    public UmsAdmin getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails admin = (AdminUserDetails) authentication.getPrincipal();
        return admin.getUmsAdmin();
    }

}
