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
		System.out.println("__________________________________________");
		System.out.println("");
		System.out.println("--------Firma i validació d'arxius--------");
		System.out.println("__________________________________________");
		System.out.println("");
		System.out.println("Selecciona una opció.");
		System.out.println("");
		System.out.println("1.- Firmar un arxiu: ");
		System.out.println("2.- Validar firma d'un arxiu: ");
		int num = teclat.nextInt();

		if (num == 1) {
			try {
				// Aquí es genera la clau.
				KeyPair claus;
				claus = ValidSign.randomGenerate(2048);

				// Aquí obtenim la clau privada i pública i passo la clau publica a byte.
				PrivateKey privada = claus.getPrivate();
				PublicKey publica = claus.getPublic();
				byte[] publicaBytes = publica.getEncoded();
				// Aquí passem per terminal el nom de l'arxiu que volem firmar.
				System.out.println("Introdueix la direcció de l'arxiu:");
				String ruta = teclat.next();
				// L'arxiu que passem per terminal el separo i creo el nom dels arxius que
				// crearem nous.
				String[] parts = ruta.split("\\.");
				String dir = parts[0] + "\\";
				String si = dir + parts[0] + ".sig";
				String pb = dir + parts[0] + ".pbl";
				// I aquí creo els arxius nous on guardaré la clau pública i la firma i creo el
				// directori on es guardarà tot.
				File sig = new File(si);
				File pbl = new File(pb);
				File file = new File(ruta);
				File directory = new File(dir);
				directory.mkdir();
				// En aquest if mirem si l'arxiu que passem existeix.
				if (file.exists()) {
					// I aquí veiem si l'arxiu ja ha sigut firmat comprovant si existeixen els dos
					// arxius de firma i clau pública.
					if (!sig.exists() && !pbl.exists()) {
						// Aquí llegeixi directament l'arxiu a byte i el firma.
						byte[] fileContent = Files.readAllBytes(file.toPath());
						byte[] textSigna = ValidSign.signData(fileContent, privada);
						// Primer cridem la classe Escriure i l'hi passem el nom de l'arxiu que crearem.
						// Passem la signa a String Base64 i l'escrivim en l'arxiu que s'ha creat.
						Escriure signa = new Escriure(si);
						String clau = Base64.getEncoder().encodeToString(textSigna);
						signa.esciure(clau);
						// Aquí tornem a cridar la classe Escriure i passem el mom de l'arxiu.
						// Convertim la clau publica a String Base64 i l'escrivim a l'arxiu.
						Escriure clPubli = new Escriure(pb);
						String publ = Base64.getEncoder().encodeToString(publicaBytes);
						clPubli.esciure(publ);
					} else {
						System.out.println("Aquest fitxer ja esta firmat");
					}
				} else {
					System.out.println("Aquest fitxer no existeix");
				}
			} catch (Error e) {
				System.out.println("ERROR");
			}
		} else if (num == 2) {
			try {
				// Passem per terminal l'arxiu que volem validar.
				System.out.println("Introdueix la direcció de l'arxiu:");
				String ruta = teclat.next();
				// Separem l'arxiu que volem validar, el separarem i crearem el nom dels nous
				// arxius.
				String[] parts = ruta.split("\\.");
				String dir = parts[0] + "\\";
				String s = dir + parts[0] + ".sig";
				String f = dir + parts[0] + ".pbl";
				// Aquí creem els arxius que llegirem.
				File sig = new File(s);
				File pbl = new File(f);
				File file = new File(ruta);
				// Primer mirem si l'arxiu que volem validar existeix.
				if (file.exists()) {
					// I aquí mirem si aquest arxiu està firmat o no.
					if (sig.exists() && pbl.exists()) {
						// Primer llegim els arxius de clau pública i firma i els passem a byte.
						String publica = new String(Files.readAllBytes(Paths.get(f)));
						String firma = new String(Files.readAllBytes(Paths.get(s)));
						byte[] decodedPubl = Base64.getDecoder().decode(publica);
						byte[] decodadFirm = Base64.getDecoder().decode(firma);
						byte[] fileContent = Files.readAllBytes(file.toPath());
						// Passem el byte de clau publica a PublicKey.
						PublicKey publicKey = KeyFactory.getInstance("RSA")
								.generatePublic(new X509EncodedKeySpec(decodedPubl));
						// I amb aquest boolean es veu si la firma concorda amb l'arxiu o no.
						boolean signValida = ValidSign.validateSignature(fileContent, decodadFirm, publicKey);
						System.out.println("Signatura: " + new Boolean(signValida));

					} else {
						System.out.println("No s'han pogut trobar els arxius de validació");
					}
				} else {
					System.out.println("Aquest fitxer no existeix");
				}
			} catch (Error e) {
				System.out.println("ERROR");
			}

		} else {
			System.out.println("Introdueix un número valid");
		}
		teclat.close();
	}
}