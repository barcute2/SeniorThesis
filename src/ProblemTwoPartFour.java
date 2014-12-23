import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.meta.Bagging;

import weka.core.Instances;
public class ProblemTwoPartFour {
	public static void main(String args[]) throws Exception{
		Baggging b = new Baggging();
		System.out.printf("Accuracy: %f\n", b.bag(5));
		System.out.printf("Accuracy: %f\n", b.bag(15));
		System.out.printf("Accuracy: %f\n", b.bag(30));
		System.out.printf("Accuracy: %f\n", b.bag(70));
	}
}
class Baggging{
	public double bag(int percent) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("glass.arff"));
		Instances data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		Bagging bag = new Bagging();
		bag.setBagSizePercent(percent);
		bag.buildClassifier(data);
		int correct = 0;
		int incorrect = 0;
		for(int i = 0; i < data.numInstances(); i++){
			double assignedClass = bag.classifyInstance(data.instance(i));
			double originalClass = data.instance(i).classValue();
			if(assignedClass == originalClass)
				correct++;
			else
				incorrect++;
		}
		double testingAccuracy = 100*(double)correct/(correct + incorrect);
		return testingAccuracy;
	}
}