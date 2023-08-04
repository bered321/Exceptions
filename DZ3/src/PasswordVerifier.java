
public class PasswordVerifier {

    public static void verifyPassword(String password) throws IllegalArgumentException {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Пароль должен быть не менее 8 символов");
        }

        boolean containsDigit = false;
        boolean containsUppercase = false;

        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                containsDigit = true;
            }
            if (Character.isUpperCase(ch)) {
                containsUppercase = true;
            }
        }

        if (!containsDigit) {
            throw new IllegalArgumentException("Пароль должен содержать хотя бы одну цифру");
        }

        if (!containsUppercase) {
            throw new IllegalArgumentException("Пароль должен содержать хотя бы одну заглавную букву");
        }
    }

    public static void main(String[] args) {
        String password = "passw123";

        try {
            verifyPassword(password);
            System.out.println("Пароль прошел проверку");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
