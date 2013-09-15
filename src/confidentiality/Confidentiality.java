package confidentiality;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class Confidentiality {

	Cipher c;
	SecretKeySpec k;
	
	public Confidentiality(byte[] key) {
		try {
			c = Cipher.getInstance("AES");
			k = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
			System.exit(1);
		} catch (NoSuchPaddingException e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	/*
	public byte[] encrypt(byte[] data) {
		c.init(Cipher.ENCRYPT_MODE, k);
		return c.doFinal(data);
	}
	
	public byte[] decrypt(byte[] data) {
		c.init(Cipher.DECRYPT_MODE, k);
		return c.doFinal(data);
	}
	*/
}
