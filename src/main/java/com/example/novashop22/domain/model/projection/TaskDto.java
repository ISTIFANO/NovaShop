package com.example.novashop22.domain.model.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class TaskDto {

	@JsonProperty("TaskID")
	private String id;

	@JsonProperty("TaskInfo")
	private TaskInfo taskInfo;

}

