package net.transolyfer.transolyfergo.app.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.utils.ImageManager;

import java.io.ByteArrayOutputStream;

import static net.transolyfer.transolyfergo.app.utils.Constant.CODE_CONTAINER_ENTERPRISE;
import static net.transolyfer.transolyfergo.app.utils.Constant.CODE_URL;

public class DialogImage extends DialogFragment implements DialogInterface.OnShowListener {

    private ImageView ivPhoto;
    private ImageView ivClose;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.photo_layout, null);
        init(rootView);
        builder.setView(rootView);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }


    private void init(View rootView) {
        ivPhoto = rootView.findViewById(R.id.ivPhoto);
        ivClose = rootView.findViewById(R.id.ivClose);
        Bundle bundle = getArguments();
        String urlPhoto = bundle.getString(CODE_URL);

        String containerName = bundle.getString(CODE_CONTAINER_ENTERPRISE);
        String photoName = urlPhoto.replace("https://transolyferstorage.blob.core.windows.net/"+containerName+"/","");

        final ByteArrayOutputStream imageStream = new ByteArrayOutputStream();

        final Handler handler = new Handler();

        Thread th = new Thread(new Runnable() {
            public void run() {

                try {

                    long imageLength = 0;

                    ImageManager.GetImage(photoName,containerName, imageStream, imageLength);

                    handler.post(new Runnable() {

                        public void run() {
                            byte[] buffer = imageStream.toByteArray();

                            Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);

                            ivPhoto.setImageBitmap(bitmap);

                        }
                    });
                }
                catch(Exception ex) {
                    final String exceptionMessage = ex.getMessage();
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), exceptionMessage, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }});
        th.start();

        ivClose.setOnClickListener(v -> closeDialog());
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null){
            dialog.setCancelable(false);
        }
    }

    private void closeDialog(){
        dismiss();
    }


    private void loadImage(String urlImage,ImageView image){
        Picasso.with(getContext())
                .load(urlImage)
                .error( R.drawable.ic_info_orange )
                .placeholder( R.drawable.progress_animation )
                .resize(400,400)
                .centerCrop()
                .into(image);
    }
}
