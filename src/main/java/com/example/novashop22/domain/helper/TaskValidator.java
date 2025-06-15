package com.example.novashop22.domain.helper;


import com.dashy.orchestrator.commun.payload.ApiRequest;
import com.dashy.orchestrator.commun.payload.ApiResponse;
import com.dashy.orchestrator.domain.model.task.TaskDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskValidator {

    private final TaskService taskService;

    /*

     */
    public ApiResponse<?> validateTaskId(ApiRequest<?> request, String taskId) {
        /*
            Verifiy is teh task id if null or empty
         */
        if (!StringUtils.hasText(taskId)) {
            log.warn("Task ID is null or empty");
            return ApiResponse.ko(request, "Task ID is required", "400");
        }

         /*
            Check inside camunda runing instance if its exists
         */
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            log.warn("Task not found for id: {}", taskId);
            return ApiResponse.ko(request, "Task not found", "404");
        }

        return null;
    }

    /*

     */
    public ApiResponse<String> validateTaskId(String taskId){
        if (!StringUtils.hasText(taskId)) {
            log.warn("Task ID is null or empty");
            return ApiResponse.ko("Task ID is required", "400");
        }
        return null;
    }

    /*

     */
    public ApiResponse<?> validateClaimTask(ApiRequest<?> request, String taskId, String userId) {
        /*
            Validate task uuid
         */
        ApiResponse<?> taskIdValidation = validateTaskId(request, taskId);
        if (taskIdValidation != null) {
            return taskIdValidation;
        }

        /*
            Validate teh user id passwed for assignement
         */
        if (!StringUtils.hasText(userId)) {
            log.warn("User ID is missing");
            return ApiResponse.ko(request, "User ID is required", "400");
        }
         /*
            check if the claimed all ready claimed
         */

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task.getAssignee() != null) {
            log.warn("Task {} is already claimed by {}", taskId, task.getAssignee());
            return ApiResponse.ko(request, "Task is already claimed", "409");
        }

        return null;
    }

    /*

     */
    public ApiResponse<?> validateUnclaimTask(ApiRequest<?> request, String taskId) {
        /*
            Validate task uuid
         */
        ApiResponse<?> taskIdValidation = validateTaskId(request, taskId);
        if (taskIdValidation != null) {
            return taskIdValidation;
        }

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task.getAssignee() == null) {
            log.warn("Task {} is not claimed", taskId);
            return ApiResponse.ko(request, "Task is not currently claimed", "400");
        }

        return null;
    }

    /*

     */
    public ApiResponse<String> validateCommentaire(String taskId) {
        /*
            Validate task uuid
         */
        ApiResponse<String> taskIdValidation = validateTaskId(taskId);
        if (taskIdValidation != null) {
            return ApiResponse.ko("Task ID is required", "400");
        }

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            log.warn("Task not found for id: {}", taskId);
            return ApiResponse.ko("Task not found", "404");
        }
        return null;
    }

    /*

     */
    public ApiResponse<TaskDto> validateCompleteTask(String taskId) {
        /*
            Validate task uuid
         */
        ApiResponse<String> taskIdValidation = validateTaskId(taskId);

        if (taskIdValidation != null) {
            return ApiResponse.ko("Task ID is required", "400");
        }

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            log.warn("Task not found for id: {}", taskId);
            return ApiResponse.ko("Task not found", "404");
        }
        return null;
    }
    /*

     */
    public ApiResponse<Map<String, Object>> validateVariables(String taskId) {
        /*
            Validate task uuid
         */
        ApiResponse<String> taskIdValidation = validateTaskId(taskId);

        if (taskIdValidation != null) {
            return ApiResponse.ko("Task ID is required", "400");
        }

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            log.warn("Task not found for id: {}", taskId);
            return ApiResponse.ko("Task not found", "404");
        }
        return null;
    }
}
