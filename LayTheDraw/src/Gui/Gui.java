package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import weka.CreateArff;
import weka.WekaMachineLearning;

import commons.LeagueObject;

import database.SqliteConnect;
import UpdateData.UpdateDatabase;

public class Gui extends JFrame{
	private JEditorPane textArea = new JEditorPane();
	private JButton getprediction, updatedb, fixturesb;
	private DefaultTableModel model;
	private JPanel tablepanel;
	//initiate JFrame
	public static void main(String[] args) {
		Gui frame = new Gui();
		frame.setVisible(true);
	}

	//setup basic layout and preferences for jframe
	public Gui() {
		
		//setup  dimensions and settings
		super("kociemba's sub-optimal cube algorithm");

		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		setContentPane(container);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(1200, 800);
	    setLocationRelativeTo(null);
	    setMinimumSize(new Dimension(1300, 960));
	    
		ArrayList<LeagueObject> farray = GetFixturesArray();
		tablepanel = new JPanel();
		tablepanel.setLayout(new GridLayout(1,1));
		tablepanel.add(TablePanel(farray));
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablepanel, OutputPanel());
        splitPane.setResizeWeight(0.70);
        splitPane.setOpaque(false);

		container.add(TitlePanel(), BorderLayout.NORTH	);
		container.add(splitPane, BorderLayout.CENTER);
		container.add( MenuPanel(),  BorderLayout.WEST	);
	}
	
	
	//main title panel
	private JPanel TitlePanel(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
	    panel.setBackground(Color.GRAY);
		panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 55 ));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new BorderLayout());
		leftpanel.setBackground(Color.GRAY);
		
		JLabel title = new JLabel("   LayTheDraw Predictions ");
		title.setFont(new Font("Serif", Font.BOLD, 22));
		title.setForeground(Color.WHITE);
		
		JLabel name = new JLabel("   Using WEKA machine learning");
		name.setFont(new Font("Serif", Font.BOLD, 14));
		name.setForeground(Color.WHITE);
		name.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		leftpanel.add(title,  BorderLayout.WEST);
		leftpanel.add(name,  BorderLayout.EAST);
		panel.add(leftpanel,  BorderLayout.WEST);
		
		return panel;
	} 
	
	//output panel that will display output to the user 
		private JPanel OutputPanel(){
			JPanel outputpanel = new JPanel();
			outputpanel.setLayout(new GridLayout(1, 1));
			outputpanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
			
			textArea.setFont(new Font("Serif", Font.PLAIN, 16));
			textArea.setEditable(false);
			textArea.setText("");
		
			outputpanel.add(textArea);

			JPanel titlepanel = new JPanel();
			titlepanel.setLayout(new BorderLayout());
			titlepanel.setBackground(Color.GRAY);
			titlepanel.setBorder(new EmptyBorder(5, 2, 8, 0));
			
			JLabel title = new JLabel("   Program Output");
			title.setFont(new Font("Serif", Font.BOLD, 17));
			title.setForeground(Color.WHITE);
			titlepanel.add(title);
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(titlepanel,  BorderLayout.NORTH);
			panel.add(outputpanel,  BorderLayout.CENTER);
		
			return panel;
		} 
		
		public JPanel TablePanel(ArrayList<LeagueObject> farray){
			//declare and configure components

			final JTable table;
		    
		    String col[] = {"Date","HomeTeam","AwayTeam", "B365H", "B365D", "B365A", "Stars" };
		    model = new DefaultTableModel(col,0); 
		    table=new JTable(model);
		    table.setEnabled(false);
			//take array and append to table
			for (int x=0; x<farray.size(); x++){
				model.insertRow(table.getRowCount(),new Object[]{farray.get(x).getDate() ,farray.get(x).getHometeam() ,farray.get(x).getAwayteam() ,farray.get(x).getHomeodds() , farray.get(x).getDrawodds(), farray.get(x).getAwayodds(), farray.get(x).getPredition() });
			}

			//add components to container pane
			JPanel container = new JPanel();
			container.setLayout(new GridLayout(1,1));
			container.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		    container.add(new JScrollPane(table));
			return container;
		}

		public JPanel MenuPanel(){
			//declare and configure components

			//add components to container pane
			JPanel btncontainer = new JPanel();
			btncontainer.setLayout(new GridLayout(3,1));
			
			fixturesb = new JButton("Show Fixtures");
			fixturesb.setFocusable(false);
			fixturesb.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
					ArrayList<LeagueObject> farray = GetFixturesArray();
					tablepanel.removeAll();
				    tablepanel.add(TablePanel(farray));
				    tablepanel.revalidate();
				    tablepanel.repaint();
			    }
			});
			
			JButton updatedb = new JButton("Update Database");
			updatedb.setFocusable(false);
			updatedb.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	textArea.setText(" Updating Database ... This may take a few moments");
			        Thread t1 = new Thread(new Runnable() {
			            public void run(){
			            	getprediction.setEnabled(false);
			            	updatedb.setEnabled(false);
			            	fixturesb.setEnabled(false);
			            	UpdateDatabase();
			            	 
			            	textArea.setText(" Database Update complete");
			                getprediction.setEnabled(true);
				            updatedb.setEnabled(true);
				            fixturesb.setEnabled(true);
			            	 
			            }});  
			        	t1.start();
			    	}
				});
			
			getprediction = new JButton("Get Predictions");
			getprediction.setFocusable(false);
			getprediction.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	textArea.setText(" Generaing predictions ... This may take a few moments");
			        Thread t1 = new Thread(new Runnable() {
			            public void run(){
			            	getprediction.setEnabled(false);
			            	updatedb.setEnabled(false);
			            	fixturesb.setEnabled(false);
			            	
			            	ArrayList<LeagueObject> t =PredictGamesToLayTheDraw();;
			            	tablepanel.removeAll();
						    tablepanel.add(TablePanel(t));
						    tablepanel.revalidate();
						    tablepanel.repaint();
						    
			            	textArea.setText(" Prediction has been generated");
			            	 getprediction.setEnabled(true);
				             updatedb.setEnabled(true);
				             fixturesb.setEnabled(true);
				 
			            }});  
			        	t1.start();
			    	}
				});
			
			btncontainer.add( fixturesb );
			btncontainer.add(updatedb);
			btncontainer.add( getprediction );

			
			//add components to container pane
			JPanel container = new JPanel();
			container.setBackground(Color.GRAY);
			container.setLayout(new BorderLayout());
			container.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		    container.add(btncontainer, BorderLayout.NORTH);
			return container;
		}	
		 
	    private void UpdateDatabase(){
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
	    
	    private ArrayList<LeagueObject> PredictGamesToLayTheDraw(){
	    	CreateArff createarff = new CreateArff(GetPastStatsArray(), GetFixturesArray());

	    	WekaMachineLearning w1 = new WekaMachineLearning(1);
	    	ArrayList<String> a1 = w1.getArray();
	    	WekaMachineLearning w2 = new WekaMachineLearning(2);
	    	ArrayList<String> a2 =w2.getArray();
	    	WekaMachineLearning w3 = new WekaMachineLearning(3);
	    	ArrayList<String> a3 =w3.getArray();
	    	
	    	
	    	ArrayList<LeagueObject> filteredfixtures = createarff.getFilteredfixtures();
	    	for(int x=0; x < filteredfixtures.size(); x++){
	    		filteredfixtures.get(x).setPredition(Integer.valueOf(a1.get(x))+Integer.valueOf(a1.get(x))+Integer.valueOf(a3.get(x)));
	    	}
			return filteredfixtures;
	    }
}
