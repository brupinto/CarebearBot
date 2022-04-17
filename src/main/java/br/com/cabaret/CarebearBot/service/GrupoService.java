package br.com.cabaret.CarebearBot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cabaret.CarebearBot.client.CharacterClient;
import br.com.cabaret.CarebearBot.model.CorpMember;
import br.com.cabaret.CarebearBot.model.Grupo;
import br.com.cabaret.CarebearBot.model.GrupoDetail;
import br.com.cabaret.CarebearBot.repository.CorpMemberRepository;
import br.com.cabaret.CarebearBot.repository.GrupoDetailRepository;
import br.com.cabaret.CarebearBot.repository.GrupoRepository;
import br.com.cabaret.CarebearBot.service.dto.GroupDto;

@Service
public class GrupoService {

	@Autowired
	CharacterClient charClient;
	
	@Autowired
	CorpMemberRepository corpMemberRep;
	
	@Autowired
	GrupoRepository grupoRep;
	
	@Autowired
	GrupoDetailRepository grupoDetailRep;

	public void groupAdd(String groupName, String mainCharacterName) throws Exception {
		
		List<Grupo> grupoList = grupoRep.findByName(groupName);
		List<CorpMember> corpMemberList = corpMemberRep.findByName(mainCharacterName);
		
		if (corpMemberList.isEmpty()) {
			throw new Exception("The main character is not found in a corp members!");
		}

		if (!grupoList.isEmpty()) {
			
			if (grupoList.get(0).getAlts().stream().filter(p -> p.getCharacterName() == mainCharacterName).count() > 0) {
				throw new Exception("Already exists Character "+mainCharacterName+ " on this group!");	
			}
			else {
				Grupo grupoUpdate = grupoList.get(0);
				GrupoDetail newAlt = new GrupoDetail();
				newAlt.setGrupo(grupoUpdate);
				newAlt.setMember(corpMemberList.get(0));
				newAlt.setCharacterName(corpMemberList.get(0).getCharacterName());
				List<GrupoDetail> alts = grupoUpdate.getAlts();
				alts.add(newAlt);
				grupoUpdate.setAlts(alts);
				grupoRep.save(grupoUpdate);
			}
		}
		else {
			Grupo grupoNew = new Grupo();
			grupoNew.setGrupoName(groupName);
			GrupoDetail grupoDetail = new GrupoDetail();
			grupoDetail.setGrupo(grupoNew);
			grupoDetail.setMember(corpMemberList.get(0));
			grupoDetail.setCharacterName(corpMemberList.get(0).getCharacterName());
			List<GrupoDetail> alts =  new ArrayList<GrupoDetail>();
			alts.add(grupoDetail);
			grupoNew.setAlts(alts);
			grupoRep.save(grupoNew);
		}
	}

	public List<GroupDto> groupList() {
		return grupoRep.getAll().stream().map(p -> GroupDto.toDto(p)).collect(Collectors.toList());
	}

	public void groupDelete(String groupName) throws Exception {
		List<Grupo> grupoList = grupoRep.findByName(groupName);
		grupoRep.delete(grupoList.get(0));
	}

	public String search(String searchName) {
		String rtn = "";
		List<GrupoDetail> grupoList = grupoDetailRep.findLikeName(searchName);
		
		if (grupoList.isEmpty()) {
			rtn = "This Character is not found in some group!";
		}
		else {
			rtn = "this Character is found on:\n";
			for (GrupoDetail g : grupoList) {
				rtn += g.getGrupo().getGrupoName()+"\n";
			}
		}
		
		return rtn;
	}
}
