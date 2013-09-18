import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Program {
	
	private String inputPath;
	
	private Cipher symCipher;
	private Cipher asymCipher;
	private Key key;
	private KeyPair keyPair;
	
	public static void main(String args[]) {
		Program p = new Program();
		
		p.inputPath = "plaintext.txt";
	
		try {
			p.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() throws Exception {
		// Generate symmetric key, and setup cipher
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
	    key = keyGen.generateKey();	    
	    symCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

	    // Generate asymmetric key, and setup cipher
	    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		keyPair = keyPairGen.generateKeyPair();
		asymCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	}
	
	private void run() throws Exception {
		init();

		// Read file 
		byte[] plainText = readFile(inputPath);
	    System.out.println("Encrypting plaintext:\t" + new String(plainText, "UTF8"));
	    
	    // Encrypt plaintext
	    byte[] cipherText = encrypt(plainText);
	    System.out.println("Ciphertext:\t\t" + new String(cipherText, "UTF8") );
	    
	    // Sign ciphertext using private key
	    byte[] signed = sign(cipherText);
	    System.out.println("Signed ciphertext:\t" + new String(signed, "UTF8"));
	    
	    // Verify signed ciphertext using public key
	    byte[] verified = verify(signed);
	    
	    // Decrypt ciphertext
	    byte[] decrypted = decrypt(verified);
	    System.out.println("Decrypted ciphertext:\t" + new String(decrypted, "UTF8"));
	}
	
	private byte[] sign(byte[] data) throws Exception {
		asymCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
		return asymCipher.doFinal(data);
	}
	
	private byte[] verify(byte[] data) throws Exception {
		asymCipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
		return asymCipher.doFinal(data);
	}
	
	private byte[] decrypt(byte[] cipherText) throws Exception {
		symCipher.init(Cipher.DECRYPT_MODE, key);
	    return symCipher.doFinal(cipherText);
	}
	
	private byte[] encrypt(byte[] plainText) throws Exception {
		symCipher.init(Cipher.ENCRYPT_MODE, key);
	    return symCipher.doFinal(plainText);
	    
	}

	private byte[] readFile(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}
}
