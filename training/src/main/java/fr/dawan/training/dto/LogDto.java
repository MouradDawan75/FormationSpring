package fr.dawan.training.dto;

import java.time.LocalDateTime;

public class LogDto {
	
	private int code;
	private String message;
	private LocalDateTime timeTamp;
	private String path;
	
	public enum LogLevel{
		DEBUG, INFO, WARNING, ERROR
	}
	
	private LogLevel logLevel;
	
	public enum LogType{
		ACCESS, CLIEN_APP, EXCEPTION
	}
	
	private LogType logType;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimeTamp() {
		return timeTamp;
	}

	public void setTimeTamp(LocalDateTime timeTamp) {
		this.timeTamp = timeTamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	
}
