package cool.blink.back.search;

import cool.blink.back.utilities.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class Score {

    private Double calculatePercentageOfMatchingCharacters(char[] word1, char[] word2) {
        Double percentageOfMatchingCharacters = 0.0;

        Integer word1TotalCharacters = word1.length;
        Integer word2TotalCharacters = word2.length;
        percentageOfMatchingCharacters += (Objects.equals(word1TotalCharacters, word2TotalCharacters) ? 10.0 : 0.0);

        Map<Character, Integer> word1Map = new HashMap<>();
        for (char character : word1) {
            if (word1Map.containsKey(character)) {
                word1Map.put(character, word1Map.get(character) + 1);
            } else {
                word1Map.put(character, 1);
            }
        }

        Map<Character, Integer> word2Map = new HashMap<>();
        for (char character : word2) {
            if (word2Map.containsKey(character)) {
                word2Map.put(character, word2Map.get(character) + 1);
            } else {
                word2Map.put(character, 1);
            }
        }

        Double allCombinationsPercentage = (word1Map.size() * 100.00);
        Double matchAccuracy = 0.00;
        for (Map.Entry<Character, Integer> word1Entry : word1Map.entrySet()) {
            if (word2Map.containsKey(word1Entry.getKey())) {
                if (word2Map.get(word1Entry.getKey()) - word1Map.get(word1Entry.getKey()) >= 0) {
                    matchAccuracy += 100;
                } else {
                    matchAccuracy += (word2Map.get(word1Entry.getKey()) / word1Entry.getKey() * 100);
                }
            }
        }
        if (allCombinationsPercentage > 0) {
            return percentageOfMatchingCharacters + (matchAccuracy / allCombinationsPercentage * 90.0);
        } else {
            return 0.00;
        }
    }

    private Double calculatePercentageOfMatchingWords(String words1, String words2) {
        List<String> words1List = new ArrayList<>();
        List<String> words2List = new ArrayList<>();
        if (words1.contains(" ")) {
            StringTokenizer stringTokenizerWords1 = new StringTokenizer(words1, " ");
            while (stringTokenizerWords1.hasMoreElements()) {
                words1List.add((String) stringTokenizerWords1.nextElement());
            }
        } else {
            words1List.add(words1);
        }
        if (words2.contains(" ")) {
            StringTokenizer stringTokenizerWords2 = new StringTokenizer(words2, " ");
            while (stringTokenizerWords2.hasMoreElements()) {
                words2List.add((String) stringTokenizerWords2.nextElement());
            }
        } else {
            words2List.add(words2);
        }

        Integer totalWords = words1List.size();
        Integer matchedWords = 0;
        matchedWords = words1List.stream().filter((word) -> (words2List.contains(word))).map((_item) -> 1).reduce(matchedWords, Integer::sum);
        return (matchedWords / totalWords * 100.00);
    }

    private Double calculateResultGrade(Query query, Result result) {
        Double percentageOfCharactersMatchedInTitle = calculatePercentageOfMatchingCharacters(query.getQuery().toLowerCase().toCharArray(), result.getTitle().toLowerCase().toCharArray());
        Double percentageOfCharactersMatchedInDescription = calculatePercentageOfMatchingCharacters(query.getQuery().toLowerCase().toCharArray(), result.getDescription().toLowerCase().toCharArray());

        Double percentageOfWordsMatchedInTitle = calculatePercentageOfMatchingWords(query.getQuery().toLowerCase(), result.getTitle().toLowerCase());
        Double percentageOfWordsMatchedInDescription = calculatePercentageOfMatchingWords(query.getQuery().toLowerCase(), result.getDescription().toLowerCase());

        Double percentageFinalScore = ((percentageOfCharactersMatchedInTitle * 0.10) + (percentageOfCharactersMatchedInDescription * 0.10) + (percentageOfWordsMatchedInTitle * 0.40) + (percentageOfWordsMatchedInDescription * 0.40));
        return percentageFinalScore;
    }

    public List<Result> findBestResults(Query query, List<Result> allResults, Integer maxResults, Integer minimumAccuracy) {
        Map<Result, Double> allScores = new HashMap<>();
        for (Result result : allResults) {
            allScores.put(result, calculateResultGrade(query, result));
        }
        allScores = Maps.sortDescendingByValue(allScores);
        List<Result> results = new ArrayList<>();
        for (Map.Entry<Result, Double> result : allScores.entrySet()) {
            if (results.size() < maxResults) {
                if (result.getValue() > minimumAccuracy) {
                    results.add(result.getKey());
                }
            } else {
                break;
            }
        }
        return results;
    }

}
