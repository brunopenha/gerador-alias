package br.nom.penha.bruno.batch.alias.processadores;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.nom.penha.bruno.batch.alias.entidades.Apelido;

public class ApelidoItemProcessor implements ItemProcessor<Apelido,Apelido>{

	private static final Logger LOG = LoggerFactory.getLogger(ApelidoItemProcessor.class);
	
	private static List<Apelido> listaGerada = new ArrayList<Apelido>();
	
	@Override
	public Apelido process(final Apelido item) throws Exception {
		
		final String nomeOriginal = item.getNomeOriginal();
		
		final Apelido sujeitoAlterado = new Apelido(nomeOriginal);
		
		// Verificar se o apelido gerado ja existe
		
		LOG.info("Convertendo (" + item + ") em (" + sujeitoAlterado + ")");
		
		
		return sujeitoAlterado;
	}

	public static List<Apelido> getListaGerada() {
		return listaGerada;
	}

	
}
