package br.com.cabaret.CarebearBot.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReportMiningGraphDto {
	private List<String> labels;
	private List<String> data;
	private List<String> backgroundColor;
	
	public ReportMiningGraphDto() {
		labels = new ArrayList<>();
		data = new ArrayList<>();
		backgroundColor =  new ArrayList<>();
	}
	public void addRegister(String labelVal, String dataVal) {
		Random longGen = new Random();
		labels.add(labelVal);
		data.add(dataVal);
		backgroundColor.add("'rgba("+ longGen.longs(0, 255).findFirst().getAsLong() +", "+ longGen.longs(0, 255).findFirst().getAsLong() +", "+ longGen.longs(0, 255).findFirst().getAsLong() +", 0.2)'" );
	}
	public String getLabels() {
		String rtn = "[";
		
		for (String v : labels) {
			rtn += "'"+ v +"',";
		}
		
		rtn = rtn.substring(0, rtn.length()-1);
		rtn += "]";
		return rtn;
	}
	
	public String getData() {
		String rtn = "[";
		
		for (String v : data) {
			rtn += v +",";
		}
		
		rtn = rtn.substring(0, rtn.length()-1);
		rtn += "]";
		return rtn;
	}
	
	public String getBackgroundColor() {
		String rtn = "[";
		
		for (String v : backgroundColor) {
			rtn +=  v +",";
		}
		
		rtn = rtn.substring(0, rtn.length()-1);
		rtn += "]";
		return rtn;
	}
	
}
