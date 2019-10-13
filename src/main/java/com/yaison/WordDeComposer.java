package com.yaison;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class WordDeComposer {
    
    private static final String[] EMPTY_ARR = new String[]{};

    public static final int DEFAULT_SIZE = 3;
    
    private final int size;

    public static void main(String[] args) {
        WordDeComposer d = new WordDeComposer();
        String world = "adams";

        String[] ans = d.decompose(world);

        System.out.println(Arrays.toString(ans));
     
    }
    
    public WordDeComposer(){
        this(DEFAULT_SIZE);
    }

    public WordDeComposer(int size){
        this.size = size;
    }

    
    private String steam(String word){
        var sb = new StringBuilder();
        word = StringUtils.stripAccents(word);
        word = word.toLowerCase();
        for(int i =0; i < word.length(); i++){
            char c = word.charAt(i);
            
            if(c >= 'a' && c <= 'z'){
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
    
   

    public String[] decompose(String... words){
        if(words == null || words.length == 0){
            return EMPTY_ARR;
        }
        
        var set = new HashSet<String>();
        for(int i =0; i < words.length; i++) {
    
            String word = words[i];
    
            word = steam(word);
            if (word.length() <= size) {
                return new String[]{word};
            }
    
            var count = word.length() - size + 1;
    
            for (int j = 0; j < count; j++) {
                String str = word.substring(j, j + size);
                
                set.add(str);
            }
            
            
        }
        
        var result = new String[set.size()];
        set.toArray(result);

        return result;
    }
}
