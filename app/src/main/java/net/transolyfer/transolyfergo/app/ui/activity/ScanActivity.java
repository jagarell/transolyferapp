package net.transolyfer.transolyfergo.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import net.transolyfer.transolyfergo.R;

/**
 * Created by jose.arellano on 19/06/2017.
 */

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private ImageView btn_flash;
    private boolean turned;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scan);
        mScannerView = (ZXingScannerView) findViewById(R.id.zxscan);
        btn_flash = (ImageView) findViewById(R.id.btn_flash);
        turned = false;
        btn_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turned){
                    turnOffFlash();
                    turned = false;
                }else{
                    turnOnFlash();
                    turned = true;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        intent.putExtra("code",result.getText());
        setResult(RESULT_OK,intent);
        finish();
    }

    private void turnOnFlash(){
        mScannerView.setFlash(true);
    }

    private void turnOffFlash(){
        mScannerView.setFlash(false);
    }
}
