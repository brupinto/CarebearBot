package br.com.cabaret.CarebearBot.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.cabaret.CarebearBot.Bot;
import br.com.cabaret.CarebearBot.service.CorporateService;

@Component
public class BatchComponent {
	
	@Autowired
	private CorporateService corpService;
	
	@Autowired
	private Bot bot;
	
	@Scheduled(cron = "* */12 * * * *") //vai rodar em 1 e 1 hora
	public void UpdateMemberList() {
		corpService.updateMemberList();
	}
	
	@Scheduled(cron = "* * * * * 4") //Roda todas as quinta-feiras
	public void updateMiningList() {
		corpService.updateMiningReport();
	}
}
