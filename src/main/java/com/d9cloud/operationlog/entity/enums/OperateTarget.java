package com.d9cloud.operationlog.entity.enums;

/**
 * Description:
 *
 * @author: litianyi
 * @date: Created on 2022/05/18
 */
public enum OperateTarget {

    NONE(-1, "无配置"),
    USER(0, "用户"),
    DEPARTMENT(1, "部门")
    ;

    OperateTarget(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    private int value;

    private String displayName;

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
}
