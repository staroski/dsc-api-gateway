package br.com.staroski.dsc.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Evento {

	private String cpf;
	private LocalDateTime ultimaConsulta;
	private Compra ultimaCompra;
	private List<Movimentacao> movimentacaoFinanceira;
}
