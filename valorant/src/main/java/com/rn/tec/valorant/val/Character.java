package com.rn.tec.valorant.val;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
	
	private String displayName;
	private String description;
	private String developerName;
	private String displayIcon;
	
}
