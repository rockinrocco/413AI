package files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class fileUtil  {

	private String fileName;
	public String fileText;
	
	@SuppressWarnings("resource")
	public fileUtil(String fileName) throws IOException{
		this.fileName = fileName;
		    BufferedReader reader = null;
		    String path = "./" + fileName;
		    FileInputStream fis = new FileInputStream(path);
			reader = new BufferedReader( new InputStreamReader(fis));
		
		    String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");

		
				while( ( line = reader.readLine() ) != null ) {
				    stringBuilder.append( line );
				    stringBuilder.append( ls );
				}
			

		    this.fileText =  stringBuilder.toString();
		}
	}

