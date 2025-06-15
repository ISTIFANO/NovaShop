package com.example.novashop22.infrastructure.http.controller.external;

import com.dashy.orchestrator.commun.payload.ApiRequest;
import com.dashy.orchestrator.commun.payload.ApiResponse;
import com.dashy.orchestrator.domain.model.task.TaskDto;
import com.dashy.orchestrator.domain.service.ITaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for managing bpmn-task.
 * Provides endpoints for creating, retrieving, updating, and deleting bpmn-task,
 * as well as searching for bpmn-task with pagination support.
 */
@Slf4j
@RestController
@RequestMapping("/v1/api/ui/task")
@AllArgsConstructor
// @PreAuthorize("hasRole('MANAGE_TASKS')")
public class TaskBpmApi {

		private final ITaskService taskService;

		/**
		 * Retrieves a bpmn task by id.
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid    the task id
		 * @return the bpmn task with the given id
		 */

		@GetMapping(value = "/{taskid}", produces = "application/json")
		public ResponseEntity<ApiResponse<TaskDto>> getTaskById(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid) {
			ApiResponse<TaskDto> response = taskService.getTaskById(ApiRequest.of(taskid, requestId, canal));
			return ResponseEntity.ok(response);
		}
		/**
		 * Claim a bpmn task .
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid    the task id
		 * @return the status of claiming
		 */
		@PostMapping(value = "/{taskid}/claim", produces = "application/json")
		public ResponseEntity<ApiResponse<?>> claimTask(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid,
				@RequestBody Map<String, String> body) {
			ApiResponse<?> response = taskService.claimTask(taskid , ApiRequest.of(body, requestId, canal));
			return ResponseEntity.ok(response);
		}

		/**
		 * Unclaim a bpmn task .
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid    the task id
		 * @return the status of claiming
		 */
		@PostMapping(value = "/{taskid}/unclaim", produces = "application/json")
		public ResponseEntity<ApiResponse<TaskDto>> unclaimTask(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid) {
			ApiResponse<TaskDto> response = taskService.unclaimTask(ApiRequest.of(taskid, requestId, canal));
			return ResponseEntity.ok(response);
		}

		/**
		 * Add comment
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid    the task id
		 * @return a message for comment added status
		 */
		@PostMapping(value = "/{taskid}/comment", produces = "application/json")
		public ResponseEntity<ApiResponse<String>> addComment(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid,
				@RequestBody Map<String, String> body) {
			ApiResponse<String> response = taskService.addComment(taskid, ApiRequest.of(body, requestId, canal));
			return ResponseEntity.ok(response);
		}

		/**
		 * Retrieves all comments
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid    the task id
		 * @return all comments task
		 */
		@GetMapping(value = "/{taskid}/comments", produces = "application/json")
		public ResponseEntity<ApiResponse<?>> getComments(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid) {
			ApiResponse<?> response = taskService.getComments(ApiRequest.of(taskid, requestId, canal));
			return ResponseEntity.ok(response);
		}

		/**
		 * Complete task
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid    the task id
		 * @return a message of completion task
		 */
		@PostMapping(value = "/{taskid}/complete", produces = "application/json")
		public ResponseEntity<ApiResponse<TaskDto>> completeTask(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid,
				@RequestBody(required = false) Map<String, Object> body) {
			Map<String, Object> params = Map.of(
					"taskId", taskid,
					"variables", body != null ? body : Map.of()
			);
			ApiResponse<TaskDto> response = taskService.completeTask(ApiRequest.of(params, requestId, canal));
			return ResponseEntity.ok(response);
		}

		/**
		 * Retrieves process instance variables
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid    the task id
		 * @return the all variables
		 */
		@GetMapping(value = "/{taskid}/form-variables", produces = "application/json")
		public ResponseEntity<ApiResponse<Map<String, Object>>> getFormVariables(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid) {
			ApiResponse<Map<String, Object>> response = taskService.getFormVariables(ApiRequest.of(taskid, requestId, canal));
			return ResponseEntity.ok(response);
		}

		/**
		 * Retrieves task history
		 *
		 * @param requestId the request ID
		 * @param canal     the canal
		 * @param taskid      the history request parameters
		 * @return the task history
		 */
		@PostMapping(value = "/{taskid}/history", produces = "application/json")
		public ResponseEntity<ApiResponse<?>> getTaskHistory(
				@RequestHeader(value = "x-api-requestId") String requestId,
				@RequestHeader(value = "x-api-canal") String canal,
				@PathVariable String taskid) {
			Map<String, Object> params = Map.of(
					"taskId", taskid
			);
			ApiResponse<?> response = taskService.getTaskHistory(ApiRequest.of(params, requestId, canal));
			return ResponseEntity.ok(response);
		}

}