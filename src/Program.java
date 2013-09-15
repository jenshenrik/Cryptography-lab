import java.util.Scanner;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.BufferedOutputStream;

import confidentiality.Confidentiality;

public class Program {
	
	public String action;
	public String inputPath;
	public String outputPath;
	
	private byte[] key;
	
	public static void main(String args[]) {
		// Check for parameters
		if (args.length != 3) {
			System.out.println(
					"Usage:\n\n$>java Enryption <action> <input> <output>\n\n" +
					" -action:\t'e' for encrypt or 'd' for decrypt\n" +
					" -input:\tpath to input file\n" +
					" -output:\tpath to output file\n");
			System.exit(0);
		}

		Program p = new Program();
		
		p.action = args[0];
		p.inputPath = args[1];
		p.outputPath = args[2];
	
		p.run();
	}

	public void run() {
		key = getInput("Please enter symmetric key:").getBytes();
		byte[] data = readFile(inputPath);
		writeFile(data);
	}
	
	public String getInput(String msg) {
		System.out.println(msg);
		Scanner in = new Scanner(System.in);
		return in.next();
	}

	public byte[] readFile(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		}

		int nRead;
		byte[] data = new byte[1024];
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		try {
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				System.out.println("Read " + data);
				buffer.write(data, 0, nRead);
			}
		
			buffer.flush();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		
		return buffer.toByteArray();
	}

	public void writeFile(byte[] data) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(outputPath));
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(data);
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}
