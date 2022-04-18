package br.com.cabaret.CarebearBot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cabaret.CarebearBot.client.CharacterClient;
import br.com.cabaret.CarebearBot.client.CorporationClient;
import br.com.cabaret.CarebearBot.client.IndustryClient;
import br.com.cabaret.CarebearBot.client.UniverseClient;
import br.com.cabaret.CarebearBot.client.dto.CharacterDto;
import br.com.cabaret.CarebearBot.client.dto.MiningObserversDetailDto;
import br.com.cabaret.CarebearBot.client.dto.MiningObserversDto;
import br.com.cabaret.CarebearBot.client.dto.TypeDto;
import br.com.cabaret.CarebearBot.model.CorpMember;
import br.com.cabaret.CarebearBot.model.MiningObserver;
import br.com.cabaret.CarebearBot.model.MiningObserverHistory;
import br.com.cabaret.CarebearBot.model.TypeEve;
import br.com.cabaret.CarebearBot.repository.CorpMemberRepository;
import br.com.cabaret.CarebearBot.repository.DirectorRepository;
import br.com.cabaret.CarebearBot.repository.MiningObserverHistoryRepository;
import br.com.cabaret.CarebearBot.repository.MiningObserverRepository;
import br.com.cabaret.CarebearBot.repository.TypeEveRepository;

@Service
public class CorporateService {

	@Autowired
	private CorporationClient corpClient;
	
	@Autowired
	private CharacterClient charClient;
	
	@Autowired
	private RegisterAuthService authService;
	
	@Autowired
	private DirectorRepository diretorRep;
	
	@Autowired
	private CorpMemberRepository corpMemberRep;
	
	@Autowired
	private IndustryClient indClient;
	
	@Autowired
	private MiningObserverRepository miningObserverRep;
	
	@Autowired
	private MiningObserverHistoryRepository miningObserverHistoryRep;
	
	
	@Autowired
	private TypeEveRepository typeRep;
	
	@Autowired
	private UniverseClient universeClient;
	
	public void updateMemberList() {
		try {
			List<Long> members = corpClient.memberList(diretorRep.getDirector().get(0).getCorporateId(), authService.refreshToken());
			
			for (Long membro : members) {
				CorpMember m = corpMemberRep.findById(membro).get();
				
				if (m == null) {
					CorpMember corp = new CorpMember();
					corp.setCharacterId(membro);
					corp.setCharacterName(null);
					corp.setDtLastUpdate(LocalDateTime.now());
					
					corpMemberRep.save(corp);
				}
			}	
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		updateMemberName();
		
	}
	
	
	public void updateMiningReport() {
		
		try {
			String token = authService.refreshToken();
			List<MiningObserversDto> observers = indClient.miningObservers(diretorRep.getDirector().get(0).getCorporateId(), token);
			List<MiningObserver> miningObsList = observers.stream().map(p -> MiningObserver.parse(p)).collect(Collectors.toList());
			
			miningObserverRep.saveAll(miningObsList);
			
			for (MiningObserver m : miningObsList) {
				Boolean isChangePage = true;
				Long page = miningObserverHistoryRep.getMaxPage(m.getObserver_id());
				
				while(isChangePage) {
					List<MiningObserversDetailDto> report = indClient.miningObserversDetail(diretorRep.getDirector().get(0).getCorporateId(), m.getObserver_id(), token, page);
					List<MiningObserverHistory> reportList = parseMiningObserverHistory(report, m, page);
					
					if (reportList.size() == 1000) {
						page++;
					}
					else {
						isChangePage = false;
					}
					
					for(MiningObserverHistory moh : reportList) {
						MiningObserverHistory mExists = miningObserverHistoryRep.searchData(moh.getObserver().getObserver_id(), moh.getCorpMember().getCharacterId(), moh.getType_id(), moh.getLast_updated());
						mExists.setQuantity(moh.getQuantity());
						miningObserverHistoryRep.save(mExists);	
					}
				}
				
				if (page != null) {
					if (page == 0) {
						page = 1L;
					}
				} 
				else {
					page = 1L;
				}
			}
			
			updateType();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void updateMemberName() {
		List<CorpMember> newMembers = corpMemberRep.findByNameNull();
		
		if (newMembers != null && !newMembers.isEmpty()) {
			for (CorpMember cm : newMembers) {
				CharacterDto dto = charClient.characters(cm.getCharacterId());
				cm.setCharacterName(dto.getName());
			}
			
			corpMemberRep.saveAll(newMembers);
		}
	}
	
	private void updateType() {
		List<Long> types = miningObserverHistoryRep.distinctType();
		List<TypeEve> typesEve = new ArrayList<>();
		
		if (types != null  && !types.isEmpty()) {
			for (Long l : types) {
				TypeDto typeDto = universeClient.typeDetail(l);
				TypeEve newType = new TypeEve();
				
				newType.setName(typeDto.getName());
				newType.setType_id(typeDto.getType_id());
				typesEve.add(newType);
			}
			typeRep.saveAll(typesEve);
			
			for (TypeEve te : typesEve) {
				List<MiningObserverHistory> mohList = miningObserverHistoryRep.findByTypeId(te.getType_id());
				
				for (MiningObserverHistory p : mohList) {
					p.setTypeDescription(te.getName());
				}
				
				miningObserverHistoryRep.saveAll(mohList);
			}
		}
	}

	private List<MiningObserverHistory> parseMiningObserverHistory(List<MiningObserversDetailDto> report, MiningObserver m, Long page) {
		List<MiningObserverHistory> miningObservHistList = new ArrayList<>();
		
		for (MiningObserversDetailDto p: report) {
			MiningObserverHistory rtn = new MiningObserverHistory();
			rtn.setCorpMember(new CorpMember(p.getCharacter_id()));
			rtn.setLast_updated(LocalDate.parse(p.getLast_updated(), DateTimeFormatter.ISO_DATE));
			rtn.setObserver(m);
			rtn.setPage(page);
			rtn.setQuantity(p.getQuantity());
			rtn.setRecorded_corporation_id(p.getRecorded_corporation_id());
			rtn.setType_id(p.getType_id());
			rtn.setTypeDescription(null);
			
			miningObservHistList.add(rtn);
		}
		
		return miningObservHistList;
	}
}
