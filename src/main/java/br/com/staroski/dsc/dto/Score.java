package br.com.staroski.dsc.dto;

import java.util.List;

import lombok.Data;

@Data
public class Score {

	private String cpf;
	private Endereco endereco;
	private String fonteRenda;
	private List<Bem> bens;
}
