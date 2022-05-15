package com.jeremy.RSAProj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class RsaProjApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test 
	void testGetRandomPrime() {
		ConfigurableApplicationContext context = SpringApplication.run(RsaProjApplication.class);
		
		PrimeNumberGenerator png = context.getBean(PrimeNumberGenerator.class);
		
		png.setLo(new BigInteger("1"));
		png.setHi(new BigInteger("10"));
		
		BigInteger selection = png.getRandomPrime();
		BigInteger ELEVEN = new BigInteger("11");
		
		assertTrue(selection.isProbablePrime(100));
		assertTrue(selection.compareTo(BigInteger.ZERO) == 1);
		assertTrue(selection.compareTo(ELEVEN) == -1);
	}
	
	@Test
	void testExecuteCipher() {
		ConfigurableApplicationContext context = SpringApplication.run(RsaProjApplication.class);
		
		Cipher cipher = context.getBean(Cipher.class);
		
		cipher.setExponent(new BigInteger("7"));
		cipher.setModulus(new BigInteger("31"));
		
		List<BigInteger> message = new ArrayList<BigInteger>();
		
		message.add(new BigInteger("111"));
		message.add(new BigInteger("213"));
		
		List<BigInteger> expected = new ArrayList<BigInteger>();
		
		expected.add(new BigInteger("9"));
		expected.add(new BigInteger("15"));
		
		List<BigInteger> actual = cipher.executeCipher(message);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testStringToNumericList() {
		ConfigurableApplicationContext context = SpringApplication.run(RsaProjApplication.class);
		
		List<BigInteger> expected = new ArrayList<BigInteger>();
		
		BigInteger modulus = new BigInteger("1101");
		
		String message = "hello";
		
		Parser parser = context.getBean(Parser.class);
		
		parser.setModulus(modulus);
		
		expected.add(new BigInteger("104"));
		expected.add(new BigInteger("101"));
		expected.add(new BigInteger("108"));
		expected.add(new BigInteger("108"));
		expected.add(new BigInteger("111"));
		
		List<BigInteger> actual = parser.stringToNumericList(message);
		
		assertEquals(expected, actual);
		
		expected.clear();
		
		expected.add(new BigInteger("72"));
		expected.add(new BigInteger("101"));
		expected.add(new BigInteger("108"));
		expected.add(new BigInteger("108"));
		expected.add(new BigInteger("111"));
		
		message = "Hello";
		
		actual = parser.stringToNumericList(message);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testNumericListToString() {
		ConfigurableApplicationContext context = SpringApplication.run(RsaProjApplication.class);
		
		List<BigInteger> numericList = new ArrayList<BigInteger>();
		
		numericList.add(new BigInteger("104"));
		numericList.add(new BigInteger("101"));
		numericList.add(new BigInteger("108"));
		numericList.add(new BigInteger("108"));
		numericList.add(new BigInteger("111"));
		
		String expected = "hello";
		
		Parser parser = context.getBean(Parser.class);
		parser.setModulus(new BigInteger("12345"));
		
		String actual = parser.numericListToString(numericList);
		
		assertEquals(expected, actual);
		
		numericList.clear();
		
		expected = "Hello";
		
		numericList.add(new BigInteger("72"));
		numericList.add(new BigInteger("101"));
		numericList.add(new BigInteger("108"));
		numericList.add(new BigInteger("108"));
		numericList.add(new BigInteger("111"));
		
		actual = parser.numericListToString(numericList);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testEncryptionDecryptionProcess() throws IOException {
		String message = "Hello bob. I am testing our cipher system!";
		
		ConfigurableApplicationContext context = SpringApplication.run(RsaProjApplication.class);
		
		Sender alice = context.getBean(Sender.class);
		Receiver bob = context.getBean(Receiver.class);
		
		alice.setMessage(message);
		String actual = alice.sendMessage(bob);
		
		assertEquals(message, actual);
		
		message = "Hello bob. Our last test worked! Here is a longer message with tons of special characters. Let's see if this works: !@#$%^&^%$#@@$54653564565";
		
		alice.setMessage(message);
		actual = alice.sendMessage(bob);
		
		assertEquals(message, actual);
	}
}
