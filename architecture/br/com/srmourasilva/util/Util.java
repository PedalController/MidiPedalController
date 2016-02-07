package br.com.srmourasilva.util;

public class Util {
	public static byte[] concat(byte[]...arrays) {
	    int totalLength = 0;
	    for (int i = 0; i < arrays.length; i++) {
	        totalLength += arrays[i].length;
	    }

	    byte[] result = new byte[totalLength];

	    int currentIndex = 0;
	    for (int i = 0; i < arrays.length; i++) {
	        System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
	        currentIndex += arrays[i].length;
	    }

	    return result;
	}
}
