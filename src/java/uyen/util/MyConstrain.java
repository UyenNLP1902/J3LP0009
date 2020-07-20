/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.util;

/**
 *
 * @author HP
 */
public class MyConstrain {

    //Login with google
    public static String GOOGLE_CLIENT_ID = "737862210716-n3p1k734evfp0ri9i1g99j3n4l4n2jb7.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "W7dSEUm3L4PXviVuAg7bJF_k";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8084/J3LP0009/login_google";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";

    //Re-captcha
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SITE_KEY = "6LdVA64ZAAAAAGiYGylK_ydL4EdzhenkeNPCkAT1";
    public static final String SECRET_KEY = "6LdVA64ZAAAAAMah8re2D4FAD7UnoihhokcAX76k";
    public static final String USER_AGENT = "Chrome/83.0.4103.116";
}
