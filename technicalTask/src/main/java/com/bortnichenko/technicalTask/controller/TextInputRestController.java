package com.bortnichenko.technicalTask.controller;

import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Vyacheslav Alekseevich
 * 29/07/2020
 */

@RestController
@RequestMapping("/rest-api/")
public class TextInputRestController {

    @PostMapping(value = "/postString", headers = "Accept=application/json")
    public String postStringText(@RequestParam String inputValue) {

        String[] splitString = inputValue.split("\n");

        bubbleSort(Arrays.asList(splitString));

        for (int i = 0; i < splitString.length; i++) {
            splitString[i] = String.format("\n\"(%s):%s\"", splitString[i].length(), splitString[i]);
        }

        return Arrays.toString(splitString);
    }

    @PostMapping(value = "/postList", headers = "Accept=application/json")
    public List<String> postList(@RequestParam(value = "inputList") List<String> inputList) {
        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).length() == 0) {
                inputList.remove(inputList.get(i));
            }
        }

        bubbleSort(inputList);

        for (int i = 0; i < inputList.size(); i++) {
            inputList.set(i, String.format("(%s):%s", inputList.get(i).length(), inputList.get(i)));
        }

        return inputList;
    }

    // sort array
    private void bubbleSort(List<String> array) {
        String temp;
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(i).length() > array.get(j).length()) {
                    temp = array.get(i);
                    array.set(i, array.get(j));
                    array.set(j, temp);
                }
                if (array.get(i).length() == array.get(j).length()) {
                    temp = array.get(i);
                    array.set(i, array.get(j));
                    array.set(j, temp);
                }
            }
        }
        System.out.println("==========");
    }

    // part 2
    @GetMapping("/getMonth")
    public String returnMonth(@RequestParam String monthInYear) {
        try {
            int monthInt = Integer.parseInt(monthInYear);
            if (monthInt >= 1 && monthInt <= 12) {
                String month = Month.of(monthInt)
                        .getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru")).toUpperCase();

                StringBuilder str = new StringBuilder(month);

                for (int length = month.length() - 1; length > 0; length--) {
                    str.insert(length, '-');
                }
                return str.toString();
            } else {
                return "INCORRECT INPUT DATA";
            }

        } catch (NumberFormatException e) {
            return "INCORRECT INPUT DATA";
        }
    }

}
