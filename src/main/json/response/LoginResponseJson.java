package main.json.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import main.json.token.TokenJson;
import main.json.menu.AbstractMenuJson;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseJson extends AbstractResponseJson {
	
	@Getter
	private TokenJson token;

	@Getter
	private AbstractMenuJson menu;

	public LoginResponseJson(String message, HttpStatus status, AbstractMenuJson menu) {
		super(message, status);
		this.menu = menu;
	}

	public LoginResponseJson(String message, HttpStatus status, TokenJson token, AbstractMenuJson menu) {
		this(message, status, menu);
		this.token = token;
	}

}