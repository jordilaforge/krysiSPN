package krysiSPN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SPN {

	String sboxUrl = "sbox.txt";
	String bitpermutationURL = "bitpermutation.txt";
	Map<Byte, Byte> sbox = new HashMap<Byte, Byte>();
	Map<Byte, Byte> bitpermutation = new HashMap<Byte, Byte>();
	
	/**
	 * Constructor for SPN
	 */
	public SPN() {
		sbox = readSbox();
		bitpermutation = readBitpermutation();
	}
	
	
	/**
	 * Reads bit permutation file from URL
	 * @return
	 */
	private Map<Byte, Byte> readBitpermutation() {
		try (BufferedReader br = new BufferedReader(new FileReader(
				bitpermutationURL))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				if (line != null) {
					String[] dual = line.split(" ");
					bitpermutation.put(Byte.parseByte(dual[0]),
							Byte.parseByte(dual[1]));
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

	/**
	 * Reads SBox from URL
	 * @return
	 */
	private Map<Byte, Byte> readSbox() {
		try (BufferedReader br = new BufferedReader(new FileReader(sboxUrl))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				if (line != null) {
					String[] dual = line.split(" ");
					sbox.put((byte) Integer.parseInt(dual[0], 16),
							(byte) Integer.parseInt(dual[1], 16));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.sbox;
	}

	/**
	 * Method to encrypt a plain text with a substitution permutation network
	 * @param plain plain text to encode type short
	 * @param key key for encoding type int
	 * @return return encrypted short value
	 */
	public short encrypt(short plain, int key) {
		System.out.println("---------Encryption--------------------");
		short working = plain;
		System.out.println("plain: " + shortToString(working));
		System.out.println("key:   " + intToString(key));
		// key calculation
		short k0 = (short) (key >>> 16);
		System.out.println("k0:    " + shortToString(k0));
		short k1 = (short) ((key << 4) >>> 16);
		System.out.println("k1:    " + shortToString(k1));
		short k2 = (short) ((key << 8) >>> 16);
		System.out.println("k2:    " + shortToString(k2));
		short k3 = (short) ((key << 12) >>> 16);
		System.out.println("k3:    " + intToString(k3));
		short k4 = (short) ((key << 16) >>> 16);
		System.out.println("k4:    " + intToString(k4));
		System.out.println("----------------------------------------");
		//white step
		working = (short) (working ^ k0);
		System.out.println("white: " + shortToString(working));
		System.out.println("---------round1-------------------------");
		//round1
		//sbox
		working = sbox(working,sbox);
		System.out.println("sbox:  " + shortToString(working));
		//permutation with table
		working = perm(working);
		System.out.println("perm:  " + shortToString(working));
		//XOR k1
		working = (short) (working ^ k1);
		System.out.println("xork1: " + shortToString(working));
		System.out.println("---------round2-------------------------");
		//round2
		//sbox
		working = sbox(working,sbox);
		System.out.println("sbox:  " + shortToString(working));
		//permutation with table
		working = perm(working);
		System.out.println("perm:  " + shortToString(working));
		//XOR k2
		working = (short) (working ^ k2);
		System.out.println("xork2: " + shortToString(working));
		System.out.println("---------round3-------------------------");
		//round3
		//sbox
		working = sbox(working,sbox);
		System.out.println("sbox:  " + shortToString(working));
		//permutation with table
		working = perm(working);
		System.out.println("perm:  " + shortToString(working));
		//XOR k3
		working = (short) (working ^ k3);
		System.out.println("xork3: " + shortToString(working));
		System.out.println("---------round4-------------------------");
		//round3
		//sbox
		working = sbox(working,sbox);
		System.out.println("sbox:  " + shortToString(working));
		//XOR k4
		working = (short) (working ^ k4);
		System.out.println("xork4: " + shortToString(working));
		System.out.println("chiff: " + shortToString(working));
		return working;
	}

	/**
	 * Uses permutation matrix to swap values 
	 * @param working short to swap
	 * @return swapped short
	 */
	private short perm(short working) {
		int temp = working;
		for (byte b = 0; b < 12; ++b) {
			if (bitpermutation.containsKey(b)) {
				temp = swap(temp, b, bitpermutation.get((byte) b));
			}
		}
		return (short) temp;
	}

	/**
	 * Method to swap bits in a integer value
	 * positions are indexed from 0 and in order ...[4][3][2][1][0]
	 * so changing 3 and 1 will make ...[4][1][2][3][0]
	 * @param i integer of value
	 * @param pos1 position1 to swap
	 * @param pos2 position2 to swap
	 * @return swapped integer
	 */
	public int swap(int i, int pos1, int pos2) {

		int bit1 = (i >> pos1) & 1;// bit at pos1
		int bit2 = (i >> pos2) & 1;// bit at pos2

		if (bit1 == bit2)
			return i; // no need to swap since we change 1 with 1 or 0 with 0

		// Since we are here it means that we need to change 1->0 and 0->1.
		// To do this we can use XOR (^).
		// Lets create mask 000010010 with ones at specified postions
		int mask = (1 << pos1) | (1 << pos2);

		return i ^ mask;// TADAM!!!
	}

	/**
	 * Reads sbox to change bits
	 * @param working short to change
	 * @return changed short
	 */
	private short sbox(short working,Map<Byte, Byte> sbox) {
		byte first = (byte) ((working << 16) >>> 28);
		byte second = (byte) ((working << 20) >>> 28);
		byte third = (byte) ((working << 24) >>> 28);
		byte fourth = (byte) ((working << 28) >>> 28);
		first = sbox.get(first);
		second = sbox.get(second);
		third = sbox.get(third);
		fourth = sbox.get(fourth);
		short merge = first;
		merge = (short) (merge << 4);
		merge += second;
		merge = (short) (merge << 4);
		merge += third;
		merge = (short) (merge << 4);
		merge += fourth;
		return merge;
	}

	/**
	 * Method to display byte as binary representation
	 * @param b byte to display
	 * @return string representation of b
	 */
	public String byteToString(byte b) {
		String str = String.format("%8s", Integer.toBinaryString(b & 0xFF))
				.replace(' ', '0');
		str = new StringBuilder(str).insert(4, " ").toString();
		return str;
	}

	/**
	 * Method to display short as binary representation
	 * @param s short to display
	 * @return string representation of s
	 */
	public String shortToString(short s) {
		String str = String.format("%16s", Integer.toBinaryString(s)).replace(
				' ', '0');
		str = new StringBuilder(str).insert(4, " ").toString();
		str = new StringBuilder(str).insert(9, " ").toString();
		str = new StringBuilder(str).insert(14, " ").toString();
		return str;
	}

	/**
	 * Method to display integer as binary representation
	 * @param i integer to display
	 * @return string representation of i
	 */
	public String intToString(int i) {
		String str = String.format("%32s", Integer.toBinaryString(i)).replace(
				' ', '0');
		str = new StringBuilder(str).insert(4, " ").toString();
		str = new StringBuilder(str).insert(9, " ").toString();
		str = new StringBuilder(str).insert(14, " ").toString();
		str = new StringBuilder(str).insert(19, " ").toString();
		str = new StringBuilder(str).insert(24, " ").toString();
		str = new StringBuilder(str).insert(29, " ").toString();
		str = new StringBuilder(str).insert(34, " ").toString();
		return str;
	}

	/**
	 * Method to decrypt a cypher text with a substitution permutation network
	 * @param chiffre cypher byte
	 * @param key int
	 * @return
	 */
	public int decrypt(short chiffre, int key) {
		System.out.println("---------Decryption--------------------");
		//invert sbox;
		Map<Byte, Byte> sboxInv = new HashMap<Byte, Byte>();
		for(byte i=0; i<16;i++){
			if(sbox.containsKey(i)){
				sboxInv.put(sbox.get(i), i);
			}
		}
		//generate keys
		short working = chiffre;
		System.out.println("chiff: " + shortToString(working));
		System.out.println("key:   " + intToString(key));
		// key calculation
		short k0inv = (short) ((key << 16) >>> 16);
		System.out.println("k0:    " + shortToString(k0inv));
		short k1inv = perm((short) ((key << 12) >>> 16));
		System.out.println("k1:    " + shortToString(k1inv));
		short k2inv = perm((short) ((key << 8) >>> 16));
		System.out.println("k2:    " + shortToString(k2inv));
		short k3inv = perm((short) ((key << 4) >>> 16));
		System.out.println("k3:    " + shortToString(k3inv));
		short k4inv = (short) (key >>> 16);
		System.out.println("k4:    " + shortToString(k4inv));
		System.out.println("----------------------------------------");
		//white step
		working = (short) (working ^ k0inv);
		System.out.println("white: " + shortToString(working));
		System.out.println("---------round1-------------------------");
		//round1
		//sbox
		working = sbox(working,sboxInv);
		System.out.println("sbox:  " + shortToString(working));
		//permutation with table
		working = perm(working);
		System.out.println("perm:  " + shortToString(working));
		//XOR k1
		working = (short) (working ^ k1inv);
		System.out.println("xork1: " + shortToString(working));
		System.out.println("---------round2-------------------------");
		//round2
		//sbox
		working = sbox(working,sboxInv);
		System.out.println("sbox:  " + shortToString(working));
		//permutation with table
		working = perm(working);
		System.out.println("perm:  " + shortToString(working));
		//XOR k2
		working = (short) (working ^ k2inv);
		System.out.println("xork2: " + shortToString(working));
		System.out.println("---------round3-------------------------");
		//round3
		//sbox
		working = sbox(working,sboxInv);
		System.out.println("sbox:  " + shortToString(working));
		//permutation with table
		working = perm(working);
		System.out.println("perm:  " + shortToString(working));
		//XOR k3
		working = (short) (working ^ k3inv);
		System.out.println("xork3: " + shortToString(working));
		System.out.println("---------round4-------------------------");
		//round4
		//sbox
		working = sbox(working,sboxInv);
		System.out.println("sbox:  " + shortToString(working));
		//XOR k4
		working = (short) (working ^ k4inv);
		System.out.println("xork4: " + shortToString(working));
		System.out.println("chiff: " + shortToString(working));
		return working;
	}
}
