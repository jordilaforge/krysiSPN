package krysiSPN;

public class MainSPN {

	public static void main(String[] args) {
		SPN a = new SPN("Hallo");
		a.crypt();
		a.decrypt();
		short plain = 3926;
		int key = 1768455;
		a.SubstitionNetwork(plain, key);

	}

}
