package com.pam.labs.pharma.collaborator.common;

import java.util.stream.Stream;

public enum UserRoles {
    ADMIN("A"),
    COLLABORATOR("C");

    private final String code;

    UserRoles(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static String getUserRoleCodeByUserRole(String userRole){
        return Stream.of(UserRoles.values())
                .filter(role -> role.name().equalsIgnoreCase(userRole))
                .findFirst()
                .map(role -> role.code)
                .orElse(null);
    }

    public static String getUserRoleByCode(String userRoleCode){
        return Stream.of(UserRoles.values())
                .filter(role -> role.code.equalsIgnoreCase(userRoleCode))
                .findFirst()
                .map(Enum::name)
                .orElse(null);
    }
}
