package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateDatabase {
    private Statement stmt = null;
    private String sql = null;
    private Connection connection = null;
    
	public CreateDatabase(Connection connection){
		this.connection=connection;
	}
	
	public void CreateFixturesTable(){
		  DeleteTable("FIXTURES");
	      try {
			  stmt = connection.createStatement();
		      sql = "CREATE TABLE FIXTURES " +
	                   "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
	                   " Div           TEXT     NOT NULL, " + 
	                   " Date          TEXT     NOT NULL, " + 
	                   " HomeTeam      TEXT     NOT NULL, " + 
	                   " AwayTeam      TEXT     NOT NULL, " + 
	                   " B365H         REAL, " + 
	                   " B365D         REAL, " + 
	                   " B365A         REAL, " + 
	                   " WHH           REAL, " + 
	                   " WHD           REAL, " + 
	                   " WHA           REAL)";

	      stmt.executeUpdate(sql);
	      stmt.close();		
	      
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void CreatePastStatsTable(){
		 DeleteTable("PASTSTATS");
	      try {
			stmt = connection.createStatement();
		    sql = "CREATE TABLE PASTSTATS " +
	                   "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
	                   " Div           TEXT    NOT NULL, " + 
	                   " Date          TEXT     NOT NULL, " + 
	                   " HomeTeam      TEXT     NOT NULL, " + 
	                   " AwayTeam      TEXT     NOT NULL, " + 
	                   " FTHG          INT, " + 
	                   " FTAG          INT, " + 
	                   " FTR           TEXT, " + 
	                   " HTHG          INT, " + 
	                   " HTAG          INT, " + 
	                   " HTR           TEXT, " + 
	                   " Referee       TEXT, " + 
	                   " HST           INT, " + 
	                   " AST           INT, " + 
	                   " HF            INT, " + 
	                   " AF            INT, " + 
	                   " HC            INT, " + 
	                   " AC            INT, " + 
	                   " HY            INT, " + 
	                   " AY            INT, " + 
	                   " HR            INT, " + 
	                   " AR            INT, " + 
	                   " B365H         REAL, " + 
	                   " B365D         REAL, " + 
	                   " B365A         REAL, " + 
	                   " WHH           REAL, " + 
	                   " WHD           REAL, " + 
	                   " WHA           REAL)";

	      stmt.executeUpdate(sql);
	      stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void DeleteTable(String tablename){
		try {
			connection.prepareStatement("DROP TABLE IF EXISTS "+ tablename).execute();
			System.out.println("table e,tied");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	  

}
