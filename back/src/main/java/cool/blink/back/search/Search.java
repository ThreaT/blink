package cool.blink.back.search;

import java.util.Arrays;
import java.util.List;

public class Search {

    private final List<Result> results;

    public Search(Result... results) {
        this.results = Arrays.asList(results);
    }

    public List<Result> getResults() {
        return results;
    }

}
