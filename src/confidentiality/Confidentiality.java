package confidentiality;

import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.NoSuchPaddingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Confidentiality {

	Cipher c;
	SecretKeySpec factory;
	
	public Confidentiality(byte[] key) {
		try {
			c = Cipher.getInstance("AES");
			factory = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
			System.exit(1);
		} catch (NoSuchPaddingException e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	public byte[] encrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		c.init(Cipher.ENCRYPT_MODE, factory);
		return c.doFinal(data);
	}
	
	public byte[] decrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		c.init(Cipher.DECRYPT_MODE, factory);
		return c.doFinal(data);
	}
}
