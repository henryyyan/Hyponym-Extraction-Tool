package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.*;

public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        WordNet wn = new WordNet(wordFile, countFile, synsetFile, hyponymFile);
        return new HyponymsHandler(wn);
    }
}
