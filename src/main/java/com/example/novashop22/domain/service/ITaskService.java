package com.example.novashop22.domain.service;

import com.dashy.orchestrator.commun.payload.ApiRequest;
import com.dashy.orchestrator.commun.payload.ApiResponse;
import com.dashy.orchestrator.domain.model.task.TaskDto;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Service interface for managing task.
 * Provides methods for creating, retrieving, updating, and deleting task,
 * as well as searching for task with pagination support.
 */
public interface ITaskService {

    /**
     * Retrieves a task by its reference.
     *
     * @param id the id of the task to be retrieved
     * @return an ApiResponse containing the retrieved task
     */
    ApiResponse<TaskDto> getTaskById(ApiRequest<String> id);
    ApiResponse<Page<TaskDto>> listTasksPaginated(ApiRequest<Map<String, String>> request);
    ApiResponse<?> claimTask(String TaskId , ApiRequest<Map<String, String>> request);
    ApiResponse<TaskDto> unclaimTask(ApiRequest<String> request);
    ApiResponse<String> addComment(String taskId ,ApiRequest<Map<String, String>> request);
    ApiResponse<?> getComments(ApiRequest<String> request);
    ApiResponse<TaskDto> completeTask(ApiRequest<Map<String, Object>> request);
    ApiResponse<Map<String, Object>> getFormVariables(ApiRequest<String> request);
    ApiResponse<Map<String, Object>> getTaskHistory(ApiRequest<Map<String, Object>> request);
}
