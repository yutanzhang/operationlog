package com.d9cloud.operationlog.entity.po;

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

    private Integer userId;

    private String operateType;

    private String operateDesc;

    private Date operateDate;

}
