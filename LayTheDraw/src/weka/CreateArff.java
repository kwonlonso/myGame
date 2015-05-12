package weka;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import commons.LeagueObject;
import database.SqliteConnect;

public class CreateArff {
    private ArrayList<LeagueObject> statistics;
    private ArrayList<LeagueObject> fixtures;
    private ArrayList<LeagueObject> filteredfixtures;
	private StringBuilder builder = new StringBuilder();
	private StringBuilder tmpfilebuilder;
	
	public CreateArff( ArrayList<LeagueObject> statistics, ArrayList<LeagueObject> fixtures){
		this.statistics = statistics;
		this.fixtures = fixtures;
        builder.append("@relation matches\n");
        
        
        builder.append("@attribute homeodds  real\n");
        builder.append("@attribute drawodds  real\n");
        builder.append("@attribute awayodds  real\n");
        
        builder.append("@attribute avggoalshome  real\n");
        builder.append("@attribute avggoalsaway  real\n");
        
        builder.append("@attribute avgsothome  real\n");
        builder.append("@attribute avgsotaway  real\n");
        
        builder.append("@attribute class {0,1}\n\n");
        
        builder.append("@data\n");
        GenerateArffFile (false);
        
       
        GenerateArffFile (true);
        

	}




	public void GenerateArffFile (Boolean iffixtures){
		tmpfilebuilder = new StringBuilder(builder);
		ArrayList<LeagueObject> stats;
		filteredfixtures = new ArrayList<LeagueObject> ();
		
		if(iffixtures){
			stats = new ArrayList<LeagueObject>(fixtures);
		}else{
			stats = new ArrayList<LeagueObject>(statistics);
		}
		
    	for(int x = 0; x < stats.size(); x++){
    		Statistics s = new Statistics(stats.get(x));
	        
	        if(stats.get(x).getDrawodds() <=3.5 && s.getPass()){
    	    	
	        	if(iffixtures){
					filteredfixtures.add(stats.get(x));
	        	}

				tmpfilebuilder.append("'"+stats.get(x).getHomeodds()+"'");
				tmpfilebuilder.append(",");
				tmpfilebuilder.append("'"+stats.get(x).getDrawodds()+"'");
				tmpfilebuilder.append(",");
				tmpfilebuilder.append("'"+stats.get(x).getAwayodds()+"'");
				tmpfilebuilder.append(",");
				
				    		 
				//teams past stats full time
				tmpfilebuilder.append(s.getFulltimegoalsavghome());
				tmpfilebuilder.append(",");
				    	        
				tmpfilebuilder.append(s.getFulltimegoalsavgaway());
				tmpfilebuilder.append(",");
				
				//teams past stats sot
				tmpfilebuilder.append(s.getFulltimeshotsontargethomeavg());
				tmpfilebuilder.append(",");
				    	        
				tmpfilebuilder.append(s.getFulltimeshotsontargetawayavg());
				tmpfilebuilder.append(",");
				    		 
			    int resultofgamefull = stats.get(x).getFulltimehomegoals() + stats.get(x).getFulltimeawaygoals();
				    	        
			    if(resultofgamefull > 0){
			    	tmpfilebuilder.append(1);
			    }else{
			       	tmpfilebuilder.append(0);
			    }
				    
			    tmpfilebuilder.append("\n"); 
    	    }
    	}
    	
		if(iffixtures){
			WriteToFile("weka/test.arff",  tmpfilebuilder.toString());
		}else{
			WriteToFile("weka/training.arff",  tmpfilebuilder.toString());
		}
    	
	}
	
	public ArrayList<LeagueObject> getFilteredfixtures() {
		return filteredfixtures;
	}

	public String getBuilder() {
		return builder.toString();
	}
	
	//write a string to file
	private void WriteToFile(String filename, String content){
		BufferedWriter output = null;
        try {
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file));
            output.write(content);
          } catch ( IOException e ) {
             e.printStackTrace();
          }finally {
   		   try {output.close();} catch (Exception ex) {}
  		}
	}
	
}
