public class Barra implements Runnable {
	private int tempo = 120;
	public void run() {
		try {
			do {
				System.out.print(((char)(8)) + "|");
				Thread.sleep(tempo);
				System.out.print(((char)(8)) + "/");
				Thread.sleep(tempo);
				System.out.print(((char)(8)) + "-");
				Thread.sleep(tempo);
				System.out.print(((char)(8)) + "\\");
				Thread.sleep(tempo);
			} while(true);
		} catch(InterruptedException ie) {
			//System.out.println(((char)(8)));
		}
	}
}
