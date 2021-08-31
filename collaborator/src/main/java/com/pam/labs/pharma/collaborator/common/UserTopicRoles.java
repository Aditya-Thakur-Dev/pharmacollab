package com.pam.labs.pharma.collaborator.common;

import java.util.stream.Stream;

public enum UserTopicRoles {
    COLLABORATOR("C"),
    REVIEWER("R");

    private final String code;

    UserTopicRoles(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static String getUserTopicRoleCodeByUserTopicRole(String userTopicRole){
        return Stream.of(UserTopicRoles.values())
                .filter(role -> role.name().equalsIgnoreCase(userTopicRole))
                .findFirst()
                .map(role -> role.code)
                .orElse(null);
    }
}
