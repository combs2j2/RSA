package com.jeremy.RSAProj;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Cipher {
	private BigInteger exponent;
	private BigInteger modulus;
	
	public Cipher() {
		super();
	}
	public BigInteger getExponent() {
		return exponent;
	}
	public void setExponent(BigInteger exponent) {
		this.exponent = exponent;
	}
	public BigInteger getModulus() {
		return modulus;
	}
	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}
	public List<BigInteger> executeCipher(List<BigInteger> message) {
		List<BigInteger> result = new ArrayList<BigInteger>();
		for (int i = 0; i < message.size(); i++) {
			BigInteger currBlock = message.get(i);
			
			result.add(currBlock.modPow(exponent, modulus));
		}
		return result;
	}
}
