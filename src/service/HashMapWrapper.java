package service;

import java.util.HashMap;
import java.util.Map;

public class HashMapWrapper {
	
	private Map<String, Double> list = new HashMap<String, Double>();
	
	public HashMapWrapper(Map<String, Double> list) {
		this.list = list;
	}

	public Map<String, Double> getList() {
		return list;
	}
	

}
