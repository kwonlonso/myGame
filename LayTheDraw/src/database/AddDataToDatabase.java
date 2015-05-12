package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddDataToDatabase {
    private static PreparedStatement preparedstatement = null; 
    private static Statement statement = null;  
    private static ResultSet resultset = null;
    private ArrayList<String> indexarray = new ArrayList<String> ();
    private Connection connection = null;
    private ArrayList<ArrayList<String>> csvdataarray = null;
    
    
	public AddDataToDatabase(Connection connection, ArrayList<ArrayList<String>> csvdataarray) {
		this.connection =connection;
		this.csvdataarray = csvdataarray;
		indexarray = new ArrayList<String> (csvdataarray.get(0));
		csvdataarray.remove(0);
	}
	
	public void UpdateFixtures(){
		CreateDatabase db = new CreateDatabase(connection);
		db.CreateFixturesTable();
		for(ArrayList<String> array : csvdataarray){
			AddFixtures(array);
		}
	}
	
	public void UpdatePastStats(){

		for(ArrayList<String> rowarray : csvdataarray){
			if(!CheckIfEntryExists(rowarray)){
				AddStatistics(rowarray);;	
			}
		}
	}
	
	private void AddFixtures(ArrayList<String> dataarray){
		try {
			preparedstatement = connection.prepareStatement("INSERT INTO FIXTURES(Div, Date, HomeTeam, AwayTeam, B365H, B365D,B365A)VALUES (?, ?, ?, ?, ?, ?, ?)");
			preparedstatement.setString(1, dataarray.get(getIndexOfColumn("Div")));
			preparedstatement.setString(2, FormatDate(dataarray.get(getIndexOfColumn("Date"))));
			preparedstatement.setString(3, dataarray.get(getIndexOfColumn("HomeTeam")));
			preparedstatement.setString(4, dataarray.get(getIndexOfColumn("AwayTeam")));
			preparedstatement.setString(5, dataarray.get(getIndexOfColumn("B365H")));
			preparedstatement.setString(6, dataarray.get(getIndexOfColumn("B365D")));
			preparedstatement.setString(7, dataarray.get(getIndexOfColumn("B365A")));
			preparedstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void AddStatistics(ArrayList<String> csvdataarray){
		try {
			preparedstatement = connection.prepareStatement("INSERT INTO PASTSTATS(Div, Date, HomeTeam, AwayTeam, FTHG, FTAG, FTR, HTHG, HTAG, HTR, HST, AST, HF, AF, HC, AC, HY, AY, HR, AR, B365H, B365D,B365A)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)");
			preparedstatement.setString(1, csvdataarray.get(getIndexOfColumn("Div")));
			preparedstatement.setString(2, FormatDate(csvdataarray.get(getIndexOfColumn("Date"))));
			preparedstatement.setString(3, csvdataarray.get(getIndexOfColumn("HomeTeam")).replaceAll("'", ""));
			preparedstatement.setString(4, csvdataarray.get(getIndexOfColumn("AwayTeam")));
			preparedstatement.setString(5, csvdataarray.get(getIndexOfColumn("FTHG")));
			preparedstatement.setString(6, csvdataarray.get(getIndexOfColumn("FTAG")));
			preparedstatement.setString(7, csvdataarray.get(getIndexOfColumn("FTR")));
			preparedstatement.setString(8, csvdataarray.get(getIndexOfColumn("HTHG")));
			preparedstatement.setString(9, csvdataarray.get(getIndexOfColumn("HTAG")));
			preparedstatement.setString(10, csvdataarray.get(getIndexOfColumn("HTR")));
			preparedstatement.setString(11, csvdataarray.get(getIndexOfColumn("HST")));
			preparedstatement.setString(12, csvdataarray.get(getIndexOfColumn("AST")));
			preparedstatement.setString(13, csvdataarray.get(getIndexOfColumn("HF")));
			preparedstatement.setString(14, csvdataarray.get(getIndexOfColumn("AF")));
			preparedstatement.setString(15, csvdataarray.get(getIndexOfColumn("HC")));
			preparedstatement.setString(16, csvdataarray.get(getIndexOfColumn("AC")));
			preparedstatement.setString(17, csvdataarray.get(getIndexOfColumn("HY")));
			preparedstatement.setString(18, csvdataarray.get(getIndexOfColumn("AY")));
			preparedstatement.setString(19, csvdataarray.get(getIndexOfColumn("HR")));
			preparedstatement.setString(20, csvdataarray.get(getIndexOfColumn("AR")));
			preparedstatement.setString(21, csvdataarray.get(getIndexOfColumn("B365H")));
			preparedstatement.setString(22, csvdataarray.get(getIndexOfColumn("B365D")));
			preparedstatement.setString(23, csvdataarray.get(getIndexOfColumn("B365A")));
			preparedstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String FormatDate(String datestr){
		String[] splitdatestr = datestr.split("/");
		String formattedstr = splitdatestr[2]+"-"+splitdatestr[1]+"-"+splitdatestr[0];
		return formattedstr;
	}
	
	public Boolean CheckIfEntryExists(ArrayList<String> rowarray){
		String date = FormatDate(rowarray.get(getIndexOfColumn("Date")));
		String hometeam = rowarray.get(getIndexOfColumn("HomeTeam"));
		String awayteam = rowarray.get(getIndexOfColumn("AwayTeam"));
		try {
			statement = (Statement) connection.createStatement();
			resultset = statement.executeQuery("SELECT ID FROM PASTSTATS WHERE Date='"+date+"' AND "+"HomeTeam='"+hometeam+"' AND "+"AwayTeam='"+awayteam+"'");
		    while(resultset.next()){
		    	Integer id = resultset.getInt("ID");
		        if(id > 0){  
		        	return true;
		        }
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private int getIndexOfColumn(String string) {
		int index = indexarray.indexOf(string);
		return index;
	}
}
