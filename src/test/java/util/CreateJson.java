package utils;

public class CreateJson {
    public static String create(int sellerID, String name, int price, int contacts,
                                int likes, int viewCount) {
        return String.format("{\"sellerID\":%d, \"name\":\"%s\", \"price\":%d, " +
                        "\"statistics\":{\"contacts\":%d, \"likes\":%d, \"viewCount\":%d}}",
                sellerID, name, price, contacts, likes, viewCount);
    }
}
