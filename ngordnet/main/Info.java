package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.*;

import java.util.*;

public class Info {
    public Hashtable<Integer, List<String>> iDToWord;
    public HashMap<String, List<Integer>> wordToID;
    public HashMap<Integer, List<Integer>> edges;
    public NGramMap ngm;
    public Info(String wordFile, String countFile, String synsetFile, String hyponymFile) {
        In stringFile = new In(synsetFile);
        iDToWord = new Hashtable<>();
        wordToID = new HashMap<>();
        while (!stringFile.isEmpty() && stringFile.hasNextLine()) {
            String line = stringFile.readLine();
            String[] split = line.split(",", 3);
            int iD = Integer.valueOf(split[0]);
            String[] words = split[1].split(" ");
            List<Integer> temp;
            for (String word : words) {
                if (wordToID.get(word) == null) {
                    temp = new ArrayList<>();
                    temp.add(iD);
                } else {
                    temp = wordToID.get(word);
                    temp.add(iD);
                }
                wordToID.put(word, temp);
            }
            List<String> temp2 = Arrays.asList(words);
            iDToWord.put(iD, temp2);
        }
        In stringFile2 = new In(hyponymFile);
        edges = new HashMap<>();
        while (!stringFile2.isEmpty() && stringFile2.hasNextLine()) {
            String line = stringFile2.readLine();
            String[] split = line.split(",");
            ArrayList split2 = new ArrayList<>();
            for (int i = 1; i < split.length; i++) {
                split2.add(Integer.valueOf(split[i]));
            }
            edges.put(Integer.valueOf(split[0]), split2);
        }
        ngm = new NGramMap(wordFile, countFile);
    }
}
