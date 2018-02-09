package com.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Country {
	
	public String name;
	
	public Country(){
		
	}
	public Country(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int hashCode() {
		if(this.name.length() %2 ==0){
			return 31;
		}else{
			return 95;
		}
	}
	public boolean equals(Object arg0) {
		Country other = (Country)arg0;
		if(name.equalsIgnoreCase(other.name)){
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
//		Country japan = new Country("Tokyo");
//		Country china = new Country("beijin");
//		Country canada = new Country("ottawa");
//		Country india = new Country("delhi");
//		Map<Country,String> map = new HashMap<Country, String>();
//		map.put(japan, "Tokyo");
//		map.put(china, "beijin");
//		map.put(canada, "ottawa");
//		map.put(india, "delhi");
//		Iterator<Country> iterator = map.keySet().iterator();
//		while(iterator.hasNext()){
//			Country next = iterator.next();
//			String capital = map.get(next);
////			System.out.println(next.getName()+"-----"+capital);
//			System.out.println(next.hashCode());
//		}
		Map<String,String> map = new HashMap<String, String>();
		String put = map.put("china","beijing");
		System.out.println(put);
		String put2 = map.put("china", "shanghai");
		System.out.println(put2);
		
	}
}