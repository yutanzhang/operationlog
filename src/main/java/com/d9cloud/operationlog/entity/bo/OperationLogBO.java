package com.d9cloud.operationlog.entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: litianyi
 * @date: Created on 2022/05/17
 */
@Data
public class OperationLogBO {

    /**
     * 坐席id
     */
    private Integer userId;

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
