package weka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.ComplementNaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.misc.HyperPipes;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
 
//predicts the color of the cube squares using weka based on a training set and test set (previously generated)
public class WekaMachineLearning {
	private ArrayList<String> array = new ArrayList<String>();
	public WekaMachineLearning(int modelnum){
		try {
			GeneratePredictions(modelnum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getArray() {
		return array;
	}

	public void GeneratePredictions(int modelnum) throws Exception{
		//open arff files as an instance
		BufferedReader datafiletrain = readDataFile("weka/training.arff");
		Instances train = new Instances(datafiletrain);
		train.setClassIndex(train.numAttributes() - 1);
		
		BufferedReader datafiletest = readDataFile("weka/test.arff");
		Instances test = new Instances(datafiletest);
		test.setClassIndex(test.numAttributes() - 1);
		
		Remove rm = new Remove();
		rm.setAttributeIndices("1");  // remove 1st attribute
		Classifier model;
		// choose classifier
		if(modelnum == 1){
			model =	new ComplementNaiveBayes();
		}else if(modelnum == 2){
			model =	new NaiveBayesUpdateable();
		}else{
			model =	new HyperPipes();
		}
		
		// meta-classifier
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(rm);;
		fc.setClassifier(model);
		
		// train and make predictions
		fc.buildClassifier(train);
		for (int i = 0; i < test.numInstances(); i++) {
			int pred = (int) fc.classifyInstance(test.instance(i));
			//System.out.println(pred +" ID: " + test.instance(i).toString());
			array.add(test.classAttribute().value((int) pred));
		 }
	}	
	
	//read file and return as buffered reader
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.out.println("Error file not found: " + filename);
		}
 
		return inputReader;
	}
}
