package br.com.cabaret.CarebearBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cabaret.CarebearBot.client.AuthClient;
import br.com.cabaret.CarebearBot.client.CharacterClient;
import br.com.cabaret.CarebearBot.client.dto.CharacterDto;
import br.com.cabaret.CarebearBot.client.dto.TokenDto;
import br.com.cabaret.CarebearBot.client.dto.VerifyDto;
import br.com.cabaret.CarebearBot.client.form.TokenForm;
import br.com.cabaret.CarebearBot.model.Director;
import br.com.cabaret.CarebearBot.repository.DirectorRepository;

@Service
public class RegisterAuthService {

	@Autowired
	AuthClient authClient;
	
	@Autowired
	CharacterClient charClient;
	
	@Autowired
	DirectorRepository directorRepo;
	
	@Value("${api-eveonline-token}")
	private String basicToken;
	
	public void register(String code) {
		TokenForm tokenForm = new TokenForm();
		tokenForm.setCode(code);
		tokenForm.setGrant_type("authorization_code");
		TokenDto tokenDto = authClient.token(tokenForm, basicToken);
		VerifyDto verifyDto = authClient.verify("Bearer "+tokenDto.getAccess_token());
		CharacterDto characterDto = charClient.characters(verifyDto.getCharacterID()); 
		
		Director diretor = new Director();
		diretor.setCharacterId(verifyDto.getCharacterID());
		diretor.setRefreshToken(tokenDto.getRefresh_token());
		diretor.setCorporateId(characterDto.getCorporation_id());

		directorRepo.deleteAll();
		directorRepo.save(diretor);

	}
}
