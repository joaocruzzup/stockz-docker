package br.com.catalisa.stockz;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "StockZ API", version = "1", description = "Uma API REST para o gerenciamento eficiente de estoques de produtos, oferecendo recursos para categorização, controle de produtos, interações com compradores e fornecedores, registro de transações de entrada e saída, além da geração de relatórios detalhados para a gestão dos estoques."))
public class StockzApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockzApplication.class, args);
	}

}
