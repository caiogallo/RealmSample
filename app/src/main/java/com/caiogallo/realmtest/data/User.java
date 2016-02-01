package com.caiogallo.realmtest.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by caio on 22/01/16.
 */
public class User extends RealmObject {
    @PrimaryKey
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
