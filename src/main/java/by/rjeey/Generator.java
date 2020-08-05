package by.rjeey;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

class Generator {


    public static void main(String[] args) {
        String country;
        int recordsCount;
        double mistakes;
        Map<Integer, String> records = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        double finalMis, finalMisCount;

        if (args.length == 3) {
            country = args[0];
            recordsCount = Integer.parseInt(args[1]);
            mistakes = Double.parseDouble(args[2]);
        } else if (args.length == 2) {
            country = args[0];
            recordsCount = Integer.parseInt(args[1]);
            mistakes = 0;
        } else {
            System.out.println("The entered data is incorrect try checking by example (us, 1, 1) or (us, 1)");
            return;
        }
        Faker faker = new Faker(new Locale(country));
        for (int i = 0; i < recordsCount; ++i) {
            records.put(i, sb.append(faker.name().nameWithMiddle()).append("; ")
                    .append(faker.address().fullAddress()).append("; ")
                    .append(faker.phoneNumber().phoneNumber()).toString());
            sb.setLength(0);
        }
        if (mistakes != 0) {
            if (mistakes < 1) {
                finalMis = mistakes *= recordsCount;
                for (int j = 0; j < mistakes; ++j) {
                    records.forEach((k, v) -> v = generateMisses(v, finalMis, country));
                }
            }

            finalMisCount = mistakes;
            records.forEach((k, v) -> v = generateMisses(v, finalMisCount, country));
        }
        records.forEach((k, v) -> System.out.println(v));
    }

    private static String generateMisses(String data, double misses, String local) {
        final char[] us = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        final char[] ru = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ0123456789".toCharArray();
        final char[] by = "абвгдеёжзійклмнопрстуўфхцчшыьэюяАБВГДЕЁЖЗІЙКЛМНОПРСТУЎФХЦЧШЫЬЭЮЯ00123456789".toCharArray();
        StringBuilder changer = new StringBuilder();
        IllegalStateException e = new IllegalStateException("Unexpected value: " + local);
        String rec = data;
        char x, y, letter;
        int ch;
        Random rand = new Random();

        for (int i = 0; i < misses; ++i) {
            changer.setLength(0);
            changer.append(rec);

            letter = switch (local) {
                case "us" -> us[(int) (Math.random() * us.length)];
                case "ru" -> ru[(int) (Math.random() * ru.length)];
                case "by" -> by[(int) (Math.random() * by.length)];
                default -> throw e;
            };
            ch = (rand.nextInt(changer.length()));

            if ((rand.nextInt(2)) == 1) {
                changer.insert(rand.nextInt(changer.length()), letter);
                changer.deleteCharAt(rand.nextInt(changer.length()));
            } else {
                x = changer.charAt(ch);
                y = changer.charAt(ch + 1);
                changer.setCharAt(ch, y);
                changer.setCharAt(ch + 1, x);
            }
            rec = changer.toString();
        }
        return rec;
    }
}