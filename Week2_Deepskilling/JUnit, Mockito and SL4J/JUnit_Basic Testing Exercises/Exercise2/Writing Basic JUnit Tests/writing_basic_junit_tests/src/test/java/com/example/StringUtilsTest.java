package com.example;
import static org.junit.Assert.*;
import org.junit.Test;

public class StringUtilsTest {
    
    // Create an instance of the class we're testing
    private StringUtils stringUtils = new StringUtils();
    
    @Test
    public void testReverse() {
        // Test normal string
        assertEquals("olleh", stringUtils.reverse("hello"));
        
        // Test empty string
        assertEquals("", stringUtils.reverse(""));
        
        // Test single character
        assertEquals("a", stringUtils.reverse("a"));
        
        // Test null input
        assertNull(stringUtils.reverse(null));
        
        // Test string with spaces and special characters
        assertEquals("!dlrow ,olleH", stringUtils.reverse("Hello, world!"));
    }
    
    @Test
    public void testIsPalindrome() {
        // Test simple palindromes
        assertTrue(stringUtils.isPalindrome("racecar"));
        assertTrue(stringUtils.isPalindrome("level"));
        
        // Test palindrome with mixed case
        assertTrue(stringUtils.isPalindrome("Racecar"));
        
        // Test palindrome with spaces and punctuation
        assertTrue(stringUtils.isPalindrome("A man a plan a canal Panama"));
        
        // Test non-palindromes
        assertFalse(stringUtils.isPalindrome("hello"));
        assertFalse(stringUtils.isPalindrome("world"));
        
        // Test edge cases
        assertTrue(stringUtils.isPalindrome("a"));
        assertTrue(stringUtils.isPalindrome(""));
        assertFalse(stringUtils.isPalindrome(null));
    }
    
    @Test
    public void testCountVowels() {
        // Test string with vowels - CORRECTED: "hello" has 2 vowels (e, o), not 3
        assertEquals(2, stringUtils.countVowels("hello"));
        assertEquals(5, stringUtils.countVowels("aeiou"));
        assertEquals(5, stringUtils.countVowels("AEIOU"));
        
        // Test string without vowels
        assertEquals(0, stringUtils.countVowels("xyz"));
        assertEquals(0, stringUtils.countVowels("123"));
        
        // Test mixed case - CORRECTED: "Hello World" has 3 vowels (e, o, o), not 4
        assertEquals(3, stringUtils.countVowels("Hello World"));
        
        // Test edge cases
        assertEquals(0, stringUtils.countVowels(""));
        assertEquals(0, stringUtils.countVowels(null));
        
        // Additional test cases for clarity
        assertEquals(5, stringUtils.countVowels("beautiful")); // e, a, u, i, u = 5 vowels
        assertEquals(5, stringUtils.countVowels("education")); // e, u, a, i, o = 5 vowels
        assertEquals(0, stringUtils.countVowels("rhythm")); // y is not a vowel in this context, no vowels
    }
    
    @Test
    public void testCapitalizeWords() {
        // Test normal sentence
        assertEquals("Hello World", stringUtils.capitalizeWords("hello world"));
        
        // Test mixed case input
        assertEquals("Hello World", stringUtils.capitalizeWords("HELLO WORLD"));
        assertEquals("Hello World", stringUtils.capitalizeWords("hELLo WoRLd"));
        
        // Test single word
        assertEquals("Hello", stringUtils.capitalizeWords("hello"));
        
        // Test multiple spaces
        assertEquals("Hello World", stringUtils.capitalizeWords("hello  world"));
        
        // Test edge cases
        assertEquals("", stringUtils.capitalizeWords(""));
        assertNull(stringUtils.capitalizeWords(null));
        
        // Test single character words
        assertEquals("A B C", stringUtils.capitalizeWords("a b c"));
    }
    
    @Test
    public void testRemoveWhitespace() {
        // Test string with spaces
        assertEquals("helloworld", stringUtils.removeWhitespace("hello world"));
        
        // Test string with tabs and newlines
        assertEquals("hello", stringUtils.removeWhitespace("h\te\nl\rl o"));
        
        // Test string with no whitespace
        assertEquals("hello", stringUtils.removeWhitespace("hello"));
        
        // Test edge cases
        assertEquals("", stringUtils.removeWhitespace(""));
        assertEquals("", stringUtils.removeWhitespace("   "));
        assertNull(stringUtils.removeWhitespace(null));
    }
    
    @Test
    public void testIsNumeric() {
        // Test numeric strings
        assertTrue(stringUtils.isNumeric("123"));
        assertTrue(stringUtils.isNumeric("0"));
        assertTrue(stringUtils.isNumeric("999999"));
        
        // Test non-numeric strings
        assertFalse(stringUtils.isNumeric("123a"));
        assertFalse(stringUtils.isNumeric("12.3"));
        assertFalse(stringUtils.isNumeric("-123"));
        assertFalse(stringUtils.isNumeric("hello"));
        
        // Test edge cases
        assertFalse(stringUtils.isNumeric(""));
        assertFalse(stringUtils.isNumeric(null));
        assertFalse(stringUtils.isNumeric(" 123 "));
    }
}