package krysiSPN;

public class MainSPN {

	public static void main(String[] args) {
		SPN a = new SPN();
		short plain = 3926;
		int key = 1768455;
		short chiffre = a.encrypt(plain, key);
		if(a.decrypt(chiffre, key)==plain){
			System.out.println("Dechiffriefbedingung erfüllt!");
		};
	}

}
