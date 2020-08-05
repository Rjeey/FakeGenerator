package by.rjeey;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

 class Generator {

    public static void main(String[] args) {
        String country;
        int records;
        double mistakes;

        if (args.length == 3) {
            country = args[0];
            records = Integer.parseInt(args[1]);
            mistakes = Double.parseDouble(args[2]);
        } else if (args.length == 2) {
            country = args[0];
            records = Integer.parseInt(args[1]);
            mistakes = 0;
        } else {
            System.out.println("The entered data is incorrect try checking by example (us, 1, 1) or (us, 1)");
            return;
        }

        generate(records, mistakes, country).forEach((k, v) -> System.out.println(v));
    }

    private static Map<Integer, String> generate(int countRecords, double mis, String local) {
        Map<Integer, String> records = new HashMap<>();
        Faker faker = new Faker(new Locale(local));
        StringBuilder sb = new StringBuilder();
        double finalMis, finalMisCount;

        for (int i = 0; i < countRecords; ++i) {
            records.put(i, sb.append(faker.name().nameWithMiddle()).append("; ")
                    .append(faker.address().fullAddress()).append("; ")
                    .append(faker.phoneNumber().phoneNumber()).toString());
            sb.setLength(0);
        }
        if (mis != 0) {
            if (mis < 1) {

                finalMis = mis *= countRecords;

                for (int j = 0; j < mis; ++j) {
                    records.forEach((k, v) -> v = generateMisses(v, finalMis, local));
                }
            }
             finalMisCount= mis;
            records.forEach((k, v) -> v = generateMisses(v, finalMisCount, local));
        }
        return records;
    }

    private static String generateMisses(String data, double misses, String local) {
        final char[] us = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        final char[] ru = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ0123456789".toCharArray();
        final char[] by = "абвгдеёжзійклмнопрстуўфхцчшыьэюяАБВГДЕЁЖЗІЙКЛМНОПРСТУЎФХЦЧШЫЬЭЮЯ00123456789".toCharArray();
        StringBuilder changer = new StringBuilder();
        IllegalStateException e = new IllegalStateException("Unexpected value: " + local);
        String rec = data;
        char x, y;
        int ch;

        for (int i = 0; i < misses; ++i) {
            changer.setLength(0);
            changer.append(rec);

            char letter = switch (local) {
                case "us" -> us[(int) (Math.random() * us.length)];
                case "ru" -> ru[(int) (Math.random() * ru.length)];
                case "by" -> by[(int) (Math.random() * by.length)];
                default -> throw e;
            };

            ch = (int) (Math.random() * (changer.length() - 1));

            if ((int) (Math.random() * 2) == 1) {
                changer.insert((int) (Math.random() * changer.length()), letter);
                changer.deleteCharAt((int) (Math.random() * changer.length()));
            }
            else {
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