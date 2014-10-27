package files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class fileUtil  {
	
	public fileUtil(){
		
	}
	
	public String readFile(String fileName) throws IOException{
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
			

		    return stringBuilder.toString();
		}
	
	public String[] format(String string){
		String[] nakedString = string.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		return nakedString;
	}
	
	public int countMatches(String query, String[] words){
		int matches = 0;
		for(String word:words){
			if(query.equals(word)){ //match found
				matches++;
			}
		}
		return matches;
	}
}
	

