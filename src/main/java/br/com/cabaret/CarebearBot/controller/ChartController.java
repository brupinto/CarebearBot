package br.com.cabaret.CarebearBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cabaret.CarebearBot.service.ResolveCommandService;
import br.com.cabaret.CarebearBot.service.dto.ReportMiningGraphDto;

@RestController
@RequestMapping("report")
public class ChartController {
	
	@Autowired
	private ResolveCommandService cmdService;
	
	@GetMapping("chart")
	@ResponseBody
	public String callBackPlayers(@RequestParam(required = false) String dtini, @RequestParam(required = false) String dtfim) {
		StringBuffer html = new StringBuffer();
		ReportMiningGraphDto dto = cmdService.reportMiningGraphPlayers(dtini, dtfim);
		html.append("<html>");
		html.append("<head><title>Graph Report</title>");
		html.append("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.8.0/chart.min.js\"></script>");
		html.append("</head>");
		html.append("<body>");
		html.append("<canvas id=\"myChart\" width=\"200\" height=\"200\"></canvas>");
		html.append("<script type=\"text/javascript\">");
		html.append("const ctx = document.getElementById('myChart').getContext('2d');");
		html.append("const myChart = new Chart(ctx, {                                                    "
				  + "  type: 'pie',                                                                      "
				  + "  data: {                                                                           "
				  + "      labels: "+ dto.getLabels() +",                                                "
				  + "      datasets: [{                                                                  "
				  + "          label: 'Quantidade Total Minerada',                                       "
				  + "          data: "+ dto.getData() +",                                                "
				  + "          backgroundColor: "+ dto.getBackgroundColor() +",                          "
				  + "          hoverOffset: 4                                                            "
				  + "      }]                                                                            "
				  + "  },                                                                                "
				  + "  options: {                                                                        "
				  + "      layout: {                                                                     " 
				  + "              padding: {                                                            "
				  + "                       left:10,                                                     "
				  + "                       right:200,                                                   "
				  + "                       bottom:1200                                                  "
				  + "                       }                                                            "
				  + "      }                                                                             "
				  + "  }                                                                                 "
				  + " });                                                                                ");
		html.append("</script>");
		html.append("");
		html.append("</body>");
		html.append("</html>");
		
		return html.toString();
	}
	
	@GetMapping("chartores")
	@ResponseBody
	public String callBackOres(@RequestParam(required = false) String dtini, @RequestParam(required = false) String dtfim) {
		StringBuffer html = new StringBuffer();
		ReportMiningGraphDto dto = cmdService.reportMiningGraphOres(dtini, dtfim);
		html.append("<html>");
		html.append("<head><title>Graph Report</title>");
		html.append("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.8.0/chart.min.js\"></script>");
		html.append("</head>");
		html.append("<body>");
		html.append("<canvas id=\"myChart\" width=\"200\" height=\"200\"></canvas>");
		html.append("<script type=\"text/javascript\">");
		html.append("const ctx = document.getElementById('myChart').getContext('2d');");
		html.append("const myChart = new Chart(ctx, {                                                    "
				  + "  type: 'pie',                                                                      "
				  + "  data: {                                                                           "
				  + "      labels: "+ dto.getLabels() +",                                                "
				  + "      datasets: [{                                                                  "
				  + "          label: 'Quantidade Total Minerada',                                       "
				  + "          data: "+ dto.getData() +",                                                "
				  + "          backgroundColor: "+ dto.getBackgroundColor() +",                          "
				  + "          hoverOffset: 4                                                            "
				  + "      }]                                                                            "
				  + "  },                                                                                "
				  + "  options: {                                                                        "
				  + "      layout: {                                                                     " 
				  + "              padding: {                                                            "
				  + "                       left:10,                                                     "
				  + "                       right:200,                                                   "
				  + "                       bottom:1200                                                  "
				  + "                       }                                                            "
				  + "      }                                                                             "
				  + "  }                                                                                 "
				  + " });                                                                                ");
		html.append("</script>");
		html.append("");
		html.append("</body>");
		html.append("</html>");
		
		return html.toString();
	}
}