import java.util.Random;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Dados {

	public static final String CAMINHO_DADOS = "dados/";
	//public static final String CAMINHO_DADOS = "dadosBianca/";

	public static final int NUMERO_QUESTOES = 100;

	/*
	 * Salva os dados das questoes lidos de um arquivo em um vetor bidimensional de floats tal que (i, 0) representa
	 * o discriminante da questao e (i, 1) o seu nivel de dificuldade, para i = 0, 1, ..., NUMERO_QUESTOES - 1.
	 */
	public static float[][] questoes() throws FileNotFoundException {
		File arquivo = new File(CAMINHO_DADOS + "questoes.txt");
		Scanner leitor = new Scanner(arquivo);

		float[][] questoes = new float[NUMERO_QUESTOES][2];

		for(int i = 0; i < questoes.length; i++) {
			questoes[i][0] = leitor.nextFloat(); // Parametro de discriminacao da questao
			questoes[i][1] = leitor.nextFloat(); // Parametro de dificuldade da questao
		}

		leitor.close();

		return questoes;
	}

	/*
	 * Calcula a probabilidade de alunos acertarem questoes com para
	 *
	 * @param habilidades Habilidade dos alunos
	 * @param questoes Vetor bidimensional onde (i, 0) eh o discriminante da questao e (i, 1) eh o nivel de dificuldade
	 * da questao, para i = 0, 1, ..., questoes.length - 1.
	 */
	public static double[][] calcularProbabilidadeAcerto(float[] habilidades, float[][] questoes) {
		float discriminante, dificuldade;
		double auxiliar;

		double[][] probabilidadeAcerto = new double[NUMERO_QUESTOES][habilidades.length];

		for(int i = 0; i < questoes.length; i++) {
			discriminante = questoes[i][0];
			dificuldade = questoes[i][1];

			for(int aluno = 0; aluno < habilidades.length; aluno++) {
				auxiliar = Math.pow(Math.E, discriminante * (habilidades[aluno] - dificuldade));
				probabilidadeAcerto[i][aluno] = (auxiliar / (auxiliar + 1));
			}
		}

		return probabilidadeAcerto;
	}

	/*
	 * Gera uma certa quantidade de provas com uma certa quantidade de questoes
	 */
	/*public static int[][] gerarProvas(int quantidadeProvas, int quantidadeQuestoes) {
		int[][] provas = new int[quantidadeProvas][quantidadeQuestoes];

		Random geradorProva = new Random();

		// Marcador para as questoes ja escolhidas
		boolean[] questoesEscolhidas = new boolean[NUMERO_QUESTOES];

		for(int prova = 0; prova < quantidadeProvas; prova++) {
			
			int indiceQuestao = 0; // Indice da questao na prova que esta sendo gerada
			while(indiceQuestao < quantidadeQuestoes) {
				int questao = geradorProva.nextInt(NUMERO_QUESTOES);
				if(questoesEscolhidas[questao]) continue; // Se a questao ja foi escolhida, continua

				questoesEscolhidas[questao] = true; // Marca a questao como escolhida
				provas[prova][indiceQuestao] = questao; // Insere a questao na prova
				indiceQuestao++;
			}

			// Desmarca todas as questoes que foram escolhidas
			for(int i = 0; i < quantidadeQuestoes; i++) questoesEscolhidas[provas[prova][i]] = false;
		}

		return provas;
	}*/

	/*
	 * (2.1) - Auxiliar
	 *
	 * Dado um vetor com as probabilidades de alunos acertarem uma questao, a probabilidade do aluno de indice "alunoA"
	 * ser melhor do que o aluno de indice "alunoB" eh calculada usando o criterio de que o melhor aluno em uma prova
	 * eh aquele que acertou mais questoes. As chances de um aluno acertar a questao indicada sao proporcionais a
	 * probabilidade de acerto indicada.
	 *
	 * @param alunoA Indice do aluno A no vetor de probabilidades - aluno que tera a probabilidade de ser melhor calculada
	 * @param alunoB Indice do aluno B no vetor de probabilidades - aluno que sera comparado com o alunoA
	 * @param probabilidadeAcerto Vetor relacionando a probabilidade de acerto de cada aluno a cada questao
	 * @param numeroBaseTestes Numero de provas aplicadas, isto eh, numero de testes feitos
	 */
	public static double[] probabilidadeSerMelhor(int alunoA, int alunoB, double[][] probabilidadeAcerto, int numeroBaseTestes) {
		Random acerto = new Random(1);

		int acertosA, acertosB; // Acertos de cada aluno em cada prova
		int alunoAMelhor; // Quantidade de vezes no qual o aluno A foi melhor do que B

		// Probabilidades de o aluno A ser melhor com o B de acordo com a prova
		double[] probabilidadeSerMelhor = new double[4];

		// Numero base de testes, isto eh, numero de testes para uma prova de uma unica questao
		//int numeroBaseTestes = 100000000;



		// Tipos de prova de acordo com o numero de questoes
		int[] tiposProva = {10, 20, 50};
		
		for(int i = 0; i < tiposProva.length; i++) {
			int testes = numeroBaseTestes; 

			Random geradorProva = new Random();

			// Marcador para as questoes ja escolhidas
			boolean[] questoesEscolhidas = new boolean[NUMERO_QUESTOES];

			int[] prova = new int[tiposProva[i]];

			// Provas geradas para cada teste de acordo com a quantidade de questoes do tipo da prova
			//int[][] provas = gerarProvas(testes, tiposProva[i]);

			alunoAMelhor = 0;

			for(int p = 0; p < testes; p++) {

				/* GERACAO DA PROVA */

				int indiceQuestao = 0; // Indice da questao na prova que esta sendo gerada
				while(indiceQuestao < prova.length) {
					int questao = geradorProva.nextInt(NUMERO_QUESTOES);
					if(questoesEscolhidas[questao]) continue; // Se a questao ja foi escolhida, continua

					questoesEscolhidas[questao] = true; // Marca a questao como escolhida
					prova[indiceQuestao] = questao; // Insere a questao na prova
					indiceQuestao++;
				}

				// Desmarca todas as questoes que foram escolhidas
				for(int j = 0; j < prova.length; j++) questoesEscolhidas[prova[j]] = false;

				/* AVALIACAO DOS ALUNOS USANDO A PROVA GERADA ANTERIORMENTE */

				acertosA = 0;
				acertosB = 0;
				for(int q = 0; q < prova.length; q++) {
					if(acerto.nextFloat() < probabilidadeAcerto[prova[q]][alunoA]) acertosA++;
					if(acerto.nextFloat() < probabilidadeAcerto[prova[q]][alunoB]) acertosB++;
				}

				if(acertosA > acertosB) alunoAMelhor++;
			}

			/*
			  A probabilidade de o aluno A ser melhor do que o B eh calculada dividindo o numero de vezes
			  em que o A foi melhor do que o B, pelo numero total de testes realizados.   
			*/

			System.out.println(alunoAMelhor + " " + testes);
			probabilidadeSerMelhor[i] = (double)(alunoAMelhor) / testes;
		}	



		int testes = numeroBaseTestes;

		alunoAMelhor = 0;

		for(int p = 0; p < testes; p++) {
			acertosA = 0;
			acertosB = 0;
			for(int q = 0; q < probabilidadeAcerto.length; q++) {
				if(acerto.nextFloat() < probabilidadeAcerto[q][alunoA]) acertosA++;
				if(acerto.nextFloat() < probabilidadeAcerto[q][alunoB]) acertosB++;
			}

			if(acertosA > acertosB) alunoAMelhor++;
		}

		probabilidadeSerMelhor[probabilidadeSerMelhor.length - 1] = (double)(alunoAMelhor) / testes;

		return probabilidadeSerMelhor;
	}

	/*
	 * (2.1)
	 *
	 * 
	 */
	public static void selecionarMelhorAluno(String nomeArquivo, int[] alunos, int alunoBase, double[][] probabilidadeAcerto, int numeroBaseTestes) throws FileNotFoundException, IOException {
		
		double[][] resultado = new double[alunos.length][];

		File arquivo = new File(CAMINHO_DADOS + nomeArquivo);
		FileWriter arquivoEditor = new FileWriter(arquivo, false); // Fase: sobrescreve o conteudo do arquivo, caso exista

		for(int i = 0; i < alunos.length; i++) {
			resultado[i] = probabilidadeSerMelhor(alunoBase, alunos[i], probabilidadeAcerto, numeroBaseTestes);
		}

		for(int p = 0; p < resultado[0].length; p++) {
			for(int a = 0; a < resultado.length; a++) arquivoEditor.write(resultado[a][p] + " ");
			if(p < resultado[0].length - 1) arquivoEditor.write("\n");
		}

		arquivoEditor.close();
	}

	public static int[][] melhoresProvas(int alunoA, int alunoB, double[][] probabilidadeAcerto) {
		int[] tiposProva = {10, 20, 50};

		double[][] diferencaProbabilidadeAcerto = new double[probabilidadeAcerto.length][2];

		for(int i = 0; i < diferencaProbabilidadeAcerto.length; i++) {
			// Indice da questao
			diferencaProbabilidadeAcerto[i][0] = i;

			// Diferenca entre as probabibidades dos alunos A e B acertarem a questao i
			diferencaProbabilidadeAcerto[i][1] = probabilidadeAcerto[i][alunoA] - probabilidadeAcerto[i][alunoB];
		}

		// Ordena as questoes de acordo com a diferente entre a probabilidades de acerto dos alunos A e B
		diferencaProbabilidadeAcerto = MergeSort.mergeSort(diferencaProbabilidadeAcerto, 1);

		int[][] provas = new int[tiposProva.length][];

		// Seleciona as melhores provas
		for(int i = 0; i < tiposProva.length; i++) {
			provas[i] = new int[tiposProva[i]];
			for(int j = 0; j < provas.length; j++) provas[i][j] = (int)diferencaProbabilidadeAcerto[j][0];
		}

		return provas;
	}

	public static void selecionarMelhorProva(String arquivo, int alunoA, int alunoB, double[][] probabilidadeAcerto) throws FileNotFoundException, IOException {

		int[][] melhoresProvas = melhoresProvas(alunoA, alunoB, probabilidadeAcerto);

	}
}













