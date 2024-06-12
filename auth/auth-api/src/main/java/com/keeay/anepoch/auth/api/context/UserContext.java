package com.keeay.anepoch.auth.api.context;


/**
 * 用户上下文
 *
 * @author xiongyu
 */
public class UserContext {

    public static final ThreadLocal<LoginUser> USER_CONTENT = new ThreadLocal<>();

    public static void setUser(LoginUser user) {
        USER_CONTENT.set(user);
    }

    public static LoginUser getUser() {
        return USER_CONTENT.get();
    }

    public static void clear() {
        USER_CONTENT.remove();
    }
}
