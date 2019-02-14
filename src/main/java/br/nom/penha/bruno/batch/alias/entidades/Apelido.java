package br.nom.penha.bruno.batch.alias.entidades;

public class Apelido {

	private String apelidoGerado;
	private String nomeOriginal;

	public Apelido() {

	}

	/**
	 * @param nomeOriginal
	 */
	public Apelido(String nomeOriginal) {
		this.apelidoGerado = "";
		this.nomeOriginal = nomeOriginal;

		// Inicial de cada parte
		int inicio = 0;
		int fim = 1;
		geraApelido(inicio, fim++);
		// Letra seguinte de cada parte ate atingir o limite de 5 caractere

		while (apelidoGerado.length() < 5) {
			geraApelido(inicio, fim++);
		}

	}

	private void geraApelido(int inicio, int fim) {

		String[] nomeSeparado = nomeOriginal.split("_");
		if (this.apelidoGerado.length() == 0) {
			
			for (String parte : nomeSeparado) {
				this.apelidoGerado += parte.substring(inicio, fim);

				if (apelidoGerado.length() == 5) {
					break;
				}
			}
		} else {
			this.apelidoGerado = "";
			for (String parte : nomeSeparado) {
				this.apelidoGerado += parte.substring(inicio, fim++);

				if (apelidoGerado.length() == 5) {
					break;
				}
			}
			
		}

	}

	/**
	 * @param apelidoGerado
	 * @param nomeOriginal
	 */
	public Apelido(String apelidoGerado, String nomeOriginal) {
		this.apelidoGerado = apelidoGerado;
		this.nomeOriginal = nomeOriginal;
	}

	public String getApelidoGerado() {
		return apelidoGerado;
	}

	public void setApelidoGerado(String primeiroNome) {
		this.apelidoGerado = primeiroNome;
	}

	public String getNomeOriginal() {
		return nomeOriginal;
	}

	public void setNomeOriginal(String ultimoNome) {
		this.nomeOriginal = ultimoNome;
	}

	@Override
	public String toString() {
		return "Apelido [apelidoGerado=" + apelidoGerado + ", nomeOriginal=" + nomeOriginal + "]";
	}

}
