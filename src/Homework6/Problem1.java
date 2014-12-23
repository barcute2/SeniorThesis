package Homework6;

import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import weka.core.Instances;


public class Problem1 {
	HashMap<Pair, Double> occurences;
	HashMap<String, Double> problemTwo;
	int numberOfInstances;
	Instances data;
	ArrayList<Entry> theFiveZero, theFiveOne, theFiveTwo, theFiveThree, theFiveFour, theFiveFive; //lol
	ArrayList<PairD> topTen;
	ArrayList<String> stopWords;
	long zero, one, two, three, four, five;
	double maxZero, maxOne, maxTwo, maxThree, maxFour, maxFive;
 	
	public Problem1(String fileName) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		data = new Instances(br);
		br.close();
		data.setClassIndex(data.numAttributes() - 1);
		occurences = new HashMap<Pair, Double>();
		problemTwo = new HashMap<String, Double>();
		numberOfInstances = data.numInstances();
		theFiveZero = new ArrayList<Entry>();
		theFiveOne = new ArrayList<Entry>();
		theFiveTwo = new ArrayList<Entry>();
		theFiveThree = new ArrayList<Entry>();
		theFiveFour = new ArrayList<Entry>();
		theFiveFive= new ArrayList<Entry>();
		topTen = new ArrayList<PairD>();
		stopWords = new ArrayList<String>();
		setUpTheStopWords();
		zero = 0;
		one = 0;
		two = 0;
		three = 0;
		four = 0;
		five = 0;
		maxZero = 0;
		maxOne = 0;
		maxTwo = 0;
		maxThree = 0;
		maxFour = 0;
		maxFive = 0;
	}
	
	private void setUpTheStopWords(){
		stopWords.add("_");
		stopWords.add("a");
		stopWords.add("about");
		stopWords.add("all");
		stopWords.add("also");
		stopWords.add("am");
		stopWords.add("an");
		stopWords.add("and");
		stopWords.add("are");
		stopWords.add("as");
		stopWords.add("b");
		stopWords.add("but");
		stopWords.add("by");
		stopWords.add("c");
		stopWords.add("d");
		stopWords.add("do");
		stopWords.add("done");
		stopWords.add("e");
		stopWords.add("et");
		stopWords.add("etc");
		stopWords.add("f");
		stopWords.add("few");
		stopWords.add("for");
		stopWords.add("g");
		stopWords.add("go");
		stopWords.add("h");
		stopWords.add("have");
		stopWords.add("had");
		stopWords.add("he");
		stopWords.add("i");
		stopWords.add("if");
		stopWords.add("in");
		stopWords.add("is");
		stopWords.add("it");
		stopWords.add("j");
		stopWords.add("k");
		stopWords.add("l");
		stopWords.add("like");
		stopWords.add("ll");
		stopWords.add("m");
		stopWords.add("me");
		stopWords.add("n");
		stopWords.add("no");
		stopWords.add("nor");
		stopWords.add("not");
		stopWords.add("now");
		stopWords.add("o");
		stopWords.add("on");
		stopWords.add("of");
		stopWords.add("or");
		stopWords.add("p");
		stopWords.add("q");
		stopWords.add("r");
		stopWords.add("s");
		stopWords.add("she");
		stopWords.add("see");
		stopWords.add("six");
		stopWords.add("t");
		stopWords.add("the");
		stopWords.add("then");
		stopWords.add("u");
		stopWords.add("up");
		stopWords.add("us");
		stopWords.add("v");
		stopWords.add("very");		
		stopWords.add("ve");
		stopWords.add("w");
		stopWords.add("we");
		stopWords.add("who");
		stopWords.add("with");
		stopWords.add("x");
	    stopWords.add("y");
	    stopWords.add("yes");
	    stopWords.add("yet");
	    stopWords.add("z");
	}
	
	public void doProblemTwo(){
		for(int i = 0; i < numberOfInstances; i++){
			for(int j = 0; j < data.instance(i).numValues(); j++){
				Pair pair = new Pair((int)data.instance(i).value(data.instance(i).classIndex()), i, data.attribute(data.instance(i).index(j)).name().substring(1));
				Double found = problemTwo.get(pair.getWord());
				if(found == null){
					found = new Double(0);
				}
				found += 1;
				problemTwo.put(pair.getWord(), found);
			}
		}
	}
	
	public void setUpTheHashMap(int formula){
		occurences = new HashMap<Pair, Double>();
		for(int i = 0; i < numberOfInstances; i++){
			int totalValues = 0, maxValue = 0;
			for(int j = 0; j < data.instance(i).numValues(); j++){
				int numberOfTimes = (int)data.instance(i).value(data.instance(i).index(j));	
				totalValues += numberOfTimes;
				if(numberOfTimes > maxValue)
					maxValue = numberOfTimes;
			}
			for(int j = 0; j < data.instance(i).numValues(); j++){
				Pair pair = new Pair((int)data.instance(i).value(data.instance(i).classIndex()), i, data.attribute(data.instance(i).index(j)).name().substring(1));
				int numberOfTimes = (int)data.instance(i).value(data.instance(i).index(j));	
				Double found = occurences.get(pair);
				if(found == null){
					found = new Double(0);
				}
				found += (double) numberOfTimes;
				if(formula == 0)
					occurences.put(pair, found / totalValues);	
				else
					occurences.put(pair,  found/ maxValue);
			}
		}
	}
	
	public void normalize3(){
		Iterator it = problemTwo.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Double> pairs = (Map.Entry<String, Double>)it.next();
			problemTwo.put(pairs.getKey(), Math.log((double)numberOfInstances / (pairs.getValue() + 1)));
		}
	}
	
	public void getTopFive(){
		Iterator<java.util.Map.Entry<Pair, Double>> it = occurences.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Pair, Double> pairs = it.next();
			Entry e = new Entry(pairs.getKey(), pairs.getValue());
			if(e.getPair().getNewsGroup() == 0){
				if((theFiveZero.isEmpty()) || (e.getValue() - theFiveZero.get(0).getValue() > 0.0000001)){
					theFiveZero.add(0, e);
				}
				else{
					int i = 0;
					while((i < theFiveZero.size()) && (e.getValue() - theFiveZero.get(i).getValue() < 0.0000001)){
						i++;
					}
					theFiveZero.add(i, e);
				}
			}
			else if(e.getPair().getNewsGroup() == 1){
				if((theFiveOne.isEmpty()) || (e.getValue() - theFiveOne.get(0).getValue() > 0.0000001)){
					theFiveOne.add(0, e);
				}
				else{
					int i = 0;
					while((i < theFiveOne.size()) && (e.getValue() - theFiveOne.get(i).getValue() < 0.0000001)){
						i++;
					}
					theFiveOne.add(i, e);
				}
			}
			else if(e.getPair().getNewsGroup() == 2){
				if((theFiveTwo.isEmpty()) || (e.getValue() - theFiveTwo.get(0).getValue() > 0.0000001)){
					theFiveTwo.add(0, e);
				}
				else{
					int i = 0;
					while((i < theFiveTwo.size()) && (e.getValue() - theFiveTwo.get(i).getValue() < 0.0000001)){
						i++;
					}
					theFiveTwo.add(i, e);
				}
			}
			else if(e.getPair().getNewsGroup() == 3){
				if((theFiveThree.isEmpty()) || (e.getValue() - theFiveThree.get(0).getValue() > 0.0000001)){
					theFiveThree.add(0, e);
				}
				else{
					int i = 0;
					while((i < theFiveThree.size()) && (e.getValue() - theFiveThree.get(i).getValue() < 0.0000001)){
						i++;
					}
					theFiveThree.add(i, e);
				}
			}
			else if(e.getPair().getNewsGroup() == 4){
				if((theFiveFour.isEmpty()) || (e.getValue() - theFiveFour.get(0).getValue() > 0.0000001)){
					theFiveFour.add(0, e);
				}
				else{
					int i = 0;
					while((i < theFiveFour.size()) && (e.getValue() - theFiveFour.get(i).getValue() < 0.0000001)){
						i++;
					}
					theFiveFour.add(i, e);
				}
			}
			else if(e.getPair().getNewsGroup() == 5){
				if((theFiveFive.isEmpty()) || (e.getValue() - theFiveFive.get(0).getValue() > 0.0000001)){
					theFiveFive.add(0, e);
				}
				else{
					int i = 0;
					while((i < theFiveFive.size()) && (e.getValue() - theFiveFive.get(i).getValue() < 0.0000001)){
						i++;
					}
					theFiveFive.add(i, e);
				}
			}
		}
	}
	
	public void getTopTen(){
		Iterator<java.util.Map.Entry<String, Double>> it = problemTwo.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Double> pairs = it.next();			
			if((topTen.isEmpty()) || (pairs.getValue() - topTen.get(0).getCount() > 0.00000000000001)){
				topTen.add(0, new PairD(pairs.getValue(), pairs.getKey()));
			}
			else{
				int i = 0;
				while((i < topTen.size()) && (pairs.getValue() - topTen.get(i).getCount() < 0.000000000000001)){
					i++;
				}
				topTen.add(i, new PairD(pairs.getValue(), pairs.getKey()));
			}
		}
		System.out.println();
		System.out.println();
		double tempVal = topTen.get(0).getCount();
		int i = 0, j = 0;
		while(j < 10){
			if((i == 0) || (tempVal - topTen.get(i).getCount() > 0.0000000000000001)){	
				System.out.printf("%s %s\n", topTen.get(i).getWord(), topTen.get(i).getCount());
				tempVal = topTen.get(i).getCount();
				j++;
			}
			i++;
		}
	}
	
	public void printStuff(int index){
		double tempVal = theFiveZero.get(0).getValue();
		int j = 0;
		System.out.println("\n\nNewsgroup 0");
		for(int i = 0; (i < theFiveZero.size()) && (j < 5); i++){
			if((i == 0) || (tempVal - theFiveZero.get(i).getValue() > 0.000000000000001)){
				j++;
				System.out.printf("%s %s\n", theFiveZero.get(i).getPair().getWord(), String.valueOf(theFiveZero.get(i).getValue()));
			}
		}
		j = 0;
		tempVal = theFiveOne.get(0).getValue();
		System.out.println("\nNewsgroup 1");
		for(int i = 0; (i < theFiveOne.size()) && (j < 5); i++){
			if((i == 0) || (tempVal - theFiveOne.get(i).getValue() > 0.000000000000001)){
				j++;
				System.out.printf("%s %s\n", theFiveOne.get(i).getPair().getWord(), String.valueOf(theFiveOne.get(i).getValue()));
			}
		}
		j = 0;
		tempVal = theFiveTwo.get(0).getValue();
		System.out.println("\nNewsgroup 2");
		for(int i = 0; (i < theFiveTwo.size()) && (j < 5); i++){
			if((i == 0) || (tempVal - theFiveTwo.get(i).getValue() > 0.000000000000001)){
				j++;
				System.out.printf("%s %s\n", theFiveTwo.get(i).getPair().getWord(), String.valueOf(theFiveTwo.get(i).getValue()));
			}
		}
		j = 0;
		tempVal = theFiveThree.get(0).getValue();
		System.out.println("\nNewsgroup 3");
		for(int i = 0; (i < theFiveThree.size()) && (j < 5); i++){
			if((i == 0) || (tempVal - theFiveThree.get(i).getValue() > 0.000000000000001)){
				j++;
				System.out.printf("%s %s\n", theFiveThree.get(i).getPair().getWord(), String.valueOf(theFiveThree.get(i).getValue()));
			}
		}
		j = 0;
		tempVal = theFiveFour.get(0).getValue();
		System.out.println("\nNewsgroup 4");
		for(int i = 0; (i < theFiveFour.size()) && (j < 5); i++){
			if((i == 0) || (tempVal - theFiveFour.get(i).getValue() > 0.000000000000001)){
				j++;
				System.out.printf("%s %s\n", theFiveFour.get(i).getPair().getWord(), String.valueOf(theFiveFour.get(i).getValue()));
			}
		}
		j = 0;
		tempVal = theFiveFive.get(0).getValue();
		System.out.println("\nNewsgroup 5");
		for(int i = 0; (i < theFiveFive.size()) && (j < 5); i++){
			if((i == 0) || (tempVal - theFiveFive.get(i).getValue() > 0.000000000000001)){
				j++;
				System.out.printf("%s %s\n", theFiveFive.get(i).getPair().getWord(), String.valueOf(theFiveFive.get(i).getValue()));
			}
		}
	}
	
	public void inverseTF(){
		Iterator<java.util.Map.Entry<Pair, Double>> it = occurences.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Pair, Double> pairs = it.next();
			double inv = problemTwo.get(pairs.getKey().getWord());
			occurences.put((Pair)pairs.getKey(), pairs.getValue() * inv);
		}
	}
	
	public void makeArff(String fileIn, String fileOut) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(fileIn));				
		PrintWriter pw = new PrintWriter(fileOut, "UTF-8");
		String line = br.readLine();
		int keepGoing  = 1;
		while(keepGoing == 1){
			pw.println(line);
			line = br.readLine();
			if((line != null) && (line.length() > 1) && (line.charAt(1) == 'd'))
				keepGoing = 0;
		}
		br.close();
		pw.println("@data");
		for(int i = 0; i < numberOfInstances; i++){
			pw.print('{');
			for(int j = 0; j < data.instance(i).numValues() - 1; j++){
				Pair pair = new Pair((int)data.instance(i).value(data.instance(i).classIndex()), i, data.attribute(data.instance(i).index(j)).name().substring(1));
				Double valueToPrint;
				if(notStopWord(data.attribute(data.instance(i).index(j)).name().substring(1)))
					valueToPrint = getIdf(pair);
				else
					valueToPrint = new Double(0.0);
				String newValue = String.valueOf(data.instance(i).index(j)) + ' ' + String.valueOf(valueToPrint) + ", ";
				pw.print(newValue);
			}
			pw.print("47205 " + (int)data.instance(i).value(data.instance(i).classIndex()) +"}\n");
		}
		pw.close();
	}
	
	private double getIdf(Pair pair){
		Iterator<java.util.Map.Entry<Pair, Double>> it = occurences.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Pair, Double> pairs = it.next();
			if ((pairs.getKey().getCount() == pair.getCount()) && (pairs.getKey().getWord().equals(pair.getWord())))
				return pairs.getValue();			
		}
		return 0;
	}
	
	private boolean notStopWord(String word){
		for (String s : stopWords)
			if(s.equalsIgnoreCase(word))
				return false;
		return true;
	}
	
	public static void main(String args[]) throws Exception{
		
		//get attribute - data.attribute(0).name().substring(1)
		//get value - data.instance(0).value(876)
		//get class - data.instance(0).value(data.instance(0).classIndex())
		
		Problem1 p1 = new Problem1("train.arff");
		p1.setUpTheHashMap(0);
		p1.getTopFive();
		p1.printStuff(5);
		
		p1 = new Problem1("train.arff");
		p1.setUpTheHashMap(1);
		p1.getTopFive();
		p1.printStuff(5);
		
		p1 = new Problem1("train.arff");
		p1.doProblemTwo();
		p1.normalize3();
		p1.getTopTen();		
		
		p1 = new Problem1("train.arff");
		p1.setUpTheHashMap(0);
		p1.doProblemTwo();
		p1.inverseTF();
		p1.getTopFive();
		p1.printStuff(5);
		p1.makeArff("train.arff", "train1.arff");
		
		p1 = new Problem1("test.arff");
		p1.setUpTheHashMap(0);
		p1.doProblemTwo();
		p1.inverseTF();
		p1.makeArff("test.arff", "test1.arff");
		
		p1 = new Problem1("application.arff");
		p1.setUpTheHashMap(0);
		p1.doProblemTwo();
		p1.inverseTF();
		p1.makeArff("application.arff", "application1.arff");
	}
	
	private class Pair{
        private int count, newsGroup;
        private String word;
        
        
        Pair() {
        	// lol
        }
        
        Pair(int n, int c, String w) {
        	newsGroup = n;
        	count = c;
        	word = w;
        }
        
        public String getWord() {
        	return word;
        }
        
        public int getCount() {
        	return count;
        }  
        
        public int getNewsGroup(){
        	return newsGroup;
        }
	}
	
	private class PairD{
        private double count;
        private String word;
        
        
        PairD() {
        	// lol
        }
        
        PairD(double c, String w) {
        	count = c;
        	word = w;
        }
        
        public String getWord() {
        	return word;
        }
        
        public double getCount() {
        	return count;
        }  
        @Override
        public boolean equals(Object obj) {
        	if(obj instanceof Pair){
        		if((((Pair) obj).getCount() == count) && ((Pair) obj).getWord().equals(word))
        			return true;
        		else
        			return false;
        	}
        	else return false;
        }
	}
	
	private class Entry{
		private Pair pair;
		private double value;
		
		public Entry(){
			
		}
		
		public Entry(Pair p, double v){
			pair = p;
			value = v;
		}
		
		public double getValue(){
			return value;
		}
		
		public Pair getPair(){
			return pair;
		}
	}
}
