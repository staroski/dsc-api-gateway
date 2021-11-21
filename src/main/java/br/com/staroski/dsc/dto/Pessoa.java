package br.com.staroski.dsc.dto;

import java.util.List;

import lombok.Data;

@Data
public class Pessoa {

	private String cpf;
	private String nome;
	private Endereco endereco;
	private List<Divida> dividas;
}
