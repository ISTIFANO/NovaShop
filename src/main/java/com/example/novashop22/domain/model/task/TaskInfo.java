package com.example.novashop22.domain.model.task;

import lombok.Data;

import java.util.Date;

/**
 * TaskInfo
 */

@Data
public class TaskInfo {

    private String name;
    private String assignee;
    private String owner;
    private String description;
    private int priority;
    private String processInstanceId;
    private String executionId;
    private String processDefinitionId;
    private String taskDefinitionKey;
    private Date createTime;
    private Date dueDate;
    private String category;
    private String parentTaskId;
    private String tenantId;
    private String processBusinessKey;

}

