package borrar;

public class aes {
	public static void main(String[] args) throws Exception {
	    // TODO Auto-generated method stub

	    aesEta d = new aesEta();
	         
	    System.out.println("Encrypted string:" + d.encrypt("Hello"));           
	    String encryptedText = d.encrypt("Hello");
	    System.out.println("Decrypted string:" + d.decrypt(encryptedText));         
	}
}
