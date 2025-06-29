package com.example;
public class StringUtils {
    
    /**
     * Reverses a given string
     */
    public String reverse(String input) {
        if (input == null) {
            return null;
        }
        return new StringBuilder(input).reverse().toString();
    }
    
    /**
     * Checks if a string is a palindrome (reads same forwards and backwards)
     */
    public boolean isPalindrome(String input) {
        if (input == null) {
            return false;
        }
        String cleaned = input.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        return cleaned.equals(reverse(cleaned));
    }
    
    /**
     * Counts the number of vowels in a string
     */
    public int countVowels(String input) {
        if (input == null) {
            return 0;
        }
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (char c : input.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Capitalizes the first letter of each word
     */
    public String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                result.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 1) {
                    result.append(words[i].substring(1).toLowerCase());
                }
            }
            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }
    
    /**
     * Removes all whitespace from a string
     */
    public String removeWhitespace(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\\s", "");
    }
    
    /**
     * Checks if string contains only digits
     */
    public boolean isNumeric(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches("\\d+");
    }
}