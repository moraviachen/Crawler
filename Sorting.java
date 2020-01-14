import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	// ADD YOUR CODE HERE
    	
    	//implement the merge sort method to sort the arraylist
    	
    	//check whether the list is greater than 0
    	
    	
    	
    	//TO-DO:
    	
    	//in order to compare the value, the hashmap needed to be the input of all functions
    	//figure out how to copy the hashmap values into the 
    	
    	
    	
    	
    	if(results.size()<=0) {
    		return null;
    	}else {
    		//if the size of the array list provided is 1, return itself
    		
    		/*if(results.size() ==1) {
    			 ArrayList<K> sortedUrls = new ArrayList<K>();
    		     sortedUrls.addAll(results.keySet());
    			return sortedUrls;
    		}else {*/
    			
    			if(results.size()<=38) {
    				return Sorting.slowSort(results);
    			}else {
    			//copy all the keys into an empty arraylist 
       			ArrayList<K> sortedUrls = new ArrayList<K>();
      		    sortedUrls.addAll(results.keySet());
    			
    			int mid = (results.size()-1)/2;
    			
    			//add the first half into list1 and the other half into list2
    			
    			HashMap<K, V> list1 = new HashMap<K, V>();
    			HashMap<K, V> list2 = new HashMap<K, V>();
    			
    			for(int i=0;i<results.size();i++) {
    				if(i<=mid) {
    					list1.put(sortedUrls.get(i), results.get(sortedUrls.get(i)));
    				}else {
    					list2.put(sortedUrls.get(i), results.get(sortedUrls.get(i)));
    				}
    			}
    			//in order for the recursion to happen
    			//instead of keys, we need to pass down the key and the value.
    			
    			//we need to make sure the length is greater or equal to 
    			ArrayList<K> list3 = new ArrayList<K>();
    			ArrayList<K> list4 = new ArrayList<K>();
    			
    			//recursion
    			
    			
    			
    			list3=Sorting.fastSort(list1);
    			list4=Sorting.fastSort(list2);
    			
    			//create a new array list for returned values
    			
    			ArrayList<K> ret = new ArrayList<K>();
    			
    			//to speed up the program
    			//i decided to use counting int instead of removing the first of the list
    			//since removing the first is O(N) instead of O(1) like get
    			//increment the counter after each round
    			
    			int l3=0;
    			int l4=0;
    			
    			while(l3<list3.size() && l4<list4.size() ) {
    				if(results.get(list3.get(l3)).compareTo(results.get(list4.get(l4))) <0) {
    					ret.add(list4.get(l4));
    					l4++;
    				}else {
    					ret.add(list3.get(l3));
    					l3++;
    				}
    			}
    			
    			while(l3<list3.size()) {
    				ret.add(list3.get(l3));
    				l3++;
    			}
    			
    			while(l4<list4.size()) {
    				ret.add(list4.get(l4));
    				l4++;
    			}
    			
    			return ret;
    			//merge everything together
    			
    			/*while(!list3.isEmpty() && !list4.isEmpty()) {
    				 
    				   if(results.get(list3.get(0)).compareTo(results.get(list4.get(0))) <0) {
    					   ret.add(list4.remove(0));
    				   }else {
    					   ret.add(list3.remove(0));
    					   
    				   }
    			   }
    			   //make sure both list is empty
    			   //one of the list is already empty from the for loop above
    			   //so there would be only one list left to check
    			   
    			   while(!list3.isEmpty()) {
    				   ret.add(list3.remove(0));
    			   }
    			   
    			   while(!list4.isEmpty()) {
    				   ret.add(list4.remove(0));
    			   }
    			   
    			   return ret;*/
    	}
    	}
    	
    	}
//    }

    
 
    
    //sort idea:
    
    //use slow sort on the smaller size so it can increase the speed
  
   
   
   
   //testing class
   
   /*public static void main(String[] args) {
	   HashMap<Integer, Integer> map = new HashMap<>();
	   
	   map.put(1, 7);
	   map.put(32,15 );
	   map.put(2, 18);
	   map.put(38,24 );
	   map.put(155, 72);
	   map.put(366,72 );
	   map.put(725,133 );
	   map.put(156,223 );
	   map.put(44, 55);
	   
	   ArrayList<Integer> results = Sorting.fastSort(map);
	   System.out.println(results);
   }*/
}