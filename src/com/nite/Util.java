package com.nite;

import java.util.Scanner;

public class Util {
    static String asciiBanner =
            """
                     █████╗ ████████╗██████╗ ██╗   ██╗██╗   ██╗██╗ █████╗
                    ██╔══██╗╚══██╔══╝██╔══██╗██║   ██║██║   ██║██║██╔══██╗
                    ███████║   ██║   ██████╔╝██║   ██║██║   ██║██║███████║
                    ██╔══██║   ██║   ██╔══██╗██║   ██║╚██╗ ██╔╝██║██╔══██║
                    ██║  ██║   ██║   ██║  ██║╚██████╔╝ ╚████╔╝ ██║██║  ██║
                    ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝   ╚═══╝  ╚═╝╚═╝  ╚═╝""";


    // Generate text-instructions based on options
    public static String generateOptionString(String[] options) {
        String finalOutputString = "";

        for (int i = 0; i < options.length; i++) {
            finalOutputString += "[" + i + "] - " + options[i] + "\n";
        }

        return finalOutputString;
    }


    // Handle all types of input

    public static String handleTextInput() {
        Scanner inputScanner = new Scanner(System.in);

        System.out.print("> ");

        String input = inputScanner.nextLine();

        return input;
    }

    public static void handleOptionInput(String[] options) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.print("> ");

        int input = inputScanner.nextInt();
        boolean hasMethodBeenCalled = false;

        for (int i = 0; i < options.length; i++) {
            if (input == i) {
                callFunctionByName(options[i]);
                hasMethodBeenCalled = true;
            }
        }

        if (!hasMethodBeenCalled) {
            System.out.println("Das ist keine verfügbare Option. Bitte versuchen sie es erneut.");
            handleOptionInput(options);
        }
    }

    // Call a function by name

    private static void callFunctionByName(String preSanitizedFunctionName) {
        String sanitizedFunctionName = preSanitizedFunctionName
                .toLowerCase()
                .replaceAll("ä", "ae")
                .replaceAll("ö", "oe")
                .replaceAll("ü", "ue")
                .replaceAll(" ", "_");

        try {
            BankingApp.class.getDeclaredMethod(sanitizedFunctionName).invoke(null);
        } catch (Exception e) {
            System.err.println("Error calling function: " + sanitizedFunctionName);
            e.printStackTrace();
        }
    }

    // Generate option text and handle input

    public static void manageOptions(String[] options) {
        System.out.println(generateOptionString(options));
        handleOptionInput(options);
    }

    // Clear terminal

    public static void clearTerminal() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
