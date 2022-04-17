package br.com.cabaret.CarebearBot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeDto {
	private String description;
	private Long group_id;
	private String name;
	private Boolean isPublished;
	private Long type_id;
}
