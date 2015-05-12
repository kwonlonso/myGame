package commons;

import java.util.ArrayList;

import weka.CreateArff;
import weka.WekaMachineLearning;
import UpdateData.UpdateDatabase;
import database.SqliteConnect;


public class testcode {
    public static void main(String[] args) throws Exception {
    	testcode m = new testcode();
    	//CreateDatabaseTables();
    	//UpdateDatabase();
    	m.PredictGamesToLayTheDraw();
    }
    
    private static void CreateDatabaseTables(){
    	SqliteConnect s = new SqliteConnect();
    	s.CreatePastStatsTable();
    	s.CreateFixturesTable();
    	s.CloseAll();
    }
    
    private static void UpdateDatabase(){
    	new UpdateDatabase();
    }
    
    private ArrayList<LeagueObject> GetPastStatsArray(){
    	SqliteConnect s = new SqliteConnect();
    	ArrayList<LeagueObject> tmp = s.GetPastStatsTable();
    	s.CloseAll();
    	return tmp;
    }
    
    private ArrayList<LeagueObject> GetFixturesArray(){
    	SqliteConnect s = new SqliteConnect();
    	ArrayList<LeagueObject> tmp = s.GetFixturesTable();
    	s.CloseAll();
    	return tmp;
    }
    
    private void PredictGamesToLayTheDraw(){
    	CreateArff createarff = new CreateArff(GetPastStatsArray(), GetFixturesArray());

    	WekaMachineLearning w1 = new WekaMachineLearning(1);
    	ArrayList<String> a1 = w1.getArray();
    	WekaMachineLearning w2 = new WekaMachineLearning(2);
    	ArrayList<String> a2 =w2.getArray();
    	WekaMachineLearning w3 = new WekaMachineLearning(3);
    	ArrayList<String> a3 =w3.getArray();
    	
    	
    	ArrayList<LeagueObject> filteredfixtures = createarff.getFilteredfixtures();
    	System.out.println(filteredfixtures.size());
    	for(int x=0; x < filteredfixtures.size(); x++){
    		filteredfixtures.get(x).setPredition(Integer.valueOf(a1.get(x))+Integer.valueOf(a1.get(x))+Integer.valueOf(a3.get(x)));
        	System.out.println(Integer.valueOf(a1.get(x))+Integer.valueOf(a1.get(x))+Integer.valueOf(a3.get(x)));
    	}
    }
}
