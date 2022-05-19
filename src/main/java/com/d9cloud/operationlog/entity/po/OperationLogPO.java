package com.d9cloud.operationlog.entity.po;

import com.d9cloud.operationlog.entity.enums.OperateTarget;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: litianyi
 * @date: Created on 2022/05/17
 */
@Data
public class OperationLogPO {

    /**
     * 坐席id
     */
    private Integer userId;

    /**
     * 操作对象(部门、用户)
     */
    private OperateTarget target;

    /**
     * 对象id
     */
    private Object targetValue;

    /**
     * 操作菜单
     */
    private String operateTag;

    /**
     * 操作描述
     */
    private String operateDesc;

    /**
     * 操作日期
     */
    private Date operateDate;

}
