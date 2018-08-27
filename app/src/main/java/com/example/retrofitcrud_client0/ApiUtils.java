package com.example.retrofitcrud_client0;

import android.content.Context;
import android.net.ConnectivityManager;

public class ApiUtils {
    //    public static final String API_URL = "https://booksreact.herokuapp.com";
//    public static final String API_URL = "https://spring-text-analizer.herokuapp.com";
    public static final String API_URL = "https://spring-boot-mysql-server-part0.herokuapp.com/";

    public ApiUtils() {
    }

    public static BookInterface getBookInterface(){
        return RetrofitClient.getBook(API_URL).create(BookInterface.class);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}