package com.resourcesManager.backend.resourcesManager.security;

public class SecurityConstant {
    public static final String SECRET_KEY = "4B6250655368566D5971337436763979244226452948404D635166546A576E5A";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 86_400_000; // 1 day
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 38_880_000_000l; // 30 days
    private static final String TOKEN_HEADER = "Bearer ";
    private static final String FORBIDDEN_MESSAGE = "you need to login to access this page";
    private static final String ACCESS_DENIED_MESSAGE = "you don't have permission to access this page";
    public static final String[] PUBLIC_URLS = {
            "/api/v1/users/register",
            "/api/v1/users/authenticate"
    };

}
