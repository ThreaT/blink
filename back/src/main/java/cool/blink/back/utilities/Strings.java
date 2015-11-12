package cool.blink.back.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

    /**
     * Encrypts a string with SHA-512 encryption
     *
     * @param string - String to encrypt
     * @param algorithm - Algorithm to use. i.e. SHA-256, SHA-512
     * @return encrypted String
     * @throws java.security.NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException UnsupportedEncodingException
     */
    public static final synchronized String encryption(final String string, final String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(string.getBytes("UTF-8"));
        byte[] digestBytes = messageDigest.digest();
        String hex;
        for (int i = 0; i < digestBytes.length; i++) {
            hex = Integer.toHexString(0xFF & digestBytes[i]);
            if (hex.length() < 2) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return new String(sb);
    }

    /**
     * @param string i.e. string or stRiNg
     * @return ProperCase String
     */
    public static final synchronized String toProperCase(final String string) {
        String lowerCase = string.toLowerCase();
        lowerCase = lowerCase.substring(1);
        String firstCharacter = "" + string.charAt(0);
        firstCharacter = firstCharacter.toUpperCase();
        return firstCharacter + lowerCase;
    }

    /**
     *
     * @param string will be used to verify length
     * @param min amount of characters in string
     * @param max amount of characters in string
     * @return true if string character count is between min and max
     */
    public static final synchronized Boolean between(final String string, final Integer min, final Integer max) {
        return ((string.length() >= min) && (string.length() <= max));
    }

    /**
     *
     * @param candidate string used as search criteria against options
     * @param options list of strings
     * @return true if string is equal to any one of the candidates disregarding
     * case sensitivity
     */
    public static final synchronized Boolean containsIgnoreCase(final String candidate, final List<String> options) {
        for (String option : options) {
            if (option.equalsIgnoreCase(candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param url string that needs url confirmation
     * @return true if url matches url format
     */
    public static final synchronized Boolean matchesUrl(final String url) {
        String regex = "\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        return m.matches();
    }

    public static final synchronized String[] toLowerCase(final String[] strings) {
        String[] lowerCaseStrings = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            lowerCaseStrings[i] = strings[i].toLowerCase();
        }
        return lowerCaseStrings;
    }

}
