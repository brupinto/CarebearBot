package br.com.cabaret.CarebearBot.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cabaret.CarebearBot.dto.ReportMiningDto;
import br.com.cabaret.CarebearBot.dto.ResultReportMiningDetailDto;
import br.com.cabaret.CarebearBot.dto.ResultReportMiningDto;
import br.com.cabaret.CarebearBot.repository.MiningObserverHistoryRepository;
import br.com.cabaret.CarebearBot.service.dto.GroupDto;
import br.com.cabaret.CarebearBot.service.dto.ReportMiningGraphDto;

@Service
public class ResolveCommandService {

	@Autowired
	private CorporateService corpService;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private MiningObserverHistoryRepository miningObsHistRep;
	
	public List<GroupDto> groupList() {
		
		return grupoService.groupList();
	}

	public String groupAdd(String contentRaw) {
		String msgRtn = "This command will input a new characters group \n"
				+ "**!group-add <GroupName> <CharacterName>**\n"
				+ "\n"
				+ "**<GroupName>** - Only one word. It is hasn't space character in compose \n"
				+ "**<CharacterName>** - The name of character you wish add in the group \n"
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
				+ "**!group-delete <GroupName>**\n"
				+ "\n"
				+ "**<GroupName>** - Only one word. It is hasn't space character in compose \n"
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
				+ "**!search <Character name>**\n"
				+ "\n"
				+ "**<GroupName>** - full name or part of name\n"
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

	public String reportMining(String contentRaw, List<ResultReportMiningDto> result) {
		String msgRtn = "This command will output mining report \n"
				+ "**!report-mining <Initial date> <End date>**\n"
				+ "\n"
				+ "**<initial date>** - Select mining records start from this date \n"
				+ "**<End date>** - Select mining records until end date. The end date is optional \n"
				+ "\n"
				+ "An example use that:\n"
				+ "!report-mining 2022-01-30 \n"
				+ "or !report-mining 2022-01-30 2022-02-15 \n";
	
		try 
		{
			String searchName = contentRaw.replace("!report-mining","").trim();
	
			if (!searchName.isEmpty()) {
				List<String> dts = Arrays.asList(searchName.split(" "));
				LocalDate dtIni = LocalDate.parse(dts.get(0));
				LocalDate dtFim = LocalDate.now();
				
				if (dts.size() == 2) {
					dtFim = LocalDate.parse(dts.get(1));
				}
				
				List<Object[]> tmp = miningObsHistRep.reportMining(dtIni, dtFim);
				List<ReportMiningDto> reportValues = new ArrayList<>();
				
				for (Object[] os : tmp) {
					ReportMiningDto d = new ReportMiningDto(); 
					d.setCharacterName((String)os[0]);
					d.setTypeName((String)os[1]);
					d.setQtMining(((BigDecimal)os[2]).longValue());
					d.setTaxaCorp(Long.valueOf(((String)os[3]).replaceAll(",", "")));
					d.setFgChar(((BigInteger) os[4]).longValue());
					if (os[5] != null)
						d.setFlNotMember(((BigInteger) os[5]).intValue());
					
					
					reportValues.add(d);
				}
				
				ResultReportMiningDto regAtual = null;
				
				for(ReportMiningDto r : reportValues) {
					if (regAtual == null) {
						regAtual = mountReg(r);
						ResultReportMiningDetailDto detail = new ResultReportMiningDetailDto(r.getTypeName(), r.getQtMining(), r.getTaxaCorp(), r.getFgChar());
						regAtual.getOres().add(detail);
					}
					else {
						if (r.getCharacterName().equalsIgnoreCase(regAtual.getCharacterName())) {
							ResultReportMiningDetailDto detail = new ResultReportMiningDetailDto(r.getTypeName(), r.getQtMining(), r.getTaxaCorp(), r.getFgChar());
							regAtual.getOres().add(detail);
						}
						else {
							result.add(regAtual);
							regAtual = mountReg(r);
							ResultReportMiningDetailDto detail = new ResultReportMiningDetailDto(r.getTypeName(), r.getQtMining(), r.getTaxaCorp(), r.getFgChar());
							regAtual.getOres().add(detail);
						}
					}
				}
				
				if (regAtual != null)
					result.add(regAtual);
				
				msgRtn = "Report to ["+dtIni.toString()+"] until ["+dtFim.toString()+"]\n **Graph Players**\n http://52.24.37.58:8080/report/chart?dtini="+dtIni.toString()+"&dtfim="+dtFim.toString()+"\n **Graph Ores**\n http://52.24.37.58:8080/report/chartores?dtini="+dtIni.toString()+"&dtfim="+dtFim.toString()+"\n";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			msgRtn = "Something is wrong :( \n"
					+ "Had error.:"+ e.getMessage();
		}
			
		return msgRtn;
	}
	
	public ReportMiningGraphDto reportMiningGraphPlayers(String dtIniVal, String dtFimVal) {
		ReportMiningGraphDto rtn = new ReportMiningGraphDto();
		
		try 
		{
			LocalDate dtIni = LocalDate.parse(dtIniVal);
			LocalDate dtFim = dtFimVal.isEmpty() ? LocalDate.now() :  LocalDate.parse(dtFimVal);
			
			List<Object[]> tmp = miningObsHistRep.reportMiningGraphPlayers(dtIni, dtFim);
			List<ReportMiningDto> reportValues = new ArrayList<>();
			
			for (Object[] os : tmp) {
				ReportMiningDto d = new ReportMiningDto(); 
				d.setCharacterName((String)os[0]);
				d.setPcMining(((BigDecimal)os[1]));
				d.setFgChar(((BigInteger) os[2]).longValue());

				reportValues.add(d);
			}
			
			for(ReportMiningDto r : reportValues) {
				rtn.addRegister(r.getCharacterName(), r.getPcMining().toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return rtn;			
	}
	
	public ReportMiningGraphDto reportMiningGraphOres(String dtIniVal, String dtFimVal) {
		ReportMiningGraphDto rtn = new ReportMiningGraphDto();
		
		try 
		{
			LocalDate dtIni = LocalDate.parse(dtIniVal);
			LocalDate dtFim = dtFimVal.isEmpty() ? LocalDate.now() :  LocalDate.parse(dtFimVal);
			
			List<Object[]> tmp = miningObsHistRep.reportMiningGraphOres(dtIni, dtFim);
			List<ReportMiningDto> reportValues = new ArrayList<>();
			
			for (Object[] os : tmp) {
				ReportMiningDto d = new ReportMiningDto(); 
				d.setTypeName((String)os[0]);
				d.setQtMining(((BigDecimal)os[1]).longValue());
				reportValues.add(d);
			}
			
			for(ReportMiningDto r : reportValues) {
				rtn.addRegister(r.getTypeName(), r.getQtMining().toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return rtn;			
	}
	
	private ResultReportMiningDto mountReg(ReportMiningDto r) {
		ResultReportMiningDto regAtual = new ResultReportMiningDto();
		regAtual.setCharacterName(r.getCharacterName());
		regAtual.setFlNotMember(r.getFlNotMember());
		regAtual.setOres(new ArrayList<ResultReportMiningDetailDto>());
		
		return regAtual;
	}

	public void updateMemberList() {
		corpService.updateMemberList();
	}
	
	public void updateAll() {
		corpService.updateMemberList();
		corpService.updateMiningReport();
	}
}
