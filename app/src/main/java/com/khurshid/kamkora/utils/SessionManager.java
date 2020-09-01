package com.khurshid.kamkora.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.khurshid.kamkora.model.AddToCart;
import com.khurshid.kamkora.model.Order;
import com.khurshid.kamkora.model.User;

public class SessionManager {

    public static final String USERID = "userid";
    public static final String USEROBJECT = "userObject";
    public static final String TOKEN = "token";
    public static final String AUTH = "auth";
    public static final String CARTOBJECT = "CartObject";
    private static final String ORDEROBJECT = "orderObject";
    private static Context context;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void startSession(Context context, User user, String token) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user, User.class);
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().commit();
        editor.putString(SessionManager.USERID, user.getId());
        editor.putString(SessionManager.TOKEN, token);
        editor.putString(SessionManager.USEROBJECT, userJson);
        editor.commit();

    }

    public static void endSession() {
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().commit();
    }

    public static boolean isLoggedIn(Context context) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String id = sharedPreferences.getString(SessionManager.USERID, null);
            if (id != null) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static String getLoggedInUserId(Context context) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String id = sharedPreferences.getString(SessionManager.USERID, null);
            return id;
        }

        return null;
    }

    public static String getToken(Context context) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String token = sharedPreferences.getString(SessionManager.TOKEN, null);
            return token;
        }

        return null;
    }

    public static String getUserObjectJson(Context context) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String userJson = sharedPreferences.getString(SessionManager.USEROBJECT, null);
            return userJson;
        }

        return null;
    }

    public static void setOrderObjectJson(Context context, Order order) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(order, Order.class);
        editor.putString(SessionManager.ORDEROBJECT, json);
        editor.commit();
    }

    public static String getOrderobjectJson(Context context) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String json = sharedPreferences.getString(SessionManager.ORDEROBJECT, null);
            return json;
        }

        return null;
    }

    public static void setCartObjectJson(Context context, AddToCart addToCart) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(addToCart, AddToCart.class);
        editor.putString(SessionManager.CARTOBJECT, json);
        editor.commit();
    }

    public static String getCartObjectJson(Context context) {
        SessionManager.context = context;
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String json = sharedPreferences.getString(SessionManager.CARTOBJECT, null);
            return json;
        }

        return null;
    }

    public static void setUserObject(Context context, User user) {
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user, User.class);
        editor.putString(SessionManager.USEROBJECT, json);
        editor.commit();
    }

    public static User getUserObject(Context context) {
        sharedPreferences = context.getSharedPreferences(SessionManager.AUTH, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sharedPreferences != null) {
            String json = sharedPreferences.getString(SessionManager.USEROBJECT, null);
            User user = gson.fromJson(json, User.class);
            return user;
        }
        return null;
    }

}
