package com.sq.sohel.blooddonor.ui.signUp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.databinding.ActivitySignupBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogView;
import com.sq.sohel.blooddonor.ui.login.LoginViewModel;
import com.sq.sohel.blooddonor.ui.splash.SplashActivity;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.CommonUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SignUpActivity extends BaseActivity<ActivitySignupBinding,
        SignUpViewModel> implements SignUpNavigator, HasSupportFragmentInjector {


    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    SignUpViewModel mSignUpViewModel;
    @Inject
    Gson mGson;
    ActivitySignupBinding mActivitySignupBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    public SignUpViewModel getViewModel() {
        return mSignUpViewModel;
    }

    @Override
    public BasePagerAdapter getPagerAdapter() {
        return null;
    }


    @Override
    public void confirmationEmailSend() {
        LinearLayout ll = findViewById(R.id.layout_signup);
        ll.setVisibility(View.GONE);
        ll = findViewById(R.id.layout_signup_confirmation_email);
        ll.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeMe() {
        finish();
    }

    @Override
    public void signUp() {
        String name = mActivitySignupBinding.txtSignupName.getText().toString();
        String email = mActivitySignupBinding.txtSignupEmail.getText().toString();
        String password = mActivitySignupBinding.txtSignupPassword.getText().toString();
        String confirmPassword = mActivitySignupBinding.txtSignupConfirmPassword.getText().toString();

//        Rx2AndroidNetworking.post("http://172.16.40.104:5000/connect/token")
//                //Admin : ubd8uu/chry6DmD15AOb3q/Wqk7Nbj3kOkTmZVT2SR8=
//                .addUrlEncodeFormBodyParameter("client_id", "DonorApp")
//                .addUrlEncodeFormBodyParameter("client_secret", "VcSwJ0LIJS3Ye158loFI68mMpZs3/LitLC77gaHwKys=")
//                //.addUrlEncodeFormBodyParameter("client_id", "Admin")
//                //.addUrlEncodeFormBodyParameter("client_secret", "ubd8uu/chry6DmD15AOb3q/Wqk7Nbj3kOkTmZVT2SR8=")
//                //DonorApp : VcSwJ0LIJS3Ye158loFI68mMpZs3/LitLC77gaHwKys=
//                .addUrlEncodeFormBodyParameter("username", "donor@bigbarta.com")
//                .addUrlEncodeFormBodyParameter("password", "YY3bpu@GRqG?JbKvPLi=h(")
//                .addUrlEncodeFormBodyParameter("grant_type", "password")
//                .addUrlEncodeFormBodyParameter("response_type", "token")
//                .addUrlEncodeFormBodyParameter("scope", "XPTOApi")
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        if(response!=null){
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        if(anError!=null){
//
//                        }
//                    }
//                });

        if (TextUtils.isEmpty(name)) {
            hideKeyboard();
            //Toast.makeText(this, getString(R.string.NameRequired), Toast.LENGTH_SHORT).show();
            ShowMessage(getString(R.string.NameRequired));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            hideKeyboard();
            //Toast.makeText(this, getString(R.string.EmailRequired), Toast.LENGTH_SHORT).show();
            ShowMessage(getString(R.string.EmailRequired));
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            hideKeyboard();
            //Toast.makeText(this, getString(R.string.EmailFormatRequired), Toast.LENGTH_SHORT).show();
            ShowMessage(getString(R.string.EmailFormatRequired));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            hideKeyboard();
            //Toast.makeText(this, getString(R.string.PasswordRequired), Toast.LENGTH_SHORT).show();
            ShowMessage(getString(R.string.PasswordRequired));
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            hideKeyboard();
            //Toast.makeText(this, getString(R.string.ConfirmPasswordRequired), Toast.LENGTH_SHORT).show();
            ShowMessage(getString(R.string.ConfirmPasswordRequired));
            return;
        }
        if (!password.equals(confirmPassword)) {
            hideKeyboard();
            //Toast.makeText(this, getString(R.string.PasswordMatchRequired), Toast.LENGTH_SHORT).show();
            ShowMessage(getString(R.string.PasswordMatchRequired));
            return;
        }
        if (isNetworkConnected()) {
            mSignUpViewModel.CreateNewAccount(name, email, password);
        } else {
            handleNoNetworkConnection();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignupBinding = getViewDataBinding();
        mSignUpViewModel.setNavigator(this);

        TextView getapp = (TextView) findViewById(R.id.txtPrivacyPolicy);
        getapp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    private void setupActivity() {
        EditText editText = findViewById(R.id.txt_signup_Email);
        ValidationModel validationModel = new ValidationModel("email", getString(R.string.EmailFormatRequired), editText, null);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && CommonUtils.isEmailValid(s.toString())) {
                    mSignUpViewModel.removeViewModelError(validationModel);
                    editText.setError(null);
                } else {
                    mSignUpViewModel.setViewModelError(validationModel);
                    editText.requestFocus();
                    editText.setError(getString(R.string.EmailFormatRequired));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        EditText editTextSignup_Name = findViewById(R.id.txt_signup_Name);
        ValidationModel validationModelSignup_Name = new ValidationModel("name", getString(R.string.signup_Name), editText, null);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mSignUpViewModel.removeViewModelError(validationModelSignup_Name);
                    editText.setError(null);
                } else {
                    mSignUpViewModel.setViewModelError(validationModelSignup_Name);
                    editText.requestFocus();
                    editText.setError(getString(R.string.EmailFormatRequired));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
