package net.transolyfer.transolyfergo.app.ui.components;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

public class CustomEditText extends AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);
        init(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        String fuente = "";
        try {
            switch (Integer.valueOf(String.valueOf(getTag()))){
                case 1: fuente = "fonts/rubik_regular.ttf";break;
                case 2: fuente = "fonts/flexo_regular.otf";break;
                case 3: fuente = "fonts/nunito_regular.ttf";break;
                case 4: fuente = "fonts/flexo_bold.otf";break;
                case 5: fuente = "fonts/nunito_bold.ttf";break;
                case 6: fuente = "fonts/flexo_demi.otf";break;
            }
        }catch (Exception e){
            fuente = "fonts/rubik_regular.ttf";
        }

        Typeface tf = Typeface.createFromAsset(context.getAssets(), fuente);
        setTypeface(tf);
    }
}
