package xifrarFitxers;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Xifra {
	public static SecretKey keygenKeyGeneration(int keySize) {
		SecretKey sKey = null;
		if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
			try {
				KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(keySize);
				sKey = kgen.generateKey();

			} catch (NoSuchAlgorithmException ex) {
				System.err.println("Generador no disponible.");
			}
		}
		return sKey;
	}

	public static byte[] encryptData(SecretKey sKey, byte[] data) {
		byte[] encryptedData = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, sKey);
			encryptedData = cipher.doFinal(data);
		} catch (Exception ex) {
			System.err.println("Error xifrant les dades: " + ex);
		}
		return encryptedData;
	}

	public static byte[] decryptData(SecretKey sKey, byte[] data) {
		byte[] decryptedData = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, sKey);
			decryptedData = cipher.doFinal(data);
		} catch (Exception ex) {
			System.err.println("Error xifrant les dades: " + ex);
		}
		return decryptedData;
	}
}
