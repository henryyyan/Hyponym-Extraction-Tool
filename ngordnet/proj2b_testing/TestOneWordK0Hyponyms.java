package ngordnet.proj2b_testing;

import ngordnet.browser.*;
import ngordnet.main.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    WordNet wn = new WordNet("data/ngrams/top_14377_words.csv", "data/ngrams/total_counts.csv", "data/wordnet/synsets.txt", "data/wordnet/hyponyms.txt");
    HyponymsHandler studentHandler = new HyponymsHandler(wn);

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void random() {
        List<String> lst = new ArrayList<>();
        lst.add("substance");
        lst.add("clary_sage");
        NgordnetQuery nq = new NgordnetQuery(lst, 1470, 2019, 0);
        WordNet wn = new WordNet("data/ngrams/top_14377_words.csv", "data/ngrams/total_counts.csv", "data/wordnet/synsets.txt", "data/wordnet/hyponyms.txt");
        HyponymsHandler studentHandler = new HyponymsHandler(wn);
        String actual = studentHandler.handle(nq);
        String expected = "[clary_sage]";
        Set<String> ans = new TreeSet<>();
        for (int i = 0; i < 2; i++) {
            ans.addAll(wn.I.iDToWord.get(wn.I.wordToID.get("clary_sage").get(i)));
        }
        System.out.println(ans);
    }

    @Test
    public void random2() {
        List<String> lst = new ArrayList<>();
        lst.add("pad"); // [diggings, digs, domiciliation, inking_pad, inkpad, launch_area, launch_pad, launching_pad, launchpad, lodgings, pad, pad_of_paper, stamp_pad, tablet]
        lst.add("movement"); // [apparent_motion, apparent_movement, bm, bowel_movement, campaign, cause, crusade, drift, drive, effort, motility, motion, move, movement, trend
        lst.add("set"); // [company, field, group, left, party, range, right, set, solution]
        lst.add("press"); // [daily, expression, impression, mill, newspaper, paper, press, pressure, sheet]
        lst.add("lead"); // [hint, lead, leading, principal, star, tip, track, trail, wind]
        lst.add("effect"); // [change, effect, figure, force, influence, issue, product, reaction, result]
        lst.add("shape"); // [base, condition, form, head, line, person, point, space, term]
        lst.add("center"); // [capital, center, centre, eye, heart, middle, see, substance, sum]
        lst.add("right"); // [claim, door, due, floor, grant, right, title, use, vote] // seat does not exist.
        NgordnetQuery nq = new NgordnetQuery(lst, 1920, 1980, 6);
        String actual = studentHandler.handle(nq);
        System.out.println(studentHandler.handle(nq));
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }
}
