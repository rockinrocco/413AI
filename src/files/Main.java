package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * Matt Rocco and Paul Bliudzius:  CSSE413 - Information Retrieval
 */
public class Main {

    public static void main(String[] args) throws IOException {

	fileUtil fileUtil = new fileUtil();

	String[] presidentFiles = new String[] { "AbrahamLincoln.txt",
		"AndrewJackson.txt", "AndrewJohnson.txt", "BarackObama.txt",
		"BenjaminHarrison.txt", "BillClinton.txt",
		"CalvinCoolidge.txt", "ChesterArthur.txt",
		"DwightEisenhower.txt", "FranklinDRoosevelt.txt",
		"FranklinPierce.txt", "GeorgeBush.txt", "GeorgeWashington.txt",
		"GeorgeWBush.txt", "GeraldFord.txt", "GroverCleveland.txt",
		"HarryTruman.txt", "HerbertHoover.txt", "JamesBuchanan.txt",
		"JamesGarfield.txt", "JamesMadison.txt", "JamesMonroe.txt",
		"JamesPolk.txt", "JimmyCarter.txt", "JohnAdams.txt",
		"JohnKennedy.txt", "JohnQuincyAdams.txt", "JohnTyler.txt",
		"LyndonJohnson.txt", "MartinVanBuren.txt",
		"MillardFillmore.txt", "RichardNixon.txt", "RonaldReagan.txt",
		"RutherfordHayes.txt", "TheodoreRoosevelt.txt",
		"ThomasJefferson.txt", "UlyssesGrant.txt", "WarrenHarding.txt",
		"WilliamHenryHarrison.txt", "WilliamMcKinley.txt",
		"WilliamTaft.txt", "WoodrowWilson.txt", "ZacharyTaylor.txt" };
	// all lowercase terms
	String[] queryTerms = new String[] { "george bush", "vice", "tea",
		"adams", "lincoln", "president", "assassinated president",
		"great president", "first president", "civil war president" };
	Map<String, String[]> fileWords = new HashMap<String, String[]>();
	Map<String, String> fileStrings = new HashMap<String, String>();
	int wordCount = 0;
	// map with all the files words. map(fileName, words[]);
	for (String names : presidentFiles) {
	    String contents = fileUtil.readFile(names);
	    fileStrings.put(names, contents);
	    String[] words = fileUtil.format(contents);
	    fileWords.put(names, words);
	    wordCount += words.length;
	}

	double averageLength = wordCount / presidentFiles.length;

	Map<String, Integer> queryMatches = new HashMap<String, Integer>();

	for (String query : queryTerms) {
	    String[] queryWords = query.split(" ");

	    for (String qWord : queryWords) {

		for (String names : presidentFiles) {
		    String[] words = fileWords.get(names);
		    int matches = fileUtil.countMatches(qWord, words);
		    queryMatches.put(names + ":" + qWord, matches);
		    // System.out.println(names + ":" + qWord +":" + matches);
		}
	    }
	}

	// bm25
	TreeMap<Double, String> fileScore = new TreeMap<Double, String>();

	for (String query : queryTerms) {
	    for (String names : presidentFiles) {
		double queryScore = 0;
		String[] queryWords = query.split(" ");
		for (String qWord : queryWords) {
		    int inDocuments = 0;
		    for (String fileNames : presidentFiles) {
			int match = queryMatches.get(fileNames + ":" + qWord);
			if (match != 0) {
			    inDocuments++;
			}
		    }

		    double matches = queryMatches.get(names + ":" + qWord);
		    double score = ((matches * 2.5) / (matches + 1.5 * (1 - 0.75 + 0.75 * (fileWords
			    .get(names).length / averageLength))));
		    double IDF = Math
			    .log(((presidentFiles.length - inDocuments) + 0.5)
				    / (inDocuments + 0.5));
		    queryScore = queryScore + (score * IDF);
		    inDocuments = 0;

		}
		while (queryScore != 0 && fileScore.get(queryScore) != null) {
		    queryScore = queryScore + 0.00001;
		}
		fileScore.put(queryScore, names + ":" + query);
	    }

	    for (int i = 0; i < 10; i++) {
		if (fileScore.isEmpty()) {
		    System.out.println("No more documents containing " + query);
		    break;

		}
		Double largest = fileScore.lastKey();
		String search = fileScore.get(largest);
		fileScore.remove(largest);
		System.out.println(search + " " + largest);
	    }
	    fileScore.clear();
	    System.out.println();
	}

	/*
	 * N Grams P(Wi|Wi-1) = count(Wi-1,Wi)/count(Wi-1)
	 */
	System.out.println();
	int nGramsConstant = 5;
	System.out.println("N Grams where N=" + nGramsConstant);
	for (String query : queryTerms) {
	    int queryCount = 0;
	    String[] queryWords = query.split(" ");
	    for (String names : presidentFiles) {
		String currentFileString = fileStrings.get(names);
		String[] fileWord = currentFileString.split(" ");
		int currentFileScore = 0;
		for (int counter = 0; counter < fileWord.length; counter++) {
		    int nGrams = nGramsConstant;
		    if (fileWord.length - counter >= nGrams) {
			nGrams = fileWord.length - counter;
		    }
		    if (queryWords.length <= nGrams) {
			for (int i = 0; i < queryWords.length; i++) {
			    if (!queryWords[i].toLowerCase().equals(
				    fileWord[counter + i].toLowerCase())) {
				break;
			    } else if (i == queryWords.length - 1) {
				currentFileScore++;
			    }
			}
		    } else {
			// compare using nGrams instead of query.len
			for (int i = 0; i < nGrams; i++) {
			    if (!queryWords[i].toLowerCase().equals(
				    fileWord[counter + i].toLowerCase())) {
				break;
			    } else if (i == nGrams - 1) {
				currentFileScore++;
			    }
			}
		    }
		}
		if (currentFileScore != 0) {
		    queryCount++;
		    float finalScore = ((float) currentFileScore)
			    / ((float) fileWord.length);
		    System.out.println("NGRAM: " + names + ":" + query + "  = "
			    + finalScore);
		}
	    }
	    if (queryCount == 0) {
		System.out.println(query + " : NO RESULTS FOUND");
	    }
	}

	/*
	 * Passage Term Matching
	 */
	System.out.println();
	System.out.println("Passage Term Matching");
	for (String query : queryTerms) {
	    String[] queryWords = query.split(" ");
	    Map<String, Integer> queryMatchCount = new HashMap<String, Integer>();
	    for (String qWord : queryWords) {
		queryMatchCount.put(qWord, 0);
	    }
	    for (String names : presidentFiles) {
		String currentFileString = fileStrings.get(names);
		String[] fileWord = currentFileString.split(" ");
		int currentFileScore = 0;
		for (int counter = 0; counter < fileWord.length; counter++) {
		    for (String qWord : queryWords) {
			if (qWord.toLowerCase().equals(
				fileWord[counter].toLowerCase())) {
			    queryMatchCount.put(qWord,
				    queryMatchCount.get(qWord) + 1);
			}
		    }
		}

		int scoreCount = 0;
		for (String qWord : queryWords) {
		    scoreCount += queryMatchCount.get(qWord);
		}
		if (scoreCount == 0) {
		    System.out.println("No matches found for PMT: " + names
			    + ":" + query);
		} else {
		    float finalScore = (scoreCount) / ((float) fileWord.length);
		    System.out.println("PMT: " + names + ":" + query + "  = "
			    + finalScore);
		}
	    }
	}
    }
}
