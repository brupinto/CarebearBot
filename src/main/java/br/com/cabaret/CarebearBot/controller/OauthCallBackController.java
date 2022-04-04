package br.com.cabaret.CarebearBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cabaret.CarebearBot.service.RegisterAuthService;

@RestController
@RequestMapping("oauth-callback")
public class OauthCallBackController {
	
	@Autowired
	private RegisterAuthService authService;
	
	@GetMapping("/")
	public ResponseEntity<String> callBack(@RequestParam String code) {
		try {
			authService.register(code);
			return ResponseEntity.ok("OK");
		} catch ( Exception ex ) {
			return  ResponseEntity.notFound().build();
		}
	}
}