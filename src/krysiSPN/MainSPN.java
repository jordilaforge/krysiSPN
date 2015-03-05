package krysiSPN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class MainSPN {

	public static void main(String[] args) {
		SPN a = new SPN();
//		short plain = 4751;
//		int key = 287869952;
//		short chiffre = a.encrypt(plain, key);
//		if(a.decrypt(chiffre, key)==plain){
//			System.out.println("Dechiffrierfbedingung erfüllt!");
//		}
//		else{
//			System.out.println("Ups Dechiffrierbedingung nicht erfüllt!");
//		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int key = a.crack(plainarray, chiffrearray);
		System.out.println("Cracked key="+key);
	}

}
