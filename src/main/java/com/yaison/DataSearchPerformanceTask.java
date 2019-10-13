package com.yaison;

import java.util.List;

public class DataSearchPerformanceTask implements PerformanceTask {
	
	private final String searchTerm;
	private final DataSearcher<Contact> ds;
	
	public DataSearchPerformanceTask(List<Contact> list, String searchTerm){
		
		var wd = new WordDeComposer(3);
		var d = new Decomposer<Contact>() {
			@Override
			public String[] decomposeObject(Contact value) {
				
				return wd.decompose(value.getFirstName(), value.getLastName(), value.getEmail(), value.getAddress());
			}
			
			@Override
			public String[] decomposeWord(String value) {
				return wd.decompose(value);
			}
		};
		
		this.ds = new DataSearcher<>(d);
		ds.compile(list);
		this.searchTerm = searchTerm;
	}
	
	
	@Override
	public void init() {
	
	}
	
	@Override
	public void execute() {
	
		ds.find(searchTerm);
	}
}
