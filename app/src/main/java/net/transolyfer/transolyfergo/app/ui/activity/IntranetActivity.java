package net.transolyfer.transolyfergo.app.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.core.BaseCompatActivity;
import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.data.entity.response.UserResponse;
import net.transolyfer.transolyfergo.presenter.LoginPresenter;
import net.transolyfer.transolyfergo.presenter.view.LoginView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.transolyfer.transolyfergo.app.utils.Constant.CODE_FULL_NAME;

public class IntranetActivity extends BaseCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;
    @BindView(R.id.vieLoading) View vieLoading;
    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.cboRemember)
    CheckBox cboRemember;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intranet);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachedView(this);
        loadData();
    }

    private void loadData(){
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            cboRemember.setChecked(true);
        else
            cboRemember.setChecked(false);

        etUser.setText(sharedPreferences.getString(KEY_USERNAME,""));
        etPassword.setText(sharedPreferences.getString(KEY_PASS,""));
    }

    public UserData generateData(){
        UserData userData = new UserData();
        userData.setOrderNumber(etUser.getText().toString());
        userData.setPassword(etPassword.getText().toString());
        return userData;
    }

    @OnClick(R.id.btnLogin)
    public void doLogin(){
        loginPresenter.doLogin(generateData());
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
    public void loginSuccess(Object object) {
        List<UserResponse> userResponse = (List<UserResponse>) object;
        Bundle bundle = new Bundle();
        bundle.putString(CODE_FULL_NAME, userResponse.get(0).getFullName());
        managePrefs();
        nextData(ReportActivity.class, bundle, true);
    }

    @OnClick(R.id.ivBack)
    public void goBack(){
        onBackPressed();
    }

    @Override
    public void showMessage(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(IntranetActivity.this);
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

    private void managePrefs(){
        if(cboRemember.isChecked()){
            editor.putString(KEY_USERNAME, etUser.getText().toString().trim());
            editor.putString(KEY_PASS, etPassword.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);
            editor.remove(KEY_USERNAME);
            editor.apply();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
