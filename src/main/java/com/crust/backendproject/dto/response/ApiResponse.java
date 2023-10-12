package com.crust.backendproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Data
public class ApiResponse {

	private Instant timeStamp;
	private boolean isSuccessful;
	private Object data;
	private int status;
	private String path;

}
