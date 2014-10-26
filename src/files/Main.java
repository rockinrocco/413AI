package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the fileName");
		String fileName = scanner.nextLine();
		System.out.println("Enter Algorithm Number \n 1 - BM25 "
				+ "\n 2 - Skip bi-Grams \n 3 - n-grams \n 4 - Passge Term Matching \n 5 - Textual Alignment");
		int algorithmNumber = scanner.nextInt();
		System.out.println("Analyzing " + fileName + " with algorithm " + algorithmNumber);	
		
		fileUtil fileUtil = new fileUtil(fileName);
		String fileContents = fileUtil.fileText;
		System.out.println(fileContents);
	}

	private String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    return stringBuilder.toString();
	}

}
