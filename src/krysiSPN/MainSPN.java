package krysiSPN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;



public class MainSPN {
	
	public static final int threadnumber=4;

	public static void main(String[] args) {
		short[] plainarray = new short[7];
		short[] chiffrearray = new short[7];
		try (BufferedReader br = new BufferedReader(new FileReader("paare.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			int linecounter=0;
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				if (line != null) {
					line=line.replaceAll("\\s","");
					String[] dual = line.split("\\;");
					plainarray[linecounter]=(short) new BigInteger(dual[0], 2).intValue();
					chiffrearray[linecounter]=(short) new BigInteger(dual[1], 2).intValue();
					++linecounter;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long pvalue= (long)(Integer.MAX_VALUE*2l);
		long min = Integer.MIN_VALUE;
		long step = (long)(pvalue/threadnumber)+1l;
		System.out.println("Searching in range from "+min+" to "+(min+(step*threadnumber)));
		System.out.println("Starting "+ threadnumber + " threads with step "+step+" !");
		for(long i=0;i<threadnumber;++i){
			CalculatorThread thread = new CalculatorThread(plainarray,chiffrearray,(int)min,(int)(min+step),(int) i);
	        Thread t = new Thread(thread);
	        t.start();
	        min = min+step;
		}

	}

}
