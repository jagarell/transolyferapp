package net.transolyfer.transolyfergo.app.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import net.transolyfer.transolyfergo.BuildConfig;
import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.adapter.EnterpriseAdapter;
import net.transolyfer.transolyfergo.app.ui.adapter.EventsAdapter;
import net.transolyfer.transolyfergo.app.ui.core.BaseCompatActivity;
import net.transolyfer.transolyfergo.app.utils.CompressImageHandler;
import net.transolyfer.transolyfergo.app.utils.DialogUtil;
import net.transolyfer.transolyfergo.app.utils.FileCompressor;
import net.transolyfer.transolyfergo.app.utils.ImageUtils;
import net.transolyfer.transolyfergo.app.utils.LocationHelper;
import net.transolyfer.transolyfergo.app.utils.NetworkUtils;
import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.data.entity.response.DetailOrderResponse;
import net.transolyfer.transolyfergo.domain.model.Enterprise;
import net.transolyfer.transolyfergo.domain.model.Event;
import net.transolyfer.transolyfergo.domain.model.Parameter;
import net.transolyfer.transolyfergo.presenter.DetailOrderPresenter;
import net.transolyfer.transolyfergo.presenter.RegisterPresenter;
import net.transolyfer.transolyfergo.presenter.view.DetailView;
import net.transolyfer.transolyfergo.presenter.view.RegisterView;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseCompatActivity implements RegisterView, PopupMenu.OnMenuItemClickListener, IPhotoFlag, DetailView {

    private static final String TAG = "RegisterActivity";

    private final static int REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_PHONE_STATE = 500;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2000;
    private static final int PERMISSION_REQUEST_FOR_TAKE_PHOTO_CODE = 200;
    private static final int PERMISSION_REQUEST_FOR_SAVE_PICTURES = 300;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static String POPUP_CONSTANT = "mPopup";
    private static String POPUP_FORCE_SHOW_ICON = "setForceShowIcon";
    private Dialog dialog;
    private DialogUtil dialogUtil;
    private String photoFlag;
    private String obsFlag;
    private String photoCargo = "";
    private String photoReference = "";
    private int photoID;
    private String latitudUbication;
    private String longitudUbication;
    private Uri imageUri = null;
    private Uri imageUri2 = null;
    public static String imagePath1;
    public static String imagePath2;
    private DetailOrderPresenter detailOrderPresenter;
    File mPhotoFile;
    FileCompressor mCompressor;

    private static final int REQUEST_PERMISSIONS_LOCATION_SETTINGS_REQUEST_CODE = 33;
    private static final int REQUEST_PERMISSIONS_LAST_LOCATION_REQUEST_CODE = 34;
    private static final int REQUEST_PERMISSIONS_CURRENT_LOCATION_REQUEST_CODE = 35;
    private static final int REQUEST_PERMISSIONS_PHONE_STATE = 36;

    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    LocationRequest locationRequest;
    Location currentLocation = null;

    @BindView(R.id.spEvents)
    Spinner spEvents;
    @BindView(R.id.spEnterprise)
    Spinner spEnterprises;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.etObservation)
    EditText etObservation;
    @BindView(R.id.vieLoading)
    View vieLoading;
    @BindView(R.id.ivPendings)
    ImageView ivPendings;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.ivPhoto1)
    ImageView ivPhoto1;
    @BindView(R.id.ivPhotoCargo)
    ImageView ivPhotoCargo;
    @BindView(R.id.btnShowDetail)
    Button btnShowDetail;

    @BindView(R.id.cboOffline)
    CheckBox cboOffline;


    @BindView(R.id.tvVersion)
    TextView tvVersion;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefsOffline";
    private static final String KEY_OFFLINE = "offline";

    private OrderRegister orderRegister;
    private OrderValidate orderValidate;
    private RegisterPresenter registerPresenter;
    private EventsAdapter eventsAdapter;
    private EnterpriseAdapter enterpriseAdapter;
    private int idNationality = 0;
    private int idEnterprise = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        loadData();
        tvVersion.setText(BuildConfig.VERSION_NAME);
        cboOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean(KEY_OFFLINE, true);
                    editor.apply();
                } else {
                    editor.putBoolean(KEY_OFFLINE, false);
                    editor.apply();
                }
            }
        });
    }

    public void init() {
        ButterKnife.bind(this);
        mCompressor = new FileCompressor(getContext());
        registerPresenter = new RegisterPresenter();
        registerPresenter.attachedView(this);
        registerPresenter.getParameters();
        if (NetworkUtils.isOnline(RegisterActivity.this) || !cboOffline.isChecked()) {
            registerPresenter.getEvents();
            registerPresenter.getEnterprises();
        } else {
            transformNationalityOfflineList();
            transformEnterpriseOfflineList();
            btnShowDetail.setBackground(getResources().getDrawable(R.drawable.rounded_bottom_gray));
            btnShowDetail.setEnabled(false);
        }
        registerPresenter.valitePendings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean readExternalStogeIsGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
            boolean cameraIdGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
            if (!readExternalStogeIsGranted || !cameraIdGranted) {
                String askPermissions[];
                if (!readExternalStogeIsGranted && !cameraIdGranted) {
                    askPermissions = new String[2];
                    askPermissions[0] = Manifest.permission.CAMERA;
                    askPermissions[1] = Manifest.permission.READ_PHONE_STATE;
                } else {
                    askPermissions = new String[1];
                    if (!readExternalStogeIsGranted)
                        askPermissions[0] = Manifest.permission.READ_PHONE_STATE;
                    if (!cameraIdGranted)
                        askPermissions[0] = Manifest.permission.CAMERA;
                }
                requestPermissions(askPermissions, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkForLocationRequest();
        checkForLocationSettings();

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((!etCode.getText().toString().isEmpty() || !etCode.getText().toString().equals(""))
                        && spEnterprises.getSelectedItemPosition() != 0
                        && spEvents.getSelectedItemPosition() != 0 && (NetworkUtils.isOnline(RegisterActivity.this) || !cboOffline.isChecked())) {
                    ivPhoto.setEnabled(true);
                    ImageUtils.setTint(ivPhoto, R.color.colorRed3, getContext());
                } else {
                    ivPhoto.setEnabled(false);
                    ImageUtils.setTint(ivPhoto, R.color.colorLightGray, getContext());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadData() {
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(KEY_OFFLINE, false))
            cboOffline.setChecked(true);
        else
            cboOffline.setChecked(false);
    }

    public void checkForLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @OnClick(R.id.btnRegister)
    public void registerOrder() {

        checkForLocationRequest();
        checkForLocationSettings();

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int status = googleAPI.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                googleAPI.showErrorDialogFragment(RegisterActivity.this, status, 1);

            } else {
                googleAPI.showErrorDialogFragment(RegisterActivity.this, status, 1);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LocationHelper.REQUEST_CODE_LOCATION);
            } else {
                showLoading();
                callLastKnownLocation();
            }
        }
    }

    private void requestPermissions(final int requestCode) {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (shouldProvideRationale) {
            showSnackbar("Permiso de ubicación debe activarse.", "Ok",
                    view -> {
                        startLocationPermissionRequest(requestCode);
                    });
        } else {
            startLocationPermissionRequest(requestCode);
        }
    }

    private void showSnackbar(final String mainTextString, final String actionString,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(actionString, listener).show();
    }

    private void startLocationPermissionRequest(int requestCode) {
        ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
    }

    public void callLastKnownLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                requestPermissions(REQUEST_PERMISSIONS_LAST_LOCATION_REQUEST_CODE);
                return;
            }
            getLastLocation();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastLocation = task.getResult();
                        Log.i("val", mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude());
                        hideLoading();
                        latitudUbication = String.valueOf(mLastLocation.getLatitude());
                        longitudUbication = String.valueOf(mLastLocation.getLongitude());
                        if (etCode.getText().toString().isEmpty() || etCode.getText().toString().equals("")) {
                            showMessage("Debe insertar el Código de la guía");
                        } else if (spEnterprises.getSelectedItemPosition() == 0) {
                            showMessage("Debe seleccionar Empresa");
                        } else if (spEvents.getSelectedItemPosition() == 0) {
                            showMessage("Debe seleccionar Evento");
                        } else if (obsFlag.equals("1") && etObservation.getText().toString().equals("")) {
                            showMessage("Debe agregar observación");
                        } else if (photoFlag.equals("1") && imageUri == null &&
                                (NetworkUtils.isOnline(RegisterActivity.this) && !cboOffline.isChecked())) {
                            showMessage("Debe tomar foto");
                        } else {
                            if (NetworkUtils.isOnline(RegisterActivity.this) && !cboOffline.isChecked()) {
                                showLoading();
                                orderRegister = generateRegisterData();
                                validateOrder();
                            } else {
                                orderRegister = generateRegisterData();
                                registerPresenter.registerPlantOffline(orderRegister, 1);
                            }
                        }
                    } else {
                        callCurrentLocation();
                    }
                });
    }

    private void validateOrder() {
        if (idEnterprise == 1 && etCode.getText().toString().trim().length() > 8) {
            orderValidate = generateValidateData();
            registerPresenter.doValidate(orderValidate);
        } else {
            orderRegister = generateRegisterData();
            registerPresenter.doRegister(orderRegister);
        }
    }

    private void clearFolder() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/net.transolyfer.transolyfergo/files/Pictures");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void callCurrentLocation() {
        try {
            if (
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(REQUEST_PERMISSIONS_CURRENT_LOCATION_REQUEST_CODE);
                return;
            }

            hideLoading();

            mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    currentLocation = locationResult.getLastLocation();

                    Log.i("val", currentLocation.getLatitude() + ", " + currentLocation.getLongitude());
                    hideLoading();
                    latitudUbication = String.valueOf(currentLocation.getLatitude());
                    longitudUbication = String.valueOf(currentLocation.getLongitude());

                    if (etCode.getText().toString().isEmpty() || etCode.getText().toString().equals("")) {
                        showMessage("Debe insertar el Código de la guía");
                    } else if (spEnterprises.getSelectedItemPosition() == 0) {
                        showMessage("Debe seleccionar Empresa");
                    } else if (spEvents.getSelectedItemPosition() == 0) {
                        showMessage("Debe seleccionar Evento");
                    } else if (obsFlag.equals("1") && etObservation.getText().toString().equals("")) {
                        showMessage("Debe agregar observación");
                    } else if (photoFlag.equals("1") && imageUri == null &&
                            (NetworkUtils.isOnline(RegisterActivity.this) && !cboOffline.isChecked())) {
                        showMessage("Debe tomar foto");
                    } else {
                        if (NetworkUtils.isOnline(RegisterActivity.this) && !cboOffline.isChecked()) {
                            showLoading();
                            orderRegister = generateRegisterData();
                            validateOrder();
                        } else {
                            orderRegister = generateRegisterData();
                            registerPresenter.registerPlantOffline(orderRegister, 1);
                        }
                    }

                }
            }, Looper.myLooper());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnClick(R.id.ivPhoto1)
    public void takePhoto1() {
        photoID = 0;
        requestStoragePermission();
        //openCameraToTakePhotoWithPermissions();
    }

    @OnClick(R.id.ivPhotoCargo)
    public void takePhoto2() {
        photoID = 1;
        requestStoragePermission();
        //openCameraToTakePhotoWithPermissions();
    }

    @OnClick(R.id.btnShowDetail)
    public void goToDetail() {
        if (etCode.getText().toString().isEmpty() || etCode.getText().toString().equals("")) {
            showMessage("Debe insertar el Código de la guía");
        } else if (spEnterprises.getSelectedItemPosition() == 0) {
            showMessage("Debe seleccionar Empresa");
        } else {

            detailOrderPresenter = new DetailOrderPresenter();
            detailOrderPresenter.attachedView(this);
            detailOrderPresenter.showDetail(generateData());
        }
    }

    public OrderDetail generateData() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderNumber(etCode.getText().toString());
        orderDetail.setEnterpriseId(idEnterprise);
        return orderDetail;
    }



    private void requestStoragePermission() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            takePicture();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> showMessage("Ocurrió un error"))
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Aceptar permisos");
        builder.setMessage("Esta aplicación necesita permisos para utilizar esta función. Puede otorgarlos en la configuración de la aplicación.");
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    public void takePicture() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    if(photoID==1) {
                        imageUri = FileProvider.getUriForFile(getContext(),
                                BuildConfig.APPLICATION_ID + ".provider",
                                photoFile);
                        mPhotoFile = photoFile;
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    }else{
                        imageUri2 = FileProvider.getUriForFile(getContext(),
                                BuildConfig.APPLICATION_ID + ".provider",
                                photoFile);
                        mPhotoFile = photoFile;
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri2);
                    }
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.ibSync)
    public void showPopupMenu() {
        if (NetworkUtils.isOnline(RegisterActivity.this) && !cboOffline.isChecked()) {
            registerPresenter.getOrdersNotSendedToServer();
        } else {
            showMessage(getString(R.string.s_not_conecction));
        }
    }

    @OnClick(R.id.ibIntranet)
    public void goToIntranet() {
        if (!NetworkUtils.isOnline(RegisterActivity.this) && cboOffline.isChecked()) {
            showMessage("Solo funciona para ONLINE");
        } else {
            nextActivity(IntranetActivity.class, true);
        }
    }

    @OnClick(R.id.ivPendings)
    public void showRegisterOffline() {
        registerPresenter.getTotalDataOffline();
    }

    @OnClick(R.id.btnScan)
    public void ScanOrder() {
        Intent intent = new Intent(RegisterActivity.this, ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        //scanBarcodeFrontCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showMessagePermission(getString(R.string.s_permissions));
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showMessagePermission(getString(R.string.s_permissions));
                }
                break;
            }
            case REQUEST_PERMISSIONS_LAST_LOCATION_REQUEST_CODE: {
                if (grantResults.length <= 0) {
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }
            }

            case REQUEST_PERMISSIONS_CURRENT_LOCATION_REQUEST_CODE: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showMessagePermission(getString(R.string.s_permissions));
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callCurrentLocation();
                }
            }

            case PERMISSION_REQUEST_FOR_TAKE_PHOTO_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                }
                break;
            }
        }
    }

    public OrderRegister generateRegisterData() {
        String deviceId;
        String code = etCode.getText().toString();
        String observation = etObservation.getText().toString();
        deviceId = getUniqueID();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        OrderRegister orderRegister = new OrderRegister();
        orderRegister.setOrderNumber(code.isEmpty() ? "":code);
        orderRegister.setObservation(observation.isEmpty() ? "":observation);
        orderRegister.setPhoneId(deviceId);
        orderRegister.setEventId(idNationality);
        orderRegister.setEnterpriseId(idEnterprise);
        orderRegister.setEventDate(date);
        orderRegister.setLatitud(latitudUbication);
        orderRegister.setLongitud(longitudUbication);
        orderRegister.setImageCargo(photoCargo);
        orderRegister.setImageReference(photoReference);
        return orderRegister;
    }

    @SuppressLint("MissingPermission")
    public String getUniqueID(){
        String myAndroidDeviceId = "";
        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null){
            myAndroidDeviceId = mTelephony.getDeviceId();
        }else{
            myAndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return myAndroidDeviceId;
    }

    public OrderValidate generateValidateData(){
        String code = etCode.getText().toString();
        OrderValidate orderValidate = new OrderValidate();
        orderValidate.setOrderNumber(code.isEmpty() ? "":code);
        orderValidate.setIdEnterprise(1);
        return orderValidate;
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
    public void detailSuccess(Object object) {
        List<DetailOrderResponse> detailOrderResponse = (List<DetailOrderResponse>) object;
        Bundle bundle = new Bundle();
        bundle.putString("name_detail",detailOrderResponse.get(0).getConsultingEnterprise());
        bundle.putString("phone_detail",detailOrderResponse.get(0).getContactNumber());
        bundle.putString("address_detail",detailOrderResponse.get(0).getAddress());
        bundle.putString("latitud_detail",detailOrderResponse.get(0).getLatitud());
        bundle.putString("longitud_detail",detailOrderResponse.get(0).getLongitud());
        bundle.putInt("pieza_detail",detailOrderResponse.get(0).getPieceNumber());
        nextData(DetailOrderActivity.class, bundle, true);
    }

    @Override
    public void registerSuccess() {
        etCode.setText("");
        etObservation.setText("");
        spEvents.setSelection(0);
        photoCargo = "";
        photoReference = "";
        imageUri = null;
        imageUri2 = null;
        ivPhotoCargo.setImageResource(R.drawable.placeholder_photo);
        ivPhoto1.setImageResource(R.drawable.placeholder_photo);
        Toast.makeText(this,R.string.s_send,Toast.LENGTH_LONG).show();
        clearFolder();
    }

    @Override
    public void validateCodeSucess(Object object) {
        String validateCode = (String)object;
        etCode.setText(validateCode);
        if(imageUri!=null || imageUri2 !=null) {
            showLoading();
            String name = etCode.getText().toString() + "_" + idNationality + ".jpg";
        }else{
            orderRegister = generateRegisterData();
            registerPresenter.doRegister(orderRegister);
        }
    }

    @Override
    public void syncSuccess() {
        ivPendings.setImageResource(R.drawable.ic_info);
    }

    @Override
    public void getNationalitiesSuccess(Object object) {
        List<Event> eventList = (List<Event>) object;
        fillDataSpinnerNationalities(eventList);
    }

    @Override
    public void getEnterprisesSuccess(Object object) {
        List<Enterprise> enterpriseList = (List<Enterprise>) object;
        fillDataSpinnerEnterprises(enterpriseList);
    }

    @Override
    public void getParameterSuccess(Object object) {
        List<Parameter> parameterList = (List<Parameter>) object;
        setEditTextMaxLength(etCode,Integer.parseInt(parameterList.get(0).getParameterValue()));
    }

    public void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

    public void fillDataSpinnerNationalities(final List<Event> eventList){
        eventsAdapter = new EventsAdapter(eventList, RegisterActivity.this,this);
        spEvents.setAdapter(eventsAdapter);
        spEvents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idNationality = eventList.get(position).getIdNationality();

                if((!etCode.getText().toString().isEmpty() || !etCode.getText().toString().equals(""))
                        && spEnterprises.getSelectedItemPosition()!=0
                        && spEvents.getSelectedItemPosition()!=0 &&
                        (NetworkUtils.isOnline(RegisterActivity.this) && !cboOffline.isChecked())){
                    ivPhoto.setEnabled(true);
                    ImageUtils.setTint(ivPhoto, R.color.colorRed3,getContext());
                }else {
                    ivPhoto.setEnabled(false);
                    ImageUtils.setTint(ivPhoto, R.color.colorLightGray,getContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void transformNationalityOfflineList() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(setEventOfflinetElement(0,"Seleccione","0"));
        eventList.add(setEventOfflinetElement(2,"PEDIDO ENTREGADO","1"));
        eventList.add(setEventOfflinetElement(3,"AUSENTE PRIMERA VISITA","1"));
        eventList.add(setEventOfflinetElement(4,"AUSENTE SEGUNDA VISITA","1"));
        eventList.add(setEventOfflinetElement(7,"DIRECCIÓN INCORRECTA","0"));
        eventList.add(setEventOfflinetElement(8,"CLIENTE RECHAZA PEDIDO","0"));
        eventList.add(setEventOfflinetElement(11,"PROBLEMA CLIMÁTICA / SOCIAL","0"));
        eventList.add(setEventOfflinetElement(14,"ZONA PELIGROSA","0"));
        eventList.add(setEventOfflinetElement(15,"ZONA DE DIFÍCIL ACCESO","0"));
        eventList.add(setEventOfflinetElement(16,"OTROS","0"));
        fillDataSpinnerNationalities(eventList);
    }

    public Event setEventOfflinetElement(int idOffline, String descriptionOffline,String idFlag){
        Event event = new Event();
        event.setIdNationality(idOffline);
        event.setName(descriptionOffline);
        event.setFlagFotoApp(idFlag);
        return event;
    }

    public void transformEnterpriseOfflineList() {
        List<Enterprise> enterpriseList = new ArrayList<>();
        enterpriseList.add(setEnterpriseDefaultElement(0,"Seleccione",""));
        enterpriseList.add(setEnterpriseDefaultElement(1,"NATURA","contenedor-natura"));
        enterpriseList.add(setEnterpriseDefaultElement(2,"MARY KAY","contenedor-mk"));
        enterpriseList.add(setEnterpriseDefaultElement(3,"ZONIA","contenedor-zonia"));
        fillDataSpinnerEnterprises(enterpriseList);
    }

    public Enterprise setEnterpriseDefaultElement(int idOffline, String descriptionOffline,String containerName){
        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseId(idOffline);
        enterprise.setEnterpriseDescription(descriptionOffline);
        enterprise.setEnterpriseContainer(containerName);
        return enterprise;
    }

    public void fillDataSpinnerEnterprises(final List<Enterprise> enterpriseList){
        enterpriseAdapter = new EnterpriseAdapter(enterpriseList, RegisterActivity.this);
        spEnterprises.setAdapter(enterpriseAdapter);
        spEnterprises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idEnterprise = enterpriseList.get(position).getEnterpriseId();

                if((!etCode.getText().toString().isEmpty() || !etCode.getText().toString().equals(""))
                        && spEnterprises.getSelectedItemPosition()!=0
                        && spEvents.getSelectedItemPosition()!=0 &&
                        (NetworkUtils.isOnline(RegisterActivity.this) && !cboOffline.isChecked())){
                    ivPhoto.setEnabled(true);
                    ImageUtils.setTint(ivPhoto, R.color.colorRed3,getContext());
                }else {
                    ivPhoto.setEnabled(false);
                    ImageUtils.setTint(ivPhoto, R.color.colorLightGray,getContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showMessagePermission(String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Aviso");
        dialog.setMessage(message);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void showMessage(String message) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("Aviso");
            dialog.setMessage(message);
            dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }catch (Exception e) {
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMessageValidate(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Aviso");
        dialog.setMessage(message);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void totalRegisterOffline(int totalRegisters) {
        if(totalRegisters==0){
            showMessage(getString(R.string.s_not_pendings));
        }else {
            showMessage("Tienes " + totalRegisters + " pedido(s) pendiente(s) por enviar");
        }
    }

    @Override
    public void validateImageView(int totalRegisters) {
        if(totalRegisters==0){
            ivPendings.setImageResource(R.drawable.ic_info);
        }else{
            ivPendings.setImageResource(R.drawable.ic_info_orange);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode == RESULT_OK && data != null){
            String code = data.getStringExtra("code");
            etCode.setText(code);
        }
        else if(requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK){
            try {
                mPhotoFile = mCompressor.compressToFile(mPhotoFile);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Exception: ", e.getMessage());
            }

            Bitmap myBitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());

            if(photoID==0){

                String filePath = mPhotoFile.getPath();
                Bitmap photo = BitmapFactory.decodeFile(filePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArrayPhoto = stream.toByteArray();
                photoReference = Base64.encodeToString(byteArrayPhoto, Base64.DEFAULT);

                ivPhoto1.setImageDrawable(null);

                Log.d("TAKE_PICTURE_OK ", photoID + " , " + imageUri2);
                ivPhoto1.setImageBitmap(myBitmap);

            }else{

                String filePath = mPhotoFile.getPath();
                Bitmap photo = BitmapFactory.decodeFile(filePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArrayPhoto = stream.toByteArray();
                photoCargo = Base64.encodeToString(byteArrayPhoto, Base64.DEFAULT);

                ivPhotoCargo.setImageDrawable(null);

                Log.d("TAKE_PICTURE_OK ", photoID + " , " + imageUri);
                ivPhotoCargo.setImageBitmap(myBitmap);
            }
        }
        else if(requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_CANCELED){

            if(photoID==0){
                Log.d("TAKE_PICTURE_CANCELED ", photoID + " , " + imageUri2);
                imageUri2 = null;
                ivPhoto1.setImageResource(R.drawable.placeholder_photo);
                photoReference = "";
            }else{
                Log.d("TAKE_PICTURE_CANCELED ", photoID + " , " + imageUri);
                imageUri = null;
                ivPhotoCargo.setImageResource(R.drawable.placeholder_photo);
                photoCargo = "";
            }
        }
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                if(NetworkUtils.isOnline(RegisterActivity.this)) {
                    registerPresenter.getOrdersNotSendedToServer();
                }else{
                    showMessage(getString(R.string.s_not_conecction));
                }
                break;
        }
        return false;
    }

    @Override
    public void getPhotoFlag(String photoFlag) {
        this.photoFlag = photoFlag;
    }

    @Override
    public void getObgFlag(String obsFlag) {
        this.obsFlag = obsFlag;
    }

    //Check for location settings.
    public void checkForLocationSettings() {
        try {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.addLocationRequest(locationRequest);
            SettingsClient settingsClient = LocationServices.getSettingsClient(RegisterActivity.this);

            settingsClient.checkLocationSettings(builder.build())
                    .addOnSuccessListener(RegisterActivity.this, locationSettingsResponse -> {
                        //Setting is success...
                    })
                    .addOnFailureListener(RegisterActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                    try {
                                        // Show the dialog by calling startResolutionForResult(), and check the
                                        // result in onActivityResult().
                                        ResolvableApiException rae = (ResolvableApiException) e;
                                        rae.startResolutionForResult(RegisterActivity.this, REQUEST_PERMISSIONS_LOCATION_SETTINGS_REQUEST_CODE);
                                    } catch (IntentSender.SendIntentException sie) {
                                        sie.printStackTrace();
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    Toast.makeText(RegisterActivity.this, "Cambios no disponibles. Intente con otro dispositivo.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void scanBarcodeFrontCamera() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        integrator.initiateScan();
    }

}
