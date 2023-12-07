package camila.davi.isabelly.yasmin.domos.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {
    // endereço base do servidor web
    public static String DOMOS_APP_URL = "https://domos1-c4ajptt0.b4a.run/html/mobile/";

    /**
     * Salva o login no espaço reservado da app
     * @param context contexto da app
     * @param login o login
     */
    public static void setLogin(Context context, String login) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("login", login).commit();
    }

    /**
     * Obtem o login
     * @param context
     * @return
     */
    public static String getLogin(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        return mPrefs.getString("login", "");
    }

    /**
     * @param context
     * @param password
     */
    public static void setPassword(Context context, String password) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("password", password).commit();
    }

    /**
     * @param context
     * @return
     */
    public static String getPassword(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        return mPrefs.getString("password", "");
    }

    public static void setNivelPermissao(Context context,  int nivelPermissao) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("nivelPermissao", nivelPermissao).commit();
    }
    public static int getNivelPermissao(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        return mPrefs.getInt("nivelPermissao", 0);
    }
}


