import java.io.IOException;

import java.util.Random;


public class Main {

	static ContadorTempo contadorTempo = new ContadorTempo(); 

	static float[] habilidades = {-1.0F, -0.5F, 0.0F, 0.5F, 1.0F};

	public static void main(String[] args) throws IOException {

		// Eh necessario alterar a localizacao padrao ingles por questoes de formatacao numerica
		java.util.Locale.setDefault(new java.util.Locale("ENGLISH"));

		float[][] questoes = Dados.questoes();

		double[][] probabilidadeAcerto = Dados.calcularProbabilidadeAcerto(habilidades, questoes);

		/* // 2.1
		contadorTempo.iniciar();
		int[] alunos = {0, 1, 2, 3};
		Dados.selecionarMelhorAluno("I1.txt", alunos, 4, probabilidadeAcerto, 100000);
		System.out.println("Tempo: " + contadorTempo.parar() + "ms");
		*/

		
		// 2.2
		contadorTempo.iniciar();
		int[][] provas = Dados.melhoresProvas(3, 0, probabilidadeAcerto);
		System.out.println("Tempo: " + contadorTempo.parar() + "ms");



		System.out.println();
	}

}
