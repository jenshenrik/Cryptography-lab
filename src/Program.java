import java.security.InvalidKeyException;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import confidentiality.Confidentiality;

public class Program {
	
	private String action;
	private String inputPath;
	private String outputPath;
	
	private byte[] key;
	
	public static void main(String args[]) {
		// Check for parameters
//		if (args.length != 1) {
//			System.out.println(
//					"Usage:\n\n$>java Enryption <action>\n\n" +
//					" -action: 'e' for encrypt or 'd' for decrypt\n");
//			System.exit(0);;
//		}

		Program p = new Program();
		
		p.action = "d"; //args[0];
		p.inputPath = "plaintext.txt";
		p.outputPath = "ciphertext.txt";
	
		p.run();
	}

	private void run() {
		key = getInput("Please enter symmetric key:").getBytes();
		if (action.equals("e")) {
			encrypt(key);
		} else {
			decrypt(key);
		}
	}
	
	private void decrypt(byte[] key) {
		try {
			Confidentiality con = new Confidentiality(key);
			byte[] data = readFile(inputPath);
			String result = new String(con.decrypt(data));
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	private void encrypt(byte[] key) {
		try {
			byte[] data = readFile(inputPath);
			Confidentiality con = new Confidentiality(key);
			byte[] encrypted = con.encrypt(data);
			writeFile(encrypted);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	private String getInput(String msg) {
		System.out.println(msg);
		Scanner in = new Scanner(System.in);
		String reply = in.next();
		in.close();
		return reply;
	}

	private byte[] readFile(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}

	private void writeFile(byte[] data) throws IOException {
		Files.write(Paths.get(outputPath), data, StandardOpenOption.CREATE);
	}
}
