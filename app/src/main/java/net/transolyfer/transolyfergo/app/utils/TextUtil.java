package net.transolyfer.transolyfergo.app.utils;

import android.text.Html;
import android.util.Patterns;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by sergio on 24/12/16.
 *
 * Clase utilitaria de dialogos
 */
public class TextUtil {

    public static void setTextWithSpannable(boolean spannable, TextView textView, String message){
        if(spannable){
            textView.setText(Html.fromHtml(message));
        } else{
            textView.setText(message);
        }
    }

    public static boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
