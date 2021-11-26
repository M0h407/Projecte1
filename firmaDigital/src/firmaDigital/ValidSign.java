package firmaDigital;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ValidSign {

	public static byte[] signData(byte[] data, PrivateKey priv) {
		byte[] signature = null;

		try {
			Signature signer = Signature.getInstance("SHA1withRSA");
			signer.initSign(priv);
			signer.update(data);
			signature = signer.sign();
		} catch (Exception ex) {
			System.err.println("Error signant les dades: " + ex);
		}
		return signature;
	}

	public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) {
		boolean isValid = false;
		try {
			Signature signer = Signature.getInstance("SHA1withRSA");
			signer.initVerify(pub);
			signer.update(data);
			isValid = signer.verify(signature);
		} catch (Exception ex) {
			System.err.println("Error validant les dades: " + ex);
		}
		return isValid;
	}

	public byte[][] encryptWrappedData(byte[] data, PublicKey pub) {

		byte[][] encWrappedData = new byte[2][];
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKey sKey = kgen.generateKey();
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, sKey);

			byte[] encMsg = cipher.doFinal(data);
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.WRAP_MODE, pub);
			byte[] encKey = cipher.wrap(sKey);
			encWrappedData[0] = encMsg;
			encWrappedData[1] = encKey;
		} catch (Exception ex) {
			System.err.println("Ha succeït un error xifrant: " + ex);
		}
		return encWrappedData;
	}

	public static KeyPair randomGenerate(int len) {
		KeyPair keys = null;
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(len);
			keys = keyGen.genKeyPair();
		} catch (Exception ex) {
			System.err.println("Generador no disponible.");
		}
		return keys;
	}
}
