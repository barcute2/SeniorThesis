import java.io.*;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
public class Partone {
	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("DataSet1.arff"));
		Instances data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		MultilayerPerceptron mp = new MultilayerPerceptron();
		Instances trainingData = new Instances(data, 0, (int)(data.numInstances() * 0.8));
		mp.buildClassifier(trainingData);
		Instances testingData = new Instances(data, (int)(data.numInstances() * 0.8), data.numInstances() - (int)(data.numInstances() * 0.8));
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
		System.out.printf("%f\n", 100*(double)correct/(correct+incorrect));
	}
}
