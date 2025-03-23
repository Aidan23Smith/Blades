package com.blades.data.common;

import lombok.Getter;

@Getter
public enum Navigation {

    CHARACTERS("/show-characters"),
    CREWS("/show-crews"),
    ;

    private final String navigationUrl;

    Navigation(String navigationUrl) {
        this.navigationUrl = navigationUrl;
    }

}
