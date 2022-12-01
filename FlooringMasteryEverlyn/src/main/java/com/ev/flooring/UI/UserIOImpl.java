package com.ev.flooring.UI;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
/**
 *
 * @author EverlynLeon
 *
 * */

@Component
public class UserIOImpl implements UserIO{
    Scanner scanner;
    public UserIOImpl(){
        scanner = new Scanner(System.in);
    }

    public void print(String message){
        System.out.println(message);
    }

    public String readString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public int readInt(String prompt){
        System.out.println(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    public int readInt(String prompt, int min, int max){
        int num;
        do {
            System.out.println(prompt);
            num = Integer.parseInt(scanner.nextLine());
        }
        while(num < min || num > max);
        return num;
    }

    public BigDecimal readBigDecimal(String prompt){
        System.out.println(prompt);
        return new BigDecimal(scanner.nextLine());
    }

    public LocalDate readDate(String prompt){
        System.out.println(prompt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(date, formatter);
        String formatDate = localDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        return localDate;
    }

}
