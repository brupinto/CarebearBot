package br.com.cabaret.CarebearBot.client.form;

import lombok.Data;

@Data
public class TokenForm {
	 private String grant_type;
     private String code;
}
