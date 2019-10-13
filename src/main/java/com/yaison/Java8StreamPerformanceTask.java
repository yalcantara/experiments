package com.yaison;

import java.util.List;
import java.util.stream.Collectors;

public class Java8StreamPerformanceTask implements PerformanceTask {
	
	
	private final List<Contact> list;
	private final String searchTearm;
	
	public Java8StreamPerformanceTask(List<Contact> list, String searchTearm){
		this.list = list;
		this.searchTearm = searchTearm;
	}
	
	@Override
	public void init() {
	
	}
	
	@Override
	public void execute() {
		list.stream().filter(e->e.getFirstName().toLowerCase().contains(searchTearm) ||
				e.getLastName().toLowerCase().contains(searchTearm) ||
				e.getEmail().toLowerCase().contains(searchTearm) ||
				e.getAddress().toLowerCase().contains(searchTearm)).collect(Collectors.toList());
	}
}
