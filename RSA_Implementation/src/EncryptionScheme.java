import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;


public class EncryptionScheme {
	
	private SecureRandom rand_;	
	private BigInteger e, d, n;
	private int bitlen_;
	private String mode_;
	private String c0_;
	private ArrayList<String> ciphertextCBC_;
	
	public EncryptionScheme(String mode) {
		
		/*
		 * Setting up all the RSA Encryption Scheme, which are:
		 * 
		 * - setting two prime numbers p,q
		 * - calculating n = p*q
		 * - calculating Euler totient function phi(n)
		 * - calculating e,d as a part of public and private key respectively
		 */
		
		mode_ = mode;
		bitlen_ = 512;
		rand_ = new SecureRandom();
		BigInteger p = new BigInteger(bitlen_ / 2, 100, rand_);
		BigInteger q = new BigInteger(bitlen_ / 2, 100, rand_);
		n = p.multiply(q);
		
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("3");
		
		while( phi.gcd(e).intValue() > 1) {
			e = e.add( new BigInteger("2"));
		}
		
		d = e.modInverse(phi);
		
		if(mode_ == "CBC")
			initCBC();
	}
	
	
	public String encrypt(String m) {
		
		if ( mode_.equals("CBC") ) {
			
			return encryptCBC(m);
			
		} else {
			
			/* ECB - default mode */
			return encryptECB(m);
		}		
	}
	
	public String decrypt(String c) {
		
		if ( mode_.equals("CBC") ) {
		
			return decryptCBC(c);
			
		} else {
			
			/* ECB - default mode */
			return decryptECB(c);
		}		
	}

	private String encryptECB(String x) {
		return new BigInteger(x.getBytes()).modPow(e, n).toString();
	}
	
	private String decryptECB(String c) {
		return new String( new BigInteger(c).modPow(d, n).toByteArray());
	}
	
	
	private String encryptCBC(String x) {

		String cjp = ciphertextCBC_.get( ciphertextCBC_.size()-1 );
		String cjs = new BigInteger(x.getBytes()).xor( new BigInteger(cjp) ).modPow(e, n).toString();
		ciphertextCBC_.add(cjs);
		
		return cjs;
	}
	
	private String decryptCBC(String c) {
		
		int index = ciphertextCBC_.indexOf(c);

		return new String(new BigInteger(c).modPow(d, n).xor(new BigInteger(ciphertextCBC_.get(index-1))).toByteArray() );
	}
	
	private String generateC0() {
		
		byte [] bytes_ = new byte[bitlen_/8];
		
		rand_.nextBytes(bytes_);
		for(int i=0; i< bytes_.length; ++i) {
			bytes_[i] = (byte) Math.abs(bytes_[i]);
		}
		
		return new BigInteger(bytes_).toString();
	}
	
	private void initCBC() {
		
		ciphertextCBC_ = new ArrayList<String>();
		c0_ = generateC0();
		ciphertextCBC_.add(c0_);
	}
	

}
