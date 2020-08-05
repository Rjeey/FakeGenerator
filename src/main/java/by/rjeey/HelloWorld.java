package by.rjeey;

import com.github.javafaker.Faker;

import java.util.*;

public class HelloWorld {
    public static void main(String[] args) {
        String country;
        int records;
        double mistakes;
        if (args != null && args.length == 3) {
            country = args[0];
            records = Integer.parseInt(args[1]);
            mistakes = Double.parseDouble(args[2]);
        } else if (args != null && args.length == 2) {
            country = args[0];
            records = Integer.parseInt(args[1]);
            mistakes = 0;
        } else {
            System.out.println("\nThe entered data is incorrect try checking by example (us, 1, 1) or (us, 1)");
            return;
        }
        generate(records, mistakes, country);
    }

    static void generate(int countRecords, double mis, String local) {
        List<Data> records = new ArrayList<>();
        Faker faker = new Faker(new Locale(local));
        for (int i = 0; i < countRecords; ++i) {
            FlyData.setFullName(faker.name().nameWithMiddle(), i);
            FlyData.setAddress(faker.address().fullAddress(), i);
            FlyData.setPhoneNumber(faker.phoneNumber().phoneNumber(), i);
        }
        if (mis != 0) {
            if (mis < 1) {
                mis = (countRecords * mis);
                for (int j = 0; j < mis; ++j) {
                    switch ((int) (Math.random() * 3)) {
                        case 1 -> FlyData.setFullName(generateMisses(FlyData.getFullName(j), (int) mis, local, 1), j);
                        case 2 -> FlyData.setAddress(generateMisses(FlyData.getAddress(j), (int) mis, local, 2), j);
                        case 3 -> FlyData.setPhoneNumber(generateMisses(FlyData.getPhoneNumber(j), (int) mis, local, 3), j);
                    }
                }
            }
            for (int j = 0; j < mis; ++j) {
                switch ((int) (Math.random() * 3)) {
                    case 1 -> FlyData.setFullName(generateMisses(FlyData.getFullName(j), (int) mis, local, 1), j);
                    case 2 -> FlyData.setAddress(generateMisses(FlyData.getAddress(j), (int) mis, local, 2), j);
                    case 3 -> FlyData.setPhoneNumber(generateMisses(FlyData.getPhoneNumber(j), (int) mis, local, 3), j);
                }
            }
        }
        for (int i=0; i< countRecords;++i)
            System.out.println(FlyData.str(i));
    }

    static String generateMisses(String data, int misses, String local, int num) {
        char letter;
        StringBuilder changer = new StringBuilder();
        char[] us = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] ru = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray();
        char[] by = "абвгдеёжзійклмнопрстуўфхцчшыьэюяАБВГДЕЁЖЗІЙКЛМНОПРСТУЎФХЦЧШЫЬЭЮЯ".toCharArray();
        for (int i = 0; i < misses; ++i) {
            changer.setLength(0);
            letter = switch (local) {
                case "us" -> us[(int) (Math.random() * us.length)];
                case "ru" -> ru[(int) (Math.random() * ru.length)];
                case "by" -> by[(int) (Math.random() * by.length)];
                default -> throw new IllegalStateException("Unexpected value: " + local);
            };
            int number = (int) (Math.random() * 9);
            if (num == 1 || num == 2) {
                changer.append(data.trim());
                int ch = (int) (Math.random() * (changer.length() - 1));
                if ((int) (Math.random() * 2) == 1) {
                    changer.insert((int) (Math.random() * changer.length()), letter);
                    changer.deleteCharAt((int) (Math.random() * changer.length()));
                } else {
                    changer.setCharAt(ch, data.charAt(ch + 1));
                    changer.setCharAt(ch + 1, data.charAt(ch));
                }
                data = changer.toString();
            } else {
                changer.append(data.trim());
                int ch = (int) (Math.random() * (changer.length() - 1));
                if ((int) (Math.random() * 2) == 1) {
                    changer.insert((int) (Math.random() * changer.length()), (char) (number + '0'));
                    changer.deleteCharAt((int) (Math.random() * changer.length()));
                } else {
                    changer.setCharAt(ch, data.charAt(ch));
                    changer.setCharAt(ch + 1, data.charAt(ch + 1));
                }
                data = changer.toString();
            }

        }
        return data;
    }
}