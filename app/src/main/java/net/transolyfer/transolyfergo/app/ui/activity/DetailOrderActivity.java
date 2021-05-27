package net.transolyfer.transolyfergo.app.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.core.BaseCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.transolyfer.transolyfergo.app.utils.Constant.CODE_LATITUD;
import static net.transolyfer.transolyfergo.app.utils.Constant.CODE_LONGITUD;

    public class DetailOrderActivity extends BaseCompatActivity {

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 100;
    @BindView(R.id.vieLoading) View vieLoading;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvPhono) TextView tvPhono;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvNumber) TextView tvNumber;
    @BindView(R.id.btnShowUbication)
    Button btnShowUbication;
    private String latitudDetail;
    private String longitudDetail;
    private int numberPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        tvName.setText(bundle.getString("name_detail"));
        tvPhono.setText(bundle.getString("phone_detail"));
        tvAddress.setText(bundle.getString("address_detail"));
        latitudDetail = bundle.getString("latitud_detail");
        longitudDetail = bundle.getString("longitud_detail");
        tvNumber.setText(String.valueOf(bundle.getInt("pieza_detail")));

        if(latitudDetail.equals("") || longitudDetail.equals("") || latitudDetail.isEmpty() || longitudDetail.isEmpty()){
            btnShowUbication.setEnabled(false);
            btnShowUbication.setBackground(getResources().getDrawable(R.drawable.rounded_bottom_gray));
        }else {
            btnShowUbication.setEnabled(true);
            btnShowUbication.setBackground(getResources().getDrawable(R.drawable.rounded_bottom_orange));
        }
    }
    @OnClick(R.id.ivBack)
    public void goBack(){
        onBackPressed();
    }


    @OnClick(R.id.ivCall)
    public void callPhone(){

        if (checkPermission(Manifest.permission.CALL_PHONE)) {

            if (!TextUtils.isEmpty(tvPhono.getText().toString())) {
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + tvPhono.getText().toString();
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DetailOrderActivity.this, "Ingrese número telefónico", Toast.LENGTH_SHORT).show();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE :
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    if (checkPermission(Manifest.permission.CALL_PHONE)) {
                        String dial = "tel:" + tvPhono.getText().toString();
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        Log.e("PERMISO: ","onRequesPermissión_Request_CODE");
                    }
                }
                return;
        }
    }


    @OnClick(R.id.btnShowUbication)
    public  void showUbicationMap(){
        Bundle bundle = new Bundle();
        bundle.putString(CODE_LATITUD, latitudDetail);
        bundle.putString(CODE_LONGITUD, longitudDetail);
        nextData(MapActivity.class, bundle, true);
    }
}
