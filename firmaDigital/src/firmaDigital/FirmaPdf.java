package firmaDigital;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class FirmaPdf {

	public static void main(String args[]) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		Scanner teclat = new Scanner(System.in);
		System.out.println("1.- Firmar un arxiu: ");
		System.out.println("2.- Validar firma d'un arxiu: ");
		int num = teclat.nextInt();
		
		if(num == 1) {
			try {
				KeyPair claus;
				claus = ValidSign.randomGenerate(2048);
				
				PrivateKey privada = claus.getPrivate();
				PublicKey publica = claus.getPublic();
				byte[] publicaBytes = publica.getEncoded();
				
				System.out.println("Introdueix la direcció de l'arxiu:");
				String ruta = teclat.next();
				String[] parts = ruta.split("\\.");
				String dir = parts[0] + "\\";
				
				String si = dir + parts[0]+".sig";
				String pb = dir + parts[0]+".pbl";
				
				File sig = new File(si);
				File pbl = new File(pb);
				File file = new File(ruta);
				File directory = new File(dir);
				directory.mkdir();
				
				if(file.exists()) {
					if(!sig.exists() && !pbl.exists()) {
						byte[] fileContent = Files.readAllBytes(file.toPath());
						byte[] textSigna = ValidSign.signData(fileContent, privada);
						
						Escriure signa = new Escriure(si);
						String clau = Base64.getEncoder().encodeToString(textSigna);
						signa.esciure(clau);
						
						Escriure clPubli = new Escriure(pb);
						String publ = Base64.getEncoder().encodeToString(publicaBytes);
						clPubli.esciure(publ);
						
					} else {
						System.out.println("Aquest fitxer ja esta firmat");
					}
				} else {
					System.out.println("Aquest fitxer no existeix");
				}
			}catch(Error e){
				System.out.println("ERROR");
			}
			
		} else if(num == 2) {
			try {
				System.out.println("Introdueix la direcció de l'arxiu:");
				String ruta = teclat.next();
				
				String[] parts = ruta.split("\\."); 
				String dir = parts[0]+"\\";
				String s = dir+parts[0]+ ".sig";
				String f = dir+parts[0]+".pbl";
				
				File sig = new File(s);
				File pbl = new File(f);
				File file = new File(ruta);
				
				if(file.exists()) {
					if(sig.exists() && pbl.exists()) {
						String publica = new String(Files.readAllBytes(Paths.get(f)));
						String firma = new String(Files.readAllBytes(Paths.get(s)));
						byte[] decodedPubl = Base64.getDecoder().decode(publica);
						byte[] decodadFirm = Base64.getDecoder().decode(firma);
						byte[] fileContent = Files.readAllBytes(file.toPath());
						PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPubl));
		
						boolean signValida = ValidSign.validateSignature(fileContent, decodadFirm, publicKey);
						System.out.println("Signatura: " + new Boolean (signValida));
					
					} else {
						System.out.println("No s'han pogut trobar els arxius de validació");
					}
				} else {
					System.out.println("Aquest fitxer no existeix");
				}
			} catch(Error e) {
				System.out.println("ERROR");
			}
			
		} else {
			System.out.println("Introdueix un número valid");
		}
		teclat.close();
	}
}