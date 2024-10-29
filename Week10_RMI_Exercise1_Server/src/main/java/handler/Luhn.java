package handler;


public class Luhn {

    public static boolean validateCardNumber(String cardNumber) {
        if (cardNumber.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
        }

        return sum % 10 == 0;
    }

    public static String generateCardNumber() {
        StringBuilder cardNumberBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int digit = (int) (Math.random() * 10);
            cardNumberBuilder.append(digit);
        }

        int checkDigit = 0;
        for (int i = 0; i < 11; i++) {
            int digit = Character.getNumericValue(cardNumberBuilder.charAt(i));

            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            checkDigit += digit;
        }

        checkDigit = (10 - (checkDigit % 10)) % 10;
        cardNumberBuilder.append(checkDigit);

        return cardNumberBuilder.toString();
    }
}
