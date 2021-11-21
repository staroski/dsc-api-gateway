package br.com.staroski.dsc.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Bem {

	private BigDecimal valor;
	private String descricao;
}
