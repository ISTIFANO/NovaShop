package com.example.novashop22.infrastructure.database.service;

import com.dashy.orchestrator.commun.payload.ApiRequest;
import com.dashy.orchestrator.commun.payload.ApiResponse;
import com.dashy.orchestrator.domain.helper.TaskValidator;
import com.dashy.orchestrator.infrastructure.http.model.comment.CommentDto;
import com.dashy.orchestrator.domain.model.task.TaskDto;
import com.dashy.orchestrator.domain.service.ITaskService;
import com.dashy.orchestrator.infrastructure.database.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImp implements ITaskService {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskValidator taskValidator;

    @Override
    public ApiResponse<TaskDto> getTaskById(ApiRequest<String> request) {
        log.debug("Entering getTaskById with request: {}", request);
        /*

         */
        String taskId = request.getBody();
        ApiResponse<?> validationResponse = taskValidator.validateTaskId(request, taskId);
        if (validationResponse != null) return (ApiResponse<TaskDto>) validationResponse;

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TaskDto taskDto = taskMapper.taskToTaskDTO(task);

        log.trace("Exiting getTaskById with response: {}", taskDto);
        return ApiResponse.ok(request, "Task retrieved successfully", taskDto);
    }

    @Override
    public ApiResponse<Page<TaskDto>> listTasksPaginated(ApiRequest<Map<String, String>> request) {
        return null;
    }

    @Override
    public ApiResponse<?> claimTask(String taskId , ApiRequest<Map<String, String>> request) {
        log.debug("Entering claimTask with request: {}", request);

        try {

            /*

             */
            Map<String, String> params = request.getBody();
            String userId = params.get("userId");

            ApiResponse<?> validationResponse = taskValidator.validateClaimTask(request, taskId, userId);
            if (validationResponse != null) {
                return validationResponse;
            }

            taskService.claim(taskId, userId);

            log.info("Task {} claimed by user {}", taskId, userId);
            return ApiResponse.ok(request, "Task claimed successfully");


        } catch (Exception e) {
            log.error("Error claiming task", e);
            return ApiResponse.ko(request, "Error claiming task", "500");
        }
    }

    @Override
    public ApiResponse<TaskDto> unclaimTask(ApiRequest<String> request) {
        /*

         */
        try{
            String taskId = request.getBody();

            ApiResponse<?> validationResponse = taskValidator.validateUnclaimTask(request, taskId);
            if (validationResponse != null) return (ApiResponse<TaskDto>) validationResponse;

            taskService.claim(taskId, null);

            log.info("Task {} unclaimed", taskId);
            return ApiResponse.ok(request, "Task unclaimed successfully","200");

        } catch (Exception e) {
            log.error("Error unclaiming task", e);
            return ApiResponse.ko(request, "Error unclaiming task", "500");
        }
    }

    @Override
    public ApiResponse<String> addComment(String taskId ,ApiRequest<Map<String, String>> request) {
        log.debug("Entering addComment with request: {}", request);

        try {
            Map<String, String> params = request.getBody();
            String message = params.get("message");

            ApiResponse<String> validationResponse = taskValidator.validateCommentaire(taskId);
            if (validationResponse != null) return validationResponse;

            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            Comment comment = taskService.createComment(taskId, task.getProcessInstanceId(), message);

            log.info("Comment added to task {}", taskId);
            return ApiResponse.ok(request, "Comment added successfully", comment.getId());

        } catch (Exception e) {
            log.error("Error adding comment", e);
            return ApiResponse.ko(request, "Error adding comment", "500");
        }
    }

    @Override
    public ApiResponse<?> getComments(ApiRequest<String> request) {
        log.debug("Entering getComments with request: {}", request);

        try {
            String taskId = request.getBody();

            ApiResponse<?> validationResponse = taskValidator.validateCommentaire(taskId);
            if (validationResponse != null) return validationResponse;

            List<Comment> comments = taskService.getTaskComments(taskId);
            List<CommentDto> commentDtos = comments.stream()
                    .map(this::convertToCommentDto)
                    .collect(Collectors.toList());

            log.debug("Retrieved {} comments for task {}", commentDtos.size(), taskId);
            return ApiResponse.ok(request, "Comments retrieved successfully", commentDtos);

        } catch (Exception e) {
            log.error("Error retrieving comments", e);
            return ApiResponse.ko(request, "Error retrieving comments", "500");
        }
    }

    private CommentDto convertToCommentDto(Comment comment) {
        log.debug("Entering convertToCommentDto with comment: {}", comment.getId());
        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setUserId(comment.getUserId());
        dto.setTaskId(comment.getTaskId());
        dto.setProcessInstanceId(comment.getProcessInstanceId());

        dto.setFullMessage(comment.getFullMessage());

        dto.setTime(comment.getTime() != null ?
                comment.getTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);

        log.trace("Converted comment DTO: id={}, userId={}, message length={}",
                dto.getId(), dto.getUserId(),
                dto.getFullMessage() != null ? dto.getFullMessage().length() : 0);

        return dto;
    }

    @Override
    public ApiResponse<TaskDto> completeTask(ApiRequest<Map<String, Object>> request) {
        log.debug("Entering completeTask with request: {}", request);

        try {
            Map<String, Object> params = request.getBody();
            String taskId = (String) params.get("taskId");
            Map<String, Object> variables = (Map<String, Object>) params.get("variables");

            ApiResponse<TaskDto> validationResponse = taskValidator.validateCompleteTask(taskId);
            if (validationResponse != null) return validationResponse;

            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            TaskDto taskDto = taskMapper.taskToTaskDTO(task);

            if (variables != null && !variables.isEmpty()) {
                taskService.complete(taskId, variables);
            } else {
                taskService.complete(taskId);
            }

            log.info("Task {} completed successfully", taskId);
            return ApiResponse.ok(request, "Task completed successfully", taskDto);

        } catch (Exception e) {
            log.error("Error completing task", e);
            return ApiResponse.ko(request, "Error completing task", "500");
        }
    }

    @Override
    public ApiResponse<Map<String, Object>> getFormVariables(ApiRequest<String> request) {
        try {
            String taskId = request.getBody();

            ApiResponse<Map<String, Object>> validationResponse = taskValidator.validateVariables(taskId);
            if (validationResponse != null) return validationResponse;

            Map<String, Object> formVariables = taskService.getVariables(taskId);

            log.debug("Retrieved {} form variables for task {}", formVariables.size(), taskId);
            return ApiResponse.ok(request, "Form variables retrieved successfully", formVariables);

        } catch (Exception e) {
            log.error("Error retrieving form variables", e);
            return ApiResponse.ko(request, "Error retrieving form variables", "500");
        }
    }

    @Override
    public ApiResponse<Map<String, Object>> getTaskHistory(ApiRequest<Map<String, Object>> request) {
        try {
            Map<String, Object> params = request.getBody();
            String taskId = (String) params.get("taskId");

            Map<String, Object> formVariables = taskService.getVariables(taskId);

            log.debug("Retrieved {} form variables for task {}", formVariables.size(), taskId);
            return ApiResponse.ok(request, "Form variables retrieved successfully", formVariables);

        } catch (Exception e) {
            log.error("Error retrieving form variables", e);
            return ApiResponse.ko(request, "Error retrieving form variables", "500");
        }
    }
}
