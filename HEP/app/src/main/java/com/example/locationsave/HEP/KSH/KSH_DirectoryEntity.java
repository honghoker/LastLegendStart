package com.example.locationsave.HEP.KSH;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KSH_DirectoryEntity implements Serializable {
    String token;
    String name;
    String updateTime;
    String createTime;

    public KSH_DirectoryEntity(){}

    public KSH_DirectoryEntity(String toString, String updateTime, String createTime) {
        this.name = toString;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",name);
        result.put("updateTime",updateTime);
        result.put("createTime",createTime);

        return result;
    }
}
