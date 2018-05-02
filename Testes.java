import java.io.IOException;
import java.io.FileNotFoundException;

public class Testes {

	private static float[] habilidades = {-1.0F, -0.5F, 0.0F, 0.5F, 1.0F};

	public static void main(String[] args) {

		// Eh necessario alterar a localizacao padrao ingles por questoes de formatacao numerica
		java.util.Locale.setDefault(new java.util.Locale("ENGLISH"));

		float[][] questoes = null;
		double[][] probabilidadeAcerto = null;

		try {
			questoes = Dados.questoes();
			probabilidadeAcerto = Dados.calcularProbabilidadeAcerto(habilidades, questoes);
		} catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}

		int[] alunos = {0, 1, 2, 3};

		int[] testes = {10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

		Barra barra = new Barra();
		Thread thread = null;

		ContadorTempo contadorTempo = new ContadorTempo();
		contadorTempo.iniciar();

		for(int teste : testes) {

			try {
				System.out.print("[" + teste + "](2.1) Selecionando melhor aluno:  ");
				thread = new Thread(barra);
				thread.start();
				Dados.selecionarMelhorAluno("testes/I1_" +teste +".txt", alunos, 4, probabilidadeAcerto, teste);
			} catch(FileNotFoundException fnfe) {
				System.out.println(fnfe.getMessage());
			} catch(IOException ioe) {
				System.out.println(ioe.getMessage());
			} finally {
				thread.interrupt();
				System.out.println(((char)8) + " pronto (" + contadorTempo.tempo() + " ms)");
			}

			try {
				System.out.print("[" + teste + "(2.2) Selecionando melhor prova:  ");
				contadorTempo.iniciar();
				thread = new Thread(new Barra());
				thread.start();
				Dados.selecionarMelhorProva("testes/I2_" + teste + ".txt", 4, 3, alunos, probabilidadeAcerto, teste);
			} catch(FileNotFoundException fnfe) {
				System.out.println(fnfe.getMessage());
			} catch(IOException ioe) {
				System.out.println(ioe.getMessage());
			} finally {
				thread.interrupt();
				System.out.println(((char)8) + " pronto (" + contadorTempo.tempo() + " ms)");
			}

		}

		System.out.println("\nFim.\nTempo total: " + contadorTempo.parar() + "ms");
	}

}
