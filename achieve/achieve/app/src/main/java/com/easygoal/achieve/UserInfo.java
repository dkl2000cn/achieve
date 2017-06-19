package com.easygoal.achieve;

/**
 * Created by Acer on 2017/5/1.
 */

public class UserInfo {
    private String userId;
    private String name;
    private String portraitUri;

    public UserInfo(String userId, String name, String portraitUri) {
        this.userId = userId;
        this.name = name;
        this.portraitUri = portraitUri;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }
}
