# Getting Started

### Tecnologias adotadas
O desafio foi implementado utilizando as seguintes tecnologias:

* [Java 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9) como linguagem de programação;
* [Spring Boot](https://spring.io/projects/spring-boot) para simplificar a implementação de microsserviços;
* [Spring Cloud OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/) para fazer a integração com serviços exitentes utilizando apenas interface e anotações;
* [Lombok](https://projectlombok.org/) para reduzir a escrita de código boilerplate;
* [Maven](https://maven.apache.org/) para gerenciamento de dependências do projeto;
* [Git](https://git-scm.com/) para controle de versões.

### Arquitetura utilizada
A arquitetura utilizada foi a de microsserviços.

### Dados armazenados
Por fazer uma integração com bases hipoteticamente já existentes (Base A, Base B e Base C) e só realizar consultas, a solução não persiste nenhum dado coletado destes acessos, eles são armazenados somente em memória durante a requisição.


### Implementação
Para trafegar os dados, foram criadas algumas classes DTO, abreviação de Data Transfer Object, um padrão de projeto para classes cujo propósito é trafegar dados pela rede.

Para representar os dados obtidos pelo Serviço A, as seguintes classes DTO foram criadas:

```
	@Data
	public class Pessoa {
		private String cpf;
		private String nome;
		private Endereco endereco;
		private List<Divida> dividas;
	}
```

```
	@Data
	public class Endereco {
		private String logradouro;
		private String bairro;
		private String cidade;
		private String estado;
	}
```

```
	@Data
	public class Divida {
		private BigDecimal valor;
		private String descricao;
	}
```

Para representar os dados obtidos pelo Serviço B, as seguintes classes DTO foram criadas:

```
	@Data
	public class Score {
		private String cpf;
		private Endereco endereco;
		private String fonteRenda;
		private List<Bem> bens;
	}
```

```
	@Data
	public class Bem {
		private BigDecimal valor;
		private String descricao;
	}
```

Para representar os dados obtidos pelo Serviço C, as seguintes classes DTO foram criadas:

```
	@Data
	public class Evento {
		private String cpf;
		private LocalDateTime ultimaConsulta;
		private Compra ultimaCompra;
		private List<Movimentacao> movimentacaoFinanceira;
	}
```

```
	@Data
	public class Compra {
		private LocalDateTime dataHora;
		private BigDecimal valor;
		private String numeroCartao;
	}
```

```
	@Data
	public class Movimentacao {
		private LocalDateTime dataHora;
		private BigDecimal valor;
	}
```

Como o problema descreve que os serviços para acessar Base A, Base B e Base C já existem, será implementado uma integração utilizando o Spring Cloud OpenFeign, desta forma para acessar cada uma das 3 bases, será declarada uma interface com algumas anotações do OpenFeign.

Interface para acessar a Base A:

```
	@FeignClient(name = "BaseAService", url = "${integration.baseA.host}")
	public interface IntegrationA {

		@GetMapping("${integration.baseA.get.pessoa}")
		public Pessoa getPessoa(@PathVariable("cpf") String cpf);
	}
```

Interface para acessar a Base B:

```
	@FeignClient(name = "BaseBService", url = "${integration.baseB.host}")
	public interface IntegrationB {

		@GetMapping("${integration.baseB.get.score}")
		public Score getScoreByCpf(@PathVariable("cpf") String cpf);
	}
```

Interface para acessar a Base C:

```
	@FeignClient(name = "BaseCService", url = "${integration.baseC.host}")
	public interface IntegrationC {

		@GetMapping("${integration.baseC.get.evento}")
		public Evento getEventoByCpf(@PathVariable("cpf") String cpf);
	}
```

Observa-se que há algumas propriedades sendo utilizadas nas anotações, essas propriedades estão definidas no arquivo `application.yml`, conforme a seguir:

```
integration:
  baseA:
    host: https://url.hipotetica.do.servico.A
    get:
      pessoa: /pessoa/{cpf}
  baseB:
    host: https://url.hipotetica.do.servico.B
    get:
      score: /score/{cpf}
  baseC:
    host: https://url.hipotetica.do.servico.C
    get:
      evento: /evento/{cpf}
```

Com as interfaces de integração definidas, basta implementar um REST Controller que atuará como um gateway para os 3 serviços:

```
	@AllArgsConstructor
	@RestController
	@RequestMapping("/dsc")
	public class DSCController {

		@Autowired
		private final IntegrationA serviceA;

		@Autowired
		private final IntegrationB serviceB;

		@Autowired
		private final IntegrationC serviceC;

		@GetMapping("/pessoa/{cpf}")
		public Pessoa getPessoa(@PathVariable("cpf") String cpf) {
			return serviceA.getPessoa(cpf);
		}

		@GetMapping("/score/{cpf}")
		public Score getScoreByCpf(@PathVariable("cpf") String cpf) {
			return serviceB.getScoreByCpf(cpf);
		}

		@GetMapping("/evento/{cpf}")
		public Evento getEventoByCpf(@PathVariable("cpf") String cpf) {
			return serviceC.getEventoByCpf(cpf);
		}
	}
```

### Execução

Finalmente, para subir a aplicação, foi criado a seguinte classe, que pode ser executada como uma aplicação Java standalone:

```
	@EnableFeignClients
	@SpringBootApplication
	public class DscApiGatewayApplication {

		public static void main(String[] args) {
			SpringApplication.run(DscApiGatewayApplication.class, args);
		}
	}
```

Com a aplicação (e os serviços A, B e C) no ar, basta acessar os seguintes endpoints:

Obter informações de uma pessoa:

```
	https://{host}:{porta}/dsc/pessoa/{cpf}
```

Obter score de uma pessoa:

```
	https://{host}:{porta}/dsc/score/{cpf}
```

Obter o último evento de uma pessoa:

```
	https://{host}:{porta}/dsc/evento/{cpf}
```

Importante:
`{host}`, `{porta}` e `{cpf}` devem ser substituídos por valores adequados às configurações do ambiente onde a aplicação está executando.