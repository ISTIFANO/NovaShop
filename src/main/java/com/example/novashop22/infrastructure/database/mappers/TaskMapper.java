package com.example.novashop22.infrastructure.database.mappers;

import com.dashy.orchestrator.domain.model.task.TaskDto;
import org.camunda.bpm.engine.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "taskInfo", source = "task")
    TaskDto taskToTaskDTO(Task task);
}

