package br.com.cabaret.CarebearBot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cabaret.CarebearBot.client.CharacterClient;
import br.com.cabaret.CarebearBot.client.CorporationClient;
import br.com.cabaret.CarebearBot.model.CorpMember;
import br.com.cabaret.CarebearBot.repository.CorpMemberRepository;
import br.com.cabaret.CarebearBot.repository.DirectorRepository;

@Service
public class CorporateService {

	@Autowired
	CorporationClient corpClient;
	
	@Autowired
	CharacterClient charClient;
	
	@Autowired
	RegisterAuthService authService;
	
	@Autowired
	DirectorRepository diretorRep;
	
	@Autowired
	CorpMemberRepository corpMemberRep;
	
	public void updateMemberList() {
		try {
			List<Long> members = corpClient.memberList(diretorRep.getDirector().get(0).getCorporateId(), authService.refreshToken());
			
			corpMemberRep.deleteAll();
			
			for (Long membro : members) {
				CorpMember corp = new CorpMember();
				corp.setCharacterId(membro);
				corp.setCharacterName(charClient.characters(membro).getName().toLowerCase());
				corp.setDtLastUpdate(LocalDateTime.now());
				
				corpMemberRep.save(corp);
			}	
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
