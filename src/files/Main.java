package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws IOException {

        fileUtil fileUtil = new fileUtil();

        String[] presidentFiles = new String[] {
            "AbrahamLincoln.txt", "AndrewJackson.txt", "AndrewJohnson.txt", "BarackObama.txt", "BenjaminHarrison.txt", "BillClinton.txt", "CalvinCoolidge.txt", "ChesterArthur.txt", "DwightEisenhower.txt", "FranklinDRoosevelt.txt", "FranklinPierce.txt", "GeorgeBush.txt", "GeorgeWashington.txt", "GeorgeWBush.txt", "GeraldFord.txt", "GroverCleveland.txt", "HarryTruman.txt", "HerbertHoover.txt", "JamesBuchanan.txt", "JamesGarfield.txt", "JamesMadison.txt", "JamesMonroe.txt", "JamesPolk.txt", "JimmyCarter.txt", "JohnAdams.txt", "JohnKennedy.txt", "JohnQuincyAdams.txt", "JohnTyler.txt", "LyndonJohnson.txt", "MartinVanBuren.txt", "MillardFillmore.txt", "RichardNixon.txt", "RonaldReagan.txt", "RutherfordHayes.txt", "TheodoreRoosevelt.txt", "ThomasJefferson.txt", "UlyssesGrant.txt", "WarrenHarding.txt", "WilliamHenryHarrison.txt", "WilliamMcKinley.txt", "WilliamTaft.txt", "WoodrowWilson.txt", "ZacharyTaylor.txt"
        };
        String[] queryTerms = new String[] {
            "adams", "lincoln", "president", "assassinated president", "great president", "first president", "civil war president"
        };
        Map < String, String[] > fileWords = new HashMap < String, String[] > ();
        //Query Terms -> Query Words -> filenames -> Algorithms -> Results

        int wordCount = 0;
        //map with all the files words. map(fileName, words[]);
        for (String names: presidentFiles) {

            String contents = fileUtil.readFile(names);
            String[] words = fileUtil.format(contents);
            fileWords.put(names, words);
            wordCount += words.length;
        }
        double averageLength = wordCount / presidentFiles.length;

      Map < String, Integer > queryMatches = new HashMap < String, Integer > ();
        
        for (String query: queryTerms) {
          String[] queryWords = query.split(" ");
          
        	for (String qWord: queryWords) {

        		for (String names: presidentFiles) {
        	  String[] words = fileWords.get(names);
            int matches = fileUtil.countMatches(qWord, words);
            queryMatches.put(names + ":" + qWord, matches);
          //  System.out.println(names + ":" + qWord +":" + matches);
        		}
        	}
        	}
        }
    }

        	//failed bm25 attempt. Will scrap and start over.
//            String[] queryWords = query.split(" ");
//            int count = 0;
//            Map < String, Double > queryScores = new HashMap < String, Double > ();
//            for (String qWord: queryWords) {
//                //individual docScores for each query word.
//                Map < String, Double > docScores = new HashMap < String, Double > ();
//
//                int inDocuments = 0;
//                for (String names: presidentFiles) {
//                    count++;
//                    String[] words = fileWords.get(names);
//                    int matches = fileUtil.countMatches(qWord, words);
//                    if (matches != 0) {
//                        inDocuments++;
//                    }
//                    //http://en.wikipedia.org/wiki/Okapi_BM25
//                    docScores.put(names, ((matches + 2.5) / (matches + 1.5 * (1 - 0.5 + 0.5 * (words.length / averageLength)))));
//                }
//
//                double IDF = (presidentFiles.length - inDocuments + 0.5) / (inDocuments + 0.5);
//                for (String names: presidentFiles) {
//                    double docScore = IDF * docScores.get(names);
//                    docScores.put(names, docScore);
//                    double current = 0;
//                    if (queryScores.get(names) == null) {
//                        current = 0;
//                    } else
//                        current = queryScores.get(names);
//
//                    queryScores.put(names, current + docScore);
//                }
//            }
//            TreeMap<Double, String> topScores = new TreeMap<Double, String>();
//            System.out.println("Query: " + query);
//
//            for (String names: presidentFiles) {
//            	//find the top ten.
//            	System.out.println(names + "..... Score: " + queryScores.get(names));
//
//            }
//
//        }
//    }
//}
