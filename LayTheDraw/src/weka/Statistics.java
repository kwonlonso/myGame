package weka;

import commons.LeagueObject;
import database.SqliteConnect;

public class Statistics {
	private float halftimegoalsavghome;
	private float fulltimegoalsavghome;
	private float fulltimeshotsontargethomeavg;
	
	private float halftimegoalsavgaway;
	private float fulltimegoalsavgaway;
	private float fulltimeshotsontargetawayavg;
	
	private Boolean pass = false;


	public Statistics(LeagueObject instance){
		SqliteConnect s = new SqliteConnect();
		int totalgameshometeam = s.GetPastGamesCount(instance.getDate(), instance.getHometeam());
		int totalgamesawayteam = s.GetPastGamesCount(instance.getDate(), instance.getAwayteam());
		
		int pastgoalsaccuredhomehalf = s.CountPastGoals(true, instance.getDate(), instance.getHometeam());
		int pastgoalsaccuredawayhalf = s.CountPastGoals(true, instance.getDate(), instance.getAwayteam());
		int pastgoalsaccuredhomefull = s.CountPastGoals(false, instance.getDate(), instance.getHometeam());
		int pastgoalsaccuredawayfull=  s.CountPastGoals(false, instance.getDate(), instance.getAwayteam());
		
		int sothome = s. GetPastSOTCount(instance.getDate(), instance.getHometeam());
		int sotaway=  s. GetPastSOTCount(instance.getDate(), instance.getAwayteam());
		
		
		if(totalgameshometeam ==0){
			halftimegoalsavghome= 0;
			fulltimegoalsavghome= 0;
			
		}else{
			halftimegoalsavghome =  (float)pastgoalsaccuredhomehalf / (float)totalgameshometeam;
			fulltimegoalsavghome =  (float)pastgoalsaccuredhomefull / (float)totalgameshometeam;
			fulltimeshotsontargethomeavg = (float)sothome / (float)totalgameshometeam ;
		}

		if(totalgamesawayteam ==0){
			halftimegoalsavgaway= 0;
			fulltimegoalsavgaway= 0;
			
		}else{
			halftimegoalsavgaway = (float)pastgoalsaccuredawayhalf / (float)totalgamesawayteam ;
			fulltimegoalsavgaway = (float)pastgoalsaccuredawayfull / (float)totalgamesawayteam;
			fulltimeshotsontargetawayavg= (float)sotaway / (float)totalgameshometeam;
		}
		
		if(halftimegoalsavghome > 1 &&  halftimegoalsavgaway > 1){
			if(fulltimegoalsavghome + fulltimegoalsavgaway > 5.3){
				pass = true;
			}
		}
		
		s.CloseAll();
	}

	public String getHalftimegoalsavghome() {
		return String.format("%.3f", halftimegoalsavghome);
	}

	public String getHalftimegoalsavgaway() {
		return String.format("%.3f", halftimegoalsavgaway);
	}

	public String getFulltimegoalsavghome() {
		return String.format("%.3f", fulltimegoalsavghome);
	}

	public String getFulltimegoalsavgaway() {
		return String.format("%.3f", fulltimegoalsavgaway);
	}
	
	public String getFulltimeshotsontargethomeavg() {
		return String.format("%.3f", fulltimeshotsontargethomeavg);
	}

	public String getFulltimeshotsontargetawayavg() {
		return String.format("%.3f", fulltimeshotsontargetawayavg);
	}

	public Boolean getPass() {
		return pass;
	}
}
