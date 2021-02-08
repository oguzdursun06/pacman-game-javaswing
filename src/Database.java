import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Database<T> {
	
	private Map<Object,ArrayList<Object>> table ;
	
	public Database(Class veri_turunun_sinifi,String dosya_ismi) throws FileNotFoundException {
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(dosya_ismi)) ;
			LinkedHashMap<Object,ArrayList<Object>> label = new LinkedHashMap<Object,ArrayList<Object>>() ;
				String row ;
				int k = 0;
				while((row =csvReader.readLine())!= null) {
					String line[] = row.split(",") ;
					
					for(int i=0;i<line.length;i++) {
						if(k == 0) {
							ArrayList<Object> tempList = new ArrayList<Object>() ;
							label.put(line[i], tempList) ;
							continue;
						}
						if(veri_turunun_sinifi == String.class) {
							Iterator<Object> iterator = label.keySet().iterator() ;
							Object key = null ;
							for(int a=i+1;a !=0;a--) {
								key = iterator.next() ;
							}
							label.get(key).add(line[i]) ;
						}
						else if(veri_turunun_sinifi == Integer.class) {
							if(line[i].matches("-?\\d+(\\d+)?") == false || line[i].contains(".")) {
								System.out.println("error");
								return;
							}
							Iterator<Object> iterator = label.keySet().iterator() ;
							Object key = null ;
							for(int a=i+1;a !=0;a--) {
								key = iterator.next() ;
							}
							label.get(key).add(Integer.parseInt(line[i])) ;
						}
						else if(veri_turunun_sinifi == Double.class) {
							if(line[i].contains(".") == false) {
								System.out.println("error");
								return;
							}
							Iterator<Object> iterator = label.keySet().iterator() ;
							Object key = null ;
							for(int a=i+1;a !=0;a--) {
								key = iterator.next() ;
							}
							label.get(key).add(Double.parseDouble(line[i])) ;
						}
						
					}
					k++;
				}
				table = label ;
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public <T> Database(Map<Object,ArrayList<Object>> table) {
		this.table = table ;
	}
	
	public <T> Database where(String column_name,T value) {
		if(table == null) {
			System.out.println("database is null");
			return null;
		}
		if(table.containsKey(column_name) == false) {
			System.out.println("error");
			return null ;
		}
		
		ArrayList<Integer> indexes = new ArrayList<Integer>() ;
		for(int i=0;i<table.get(column_name).size();i++) {
			if(table.get(column_name).get(i).equals(value)) {
				indexes.add(i) ;
			}
		}
		Map<Object,ArrayList<Object>> label = new HashMap<Object,ArrayList<Object>>() ;
		for(Object key : table.keySet()) {
			label.put(key,new ArrayList<Object>()) ;
		}
		
		for(Object key: table.keySet()) {
			for(int i=0;i<indexes.size();i++) {
				label.get(key).add(table.get(key).get(indexes.get(i))) ;
			}
		}
		
		Database temp = new Database(label) ;
		
		return temp ;
	}
	
	public Database select(String[] column_names) {
		if(table == null) {
			System.out.println("database is null");
			return null;
		}
		for(int i=0;i<column_names.length;i++) {
			if(table.containsKey(column_names[i])== false) {
				System.out.println("error");
				return null ;
			}
		}
		Map<Object,ArrayList<Object>> label = new HashMap<Object,ArrayList<Object>>() ;
		for(int i=0;i<column_names.length;i++) {
			label.put(column_names[i],(ArrayList<Object>) table.get(column_names[i]).clone()) ;
		}
		Database temp = new Database(label) ;
		return temp;
	}
	
	public <T> void add_column(String label,T[] data) {
		if(table == null) {
			System.out.println("database is null");
			return;
		}
		int size = 0;
		for(Object key : table.keySet()) {
			size = table.get(key).size();
			break;
		}
		if(data.length != size) {
			System.out.println("error");
			return;
		}
		ArrayList<Object> tempList = new ArrayList<Object>();
		Collections.addAll(tempList, data);
		table.put(label, (ArrayList<Object>) tempList.clone());
	}
	
	public <T> void remove(String column_name,T value) {
		if(table == null) {
			System.out.println("database is null");
			return;
		}
		if(table.containsKey(column_name) == false) {
			System.out.println("error");
			return;
		}
		
		ArrayList<Integer> indexes = new ArrayList<Integer>() ;
		for(int i=0;i<table.get(column_name).size();i++) {
			if(table.get(column_name).get(i).equals(value)) {
				indexes.add(i) ;
			}
		}
		for(Object key: table.keySet()) {
			for(int i=0;i<indexes.size();i++) {
				table.get(key).remove(table.get(key).get(indexes.get(i)-i));
			}
		}
	}

	public void display() {
		if(table == null) {
			System.out.println("database is null");
			return;
		}
		int size = 0;
		for(Object key : table.keySet()) {
			size = table.get(key).size();
			break;
		}
		 table.entrySet().forEach(entry->{
			    System.out.printf("%-20s",entry.getKey());  
			 });
		 System.out.println();
		int j = 0;
		while(j < size) {
			for(Object key : table.keySet())
				System.out.printf("%-20s",table.get(key).get(j));
			System.out.println();
			j++;
		}
	}
	
	public Database join(Database second,String column_name) {
		if(second.table == null || table == null) {
			System.out.println("database is null");
			return null;
		}
		if(table.containsKey(column_name) == false || second.table.containsKey(column_name) == false) {
			System.out.println("error");
			return null;
		}
		LinkedHashMap<Object,ArrayList<Integer>> valuesAndIndexes = new LinkedHashMap<Object,ArrayList<Integer>>() ;
		for(int i=0;i<((Map<Object, ArrayList<Object>>) second.table.get(column_name)).size();i++) {
			if(table.get(column_name).contains(((Map<Object, ArrayList<Object>>) second.table.get(column_name)).get(i))==true) {
				valuesAndIndexes.put(((Map<Object, ArrayList<Object>>) second.table.get(column_name)).get(i),new ArrayList<Integer>()) ;
				valuesAndIndexes.get(((Map<Object, ArrayList<Object>>) second.table.get(column_name)).get(i)).add(i) ;
			}	
		}
		for(int i=0;i<table.get(column_name).size();i++) {
			if(valuesAndIndexes.keySet().contains(table.get(column_name).get(i))) {
				valuesAndIndexes.get(table.get(column_name).get(i)).add(i) ;
			}
		}
		LinkedHashMap<Object,ArrayList<Object>> label = new LinkedHashMap<Object,ArrayList<Object>>() ;
		for(Object key: table.keySet()) {
			if(label.keySet().contains(key) == false) {
				label.put(key,new ArrayList<Object>()) ;
				for(Object item: valuesAndIndexes.keySet()) {
					label.get(key).add(table.get(key).get(valuesAndIndexes.get(item).get(1))) ;
				}
			}
		}
		for(Object key: second.table.keySet()) {
			if(label.keySet().contains(key) == true && key.equals(column_name) == false) {
				label.put(key+"2",new ArrayList<Object>()) ;
				for(Object item: valuesAndIndexes.keySet()) {
					label.get(key+"2").add(((Map<Object, ArrayList<Object>>) second.table.get(key)).get(valuesAndIndexes.get(item).get(0))) ;
				}
			}
			else if(label.keySet().contains(key) == false) {
				label.put(key,new ArrayList<Object>()) ;
				for(Object item: valuesAndIndexes.keySet()) {
					label.get(key).add(((Map<Object, ArrayList<Object>>) second.table.get(key)).get(valuesAndIndexes.get(item).get(0))) ;
				}
			}
		}
		Database temp = new Database(label) ;
		return temp;
	}
	
	public static void main(String[] args) throws FileNotFoundException {	

		
	}
}
