package confidentiality;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Confidentiality {

	Cipher c;
	SecretKeySpec keySpec;
	int ctLength;
	
	public Confidentiality(byte[] key) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			
			keySpec = new SecretKeySpec(key, "AES");
			c = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
			System.exit(1);
		} catch (NoSuchPaddingException e) {
			System.out.println(e);
			System.exit(1);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] encrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, 
			BadPaddingException, InvalidAlgorithmParameterException, ShortBufferException {
		
		c.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] cipherText = new byte[c.getOutputSize(data.length)];
		ctLength = c.update(data,  0, data.length, cipherText, 0);
		ctLength += c.doFinal(cipherText, ctLength);
		
		return cipherText;
	}
	
	public byte[] decrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, 
			BadPaddingException, InvalidAlgorithmParameterException, ShortBufferException {
		
		c.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] plainText = new byte[c.getOutputSize(ctLength)];
		int ptLength = c.update(data, 0, ctLength, plainText, 0);
		ptLength += c.doFinal(plainText, ptLength);
		
		return plainText;
	}
}
