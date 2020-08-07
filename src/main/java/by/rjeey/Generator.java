package by.rjeey;

import com.github.javafaker.Faker;

import java.util.Locale;

class Generator {

    public static void main(String[] args) {

        String country;
        int recordsCount;
        double mistakes;
        String name;
        int spaces;
        final StringBuilder sb = new StringBuilder();
        final Faker faker;

        if (args != null && args.length == 3 && args[1].matches("\\d+")) {
            country = args[0];
            recordsCount = Integer.parseInt(args[1]);
            mistakes = Double.parseDouble(args[2]);
        } else if (args != null && args.length == 2 && args[1].matches("\\d+")) {
            country = args[0];
            recordsCount = Integer.parseInt(args[1]);
            mistakes = 0;
        } else {
            throw new IllegalStateException("Unexpected value");
        }

        faker = new Faker(new Locale(country));

        String nameCountry = switch (country) {
            case "us" -> "USA, ";
            case "ru" -> "Россия, ";
            case "by" -> "Беларусь, ";
            default -> throw new IllegalStateException("Unexpected value: " + country);
        };

        if (mistakes < 1)
            mistakes *= recordsCount;

        for (int i = 0; i < recordsCount; ++i) {
            name = faker.name().name();
            spaces = name.length() - name.replaceAll(" ", "").length();
            sb.append(name);

            if (spaces != 2)
                sb.append(" ").append(faker.name().lastName());

            sb.append("; ")
                    .append(faker.address().zipCode()).append(", ")
                    .append(nameCountry)
                    .append(faker.address().streetName())
                    .append(country.equals("us") ? " " : " , д.")
                    .append(faker.random().nextInt(1, 2000))
                    .append(country.equals("us") ? ", " : ", кв.")
                    .append(faker.random().nextInt(1, 200)).append(", ")
                    .append(faker.address().state()).append("; ")
                    .append(faker.phoneNumber().phoneNumber());

            if (mistakes == 0)
                System.out.println(sb.toString());
            else if (i < mistakes)
                System.out.println(generateMisses(sb, faker.random().nextInt((int) mistakes), country, faker));
            else
                System.out.println(generateMisses(sb, mistakes, country, faker));
            sb.setLength(0);
        }
    }

    private static String generateMisses(StringBuilder changer, double mistakes, String local, Faker faker) {

        final char[] us = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        final char[] ru = ("\u0430\u0431\u0432\u0433\u0434\u0435\u0451\u0436\u0437\u0438\u0439\u043A\u043B\u043C" +
                "\u043D\u043E\u043F\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044A\u044B\u044C" +
                "\u044D\u044E\u044F\u0410\u0411\u0412\u0413\u0414\u0415\u0401\u0416\u0417\u0418\u0419\u041A\u041B" +
                "\u041C\u041D\u041E\u041F\u0420\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429\u042A\u042B" +
                "\u042C\u042D\u042E\u042F0123456789").toCharArray();
        final char[] by = ("\u0430\u0431\u0432\u0433\u0434\u0435\u0451\u0436\u0437\u0456\u0439\u043A\u043B\u043C" +
                "\u043D\u043E\u043F\u0440\u0441\u0442\u0443\u045E\u0444\u0445\u0446\u0447\u0448\u044B\u044C\u044D" +
                "\u044E\u044F\u0410\u0411\u0412\u0413\u0414\u0415\u0401\u0416\u0417\u0406\u0419\u041A\u041B\u041C" +
                "\u041D\u041E\u041F\u0420\u0421\u0422\u0423\u040E\u0424\u0425\u0426\u0427\u0428\u042B\u042C\u042D" +
                "\u042E\u042F00123456789").toCharArray();
        char x, y, letter;
        int ch, rn;

        if (changer != null && faker != null && local != null)
            for (int i = 0; i < mistakes; ++i) {
                rn = (local.equals("us")) ? faker.random().nextInt(us.length)
                        : (local.equals("ru")) ? faker.random().nextInt(ru.length)
                        : faker.random().nextInt(by.length);
                letter = switch (local) {
                    case "us" -> us[rn];
                    case "ru" -> ru[rn];
                    case "by" -> by[rn];
                    default -> throw new IllegalStateException("Unexpected value: " + local);
                };
                ch = (faker.random().nextInt(changer.length() - 1));

                if ((faker.random().nextInt(2)) == 1) {
                    changer.insert(ch, letter);
                    changer.deleteCharAt(faker.random().nextInt(changer.length() - 1));
                } else {
                    x = changer.charAt(ch);
                    y = changer.charAt(ch + 1);
                    changer.setCharAt(ch, y);
                    changer.setCharAt(ch + 1, x);
                }
            }
        return changer != null ? changer.toString() : "";
    }
}