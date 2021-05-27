package net.transolyfer.transolyfergo.app.ui.components;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.util.AttributeSet;

/**
 * Created by OnlineStudioProductions on 22/03/2016.
 */
public class CustomCheckBox extends AppCompatCheckBox {

    public CustomCheckBox(Context context) {
        super(context);
        init(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyle) {
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
                case 7: fuente = "fonts/nunito_semibold.ttf";break;
                case 8: fuente = "fonts/flexo_medium.otf";break;
                case 9: fuente = "fonts/nunito_extrabold.ttf";break;
            }
        }catch (Exception e){
            fuente = "fonts/rubik_regular.ttf";
        }

        Typeface tf = Typeface.createFromAsset(context.getAssets(), fuente);
        setTypeface(tf);
    }
    public void changeTypeFace(int type){
        String fuente = "";
        try {
            switch (type){
                case 1: fuente = "fonts/rubik_regular.ttf";break;
                case 2: fuente = "fonts/flexo_regular.otf";break;
                case 3: fuente = "fonts/nunito_regular.ttf";break;
                case 4: fuente = "fonts/flexo_bold.otf";break;
                case 5: fuente = "fonts/nunito_bold.ttf";break;
            }
        }catch (Exception e){
            fuente = "fonts/rubik_regular.ttf";
        }

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), fuente);
        setTypeface(tf);
    }
}
