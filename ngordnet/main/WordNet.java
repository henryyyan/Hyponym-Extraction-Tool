package ngordnet.main;

import java.util.*;

public class WordNet {
    public Info I;
    public Graph g;
    //wrapper for graph
    public WordNet(String wordFile, String countFile, String synsetFile, String hyponymFile) {
        I = new Info(wordFile, countFile, synsetFile, hyponymFile);
        g = new Graph(I);
    }
}
