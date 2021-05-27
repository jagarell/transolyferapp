package net.transolyfer.transolyfergo.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.transolyfer.transolyfergo.R;

/**
 * Created by sergio on 24/12/16.
 *
 * Clase utilitaria de dialogos
 */
public class DialogUtil {

    private OnDialogListener onDialogListener;
    private OnDialogForgortPasswordListener onDialogForgortPasswordListener;
    private OnDialogChangeTypeMapListener onDialogChangeTypeMapListener;

    public DialogUtil(OnDialogListener onDialogListener){
        this.onDialogListener = onDialogListener;
    }
    public DialogUtil(OnDialogForgortPasswordListener onDialogForgortPasswordListener){
        this.onDialogForgortPasswordListener = onDialogForgortPasswordListener;
    }
    public DialogUtil(OnDialogChangeTypeMapListener onDialogChangeTypeMapListener){
        this.onDialogChangeTypeMapListener = onDialogChangeTypeMapListener;
    }

    public interface OnDialogForgortPasswordListener {

        void onSendEmail(String email, RelativeLayout rlayLoading);
    }

    public Dialog createCustomDialog(String title, String message, Context context, final int index, boolean buttonAccept, boolean buttonCancel,
                                     boolean spannable){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(context, R.layout.custom_dialog, null);

        TextView tviMessage = (TextView) view.findViewById(R.id.tviMessage);
        TextView tviAccept = (TextView) view.findViewById(R.id.tviAccept);
        TextView tviCancel = (TextView) view.findViewById(R.id.tviCancel);

        if(tviMessage == null)tviMessage.setVisibility(View.GONE);
        if(!buttonAccept){
            tviAccept.setVisibility(View.GONE);
        }
        if(!buttonCancel){
            tviCancel.setVisibility(View.GONE);
        }

        tviAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogListener.onAccept(index);
            }
        });

        tviCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogListener.onCancel(index);
            }
        });

        if(spannable)
            tviMessage.setText(Html.fromHtml(message));
        else
            tviMessage.setText(message);

        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        return  dialog;
    }

    public interface OnDialogListener {

        void onAccept(int index);
        void onCancel(int index);
    }

    public interface OnDialogChangeTypeMapListener {


    }

}
