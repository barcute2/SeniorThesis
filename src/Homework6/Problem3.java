package Homework6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.*;

public class Problem3 {
	Instances data, filteredData;
	File myFile;
	J48 j48;
	FilteredClassifier fc;
	int numberOfInstances, goodResults;
	
	public Problem3() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("train1.arff"));
		data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		j48 = new J48();
		j48.setUnpruned(false);
		fc = new FilteredClassifier();
		fc.setClassifier(j48);
		fc.buildClassifier(data);
	}

	
	public void apply(String fileIn, String fileOut)throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(fileIn));
		Instances apData = new Instances(br);
		br.close();
		apData.setClassIndex(apData.numAttributes() - 1);		
		PrintWriter pw = new PrintWriter(fileOut, "UTF-8");
		for (int i = 0; i < apData.numInstances(); i++){
			   double classification = fc.classifyInstance(apData.instance(i));	
			   pw.println(classification);
		}
		pw.close();
	}
	
	public void test(String fileIn) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(fileIn));
		Instances apData = new Instances(br);
		br.close();
		apData.setClassIndex(apData.numAttributes() - 1);
		numberOfInstances = apData.numInstances();
		goodResults = 0;
		for (int i = 0; i < apData.numInstances(); i++){
			   int classification = (int)fc.classifyInstance(apData.instance(i));
			   if(classification == apData.instance(i).value(apData.instance(i).classIndex()))
				   goodResults++;			   
		}			   
		System.out.printf("The accuracy is: %s", String.valueOf((double)goodResults / numberOfInstances));
	}
	
	public static void main(String args[]) throws Exception{
		Problem3 pm = new Problem3();
		pm.apply("application1.arff", "barcute2_application_result.txt");
		pm.test("test1.arff");
	}
}
