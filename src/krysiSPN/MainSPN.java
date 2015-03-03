package krysiSPN;

public class MainSPN {

	public static void main(String[] args) {
		SPN a = new SPN("Hallo");
		a.crypt();
		a.decrypt();
		short plain = 4751;
		int key = 287869952;
		a.SubstitionNetwork(plain, key);

	}

}
