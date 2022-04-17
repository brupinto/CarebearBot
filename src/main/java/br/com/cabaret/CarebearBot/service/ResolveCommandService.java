package br.com.cabaret.CarebearBot.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cabaret.CarebearBot.service.dto.GroupDto;

@Service
public class ResolveCommandService {

	@Autowired
	CorporateService corpService;
	
	@Autowired
	GrupoService grupoService;
	
	public List<GroupDto> groupList() {
		
		return grupoService.groupList();
	}

	public String groupAdd(String contentRaw) {
		String msgRtn = "This command will input a new characters group \n"
				+ "!group-add <GroupName> <CharacterName>\n"
				+ "\n"
				+ "<GroupName> - Only one word. It is hasn't space character in compose \n"
				+ "<CharacterName> - The name of character you wish add in the group \n"
				+ "\n"
				+ "An example use that:\n"
				+ "!group-add bruno ziccaminer \n";
		
		List<String> cmds = Arrays.asList(contentRaw.split(" "));
		
		if (cmds.size() >= 3) {
			try {
				String groupName = cmds.get(1);
				String mainCharacterName = contentRaw.replace(cmds.get(0)+" "+cmds.get(1),"").trim();
				
				grupoService.groupAdd(groupName.toLowerCase(), mainCharacterName.toLowerCase());
				
				msgRtn = "Group "+ groupName +" has created or updated!";
			}
			catch(Exception e) {
				msgRtn = "Something is wrong :( \n"
						+ "Had error.:"+ e.getMessage();
			}
			
		}
				
		return msgRtn;
	}

	public String groupDelete(String contentRaw) {
		String msgRtn = "This command will remove a characters group \n"
				+ "!group-delete <GroupName>\n"
				+ "\n"
				+ "<GroupName> - Only one word. It is hasn't space character in compose \n"
				+ "\n"
				+ "An example use that:\n"
				+ "!group-delete bruno \n";
		
		List<String> cmds = Arrays.asList(contentRaw.split(" "));
		
		if (cmds.size() == 2) {
			try {
				grupoService.groupDelete(cmds.get(1).toLowerCase());
				msgRtn = "Group removed with sucess!";
			}
			catch(Exception e) {
				msgRtn = "Something is wrong :( \n"
						+ "Had error.:"+ e.getMessage();
			}
		}
		
		return msgRtn;
	}

	public String search(String contentRaw) {
		String msgRtn = "This command will remove a characters group \n"
				+ "!search <Character name>\n"
				+ "\n"
				+ "<GroupName> - full name or part of name\n"
				+ "\n"
				+ "An example use that:\n"
				+ "!search sharp \n";
		
		String searchName = contentRaw.replace("!search","").trim();

		if (!searchName.isEmpty()) {
			try {
				msgRtn = grupoService.search("%"+searchName.toLowerCase()+"%");	
			}
			catch(Exception e) {
				msgRtn = "Something is wrong :( \n"
						+ "Had error.:"+ e.getMessage();
			}
		}
		
		return msgRtn;
	}

	public String reportMining(String contentRaw) {
		corpService.updateMiningReport();
		return "done!";
	}

}
