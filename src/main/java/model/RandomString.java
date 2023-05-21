package model;

public class RandomString {
    private static final String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

    public static String getRandomString(int n) {
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++)
            sb.append(string.charAt((int) (string.length() * Math.random())));

        return sb.toString();
    }
}
