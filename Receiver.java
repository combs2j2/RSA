package com.jeremy.RSAProj;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	@Autowired
	private Cipher cipher;
	@Autowired
	private Parser parser;
	private BigInteger prime1;
	private BigInteger prime2;
	@Autowired
	private PrimeNumberGenerator png;
	
	public PrimeNumberGenerator getPng() {
		return png;
	}
	public void setPng(PrimeNumberGenerator png) {
		this.png = png;
	}
	public Cipher getCipher() {
		return cipher;
	}
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	public void setPrime1(BigInteger prime1) {
		this.prime1 = prime1;
	}
	public void setPrime2(BigInteger prime2) {
		this.prime2 = prime2;
	}
	public BigInteger calculatePhi() {
		BigInteger prime1Minus1 = prime1.subtract(BigInteger.ONE);
		BigInteger prime2Minus1 = prime2.subtract(BigInteger.ONE);
		return prime1Minus1.multiply(prime2Minus1);
	}
	public BigInteger generatePublicKey() {
		BigInteger publicKey = BigInteger.ZERO;
		
		Random rand = new Random();
		
		while(!(publicKey.gcd(calculatePhi()).equals(BigInteger.ONE))) {
			publicKey = BigInteger.valueOf(rand.nextInt(Integer.MAX_VALUE));
		}
		
		cipher.setExponent(publicKey.modInverse(calculatePhi()));
		System.out.println("Private key: " + this.cipher.getExponent());
		System.out.println("Public key: " + publicKey.mod(calculatePhi()));
		System.out.println("Phi: " + calculatePhi());
		cipher.setModulus(prime1.multiply(prime2));
		System.out.println("Modulus: " + prime1.multiply(prime2));
		
		return publicKey.mod(calculatePhi());
	}
	
	public void setPrimes() {
		png.setLo(new BigInteger("2000"));
		png.setHi(new BigInteger("20000"));
		
		prime1 = png.getRandomPrime();
		System.out.println("Prime1: " + prime1);
		prime2 = png.getRandomPrime();
		System.out.println("Prime2: " + prime2);
	}
	
	public String receiveMessage(List<BigInteger> encryptedMessage) {
		List<BigInteger> decryptedMessage = cipher.executeCipher(encryptedMessage);
		System.out.println("Recovered plaintext: " + decryptedMessage);
		String originalMessage = parser.numericListToString(decryptedMessage);
		System.out.println("Recipient received: " + originalMessage);
		return originalMessage;
	}
	
}
