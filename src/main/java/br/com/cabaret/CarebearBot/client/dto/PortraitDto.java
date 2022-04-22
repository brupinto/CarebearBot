package br.com.cabaret.CarebearBot.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PortraitDto {
	private String px128x128;
	private String px256x256;
	private String px512x512;
	private String px64x64;
}
