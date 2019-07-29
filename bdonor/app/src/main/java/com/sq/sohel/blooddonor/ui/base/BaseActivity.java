package com.sq.sohel.blooddonor.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
//import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sq.sohel.blooddonor.BuildConfig;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogView;
import com.sq.sohel.blooddonor.ui.login.LoginActivity;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.CommonUtils;
import com.sq.sohel.blooddonor.utils.NetworkUtils;
import com.sq.sohel.blooddonor.utils.PermissionUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.sq.sohel.blooddonor.utils.AppConstants.LOCATION_PERMISSION_LIST;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;
    private T mViewDataBinding;
    private V mViewModel;

    DialogView mDialogView = DialogView.newInstance();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    public abstract BasePagerAdapter getPagerAdapter();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public android.location.Address getAddressFromLatLong() {
        if (PermissionUtils.hasPermissions(this, LOCATION_PERMISSION_LIST)) {
            String str[] = mViewModel.getDataManager().getCurrentUserLatLong();
            if (str != null && str.length > 0) {
                double longitude = Double.parseDouble(str[1]);
                double latitude = Double.parseDouble(str[0]);

                try {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (Exception e) {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    }
                    if (addresses.size() > 0) {
                        return addresses.get(0);
                    }

                } catch (Exception e) {
                    if (e != null) {

                    }
                    //e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void defaultErrorHandler(Throwable throwable) {
        String description = "";
        try {
            String error = ((ANError) throwable).getErrorBody();
            if (error != null) {
                try {
                    Object json = new JSONTokener(error).nextValue();
                    if (json instanceof JSONObject) {
                        JsonObject o = new JsonParser().parse(error).getAsJsonObject();
                        if (o != null) {
                            description = o.get("description") == null ? "" : o.get("description").getAsString();
                            if (StringExtension.isNullOrWhiteSpace(description)) {
                                description = o.get("error_description") == null ? "" : o.get("error_description").getAsString();
                            }
                            if (!StringExtension.isNullOrWhiteSpace(description)) {
                                //Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
                                ShowMessage(description);
                            } else {
                                //Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
                                ShowMessage("Something went wrong, please try again.");
                            }
                        }
                    }
                    //you have an object
                    else if (json instanceof JSONArray) {
                        JsonArray o = new JsonParser().parse(error).getAsJsonArray();
                        if (o != null && o.size() > 0) {

                            description = o.get(0).getAsJsonObject().get("description") == null ? "" : o.get(0).getAsJsonObject().get("description").getAsString();
                            if (StringExtension.isNullOrWhiteSpace(description)) {
                                description = o.get(0).getAsJsonObject().get("error_description") == null ? "" : o.get(0).getAsJsonObject().get("error_description").getAsString();
                            }

                            if (!StringExtension.isNullOrWhiteSpace(description)) {
                                //Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
                                ShowMessage(description);
                            }
                        }
                    }
                } catch (Exception ex) {

                }
            } else {
                //Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                ShowMessage(throwable.getMessage());
            }
        } catch (Exception ex) {
            if (BuildConfig.dEBUG) {
                ShowMessage(throwable.getMessage());
            } else {
                ShowMessage("Something went wrong, please try again.");
            }
        }
    }

    public final void handleError(Throwable throwable) {
        try {
            ANError anError = ((ANError) throwable);
            switch (anError.getErrorCode()) {
                case 401: //Unauthorized
                    //ShowMessage("Your session is expired, please login again");
                    Toast.makeText(this, "Your session is expired, please login again.", Toast.LENGTH_SHORT).show();
                    mViewModel.getDataManager().setUserAsLoggedOut();
                    openLoginActivity();
                    break;
                default:
                    defaultErrorHandler(throwable);
                    break;
            }

        } catch (Exception ex) {
            if (BuildConfig.dEBUG) {
                ShowMessage(throwable.getMessage());
            } else {
                ShowMessage("Something went wrong, please try again.");
            }
        }
    }


    public final void handleError(ValidationModel validationModel) {
        if (validationModel != null) {
            EditText control = (EditText) validationModel.getControl();
            if (control != null) {
                control.requestFocus();
                control.setError(validationModel.getValue());
                //Toast.makeText(this, validationModel.getValue(), Toast.LENGTH_SHORT).show();
                ShowMessage(validationModel.getValue());
            }
        }
    }

    public final void ShowMessage(String message) {
        mDialogView.SetMessage(message);
        mDialogView.show(getSupportFragmentManager());
    }

    public final void ShowMessage(String message, AppUtils.MessageType messageType) {
        mDialogView.SetMessage(message, messageType);
        mDialogView.show(getSupportFragmentManager());
    }

    public final void handleNoNetworkConnection() {
        if (!isNetworkConnected()) {
            mDialogView.SetMessageType(AppUtils.MessageType.INTERNET_NOT_FOUND);
            mDialogView.show(getSupportFragmentManager());
        }
    }

    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }
}


