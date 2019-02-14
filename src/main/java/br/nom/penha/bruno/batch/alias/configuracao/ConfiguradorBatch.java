package br.nom.penha.bruno.batch.alias.configuracao;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import br.nom.penha.bruno.batch.alias.entidades.Apelido;
import br.nom.penha.bruno.batch.alias.notificadores.OuvinteNotificacaoTarefaConcluida;
import br.nom.penha.bruno.batch.alias.processadores.ApelidoItemProcessor;

@Configuration
@EnableBatchProcessing
public class ConfiguradorBatch {
	
	@Autowired
	public JobBuilderFactory fabricaMontadorTarefas;
	
	@Autowired
	public StepBuilderFactory fabricaMontadorPassos;

	// Inicio do Reader Writer Processor
	@Bean
	public FlatFileItemReader<Apelido> leitor(){
		return new FlatFileItemReaderBuilder<Apelido>()
				.name("sujeitoItemReader")
				.resource(new ClassPathResource("lista-arquivos.txt"))
				.delimited()
				.names(new String[] {"nomeOriginal"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Apelido>() {
					{
						setTargetType(Apelido.class);
					}
				})
				.build();
	}
	
	@Bean
	public ApelidoItemProcessor processador() {
		return new ApelidoItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Apelido> escritor(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Apelido>()
					.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
					.sql("INSERT INTO cidadaos (primeiro_nome,ultimo_nome) VALUES (:primeiroNome,:ultimoNome)")
					.dataSource(dataSource)
					.build();
				
				
	}
	 // tag::readerwriterprocessor[]
	// Fim do Reader Writer Processor
	
	// Inicio do Job Step
	
	@Bean
	public Job tarefaImportarSujeitos(OuvinteNotificacaoTarefaConcluida ouvinte, Step passo1) {
		
		return fabricaMontadorTarefas.get("tarefaImportarSujeitos")
				.incrementer(new RunIdIncrementer())
				.listener(ouvinte)
				.flow(passo1)
				.end()
				.build();
	}
	
	@Bean
	public Step passo1(JdbcBatchItemWriter<Apelido> escritorParam) {
		return fabricaMontadorPassos.get("passo1")
				.<Apelido, Apelido> chunk(10)
				.reader(leitor())
				.processor(processador())
				.writer(escritorParam)
				.build();
	}
	
	// Fim do Job Step
}
