package com.robinson.ctic_travel.models;

import java.text.Normalizer;
import java.util.Arrays;

public class Tags {
    public static String generateItemTag(String title){
        String normalizedTitle = Normalizer.normalize(title, Normalizer.Form.NFD).replaceAll("\\p{M}", "");

        String[] words = normalizedTitle.split(" ");
        String shortTitle = String.join("-", Arrays.copyOfRange(words, 0, Math.min(words.length, 10)));
        shortTitle = shortTitle.replaceAll("[^\\w\\s-]", "");

        return shortTitle;
    }
}
