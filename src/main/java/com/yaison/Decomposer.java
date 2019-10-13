package com.yaison;

public interface Decomposer<T> {
	
	public String[] decomposeObject(T value);
	public String[] decomposeWord(String value);
}
