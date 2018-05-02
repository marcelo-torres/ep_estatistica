import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Random;

public class Main {

	static ContadorTempo contadorTempo = new ContadorTempo(); 

	static float[] habilidades = {-1.0F, -0.5F, 0.0F, 0.5F, 1.0F};

	static int numeroPadraoTestes = 1000000;

	public static void main(String[] args) throws IOException {

		// Eh necessario alterar a localizacao padrao ingles por questoes de formatacao numerica
		java.util.Locale.setDefault(new java.util.Locale("ENGLISH"));

		float[][] questoes = Dados.questoes();

		double[][] probabilidadeAcerto = Dados.calcularProbabilidadeAcerto(habilidades, questoes);

		int[] alunos = {0, 1, 2, 3};

		Barra barra = new Barra();

		Thread thread = null;

		try {
			System.out.print("(2.1) Selecionando melhor aluno:  ");
			contadorTempo.iniciar();
			thread = new Thread(barra);
			thread.start();
			Dados.selecionarMelhorAluno("I1.txt", alunos, 4, probabilidadeAcerto, numeroPadraoTestes);
		} catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			thread.interrupt();
			System.out.println(((char)8) + " pronto (" + contadorTempo.parar() + " ms)");
		}

		try {
			System.out.print("(2.2) Selecionando melhor prova:  ");
			contadorTempo.iniciar();
			thread = new Thread(new Barra());
			thread.start();
			Dados.selecionarMelhorProva("I2.txt", 4, 3, alunos, probabilidadeAcerto, numeroPadraoTestes);
		} catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			thread.interrupt();
			System.out.println(((char)8) + " pronto (" + contadorTempo.parar() + " ms)");
		}

	}

}
