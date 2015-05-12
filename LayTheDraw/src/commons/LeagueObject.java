package commons;

public class LeagueObject {
	private String date;
	private String hometeam;
	private String awayteam;
	
	private int fulltimehomegoals;
	private int fulltimeawaygoals;
	private int halftimehomegoals;
	private int halftimeawaygoals;
	
	private int HST;
	private int AST;
	
	private float homeodds;
	private float drawodds;
	private float awayodds;
	
	private int predition;
	
	public int getPredition() {
		return predition;
	}


	public void setPredition(int predition) {
		this.predition = predition;
	}


	public LeagueObject(){

	}
	

	public void setDate(String date) {
		this.date = date;
	}


	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}


	public void setAwayteam(String awayteam) {
		this.awayteam = awayteam;
	}


	public void setFulltimehomegoals(int fulltimehomegoals) {
		this.fulltimehomegoals = fulltimehomegoals;
	}


	public void setFulltimeawaygoals(int fulltimeawaygoals) {
		this.fulltimeawaygoals = fulltimeawaygoals;
	}


	public void setHalftimehomegoals(int halftimehomegoals) {
		this.halftimehomegoals = halftimehomegoals;
	}


	public void setHalftimeawaygoals(int halftimeawaygoals) {
		this.halftimeawaygoals = halftimeawaygoals;
	}


	public void setHST(int hST) {
		HST = hST;
	}


	public void setAST(int aST) {
		AST = aST;
	}


	public void setHomeodds(float homeodds) {
		this.homeodds = homeodds;
	}


	public void setDrawodds(float drawodds) {
		this.drawodds = drawodds;
	}


	public void setAwayodds(float awayodds) {
		this.awayodds = awayodds;
	}


	public float getHomeodds() {
		return homeodds;
	}

	public float getDrawodds() {
		return drawodds;
	}

	public float getAwayodds() {
		return awayodds;
	}

	public String getDate() {
		return date;
	}

	public String getHometeam() {
		return hometeam;
	}

	public String getAwayteam() {
		return awayteam;
	}

	public int getFulltimehomegoals() {
		return fulltimehomegoals;
	}

	public int getFulltimeawaygoals() {
		return fulltimeawaygoals;
	}

	public int getHalftimehomegoals() {
		return halftimehomegoals;
	}

	public int getHalftimeawaygoals() {
		return halftimeawaygoals;
	}
	
	public int getHST() {
		return HST;
	}

	public int getAST() {
		return AST;
	}
	
}
