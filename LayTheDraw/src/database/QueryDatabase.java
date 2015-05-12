package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import commons.LeagueObject;

public class QueryDatabase {
	private Connection connection = null;
	public QueryDatabase(Connection connection){
		this.connection =connection;
	}
	
	public ArrayList<LeagueObject> GetPastStats() {
		ArrayList<LeagueObject> templist = new ArrayList<LeagueObject>();
		try {
			Statement statement = (Statement) connection.createStatement();
			String query= "SELECT Date, HomeTeam, AwayTeam, FTHG, FTAG, HTHG, HTAG, HST, AST, B365H, B365D, B365A FROM PASTSTATS ORDER BY Date";
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				String date = resultset.getString("Date");
				String hometeam = resultset.getString("HomeTeam");
				String awayteam = resultset.getString("AwayTeam");
				Integer fthg = resultset.getInt("FTHG");
				Integer ftag = resultset.getInt("FTAG");
				Integer hthg = resultset.getInt("HTHG");
				Integer htag = resultset.getInt("HTAG");
				Integer hst = resultset.getInt("HST");
				Integer ast = resultset.getInt("AST");
				Float homeodds = resultset.getFloat("B365H");
				Float drawodds = resultset.getFloat("B365D");
				Float awayodds = resultset.getFloat("B365A");
				
				LeagueObject dataset = new LeagueObject();
				dataset.setDate(date);
				dataset.setHometeam(hometeam);
				dataset.setAwayteam(awayteam);
				dataset.setFulltimehomegoals(fthg);
				dataset.setFulltimeawaygoals(ftag);
				dataset.setHalftimehomegoals(hthg);
				dataset.setHalftimeawaygoals(htag);
				dataset.setHST(hst);
				dataset.setAST(ast);
				dataset.setHomeodds(homeodds);
				dataset.setDrawodds(drawodds);
				dataset.setAwayodds(awayodds);
				templist.add(dataset);
				
			}
			} catch (SQLException e) {
				 System.out.println("Error: " + e.getMessage());
			}

			return templist;
	}
	
	public ArrayList<LeagueObject> GetFutureFixtures() {
		ArrayList<LeagueObject> templist = new ArrayList<LeagueObject>();
		try {
			Statement statement = (Statement) connection.createStatement();
			String query= "SELECT Date, HomeTeam, AwayTeam, B365H, B365D, B365A FROM FIXTURES ORDER BY Date";
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				String date = resultset.getString("Date");
				String hometeam = resultset.getString("HomeTeam");
				String awayteam = resultset.getString("AwayTeam");
				Float homeodds = resultset.getFloat("B365H");
				Float drawodds = resultset.getFloat("B365D");
				Float awayodds = resultset.getFloat("B365A");
				
				LeagueObject dataset = new LeagueObject();
				dataset.setDate(date);
				dataset.setHometeam(hometeam);
				dataset.setAwayteam(awayteam);
				dataset.setHomeodds(homeodds);
				dataset.setDrawodds(drawodds);
				dataset.setAwayodds(awayodds);
				templist.add(dataset);
				
			}
		} catch (SQLException e) {
			 System.out.println("Error: " + e.getMessage());
		}

		return templist;
	}
	
	
	//get count of games played before date where a goal was scored or concieved for a specified team.
	public Integer CountQuery(String query) {
		Integer count = null;
		try {
			Statement statement = (Statement) connection.createStatement();
			//
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				count = resultset.getInt(1);
			}
		} catch (SQLException e) {
			 System.out.println("Error: " + e.getMessage());
		}

		return count;
	}
	
	//get count of games played before a date for a specified team
	public int GetPastGamesCount(String date, String team){
		String query= "SELECT COUNT(*) FROM PASTSTATS WHERE Date < '"+date+"' AND (HomeTeam = '"+team+"' OR AwayTeam='"+team+"')";
		return CountQuery(query);
	}
	
	//count of past shots for team
	public int GetPastSOTCount(String date, String team){
		String query= "SELECT SUM(HST)+ SUM(AST) FROM PASTSTATS WHERE Date < '"+date+"' AND (HomeTeam = '"+team+"' OR AwayTeam='"+team+"') ";
		return CountQuery(query);
	}
	
	//count goals scored + concived goals by a team before the specified date
	public int CountPastGoals(Boolean halftime, String date, String team){
		String query = null;
		if(halftime){
			query= "SELECT SUM(HTHG)+ SUM(HTAG) ";
		}else{
			query= "SELECT SUM(FTHG)+ SUM(FTAG) ";
		}
		query +="FROM PASTSTATS WHERE Date < '"+date+"' AND (HomeTeam = '"+team+"' OR AwayTeam='"+team+"') AND (FTHG > 0 OR FTAG > 0)";
		return CountQuery(query);
	}
	

	//get count of games played before date where a goal was scored or concieved for a specified team.
	public Integer GetPastGameCountWhereGoalAccured(Boolean halftime, String date, String team) {
		String query= "SELECT COUNT(*) FROM PASTSTATS WHERE Date < '"+date+"' AND (HomeTeam = '"+team+"' OR AwayTeam='"+team+"') AND ";
		if(halftime){
			query+= "(HTHG > 0 OR HTAG > 0) ";
			}else{
			query+= "(FTHG > 0 OR FTAG > 0) ";
		}
		return CountQuery(query);
	}
	
}
