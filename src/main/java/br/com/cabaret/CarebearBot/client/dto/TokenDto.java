package br.com.cabaret.CarebearBot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
	private String access_token;
	private String token_type;
	private String refresh_token;
}
