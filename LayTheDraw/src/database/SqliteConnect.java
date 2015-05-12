package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import commons.LeagueObject;

public class SqliteConnect {
	private static Connection connection = null;
	
	public SqliteConnect() {
	    try {
	        Class.forName("org.sqlite.JDBC");
	        connection = DriverManager.getConnection("jdbc:sqlite:stats.db");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Error: " + e.getMessage());
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    } 
	}
	
	public void CloseAll(){
		if(connection!= null){try {connection.close();} catch (SQLException ignore) {}}
	}
	
	public void CreateFixturesTable(){
		CreateDatabase cdb= new CreateDatabase(connection);
		cdb.CreateFixturesTable();
	}
	
	public void CreatePastStatsTable(){
		CreateDatabase cdb= new CreateDatabase(connection);
		cdb.CreatePastStatsTable();
	}
	
	public void UpdateFixtures(ArrayList<ArrayList<String>> csvdataarray){
		AddDataToDatabase atdb = new AddDataToDatabase(connection, csvdataarray);
		atdb.UpdateFixtures();
	}
	
	public void UpdatePastStats(ArrayList<ArrayList<String>> csvdataarray){
		AddDataToDatabase atdb = new AddDataToDatabase(connection, csvdataarray);
		atdb.UpdatePastStats();
	}
	
	public ArrayList<LeagueObject> GetPastStatsTable(){
		QueryDatabase qdb= new QueryDatabase(connection);
		return qdb.GetPastStats();
	}
	
	public ArrayList<LeagueObject> GetFixturesTable(){
		QueryDatabase qdb= new QueryDatabase(connection);
		return qdb.GetFutureFixtures();
	}
	
	public Integer GetPastGameCountWhereGoalAccured(Boolean halftime, String date, String team){
		QueryDatabase qdb= new QueryDatabase(connection);
		return qdb.GetPastGameCountWhereGoalAccured(halftime, date, team);
	}
	
	public Integer CountPastGoals(Boolean halftime, String date, String team){
		QueryDatabase qdb= new QueryDatabase(connection);
		return qdb.CountPastGoals(halftime, date, team);
		}
	
	public Integer GetPastGamesCount(String date, String team){
		QueryDatabase qdb= new QueryDatabase(connection);
		return qdb.GetPastGamesCount(date, team);
	}
	
	public Integer GetPastSOTCount(String date, String team){
		QueryDatabase qdb= new QueryDatabase(connection);
		return qdb.GetPastSOTCount(date, team);
		
	}
	
}