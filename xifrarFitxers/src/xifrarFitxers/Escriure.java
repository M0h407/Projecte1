package xifrarFitxers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//En aquesta classe escrivim els arxius.
public class Escriure {
	private File arxiu;
	private PrintWriter pw;
	private FileWriter fw;

	public Escriure(String nom) {
		this.arxiu = new File(nom);
	}

	public void esciure(String linia) throws IOException {
		this.fw = new FileWriter(this.arxiu);
		this.pw = new PrintWriter(this.fw);
		try {
			this.pw.print(linia);
			this.pw.close();
			this.fw.close();
			System.out.println("S'ha guardat correctament");
		} catch (IOException e) {
			System.out.println("No s'ha pogut guardar " + e);
		}
	}
}
