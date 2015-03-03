package krysiSPN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SPN {
	
	String key="test";
	String text;
	String cyphertext;
	String sboxUrl="sbox.txt";
	String bitpermutationURL="bitpermutation.txt";
	Map<Byte, Byte> sbox = new HashMap<Byte, Byte>();
	Map<Byte, Byte> bitpermutation = new HashMap<Byte, Byte>();
	
	



	public SPN(String text){
		this.text = text;
		sbox = readSbox();
		bitpermutation = readBitpermutation();
	}


	private Map<Byte, Byte> readBitpermutation() {
		try(BufferedReader br = new BufferedReader(new FileReader(bitpermutationURL))) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	            if(line!=null){
	            	String[] dual = line.split(" ");
	            	bitpermutation.put(Byte.parseByte(dual[0]),Byte.parseByte(dual[1]));
	            }
	        }
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.bitpermutation;
	}


	private Map<Byte, Byte> readSbox() {
		try(BufferedReader br = new BufferedReader(new FileReader(sboxUrl))) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	            if(line!=null){
	            	String[] dual = line.split(" ");
	            	sbox.put((byte)Character.digit(dual[0].charAt(0),16),(byte)Character.digit(dual[1].charAt(0),16));
	            }
	        }
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.sbox;
	}


	public void crypt() {
		// TODO Auto-generated method stub
		
	}


	public void decrypt() {
		// TODO Auto-generated method stub
		
	}
	
	public String toHex(String arg) {
	    return String.format("%040x", new BigInteger(1, arg.getBytes()));
	}
	
	
	public byte SubstitionNetwork(short plain, int key){
		short working = plain;
		System.out.println("Plain: "+shortToString(working));
		System.out.println("Key:   "+intToString(key));
		//Schlüsselberechnung
		short k0=(short) (key >> 12);
		System.out.println("k0:    "+shortToString(k0));
		short k1=(short) (key >> 8);
		System.out.println("k1:    "+shortToString(k1));
		short k2=(short) (key >> 4);
		System.out.println("k2:    "+shortToString(k2));
		short k3=(short) key;
		System.out.println("k3:    "+shortToString(k3));
		
		//Weiss-Schritt
		working= (short) (working ^ k0);
		System.out.println("Weiss: "+shortToString(working));
		//SBOX
//		byte first = (byte) (working >> 12);
//		byte second = (byte) (working >> 8);
//		byte third = (byte) (working >> 4);
//		byte fourth = (byte) working;
//		first = sbox.get(first);
//		second = sbox.get(second);
//		third = sbox.get(third);
//		fourth = sbox.get(fourth);
//		System.out.println(byteToString(first)+byteToString(second)+byteToString(third)+byteToString(fourth));
		
		return 0;
	}
	
	public String byteToString(byte b){
		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
	}
	
	public String shortToString(short s){
		String out;
		out=String.format("%16s", Integer.toBinaryString(s)).replace(' ', '0');
		if(out.length()>16){
			out=out.substring(16,32);
		}
		return out;
	}
	
	public String intToString(int i){
		return String.format("%16s", Integer.toBinaryString(i)).replace(' ', '0');
	}
}
