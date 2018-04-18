public class MergeSort {

	/*
	  -> Divide os vetores em dois subvetores e faz uma chamada recursiva para cada um deles
	  -> Intercala cada elemento dos vetores, pois eles já foram ordenados
	*/

	public static double[][] mergeSort(double v[][], int parametroComparacao) {
		if(v.length <= 1) return v;

		double metade1[][] = new double[v.length/2][];
		double metade2[][] = new double[v.length/2 + (v.length % 2)][];

		int i = 0;
		for(int j = 0; j < metade1.length; j++, i++) metade1[j] = v[i];
		for(int j = 0; j < metade2.length; j++, i++) metade2[j] = v[i];

		metade1 = mergeSort(metade1, parametroComparacao);
		metade2 = mergeSort(metade2, parametroComparacao);

		int k = 0; // Iterador da metade 1
		int l = 0; // Iterador da metade 2
		int m = 0; // Iterador do vetor principal

		/*
		  Intercala os elementos dos dois vetores enquanto os dois vetores (metade1 e metade2)
		  não estiverem vazios.
		*/
		while(k < metade1.length && l < metade2.length) {
			if(metade1[k][parametroComparacao] >= metade2[l][parametroComparacao]) {
				v[m] = metade1[k];
				k++;
			} else {
				v[m] = metade2[l];
				l++;
			}
			m++;
		}

		/*
		  Caso um dos vetores tenha ficado vazio e o outro não, então o vetor principal
		  será preenchido com os elementos restantes do vetor que ainda tem elementos.
		*/
		while(k < metade1.length) {
			v[m] = metade1[k];
			k++;
			m++;
		}
		while(l < metade2.length) {
			v[m] = metade2[l];
			l++;
			m++;
		}

		return v;
	}

}
