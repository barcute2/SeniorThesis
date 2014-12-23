import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.Evaluation;
import weka.core.Instances;
public class PartTwoProblemThree {
	public static void main(String args[]) throws Exception{
		NeuralNetwork nn = new NeuralNetwork();
		nn.crossValidation("1");
		nn.crossValidation("2");
		nn.crossValidation("15");
		nn.crossValidation("30");
	}
}
class NeuralNetwork{	
	public void crossValidation(String layers) throws Exception{			
		BufferedReader br = new BufferedReader(new FileReader("glass.arff"));
		Instances data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		MultilayerPerceptron mp = new MultilayerPerceptron();
		mp.setHiddenLayers(layers);
		mp.buildClassifier(data);
		Random rand = new Random(10);   // create seeded number generator
		Evaluation eval = new Evaluation(data);		
		eval.crossValidateModel(mp, data, 10, rand);
		System.out.println(eval.toSummaryString());		
	}

}