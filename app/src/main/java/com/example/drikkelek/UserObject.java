package com.example.drikkelek;

import java.io.Serializable;

public class UserObject implements Serializable {

    public String imgUri;
    public String name;
    public UserObject() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getName() {
        return name;
    }

    public String getImgUri() {
        return imgUri;
    }
}
