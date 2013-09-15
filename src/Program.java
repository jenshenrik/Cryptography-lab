import java.util.Scanner;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import confidentiality.Confidentiality;

public class Program {
	
	public String action;
	public String inputPath;
	public String outputPath;
	
	private byte[] key;
	
	public static void main(String args[]) {
		// Check for parameters
//		if (args.length != 3) {
//			System.out.println(
//					"Usage:\n\n$>java Enryption <action> <input> <output>\n\n" +
//					" -action:\t'e' for encrypt or 'd' for decrypt\n" +
//					" -input:\tpath to input file\n" +
//					" -output:\tpath to output file\n");
//			//System.exit(0);
//			args[0] = "e";
//			args[1] = "input";
//			args[2] = "output";
//		}

		Program p = new Program();
		
		p.action = "e"; //args[0];
		p.inputPath = "input"; //args[1];
		p.outputPath = "output"; //args[2];
	
		p.run();
	}

	public void run() {
		//key = getInput("Please enter symmetric key:").getBytes();
		try {
			byte[] data = readFile(inputPath);
			writeFile(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getInput(String msg) {
		System.out.println(msg);
		Scanner in = new Scanner(System.in);
		return in.next();
	}

	public byte[] readFile(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}

	public void writeFile(byte[] data) throws IOException {
		Files.write(Paths.get(outputPath), data, StandardOpenOption.WRITE);
	}
}
