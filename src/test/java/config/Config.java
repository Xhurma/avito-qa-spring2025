package config;

public class Config {
    public static final String BASE_URL = "https://qa-internship.avito.com/api/1";

    public static final String CREATE_ITEM = Config.BASE_URL + "/item";
    public static final String GET_ITEM_BY_ID = Config.BASE_URL + "/item/{id}";
    public static final String GET_ITEMS_BY_SELLER = Config.BASE_URL + "/{sellerID}/item";
    public static final String GET_STATISTICS_BY_ID = Config.BASE_URL + "/statistic/{id}";
}
