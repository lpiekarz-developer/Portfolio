import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


public class ProjektRSA {

	public static void main(String args[]) throws IOException {
		
		String path_ = "/home/lukasz/workspace/RSA_Implementation/src/Message";
		String mode_ = "CBC";
		ArrayList<String> plaintext_ = new ArrayList<String>();
		ArrayList<String> ciphertext_ = new ArrayList<String>();
		ArrayList<String> decryptedtext_ = new ArrayList<String>();
			
		//MessageLoader msl = new MessageLoader(args[0]);
		
		MessageLoader msl = new MessageLoader(path_, plaintext_);
		msl.loadIntoBlocks();
		msl.showMessage();	   

		EncryptionScheme rsa_ = new EncryptionScheme(mode_);

	
		/* 
		 * Encryption of plaintext 
		 */
		
		for(int j = 0; j < plaintext_.size(); ++j) {
			
			String cj = rsa_.encrypt(plaintext_.get(j));
			ciphertext_.add(cj);			
		}
		
		/*
		 * Decryption of ciphertext
		 */
		
		for(int j = 0; j < ciphertext_.size(); ++j) {

			String xj = rsa_.decrypt(ciphertext_.get(j));
			
			if(msl.getFlag() && j == ciphertext_.size()-1) {
				
				byte[] bytes = xj.getBytes();
				byte[] bytes_with_no_padding = new byte[64];
				
				int padding = (int)bytes[xj.length()-1];

				System.out.println("PADDING: " + padding);
				
				for(int i = 0; i < bytes.length - padding; ++i) {
					bytes_with_no_padding[i] = bytes[i];
				}
				
				xj = new String(bytes_with_no_padding);				
			}
						
			decryptedtext_.add(xj);	
		}
		
		/*
		 * Printing decrypted message on screen
		 */
		
		for(int j = 0; j < decryptedtext_.size(); ++j) {
			
			System.out.print(decryptedtext_.get(j));
		}
		
	}
}
