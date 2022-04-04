package br.com.cabaret.CarebearBot.client.form;

import lombok.Data;

@Data
public class TokenRefreshForm {
	private String grant_type;
    private String refresh_token;
}
