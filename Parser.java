package com.jeremy.RSAProj;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Parser {
	BigInteger modulus;

	public BigInteger getModulus() {
		return modulus;
	}

	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}

	public Parser() {
		super();
	}
	
	public List<BigInteger> stringToNumericList(String message) {
		
		String twoFiftySix = "256";
		BigInteger twoFiftySixBI = new BigInteger(twoFiftySix);
		int blockSize = 0;
		
		while (modulus.compareTo(twoFiftySixBI) == 1) {
			twoFiftySix = twoFiftySix + twoFiftySix;
			twoFiftySixBI = new BigInteger(twoFiftySix);
			blockSize++;
		}
		
		blockSize = 3 * blockSize;
		
		BigInteger numericEquivalent = BigInteger.ZERO;
		
		int power = 0;
		BigInteger THOUSAND = new BigInteger("1000");
		
		for (int i = message.length()-1; i >= 0; i--) {
			BigInteger currAsciiVal = BigInteger.valueOf((int) message.charAt(i));
			numericEquivalent = numericEquivalent.add(currAsciiVal.multiply(THOUSAND.pow(power)));
			power++;
		}
		
		List<BigInteger> resultReversed = new ArrayList<BigInteger>();
		
		while (!numericEquivalent.equals(BigInteger.ZERO)) {
			resultReversed.add(numericEquivalent.mod(BigInteger.TEN.pow(blockSize)));
			numericEquivalent = numericEquivalent.divide(BigInteger.TEN.pow(blockSize));
		}
		
		List<BigInteger> result = new ArrayList<BigInteger>();
		
		for (int i = resultReversed.size()-1; i >= 0; i--) {
			result.add(resultReversed.get(i));
		}
		
		return result;
	}
	
	public String numericListToString(List<BigInteger> list) {
		
		String twoFiftySix = "256";
		BigInteger twoFiftySixBI = new BigInteger(twoFiftySix);
		int blockSize = 0;
		
		while (modulus.compareTo(twoFiftySixBI) == 1) {
			twoFiftySix = twoFiftySix + twoFiftySix;
			twoFiftySixBI = new BigInteger(twoFiftySix);
			blockSize++;
		}
		
		blockSize = 3 * blockSize;
		
		BigInteger combinedList = BigInteger.ZERO;
		
		BigInteger base = BigInteger.TEN.pow(blockSize);
		
		int power = 0;
		
		for (int i = list.size()-1; i >= 0; i--) {
			combinedList = combinedList.add(list.get(i).multiply(base.pow(power)));
			power++;
		}
		System.out.println("Combined list: " + combinedList);
		
		BigInteger THOUS = new BigInteger("1000");
		String resultReversed = "";
		
		while(!combinedList.equals(BigInteger.ZERO)) {
			resultReversed = resultReversed + (char) combinedList.mod(THOUS).intValue();
			combinedList = combinedList.divide(THOUS);
		}
		
		String result = "";
		
		for (int i = resultReversed.length()-1; i >= 0; i--) {
			result = result + resultReversed.charAt(i);
		}
		
		return result;
	}
}
