package br.com.staroski.dsc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Movimentacao {

	private LocalDateTime dataHora;
	private BigDecimal valor;
}
