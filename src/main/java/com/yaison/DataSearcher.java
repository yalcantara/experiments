package com.yaison;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ArrayUtils.toArray;

public class DataSearcher<T> {
	
	private final IntArrayList buff;
	private final List<T> list;
	
	private final Map<String, Integer> map;
	private final Decomposer<T> de;
	
	public DataSearcher(Decomposer<T> de) {
		map = new TreeMap<>();
		buff = new IntArrayList();
		list = new ArrayList<>();
		this.de = de;
	}
	
	
	private void write(int val){
		buff.add(val);
	}
	
	private void generateBuffer(){
		buff.clear();
		final var len = list.size();
		
		write(len);
		for(var i =0; i < len; i++){
			
			var v = list.get(i);
			var arr = de.decomposeObject(v);
			
			var objLen = arr.length;
			write(objLen);//the +1 is the index in the list.
			for(var key:arr){
				var encoded = map.get(key);
				if(encoded == null){
					throw new RuntimeException("Could not find the mapping for key '" + key +"'.");
				}
				write(encoded);
			}
			//the last field of the object is the index in the list.
			write(i);
		}
	}
	
	private void generateMap(){
		map.clear();
		final int len = list.size();
		
		var tree = new TreeSet<String>();
		for(int i =0; i < len; i++){
			
			T v = list.get(i);
			String[] arr = de.decomposeObject(v);
			
			for(String str:arr){
				tree.add(str);
			}
		}
		
		for(String str:tree){
			map.put(str, map.size());
		}
	}
	
	public void compile(List<T> extList){
		list.clear();
		list.addAll(extList);
		generateMap();
		generateBuffer();
	}
	
	public List<T> find(String word){
	
		String[] arr = de.decomposeWord(word);
		
		IntArrayList found = new IntArrayList();
		
		for(String str:arr){
			Integer v = map.get(str);
			if(v != null){
				found.add(v);
			}
		}
		
		int[] foundi = found.toArray();
		
		int idx = 0;
		int len = buff.get(idx);
		idx++;
		
		IntArrayList ans = new IntArrayList();
		
		for(int i =0; i < len; i++){
			int objLen = buff.get(idx);
			idx++;
		
			var objFound = false;
			for(int j =0; j < objLen; j++) {
				int encoded = buff.get(idx);
				idx++;
				
				for(var k =0; k < foundi.length; k++){
					if(encoded == foundi[k]){
						objFound = true;
						break;
					}
				}
			}
			
			int extIdx = buff.get(idx);
			idx++;
			
			if(objFound){
				ans.add(extIdx);
			}
		}
		
		List<T> result = new ArrayList<>();
		
		for(int i=0; i < ans.size(); i++){
			int extIdx = ans.get(i);
			result.add(list.get(extIdx));
		}
		
		return result;
	}
	
	
	private void writeArr(String[] arr, int id) {
		
		int len = arr.length;
		
		write(len);
		for (int i = 0; i < len; i++) {
			String key = arr[i];
			int idx = map.get(key);
			write(idx);
		}
		write(id);
		
	}
}
