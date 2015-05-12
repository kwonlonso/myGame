package UpdateData;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import database.SqliteConnect;

public class UpdateDatabase {
	private ArrayList<String> locationsarray = new ArrayList<String>();
	
	public UpdateDatabase(){
		String filestr = null;
		
		locationsarray.add("http://www.football-data.co.uk/mmz4281/1314/E0.csv");
		locationsarray.add("http://www.football-data.co.uk/mmz4281/1314/E1.csv");
		locationsarray.add("http://www.football-data.co.uk/mmz4281/1314/E2.csv");
		
		locationsarray.add("http://www.football-data.co.uk/mmz4281/1415/E0.csv");
		locationsarray.add("http://www.football-data.co.uk/mmz4281/1415/E1.csv");
		locationsarray.add("http://www.football-data.co.uk/mmz4281/1415/E2.csv");
		
		//update past stats
    	SqliteConnect s = new SqliteConnect();
		for(String locations :  locationsarray){
			//download csv file
			filestr = saveUrl(locations);
			
			//extract data
	    	ExtractCsvData e = new ExtractCsvData(filestr);
	    
	    	//write to database
	    	s.UpdatePastStats(e.getCsvdataarray());

		}
    	s.CloseAll();
    	
    	
    	//update fixtures table
    	SqliteConnect s2 = new SqliteConnect();
		filestr = saveUrl("http://www.football-data.co.uk/fixtures.csv");
		ExtractCsvData e2 = new ExtractCsvData(filestr);
		s.UpdateFixtures(e2.getCsvdataarray());
    	s2.CloseAll();
	}

	public String saveUrl(final String urlString) {
	    BufferedInputStream in = null;
	    String output = null;
	    try {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        output = IOUtils.toString(in, "UTF-8");
	    } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        if (in != null) {
	            try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	    return output;
	} 
}


