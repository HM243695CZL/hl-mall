package cn.hl.admin.security;


import cn.hl.admin.modules.ums.model.UmsAdmin;

public class UserThreadLocal {

    private static final ThreadLocal<UmsAdmin> LOCAL = new ThreadLocal<>();

    public static void put(UmsAdmin user) {
        LOCAL.set(user);
    }

    public static UmsAdmin get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }

}
