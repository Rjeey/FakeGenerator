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
        generate(records,mistakes, country).forEach(System.out::println);
    }

    static List<Data> generate(int countRecords, double mis, String local) {
        List<Data> records = new ArrayList<>();
        Faker faker = new Faker(new Locale(local));
        int i = 0;
        while (i < countRecords) {
            records.add(new Data(faker.name().nameWithMiddle(),
                    faker.address().fullAddress(), faker.phoneNumber().phoneNumber()));
            ++i;
        }
        if (mis != 0) {
            if (mis < 1) {
                mis = (records.size() * mis);
                double finalMis = mis;
                for (int j = 0; j < mis; ++j) {
                    records.forEach(d -> d.Replacement(generateMisses(d, (int)finalMis, local)));
                }
            }
            double finalMisCount = mis;
            records.forEach(d -> d.Replacement(generateMisses(d, (int)finalMisCount, local)));
        }
        return records;
    }

    static Data generateMisses(Data data, int misses, String local) {
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
            switch ((int) (Math.random() * 3)) {
                case 1 -> {
                    changer.append(data.getFullName().trim());
                    int ch = (int) (Math.random() * (changer.length()-1));
                    if ((int) (Math.random() * 2) == 1) {
                        changer.insert((int) (Math.random() * changer.length()), letter);
                        changer.deleteCharAt((int) (Math.random() * changer.length()));
                    } else {
                        changer.setCharAt(ch, data.getFullName().charAt(ch+1));
                        changer.setCharAt(ch + 1, data.getFullName().charAt(ch));
                    }
                    data.setFullName(changer.toString());
                }
                case 2 -> {
                    changer.append(data.getAddress().trim());
                    int ch = (int) (Math.random() * (changer.length()-1));
                    if ((int) (Math.random() * 2) == 1) {
                        changer.insert((int) (Math.random() * changer.length()), letter);
                        changer.deleteCharAt((int) (Math.random() * changer.length()));
                    } else {
                        changer.setCharAt(ch, data.getAddress().charAt(ch));
                        changer.setCharAt(ch + 1, data.getAddress().charAt(ch + 1));
                    }
                    data.setAddress(changer.toString());
                }
                case 3 -> {
                    changer.append(data.getPhoneNumber().trim());
                    int ch = (int) (Math.random() * (changer.length()-1));
                    if ((int) (Math.random() * 2) == 1) {
                        changer.insert((int) (Math.random() * changer.length()), (char) (number + '0'));
                        changer.deleteCharAt((int) (Math.random() * changer.length()));
                    } else {
                        changer.setCharAt(ch, data.getPhoneNumber().charAt(ch));
                        changer.setCharAt(ch + 1, data.getPhoneNumber().charAt(ch + 1));
                    }
                    data.setPhoneNumber(changer.toString());
                }
            }
        }
        return data;
    }
}