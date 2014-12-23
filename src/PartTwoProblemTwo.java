import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;

import weka.core.Instances;
public class PartTwoProblemTwo{
	public static void main(String args[]) throws Exception{
		KernelFunctions kf = new KernelFunctions();
		System.out.print("Linear or string kernel: ");
		System.out.println(kf.getAccuracy(1, false));
		System.out.print("Homogenous quadratic kernel: ");
		System.out.println(kf.getAccuracy(2, false));
		System.out.print("Nonhomogenous quadratic kernel: ");
		System.out.println(kf.getAccuracy(2, true));
		System.out.print("Homogenous cubic kernel: ");
		System.out.println(kf.getAccuracy(3, false));
	}
}

class KernelFunctions{
	public double accuracy;
	public KernelFunctions(){
		accuracy = 0;
	}
	public double getAccuracy(int degree, boolean homo) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("glass.arff"));
		Instances data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		PolyKernel pl = new PolyKernel(data, 0, degree, homo);
		SMO smo = new SMO();
		smo.setKernel(pl);
		smo.buildClassifier(data);
		int correct = 0;
		int incorrect = 0;
		for(int i = 0; i < data.numInstances(); i++){
			double assignedClass = smo.classifyInstance(data.instance(i));
			double originalClass = data.instance(i).classValue();
			if(assignedClass == originalClass)
				correct++;
			else
				incorrect++;
		}
		return 100*(double)correct/(correct + incorrect);
	}
}