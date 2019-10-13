package com.yaison;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BagOfWords {
	
	
	private final Map<String, Integer> map;
	
	
	public BagOfWords() {
		map = new TreeMap<>();
	}
	
	public int size(){
		return map.size();
	}
	
	public Map<String, Integer> sortedByCount(){
		Map<String, Integer> ans = new LinkedHashMap<>();
		map.entrySet().stream().sorted((a, b)->b.getValue().compareTo(a.getValue())).forEach(e->ans.put(e.getKey(), e.getValue()));
		
		return ans;
	}
	
	public void put(String... words) {
		for (int i = 0; i < words.length; i++) {
			var word = words[i];
			Integer val = map.get(word);
			if (val == null) {
				map.put(word, 1);
			} else {
				map.put(word, val + 1);
			}
		}
	}
	

	
	public void print(int max) {
		int i = 1;
		for (var e : sortedByCount().entrySet()) {
			if (i > max) {
				break;
			}
			var k = e.getKey();
			var v = e.getValue();
			System.out.println(k + ": " + v);
			i++;
		}
	}
}
