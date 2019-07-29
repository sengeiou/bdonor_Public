package com.sq.sohel.blooddonor.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
//import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.databinding.ActivityLoginBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogView;
import com.sq.sohel.blooddonor.ui.forgotPassword.ForgotPasswordActivity;
import com.sq.sohel.blooddonor.ui.main.MainActivity;
import com.sq.sohel.blooddonor.ui.signUp.SignUpActivity;
import com.sq.sohel.blooddonor.ui.splash.SplashActivity;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.JGoogleAuthenticator.SetupCode;
import com.sq.sohel.blooddonor.utils.JGoogleAuthenticator.TwoFactorAuthenticator;
import com.sq.sohel.blooddonor.utils.NetworkUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.inject.Inject;

import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import org.json.JSONException;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.sq.sohel.blooddonor.utils.AppConstants.FACEBOOK_SIGN_IN;
import static com.sq.sohel.blooddonor.utils.AppConstants.GOOGLE_SIGN_IN;


public class LoginActivity extends BaseActivity<ActivityLoginBinding,
        LoginViewModel> implements LoginNavigator, GoogleApiClient.OnConnectionFailedListener, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;

    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;

    private CallbackManager facebookCallbackManager;
    AccessToken facebookAccessToken;


    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
    }

    @Override
    public BasePagerAdapter getPagerAdapter() {
        return null;
    }


    @Override
    public void login() {
        String email = mActivityLoginBinding.etEmail.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();
        if (mLoginViewModel.isEmailAndPasswordValid(email, password)) {
            hideKeyboard();
            if (isNetworkConnected()) {
                mLoginViewModel.login(email, password);
            } else {
                handleNoNetworkConnection();
            }
        } else {
            //Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
            ShowMessage(getString(R.string.invalid_email_password));
        }
    }

    @Override
    public void openSplashActivity() {
        Intent intent = SplashActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void signUp() {
        Intent intent = SignUpActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        //finish();
    }

    @Override
    public void forgotPassword() {
        Intent intent = ForgotPasswordActivity.newIntent(LoginActivity.this);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.sq.sohel.blooddonor",
                    PackageManager.GET_SIGNING_CERTIFICATES);

//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
        } catch (PackageManager.NameNotFoundException e) {
            if (e != null) {

            }

        }
//        catch (NoSuchAlgorithmException e) {
//            if (e != null) {
//
//            }
//        }

        facebookCallbackManager = CallbackManager.Factory.create();


        final String EMAIL = "email"; //

        LoginButton fbLoginButton = findViewById(R.id.fb_login_button);
        fbLoginButton.setReadPermissions(Arrays.asList(EMAIL));

        // Callback registration
        fbLoginButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                if (loginResult != null) {
                    facebookAccessToken = loginResult.getAccessToken();
                    handleFacebookSignInResult(facebookAccessToken);
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                handleNoNetworkConnection();
            }
        });

        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);

        String d = "248662075460-77k4cqk18ntpgq3pdrpmjfui41o2cs3c.apps.googleusercontent.com";
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestProfile().requestServerAuthCode(d).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        SignInButton signInButton = findViewById(R.id.google_sign_in_button);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Login with Google");
        signInButton.setOnClickListener(v -> {
            switch (v.getId()) {
                case R.id.google_sign_in_button:
                    googleSignIn();
                    break;
                // ...
            }
        });

    }

    private void googleSignIn() {
        if (isNetworkConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient); //mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
        } else {
            handleNoNetworkConnection();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FACEBOOK_SIGN_IN) {

        }
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            //super.onActivityResult(requestCode, resultCode, data);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        } else {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleFacebookSignInResult(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            try {
//                String string = object.getJSONObject("picture").getJSONObject("data").getString("url");
//                string = object.getString("name");
//                string = object.getString("email");
//                string = object.getString("id");
                if (object.has("email")) {
                    if (!StringExtension.isNullOrWhiteSpace(object.getString("email"))) {
                        mLoginViewModel.onFbLoginClick(accessToken.getToken(), object);
                    } else {
                        LoginManager.getInstance().logOut();
                        //Toast.makeText(getApplicationContext(), "Email address required to run the app.", Toast.LENGTH_SHORT).show();
                        ShowMessage("Email address required to run the app.");
                    }
                } else {
                    LoginManager.getInstance().logOut();
                    //Toast.makeText(getApplicationContext(), "Email address required to run the app.", Toast.LENGTH_SHORT).show();
                    ShowMessage("Email address required to run the app.");
                }

            } catch (JSONException e) {
                LoginManager.getInstance().logOut();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void handleGoogleSignInResult(GoogleSignInResult completedTask) {
        try {
            GoogleSignInAccount account = null;
            if (completedTask.isSuccess()) {
                account = completedTask.getSignInAccount();
                //Toast.makeText(getApplicationContext(), account.getDisplayName(), Toast.LENGTH_SHORT).show();
                mLoginViewModel.onGoogleLoginClick(account);
            } else {
                //Toast.makeText(getApplicationContext(), "Sign in problem, try again", Toast.LENGTH_SHORT).show();
                ShowMessage("Sign in problem, please try again.");
            }


        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Sign in problem, try again", Toast.LENGTH_SHORT).show();
            ShowMessage("Sign in problem, please try again.");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        handleNoNetworkConnection();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}

