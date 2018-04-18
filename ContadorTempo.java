public class ContadorTempo {

	long tempo;

	public void iniciar() {
		this.tempo = System.currentTimeMillis();
	}

	public long tempo() {
		return System.currentTimeMillis() - this.tempo;
	}

	public long parar() {
		return System.currentTimeMillis() - this.tempo;
	}

}
