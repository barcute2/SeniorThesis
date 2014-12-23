import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.Evaluation;
import weka.core.Instances;
public class PartTwoProblemOne {
	public static void main(String args[]) throws Exception{		
		Accuracy acc = new Accuracy();
		System.out.println("Part a)");
		acc.getAccuracy(10);
		System.out.printf("Training: %f Testing: %f\n", acc.trainingAccuracy, acc.testingAccuracy);
		acc.getAccuracy(30);
		System.out.printf("Training: %f Testing: %f\n", acc.trainingAccuracy, acc.testingAccuracy);
		acc.getAccuracy(50);
		System.out.printf("Training: %f Testing: %f\n", acc.trainingAccuracy, acc.testingAccuracy);
		acc.getAccuracy(70);
		System.out.printf("Training: %f Testing: %f\n", acc.trainingAccuracy, acc.testingAccuracy);
		acc.getAccuracy(90);
		System.out.printf("Training: %f Testing: %f\n", acc.trainingAccuracy, acc.testingAccuracy);
		System.out.println("\nPart b)");
		acc.crossValidation(2);
		acc.crossValidation(5);
		acc.crossValidation(10);
		acc.crossValidation(15);
		acc.crossValidation(20);
	}
}

class Accuracy{
	public double testingAccuracy, trainingAccuracy;
	public Accuracy(){
		testingAccuracy = 0;
		trainingAccuracy = 0;
	}
	public void getAccuracy(double percentage) throws Exception{
		percentage = percentage/100;
		BufferedReader br = new BufferedReader(new FileReader("glass.arff"));
		Instances data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		MultilayerPerceptron mp = new MultilayerPerceptron();
		Instances trainingData = new Instances(data, 0, (int)(data.numInstances() * percentage));
		mp.buildClassifier(trainingData);
		Instances testingData = new Instances(data, (int)(data.numInstances() * percentage), data.numInstances() - (int)(data.numInstances() * percentage));
		int correct = 0;
		int incorrect = 0;
		for(int i = 0; i < testingData.numInstances(); i++){
			double assignedClass = mp.classifyInstance(testingData.instance(i));
			double originalClass = testingData.instance(i).classValue();
			if(assignedClass == originalClass)
				correct++;
			else
				incorrect++;
		}
		testingAccuracy = 100*(double)correct/(correct + incorrect);
		correct = 0;
		incorrect = 0;
		for(int i = 0; i < trainingData.numInstances(); i++){
			double assignedClass = mp.classifyInstance(trainingData.instance(i));
			double originalClass = trainingData.instance(i).classValue();
			if(assignedClass == originalClass)
				correct++;
			else
				incorrect++;
		}
		trainingAccuracy = 100*(double)correct/(correct + incorrect);
	}
	
	public void crossValidation(int folds) throws Exception{			
		BufferedReader br = new BufferedReader(new FileReader("glass.arff"));
		Instances data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		MultilayerPerceptron mp = new MultilayerPerceptron();
		mp.buildClassifier(data);
		Random rand = new Random(folds);   // create seeded number generator
		Evaluation eval = new Evaluation(data);
		long prevTime = System.currentTimeMillis();
		eval.crossValidateModel(mp, data, folds, rand);
		long currentTime = System.currentTimeMillis();
		System.out.println(eval.toSummaryString());
		System.out.println(currentTime - prevTime);
	}

}
