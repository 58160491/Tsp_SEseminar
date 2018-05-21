package com.palapol.tsp_sesaminar_team02.remote;

public class ApiUtils {

    public static final String BASE_URL = "https://se.informatics.buu.ac.th/seminar/";
    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
