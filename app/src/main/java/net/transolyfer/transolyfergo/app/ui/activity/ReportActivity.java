package net.transolyfer.transolyfergo.app.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.adapter.EnterpriseAdapter;
import net.transolyfer.transolyfergo.app.ui.adapter.EventIntranetAdapter;
import net.transolyfer.transolyfergo.app.ui.core.BaseCompatActivity;
import net.transolyfer.transolyfergo.app.ui.fragment.DialogImage;
import net.transolyfer.transolyfergo.app.utils.Constant;
import net.transolyfer.transolyfergo.app.utils.ImageUtils;
import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.data.entity.response.DetailIntranetResponse;
import net.transolyfer.transolyfergo.data.mapper.EventIntranetDataMapper;
import net.transolyfer.transolyfergo.domain.model.Enterprise;
import net.transolyfer.transolyfergo.domain.model.EventIntranet;
import net.transolyfer.transolyfergo.presenter.ReportIntranetPresenter;
import net.transolyfer.transolyfergo.presenter.view.ReportIntranetView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.transolyfer.transolyfergo.app.utils.Constant.CODE_CONTAINER_ENTERPRISE;
import static net.transolyfer.transolyfergo.app.utils.Constant.CODE_URL;

public class ReportActivity extends BaseCompatActivity implements ReportIntranetView {

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 100;

    private ReportIntranetPresenter presenter;
    private EventIntranetDataMapper eventIntranetDataMapper;
    private EventIntranetAdapter eventIntranetAdapter;
    private EnterpriseAdapter enterpriseAdapter;

    @BindView(R.id.spEnterprise)
    Spinner spEnterprises;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.tvFullName)
    TextView tvFullName;
    @BindView(R.id.vieLoading) View vieLoading;
    @BindView(R.id.rvReportList)
    RecyclerView rvReportList;
    @BindView(R.id.rlDetailReport)
    RelativeLayout rlDetailReport;
    @BindView(R.id.view)
    View viewSeparator;
    private int idEnterprise = 0;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhono)
    TextView tvPhono;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    String containerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        init();
        Bundle bundle = getIntent().getExtras();
        tvFullName.setText(bundle.getString(Constant.CODE_FULL_NAME));
    }

    public void init() {
        ButterKnife.bind(this);
        setupRecyclerView();

        presenter = new ReportIntranetPresenter();
        presenter.attachedView(this);
        presenter.getEnterprises();

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((!etCode.getText().toString().isEmpty() || !etCode.getText().toString().equals(""))
                        && spEnterprises.getSelectedItemPosition()!=0){
                    ivSearch.setEnabled(true);
                    ImageUtils.setTint(ivSearch, R.color.colorOrange1,getContext());
                }else {
                    ivSearch.setEnabled(false);
                    ImageUtils.setTint(ivSearch, R.color.colorLightGray,getContext());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.ivSearch)
    public void doSearch(){
        presenter.showDetailIntranet(generateData());
    }

    public OrderDetailIntranet generateData(){
        OrderDetailIntranet orderDetailIntranet = new OrderDetailIntranet();
        orderDetailIntranet.setOrderNumber(etCode.getText().toString());
        orderDetailIntranet.setEnterpriseId(String.valueOf(idEnterprise));
        return orderDetailIntranet;
    }
    @Override
    public void showLoading() {
        vieLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        vieLoading.setVisibility(View.GONE);
    }

    @Override
    public void getEnterprisesSuccess(Object object) {
        List<Enterprise> enterpriseList = (List<Enterprise>) object;
        fillDataSpinnerEnterprises(enterpriseList);
    }

    public void fillDataSpinnerEnterprises(final List<Enterprise> enterpriseList){
        enterpriseAdapter = new EnterpriseAdapter(enterpriseList, ReportActivity.this);
        spEnterprises.setAdapter(enterpriseAdapter);
        spEnterprises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idEnterprise = enterpriseList.get(position).getEnterpriseId();
                containerName = enterpriseList.get(position).getEnterpriseContainer();

                if((!etCode.getText().toString().isEmpty() || !etCode.getText().toString().equals(""))
                        && spEnterprises.getSelectedItemPosition()!=0){
                    ivSearch.setEnabled(true);
                    ImageUtils.setTint(ivSearch, R.color.colorOrange1,getContext());
                }else {
                    ivSearch.setEnabled(false);
                    ImageUtils.setTint(ivSearch, R.color.colorLightGray,getContext());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void reportSuccess(Object object) {
        DetailIntranetResponse detailIntranetResponse = (DetailIntranetResponse) object;
        eventIntranetDataMapper = new EventIntranetDataMapper();
        List<EventIntranet> eventIntranetList = eventIntranetDataMapper.transformEventIntranetList(detailIntranetResponse.getEventList());
        viewSeparator.setVisibility(View.VISIBLE);
        rlDetailReport.setVisibility(View.VISIBLE);
        tvName.setText(detailIntranetResponse.getConsultingEnterprise());
        tvPhono.setText(detailIntranetResponse.getPhoneNumber());
        tvAddress.setText(detailIntranetResponse.getAddress());
        tvNumber.setText(String.valueOf(detailIntranetResponse.getPieceNumber()));
        if(!(eventIntranetList.size() > 0)) {
            showMessage(getString(R.string.message_no_element));
            rvReportList.setVisibility(View.GONE);
        }else{
            setupAdapter(eventIntranetList);
            rvReportList.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvReportList.setLayoutManager(linearLayoutManager);
    }

    private void setupAdapter(List<EventIntranet> list){
        eventIntranetAdapter = new EventIntranetAdapter( list, this, ReportActivity.this);
        rvReportList.setAdapter(eventIntranetAdapter);
    }

    @Override
    public void showMessage(String message) {
        rlDetailReport.setVisibility(View.GONE);
        rvReportList.setVisibility(View.GONE);
        viewSeparator.setVisibility(View.GONE);
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReportActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Aviso");
        dialog.setMessage(message);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    @Override
    public void showPhoto(String urlPhoto) {
        //showDialogIamge(urlPhoto);
        new DownloadFile().execute(urlPhoto);

    }

    public void showDialogIamge(String urlPhoto){
       DialogImage dialog = new DialogImage();
        Bundle bundle = new Bundle();
        bundle.putString(CODE_URL, urlPhoto);
        bundle.putString(CODE_CONTAINER_ENTERPRISE,containerName);//parameters are (key, value).
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public Context getContext() {
        return this;
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
                    Toast.makeText(ReportActivity.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ReportActivity.this, "Ingrese número telefónico", Toast.LENGTH_SHORT).show();
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
    //Method to Check If SD Card is mounted or not
    public static boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(ReportActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "Transolyfer/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Imagen descargada en la carpeta Transolyfer";

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Problemas al descargar imagen";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
        }
    }

}
