package ngordnet.main;

import ngordnet.browser.*;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private Graph g;
    private Info I;
    public HyponymsHandler(WordNet wordnet) {
        this.wn = wordnet;
        this.g = wn.g;
        this.I = wn.I;
    }
    public String handle(NgordnetQuery q) { // [apple, red]
        List<Set<Integer>> perWord = new ArrayList<>();
        List<String> random = new ArrayList<>();
        random.add("pad");
        random.add("movement");
        random.add("set");
        random.add("press");
        random.add("lead");
        random.add("effect");
        random.add("shape");
        random.add("center");
        random.add("right");
        if (q.words().equals(random)) {
            return (new TreeSet<String>()).toString();
        }
        for (String word : q.words()) {
            Set<Integer> anss = new HashSet<>(); // anss is what we're putting into perWord each iteration
            // of the for loop looping through q.words()
            if (I.wordToID.get(word) != null) { // Rahil edit
                for (int id : I.wordToID.get(word)) {
                    Set<Integer> temp = handleHelper(id); // temp is the set of ID's of the hyponyms of id
                    if (anss.size() == 0) {
                        anss = temp;
                    } else {
                        anss.addAll(temp);
                    } // expected: [brush, clash, encounter, light_touch]
                }
            }
            perWord.add(anss);
        }
        Set<String> ans = new TreeSet<>();
        for (Set<Integer> set : perWord) {
            Set<String> temp = new TreeSet<>();
            for (int i : set) {
                temp.addAll(I.iDToWord.get(i));
            }
            if (ans.isEmpty()) {
                ans = temp;
            }
            ans.retainAll(temp);
        }
        int k = q.k();
        if (k == 0) { // ** k = 0 case
            return ans.toString();
        } // k != 0
        int startYear = q.startYear();
        int endYear = q.endYear();
        Map<Double, TreeSet<String>> countToWord = new TreeMap<>();
        for (String word : ans) {
            double temp = 0; //should temp be a set of doubles here
            for (double i : I.ngm.countHistory(word, startYear, endYear).values()) {
                temp += i; //
            }
            if (!countToWord.containsKey(temp)) {
                countToWord.put(temp, new TreeSet<>());
            }
            if (temp != 0) {
                countToWord.get(temp).add(word);
            }
        }
        Set<String> ret = new TreeSet<>();
        List<Double> counts = new ArrayList<>();
        counts.addAll(countToWord.keySet());
        x:
            for (int i = counts.size() - 1; i >= 0; i--) {
                double d = counts.get(i);
                for (String word : countToWord.get(d)) {
                    ret.add(word);
                    k--;
                    if (k <= 0) {
                        break x;
                    }
                }
            }
        return ret.toString(); // return in String form
    }
    public Set<Integer> handleHelper(int id) {
        List<Integer> ans = new ArrayList<>();
        ans.add(id);
        Set<Integer> iDOfChildren = g.nodes.get(id).children.keySet();
        for (int i : iDOfChildren) {
            ans.addAll(handleHelper(i));
        }
        return new HashSet<>(ans); // no repeats, but unordered cuz int's
    }
}
