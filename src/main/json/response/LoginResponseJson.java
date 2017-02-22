package main.json.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import main.json.token.TokenJson;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseJson extends AbstractResponseJson {
	
	@Getter
	private TokenJson token;

	@Getter
	private String role;

	public LoginResponseJson(String message, HttpStatus status) {
		super(message, status);
		this.role = "GUEST";
	}

	public LoginResponseJson(String message, HttpStatus status, TokenJson token, String role) {
		this(message, status);
		this.token = token;
		this.role = role;
	}

}