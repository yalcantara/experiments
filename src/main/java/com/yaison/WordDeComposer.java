package com.yaison;

import java.util.Arrays;

public class WordDeComposer {

    private final int size;

    public static void main(String[] args) {
        WordDeComposer d = new WordDeComposer();
        String world = "pop25wz";

        String[] ans = d.decompose(world);

        System.out.println(Arrays.toString(ans));
    }


    public WordDeComposer(){
        size = 3;
    }


    public String[] decompose(String word){

        String[] ans = new String[word.length() - size + 1];

        for(int i = 0; i < ans.length; i++){
            String str = word.substring(i, i + size);

            ans[i] = str;
        }

        return ans;
    }
}
