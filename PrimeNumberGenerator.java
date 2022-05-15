package com.jeremy.RSAProj;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PrimeNumberGenerator {
	BigInteger lo;
	BigInteger hi;
	
	public PrimeNumberGenerator() {
		super();
	}
	public BigInteger getLo() {
		return lo;
	}
	public void setLo(BigInteger lo) {
		this.lo = lo;
	}
	public BigInteger getHi() {
		return hi;
	}
	public void setHi(BigInteger hi) {
		this.hi = hi;
	}
	
	public BigInteger getRandomPrime() {
		List<BigInteger> candidates = new ArrayList<BigInteger>();
		
		BigInteger currNum = lo;
		
		while (!currNum.equals(hi)) {
			if (currNum.isProbablePrime(100)) {
				candidates.add(currNum);
			}
			currNum = currNum.add(BigInteger.ONE);
		}
		
		Random rand = new Random();
		
		return candidates.get(rand.nextInt(candidates.size()));
	}
}
