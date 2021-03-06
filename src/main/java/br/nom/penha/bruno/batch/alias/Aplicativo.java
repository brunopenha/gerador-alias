package br.nom.penha.bruno.batch.alias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Essa classe deve ficar na raiz de todos os pacotes, pois ele será o "root context" do Batch
 * @author brunopenha
 *
 */
@SpringBootApplication
public class Aplicativo {

	public static void main(String[] args) {
		
		SpringApplication.run(Aplicativo.class, args);
	}
}
