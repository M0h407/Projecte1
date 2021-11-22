package xifrarFitxers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EscrBin {
	private File arxiu;
	private FileOutputStream fo;
	private BufferedOutputStream bi;

	public EscrBin(String nom) {
		this.arxiu = new File(nom);
	}
	
	public void escriu(byte[] cadena) throws IOException {
		//iniciem les propietats
		this.fo = new FileOutputStream(this.arxiu);
		this.bi = new BufferedOutputStream(this.fo);
		
		//esciur la linia a l'arxiu
		this.fo.write(cadena);
		this.bi.close();
		this.fo.close();
	}
}
