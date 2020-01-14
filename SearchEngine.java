import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
		//note:
		
		//for parser, getContent return an arraylist of strings corresponding to the set of words
		//in the url
		//getLinks return an arraylist of strings containing all the hyperlinks going out
		
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add edge case
		
		
		
		ArrayList<String> words = new ArrayList<String>();
		
		ArrayList<String> webs = new ArrayList<String>();
		
		//1. put all the hyperlinks into the webGraph
		//add the current url into the internet
		//only add it if its not already existed
		
		//if the vertex is already exist, it would return false
		
			this.internet.addVertex(url);
			
		
			//make sure the web page is not already visited
			if(this.internet.getVisited(url)) {
				return;
			}
		
		this.internet.setVisited(url, true);
		words=parser.getContent(url);
		
		
		//put all the related words into the wordIndex array list
		
		this.wordIndex.putIfAbsent(url, words);
		
		webs=parser.getLinks(url);
		if(webs.isEmpty()) {
			return;
		}
		
		//add edges to all the urls
		//then visit the url 
        for(String s: webs) {
        	this.internet.addVertex(s);
        	this.internet.addEdge(url, s);
        	this.crawlAndIndex(s);
        }
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		
		ArrayList<String> webs = new ArrayList<String>();
		ArrayList<Double> oldRanks = new ArrayList<Double>();
		ArrayList<Double> newRanks = new ArrayList<Double>();
		boolean isSmaller=false;
		int size;
		
		
		//get all the verticies and store them into an arrayList
		
		webs=this.internet.getVertices();
		size=webs.size();
		
		//set all the page rank into 1
		
		for(String s:webs) {
		      this.internet.setPageRank(s, 1);
		}
		
		//use a while loop to check if all the rank is smaller than the epsilon
		//update every web's rank
		//check if they're smaller in the end
		
		while(!isSmaller) {
			oldRanks=this.getRanks(webs);
			isSmaller=true;
			newRanks=this.computeRanks(webs);
            
			//use for loop to check the rank 
			
			for(int i=0; i<newRanks.size();i++) {
				double temp = Math.abs(newRanks.get(i)-oldRanks.get(i));
				if(temp>=epsilon) {
					isSmaller=false;
					break;
				}
			}
			//update rank
			for(int i=0; i<size;i++) {
				this.internet.setPageRank(webs.get(i), newRanks.get(i));
			}
			
		}
		
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here
		//create a new arraylist for return
		if(vertices.isEmpty()) {
			return null;
		}
		
		ArrayList<Double> ret = new ArrayList<Double>();
		ArrayList<String> neighbors = new ArrayList<String>();
		ArrayList<Double> neiRank= new ArrayList<Double>();
		
		//compute each individual urls to find its neighbors
		//update the rank
		
		for(String s: vertices) {
			neighbors = this.internet.getEdgesInto(s);
			
			double temp=0;
			
			neiRank=this.getRanks(neighbors);
			
			for(int i=0;i<neighbors.size();i++) {
				temp=temp+((neiRank.get(i))/(internet.getOutDegree(neighbors.get(i))));
			}
			
			double rank = 0.5+(0.5*temp);
			ret.add(rank);
		}
		
		return ret;
	}

	
	//helper method: to get the rank of all the verticies
	//store them in a double array list
	
	private ArrayList<Double> getRanks(ArrayList<String> urls){
		ArrayList<Double> ret=new ArrayList<Double>();
		
		for(String s: urls) {
			ret.add(this.internet.getPageRank(s));
		}
		
		return ret;
	}
	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		
		ArrayList<String> webs=new ArrayList<String>();
		ArrayList<String> words=new ArrayList<String>();
		HashMap<String, Double> ranks = new HashMap<String, Double>();
		
		//put all the vertecies into an arraylist
		
		webs=this.internet.getVertices();
		
		//turn the query word into lower cases
		query=query.toLowerCase();
		
		//loop through all the urls inside the webgraph
		//check whether the query is inside the wordIndex of this url
		for(String s: webs) {
			words=this.wordIndex.get(s);
			if(words!=null) {
			for(String t:words) {
				t=t.toLowerCase();
				if(t.equals(query)) {
					ranks.put(s, this.internet.getPageRank(s));
					break;
				}
			}
			}
		}
		
		return Sorting.fastSort(ranks);
	}
	
	
	
	
	/*public static void main(String[] args) throws Exception {
		SearchEngine huh = new SearchEngine("testAcyclic.xml");
		
		huh.crawlAndIndex("siteA");
		ArrayList<Double> lol = new ArrayList<Double>();
		for(String s: huh.internet.getVertices()) {
			huh.internet.setPageRank(s, 1);
		}
		
		lol=huh.computeRanks(huh.internet.getVertices());
		
		for(int i=0; i<huh.internet.getVertices().size(); i++) {
			System.out.println(huh.internet.getVertices().get(i) + "  " + huh.internet.getPageRank(huh.internet.getVertices().get(i)));
		}
	}*/
}
