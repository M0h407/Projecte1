package xifrarFitxers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class xifraFitx {
	
	public static void main(String[] args) throws IOException {
		Scanner teclat = new Scanner(System.in);
		System.out.println("1.- Xifrar un fitxer: ");
		System.out.println("2.- Desencriptar un fitxer: ");
		System.out.println("3.- Sortir");
		int num = teclat.nextInt();
		
		if (num == 1) {
			try {
				SecretKey Key;
				Key = Xifra.keygenKeyGeneration(256);
				String Clau = Base64.getEncoder().encodeToString(Key.getEncoded());

				System.out.println("Introdueix la direcció de l'arxiu:");
				String ruta = teclat.next();
				String[] parts = ruta.split("\\.");
				String dir = parts[0] + "\\";
				String ter = parts[1];

				String ke = dir + parts[0] + ".key";
				String xi = dir + parts[0] + ".xif";
				String terminacio = dir + parts[0] + ".ter";

				File key = new File(ke);
				File xif = new File(xi);
				File file = new File(ruta);
				File directory = new File(dir);
				directory.mkdir();
				
				if (file.exists()) {
					if (!key.exists() && !xif.exists()) {
						byte[] fileContent = Files.readAllBytes(file.toPath());
						byte[] textXifrat = Xifra.encryptData(Key, fileContent);

						Escriure Sclau = new Escriure(ke);
						Sclau.esciure(Clau);

						Escriure arXif = new Escriure(xi);
						String text64 = Base64.getEncoder().encodeToString(textXifrat);
						arXif.esciure(text64);

						Escriure termin = new Escriure(terminacio);
						termin.esciure(ter);
					} else {
						System.out.println("Aquest arxius ja esta xifrat");
					}
				} else {
					System.out.println("Aquest fitxer no existeix");
				}
			} catch (Error e) {
				System.out.println("Error " + e);
			}
		} else if (num == 2) {
			try {
				System.out.println("Introdueix la direcció de l'arxiu:");
				String ruta = teclat.next();
				String[] parts = ruta.split("\\.");
				String dir = parts[0] + "\\";
				String s = dir + parts[0] + ".key";
				String f = dir + parts[0] + ".xif";
				String i = dir + parts[0] + ".ter";
				
				String term = new String(Files.readAllBytes(Paths.get(i)));
				String d = dir + parts[0] + "." + term;
				
				File file = new File(f);
				
				if (file.exists()) {
					String secKey = new String(Files.readAllBytes(Paths.get(s)));
					String xifrt = new String(Files.readAllBytes(Paths.get(f)));
					
					byte[] fileContent = Base64.getDecoder().decode(xifrt);
					byte[] encodedKey = Base64.getDecoder().decode(secKey);
					
					SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
					
					byte[] textDesxifrat = Xifra.decryptData(originalKey, fileContent);
					// byte[] textDesxifrat = decryptData(originalKey, textXifrat);
					
					EscrBin si = new EscrBin(d);
					si.escriu(textDesxifrat);
				}
			} catch (Error e) {
				System.out.println("Error " + e);
			}
		} else if (num == 3) {
			return;
		} else {
			System.out.println("ERROR!");
		}
	}
}
