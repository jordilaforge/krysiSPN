package krysiSPN;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class CalculatorThread implements Runnable{

	

	short[] plainarray;
	short[] chiffrearray;
	int start;
	int end;
	int threadnumber;

	    public CalculatorThread(short[] plainarray,short[] chiffrearray,int start, int end, int threadnumber) {
	        this.plainarray = plainarray;
	        this.chiffrearray = chiffrearray;
	        this.start = start;
	        this.end = end;
	        this.threadnumber = threadnumber;
	    }
	
	
	@Override
	public void run() {	
		SPN a = new SPN();
		long startTime = System.nanoTime(); 
		int key = a.crack(plainarray, chiffrearray, start, end, threadnumber);
		double seconds = (double)(System.nanoTime() - startTime) / 1000000000.0;
		System.out.println("Key: "+key);
		if(key!=0){
			try {
				PrintWriter out = new PrintWriter("key.txt");
				out.println("key: "+key+" time: "+seconds+"s");
				out.close();
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("Key written!");
		}
	}



}
