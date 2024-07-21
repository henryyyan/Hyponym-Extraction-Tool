package ngordnet.main;
import org.w3c.dom.Node;
import java.util.*;

public class Graph extends HashMap<Integer, Node> {
    public static HashMap<Integer, Node> nodes;
    private static Info I;
    public class Node {
        int id;
        List<String> words;
        HashMap<Integer, Node> children; // map from id of child to the actual child node
        public Node(int iD, List<String> s) {
            id = iD;
            words = s;
            children = new HashMap<>();
        }
    }
    public Graph(Info I) {
        this.I = I;
        for (int i : I.edges.keySet()) {
            List<Integer> children = I.edges.get(i);
            for (int j = 0; j < children.size(); j++) {
                int id1 = i;
                int id2 = children.get(j);
                addEdge(id1, id2);
            }
        }
    }
    public void addEdge(int id1, int id2) { // id is the id of the word that you're connecting to newWord
        if (nodes == null) {
            nodes = new HashMap<>();
        }
        if (!nodes.keySet().contains(id1)) {
            nodes.put(id1, new Node(id1, I.iDToWord.get(id1)));
        }
        if (!nodes.keySet().contains(id2)) {
            nodes.put(id2, new Node(id2, I.iDToWord.get(id2)));
        }
        nodes.get(id1).children.put(id2, nodes.get(id2));
    }
    public Set<String> hyponyms(String s) {
        Set<String> ans = new TreeSet<>();
        Set<Integer> iDs = new HashSet<>();
        for (int i : I.wordToID.get(s)) {
            iDs.addAll(hyponymsHelper(i));
        }
        for (int id : iDs) {
            ans.addAll(I.iDToWord.get(id));
        }
        return ans;
    }

    public Set<Integer> hyponymsHelper(int id) {
        Set<Integer> ans = new HashSet<>(); // this is a set keeping track of the ID of all the hyponyms
        ans.add(id);
        for (int i : nodes.get(id).children.keySet()) {
            ans.addAll(hyponymsHelper(i));
        }
        return ans;
    }
}
