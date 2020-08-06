package by.rjeey;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

class Generator {


    public static void main(String[] args) {

        String country;
        int recordsCount;
        double mistakes;
        StringBuilder sb = new StringBuilder();
        if (args.length == 3) {
            country = args[0];
            recordsCount = Integer.parseInt(args[1]);
            mistakes = Double.parseDouble(args[2]);
        } else if (args.length == 2) {
            country = args[0];
            recordsCount = Integer.parseInt(args[1]);
            mistakes = 0;
        } else {
            System.err.println("The entered data is incorrect try checking by example (us, 1, 1) or (us, 1)");
            return;
        }
        String nameCountry = switch (country) {
            case "us" -> "USA, ";
            case "ru" -> "Россия, ";
            case "by" -> "Беларусь, ";
            default -> throw new IllegalStateException("Unexpected value: " + country);
        };
        String name;
        int spaces;
        Faker faker = new Faker(new Locale(country));
        for (int i = 0; i < recordsCount; ++i) {
            name = faker.name().name();
            spaces = name.length() - name.replaceAll(" ", "").length();
            sb.append(name);
            if (spaces != 2) {
                sb.append(" ").append(faker.name().lastName());
            }
            sb.append("; ")
                    .append(faker.address().zipCode()).append(", ")
                    .append(nameCountry)
                    .append(faker.address().streetName())
                    .append(country.equals("us") ? " " : " , д.")
                    .append(faker.random().nextInt(2000))
                    .append(country.equals("us") ? ", " : ", кв.")
                    .append(faker.random().nextInt(1, 200)).append(", ")
                    .append(faker.address().state()).append("; ")
                    .append(faker.phoneNumber().phoneNumber());
            if (mistakes != 0) {
                if (mistakes < 1 && i < mistakes) {
                    mistakes *= recordsCount;
                    System.out.println(generateMisses(sb.toString(), faker.random().nextInt((int) mistakes), country));
                }
                System.out.println(generateMisses(sb.toString(), mistakes, country));
            } else
                System.out.println(sb.toString());
            sb.setLength(0);
        }
    }

    private static String generateMisses(String data, double mistakes, String local) {
        final char[] us = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        final char[] ru = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ0123456789".toCharArray();
        final char[] by = "абвгдеёжзійклмнопрстуўфхцчшыьэюяАБВГДЕЁЖЗІЙКЛМНОПРСТУЎФХЦЧШЫЬЭЮЯ00123456789".toCharArray();
        StringBuilder changer = new StringBuilder();
        Random rand = new Random();
        char x, y, letter;
        int ch;
        changer.setLength(0);
        changer.append(data);

        for (int i = 0; i < mistakes; ++i) {
            letter = switch (local) {
                case "us" -> us[(rand.nextInt(us.length))];
                case "ru" -> ru[(rand.nextInt(ru.length))];
                case "by" -> by[(rand.nextInt(by.length))];
                default -> throw new IllegalStateException("Unexpected value: " + local);
            };
            ch = (rand.nextInt(changer.length() - 1));

            if ((rand.nextInt(2)) == 1) {
                changer.insert(rand.nextInt(changer.length() - 1), letter);
                changer.deleteCharAt(rand.nextInt(changer.length() - 1));
            } else {
                x = changer.charAt(ch);
                y = changer.charAt(ch + 1);
                changer.setCharAt(ch, y);
                changer.setCharAt(ch + 1, x);
            }
        }
        return changer.toString();
    }
}