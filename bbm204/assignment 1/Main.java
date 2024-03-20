import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

class Main {
	public static void main(String args[]) throws IOException {

    	//*****************************************************
    	File file = new File("TrafficFlowDataset.csv");
    	Scanner scanner1 = new Scanner(file);
    	String line = "";
    	int[] randomdata = new int[251281];
    	int counter = 0;
		scanner1.nextLine();
    	while(scanner1.hasNext()) {
    		line = scanner1.nextLine();
			String[] elements = line.split(",");
			randomdata[counter] = Integer.parseInt(elements[6]);
			counter++;
    	}
    	//*****************************************************
    	
    	
		double[][] finallist = new double[12][10];
		finallist = exec(randomdata);
				
        // X axis data
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072,251282};
        showAndSaveChart("Sample Test", inputAxis, finallist);

    }
	public static double[][] exec(int datalist[]){
		double[][] timelist = new double[12][10];
		int[] inputsize = {500,1000,2000,4000,8000,16000,32000,64000,128000,250000};
		for (int input = 0; input < 10 ; input++) {
			int[] templist = new int[inputsize[input]];
			for (int i = 0; i<inputsize[input] ;i++) {
				templist[i] = datalist[i];
			}
			//******** RANDOM INPUT SORT
			double timeSR = 0;
			double timeQR = 0;
			double timeBR = 0;
			for(int i = 0; i<10 ;i++) {
				timeSR = timeSR + selectionSort(templist.clone());
				timeQR = timeQR + quickSort(templist.clone(),0,templist.length-1);
				timeBR = timeBR + bucketSort(templist.clone());
			}
			timelist[0][input] = timeSR/10;	
			timelist[1][input] = timeQR/10;	
			timelist[2][input] = timeBR/10;	
			//******* RANDOM INPUT LINEAR SEARCH
			int rand = new Random().nextInt(inputsize[input]);
			double timeLSR = 0;
			for (int i = 0; i<1000 ; i++) {
				timeLSR = timeLSR + linearSearch(templist.clone(),rand);
			}
			timelist[9][input] = timeLSR/1000;
			//******* SORTED INPUT SORT
			bucketSort(templist);
			double timeSS = 0;
			double timeQS = 0;
			double timeBS = 0;
			for(int i = 0; i<10 ;i++) {
				timeSS = timeSS + selectionSort(templist);
				//timeQS = timeQS + quickSort(templist,0,templist.length-1);
				timeBS = timeBS + bucketSort(templist);
			}
			timelist[3][input] = timeSS/10;	
			timelist[4][input] = timeQS/10;	
			timelist[5][input] = timeBS/10;	
			//************* SORTED SEARCH
			double timeLSS = 0;
			double timeBSS = 0;
			for (int i = 0; i<1000 ; i++) {
				timeLSS = timeLSS + linearSearch(templist,rand);
				timeBSS = timeBSS + binarySearch(templist,rand);
			}
			timelist[10][input] = timeLSS/1000;
			timelist[11][input] = timeBSS/1000;
			//************* REVERSELY SORTED SORT
			int[] Rtemp = new int[templist.length];
			for(int i = 0; i< templist.length; i++) {
				Rtemp[templist.length-i-1] = templist[i]; 
			}
			double timeSRS = 0;
			double timeQRS = 0;
			double timeBRS = 0;
			for(int i = 0; i<10 ; i++) {
				timeSRS = timeSRS + selectionSort(Rtemp.clone());
				//timeQRS = timeQRS + quickSort(Rtemp,0,Rtemp.length-1);
				timeBRS = timeBRS + bucketSort(Rtemp.clone());
			}
			timelist[6][input] = timeSRS/10;	
			timelist[7][input] = timeQRS/10;	
			timelist[8][input] = timeBRS/10;	
			for (int i = 0; i<12;i++) {
				for (int k = 0; k < 10; k++) {
					System.out.print(timelist[i][k] + "  ");
				}
				System.out.println();
			}
		}

		
		return timelist;
	}
	public static double bucketSort(int datalist[]) {
		double start = System.currentTimeMillis();
		int numberofbuckets = (int) Math.sqrt(datalist.length);
        ArrayList<Integer>[] buckets = new ArrayList[numberofbuckets];
		int max = findmax(datalist);
		
        for (int i = 0; i < numberofbuckets; i++) {
            buckets[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < datalist.length; i++) {
            int bucketIndex = hash(i, max, numberofbuckets);
            buckets[bucketIndex].add(datalist[i]);
        }

        for (int i = 0; i < numberofbuckets; i++) {
            Collections.sort(buckets[i]);
        }

        int k = 0;
        for (int i = 0; i < numberofbuckets; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
            	datalist[k] = buckets[i].get(j);
                k++;
            }
        }
        double now = System.currentTimeMillis();
        return (now-start);
	}
	
	private static int hash(int i, int max, int numberOfBuckets) {
		int temp = i/max*(numberOfBuckets-1);
		return (int)temp;
	}
	
	private static int findmax(int[] datalist) {
		int temp = datalist[0];
		for (int i = 1; i < datalist.length;i++) {
			if(temp >= datalist[i]) {
				continue;
			}
			else {	temp = datalist[i];}
		}
		return temp;
	}
	public static double quickSort(int datalist[], int lowindex, int highindex) {
		double start = System.currentTimeMillis();
		double now;
		if(lowindex >= highindex) {
			now = System.currentTimeMillis();

			return (now-start);
		}
		
		int pivot = datalist[highindex];
		int leftpointer = lowindex;
		int rightpointer = highindex;
		
		while(leftpointer < rightpointer) {
			while (datalist[leftpointer] <= pivot && leftpointer < rightpointer) {
				leftpointer++;
			}
			while (datalist[rightpointer] >= pivot && leftpointer < rightpointer) {
				rightpointer--;
			}
			swap(datalist, leftpointer, rightpointer);

		}
		swap(datalist, leftpointer, highindex);
		quickSort(datalist, lowindex, leftpointer-1);
		quickSort(datalist, leftpointer + 1, highindex);
		
		now = System.currentTimeMillis();
		return (now-start);
	}
	private static void swap(int datalist[], int index1, int index2) {
		int temp = datalist[index1];
		datalist[index1] = datalist[index2];
		datalist[index2] = temp;
	}
	
	public static double selectionSort(int datalist[]){
		double start = System.currentTimeMillis();
		
		for (int i = 0; i<datalist.length-1; i++) {
			int minindex = i;
			for(int j = i+1;j<datalist.length;j++) {
				if(datalist[j] < datalist[minindex]) {
					minindex = j;
				}
			}
			int min = datalist[minindex];
			if(minindex != i) {
				datalist[minindex] = datalist[i];
				datalist[i] = min;
			}
		}
		double now = System.currentTimeMillis();

		return (now-start);
		
	}
	
	public static double linearSearch(int datalist[], int x) {
		double start = System.nanoTime();

		for (int i = 0; i<datalist.length;i++) {
			if(datalist[i] == x) {
				double now = System.nanoTime();
				return (now-start);
			}
		}
		double now = System.nanoTime();
		return (now-start);
	}
	
	public static double binarySearch(int datalist[], int x) {
		double start = System.nanoTime();
		int low = 0;
		int high = datalist.length-1;
		while ( high - low > 1) {
			int mid =  (high+low)/2;
			if(datalist[mid] < x) {
				low = mid + 1;
			}
			else {
				high = mid;
			}
		}
		if(datalist[low] == x) {
			double now = System.nanoTime();
			return (now-start);
		}
		else if(datalist[high] == x) {
			double now = System.nanoTime();
			return (now-start);
		}
		else {
			double now = System.nanoTime();
			return (now-start);
		}
	}
	
    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        //************************************
        XYChart chartsort = new XYChartBuilder().width(800).height(600).title("Random Input Sorting Methods")
        		.yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();
        double[] doubleX2 = Arrays.stream(xAxis).asDoubleStream().toArray();
        chartsort.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartsort.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chartsort.addSeries("Selection Sort", doubleX2, yAxis[0]);
        chartsort.addSeries("Quick Sort", doubleX2, yAxis[1]);
        chartsort.addSeries("Bucket Sort", doubleX2, yAxis[2]);
        BitmapEncoder.saveBitmap(chartsort, title + ".png", BitmapEncoder.BitmapFormat.PNG);
        new SwingWrapper(chartsort).displayChart();
        //************************************
        XYChart chartsortsorted = new XYChartBuilder().width(800).height(600).title("Sorted Input Sorting Methods")
        		.yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();
        double[] doubleX3 = Arrays.stream(xAxis).asDoubleStream().toArray();
        chartsortsorted.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartsortsorted.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chartsortsorted.addSeries("Selection Sort", doubleX3, yAxis[3]);
        chartsortsorted.addSeries("Quick Sort", doubleX3, yAxis[4]);
        chartsortsorted.addSeries("Bucket Sort", doubleX3, yAxis[5]);
        BitmapEncoder.saveBitmap(chartsortsorted, title + ".png", BitmapEncoder.BitmapFormat.PNG);
        new SwingWrapper(chartsortsorted).displayChart();
        //************************************
        XYChart chartsortRsorted = new XYChartBuilder().width(800).height(600).title("Reversely Sorted Input Sorting Methods")
        		.yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();
        double[] doubleX4 = Arrays.stream(xAxis).asDoubleStream().toArray();
        chartsortRsorted.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartsortRsorted.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chartsortRsorted.addSeries("Selection Sort", doubleX4, yAxis[6]);
        chartsortRsorted.addSeries("Quick Sort", doubleX4, yAxis[7]);
        chartsortRsorted.addSeries("Bucket Sort", doubleX4, yAxis[8]);
        BitmapEncoder.saveBitmap(chartsortRsorted, title + ".png", BitmapEncoder.BitmapFormat.PNG);
        new SwingWrapper(chartsortRsorted).displayChart();
        //************************************
        XYChart chartsearch = new XYChartBuilder().width(800).height(600).title("Searching Methods")
        		.yAxisTitle("Time in Nanoseconds").xAxisTitle("Input Size").build();
        double[] doubleX5 = Arrays.stream(xAxis).asDoubleStream().toArray();
        chartsearch.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartsearch.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chartsearch.addSeries("Linear Search (random data)", doubleX5, yAxis[9]);
        chartsearch.addSeries("Linear Search (sorted data)", doubleX5, yAxis[10]);
        chartsearch.addSeries("Binary Search (sorted data)", doubleX5, yAxis[11]);
        BitmapEncoder.saveBitmap(chartsearch, title + ".png", BitmapEncoder.BitmapFormat.PNG);
        new SwingWrapper(chartsearch).displayChart();
        //************************************
    }
}
