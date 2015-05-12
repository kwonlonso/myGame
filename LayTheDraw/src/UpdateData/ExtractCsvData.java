package UpdateData;
import java.util.ArrayList;


public class ExtractCsvData {
	private ArrayList<ArrayList<String>> csvdataarray = null;
	
	public ExtractCsvData(String filestr){
		
		String[] rows = filestr.split("\n");
	       
	     //Read CSV line by line and input into array
		 csvdataarray = new ArrayList<ArrayList<String>>();
	     for(String row : rows){
	    	 String[] elements = row.split(",");
	    	 ArrayList<String> dataarray = new ArrayList<String>();
	    	 
	    	 for(String element : elements){
	    		 dataarray.add(element.replaceAll("'", ""));
	    	 }
	    	 
    		 csvdataarray.add(dataarray);
	    }
	}
	
	   public ArrayList<ArrayList<String>> getCsvdataarray() {
		return csvdataarray;
	}

}
