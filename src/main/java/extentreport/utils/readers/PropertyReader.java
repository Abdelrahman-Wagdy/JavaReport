package utils.readers;

import utils.PropertiesLoader;

public class PropertyReader {
    public static int[] passwordArray;

    public static int[] readPasswordFromPropertyFile(String propertyName) {
        String password = PropertiesLoader.readTestdata(propertyName);
        int[] passwordArray = new int[password.length()];

        for (int i = 0; i < password.length(); i++) {
            passwordArray[i] = Character.getNumericValue(password.charAt(i));
            System.out.println("Array[" + i + "] = " + passwordArray[i]);
        }

        return passwordArray;
    }

}
