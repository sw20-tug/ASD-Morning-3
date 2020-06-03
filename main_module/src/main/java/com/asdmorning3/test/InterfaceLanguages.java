package com.asdmorning3.test;

import java.io.*;

public class InterfaceLanguages {

    public enum Languages{
        DE,
        EN,
        FR
    }

    //example getString(InterfaceLanguages.Languages.DE, "add");
    public String getString(Languages lang, String word) {
        String fileName = "";
        switch (lang) {
            case DE:
                fileName = System.getProperty("user.dir") + "/main_module/src/main/resources/de.conf";
                break;
            case EN:
                fileName = System.getProperty("user.dir") + "/main_module/src/main/resources/en.conf";
                break;
            case FR:
                fileName = System.getProperty("user.dir") + "/main_module/src/main/resources/fr.conf";
                break;
            default:
                System.out.println("error: language not found");
        }

        File file = new File(fileName);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] arr = line.split(";");
                if(arr[0].equals(word))
                    return arr[1];
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "string not found";
    }


}
