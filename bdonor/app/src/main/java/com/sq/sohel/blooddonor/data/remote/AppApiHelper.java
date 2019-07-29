package com.sq.sohel.blooddonor.data.remote;


import android.databinding.ObservableField;
import android.util.Base64;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.sq.sohel.blooddonor.BuildConfig;
import com.sq.sohel.blooddonor.data.local.prefs.PreferencesHelper;
import com.sq.sohel.blooddonor.data.model.api.Donor.DonorImageRequest;
import com.sq.sohel.blooddonor.data.model.api.Donor.DonorRequest;
import com.sq.sohel.blooddonor.data.model.api.Donor.DonorResponse;
import com.sq.sohel.blooddonor.data.model.api.DonorListResponse;
import com.sq.sohel.blooddonor.data.model.api.Login.LoginRequest;
import com.sq.sohel.blooddonor.data.model.api.Login.LoginResponse;
import com.sq.sohel.blooddonor.data.model.api.LogoutResponse;
import com.sq.sohel.blooddonor.data.model.api.RateUs.RateUsRequest;
import com.sq.sohel.blooddonor.data.model.api.RateUs.RateUsResponse;
import com.sq.sohel.blooddonor.data.model.api.RequestBlood.RequestBloodRequest;
import com.sq.sohel.blooddonor.data.model.api.RequestBlood.RequestBloodResponse;
import com.sq.sohel.blooddonor.data.model.api.SignUp.SignUpRequest;
import com.sq.sohel.blooddonor.data.model.api.User.UserRequest;
import com.sq.sohel.blooddonor.data.model.api.User.UserResponse;
import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.data.model.others.CompanyContactRequestCreatedViewModel;
import com.sq.sohel.blooddonor.data.model.others.CompanyContactRequestUpdatedViewModel;
import com.sq.sohel.blooddonor.data.model.others.NameValuePair;
import com.sq.sohel.blooddonor.data.model.others.UserClaimViewModel;
import com.sq.sohel.blooddonor.data.model.others.UserViewModel;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Response;


import static com.sq.sohel.blooddonor.utils.AppConstants.API_RESULT_SUCCESS;


@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private final Gson mGson;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppApiHelper(ApiHeader apiHeader, Gson gson, PreferencesHelper preferencesHelper) {
        mApiHeader = apiHeader;
        mGson = gson;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        Single<LoginResponse> loginResponse = new LoginResponse(request);
        return loginResponse;
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        Single<LoginResponse> loginResponse = new LoginResponse(request);
        return loginResponse;
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
//        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_LOGOUT)
//                .addHeaders(mApiHeader.getProtectedApiHeader())
//                .build()
//                .getObjectSingle(LogoutResponse.class);
        return new LogoutResponse();
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TOKEN)
                .addUrlEncodeFormBodyParameter("client_id", BuildConfig.CLIENT_ID)
                .addUrlEncodeFormBodyParameter("client_secret", BuildConfig.CLIENT_SECRET)
                .addUrlEncodeFormBodyParameter("username", request.getEmail())
                .addUrlEncodeFormBodyParameter("password", request.getPassword())
                .addUrlEncodeFormBodyParameter("grant_type", BuildConfig.GRANTTYPE)
                .addUrlEncodeFormBodyParameter("response_type", BuildConfig.RESOPONSETYPE)
                .addUrlEncodeFormBodyParameter("scope", BuildConfig.SCOPE)
                .build()
                .getObjectSingle(LoginResponse.class);

    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<DonorListResponse> getDonorApiCall() {
        Log.i("API CALL:", ApiEndPoint.ENDPOINT_DONOR_LIST);
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_DONOR_LIST)
                .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken())
                .build()
                .getObjectSingle(DonorListResponse.class);
    }

    @Override
    public Single<UserResponse> postUserInfo(UserRequest userRequest) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mGson.toJson(userRequest));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DONOR_USER_POST)
                .addHeaders(mApiHeader.getProtectedApiHeader()).addJSONObjectBody(jsonObject)
                .build()
                .getObjectSingle(UserResponse.class);
    }

    @Override
    public Single<DonorResponse> saveDonorInfo(DonorRequest donor) {
        //ObservableField<DonorResponse> returnData = new ObservableField<>();
        //final DonorResponse[] temp = new DonorResponse[1];
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mGson.toJson(donor));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (StringExtension.isNullOrWhiteSpace(donor.Id)) {
            return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DONOR_INFO)
                    .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken()).
                            addJSONObjectBody(jsonObject)
                    .build().getObjectSingle(DonorResponse.class);

        } else {
            return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_DONOR_INFO)
                    .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken()).
                            addJSONObjectBody(jsonObject)
                    .build().getObjectSingle(DonorResponse.class);
        }
    }

    @Override
    public Single<DonorResponse> saveDonorImageInfo(DonorImageRequest donor) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mGson.toJson(donor));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_DONOR_IMAGE_INFO)
                .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken()).
                        addJSONObjectBody(jsonObject)
                .build().getObjectSingle(DonorResponse.class);
    }

    @Override
    public Single<RequestBloodResponse> saveRequestBloodInfo(RequestBloodRequest requestBloodRequest) {
        final DonorResponse[] temp = new DonorResponse[1];
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mGson.toJson(requestBloodRequest));
        } catch (JSONException e) {
            //e.printStackTrace();
        }

        if (StringExtension.isNullOrWhiteSpace(requestBloodRequest.id)) {
            return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_BLOOD_REQUEST)
                    .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken()).
                            addJSONObjectBody(jsonObject)
                    .build().getObjectSingle(RequestBloodResponse.class);

        } else {
            return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_BLOOD_REQUEST)
                    .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken()).
                            addJSONObjectBody(jsonObject)
                    .build().getObjectSingle(RequestBloodResponse.class);
        }
    }


    @Override
    public Single<SignUpRequest> postSignUpUser(SignUpRequest signUpRequest) {
        if (signUpRequest != null) {

        }

//        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TOKEN)
//                .addUrlEncodeFormBodyParameter("client_id", BuildConfig.CLIENT_ID)
//                .addUrlEncodeFormBodyParameter("client_secret", BuildConfig.CLIENT_SECRET)
//                .addUrlEncodeFormBodyParameter("username", BuildConfig.UUID)
//                .addUrlEncodeFormBodyParameter("password", BuildConfig.UUPD)
//                .addUrlEncodeFormBodyParameter("grant_type", BuildConfig.GRANTTYPE)
//                .addUrlEncodeFormBodyParameter("response_type", BuildConfig.RESOPONSETYPE)
//                .addUrlEncodeFormBodyParameter("scope", BuildConfig.SCOPE)
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

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TOKEN)
                .addUrlEncodeFormBodyParameter("client_id", BuildConfig.CLIENT_ID)
                .addUrlEncodeFormBodyParameter("client_secret", BuildConfig.CLIENT_SECRET)
                .addUrlEncodeFormBodyParameter("username", BuildConfig.UUID)
                .addUrlEncodeFormBodyParameter("password", BuildConfig.UUPD)
                .addUrlEncodeFormBodyParameter("grant_type", BuildConfig.GRANTTYPE)
                .addUrlEncodeFormBodyParameter("response_type", BuildConfig.RESOPONSETYPE)
                .addUrlEncodeFormBodyParameter("scope", BuildConfig.SCOPE)
                .build()
                .getObjectSingle(SignUpRequest.class);
    }

    @Override
    public Single<String> createCompanyContact(SignUpRequest signUpRequest) {
        String companyId = BuildConfig.COMUGID;
        CompanyContactRequestCreatedViewModel companyContactRequestCreatedViewModel = new CompanyContactRequestCreatedViewModel();
        companyContactRequestCreatedViewModel.Email = signUpRequest.email;
        companyContactRequestCreatedViewModel.IsUser = true;
        companyContactRequestCreatedViewModel.Label = "Donor Contact User";
        companyContactRequestCreatedViewModel.Name = signUpRequest.name;
        companyContactRequestCreatedViewModel.SortOrder = 1;

        JSONObject companyContactJsonObject = null;
        try {
            companyContactJsonObject = new JSONObject(mGson.toJson(companyContactRequestCreatedViewModel));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMPANY_CONTACT)
                .addHeaders("Authorization", "Bearer " + signUpRequest.access_token)
                .addJSONObjectBody(companyContactJsonObject)
                .build().getStringSingle();
    }

    public Single<JsonObject> createUser(SignUpRequest signUpRequest) {
        String contactId = signUpRequest.contactId;// //cc.substring(1, cc.length() - 1).split(" \"")[0];
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.CompanyId = UUID.fromString(BuildConfig.COMUGID);
        userViewModel.XPTOUserId = UUID.fromString(contactId);
        userViewModel.UserName = signUpRequest.name;
        userViewModel.PasswordHash = signUpRequest.password;
        userViewModel.EmailAddress = signUpRequest.email;

        userViewModel.Claims = new ArrayList<>();
        UserClaimViewModel userClaimViewModel;//= new UserClaimViewModel();
//        userClaimViewModel.Type = "DonorUser";
//        userClaimViewModel.Value = "Donor User";
//        userViewModel.Claims.add(userClaimViewModel);
        userClaimViewModel = new UserClaimViewModel();
        userClaimViewModel.Type = "appType";
        userClaimViewModel.Value = "DonorUser";
        userViewModel.Claims.add(userClaimViewModel);
        JSONObject userJsonObject = null;
        try {
            userJsonObject = new JSONObject(mGson.toJson(userViewModel));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_USERS)
                .addHeaders("Authorization", "Bearer " + signUpRequest.access_token)
                .addJSONObjectBody(userJsonObject).build().getObjectSingle(JsonObject.class);
    }

    @Override
    public void forgotPassword(String email) {
        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FORGOT_PASSWORD.replace("{email}", email)).build().getAsOkHttpResponse(new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onError(ANError anError) {
            }
        });
    }

    @Override
    public void updateCompanyContact(SignUpRequest signUpRequest) {
        CompanyContactRequestUpdatedViewModel companyContactRequestUpdatedViewModel = new CompanyContactRequestUpdatedViewModel();
        companyContactRequestUpdatedViewModel.Id = UUID.fromString(signUpRequest.contactId);
        companyContactRequestUpdatedViewModel.Status = "Active";
        companyContactRequestUpdatedViewModel.Email = signUpRequest.email;
        companyContactRequestUpdatedViewModel.IsUser = true;
        companyContactRequestUpdatedViewModel.Label = "Donor Contact User";
        companyContactRequestUpdatedViewModel.Name = signUpRequest.name;
        companyContactRequestUpdatedViewModel.SortOrder = 1;
        companyContactRequestUpdatedViewModel.AspNetUserId = signUpRequest.userId;
        JSONObject companyContactUpdateJsonObject = null;
        try {
            companyContactUpdateJsonObject = new JSONObject(mGson.toJson(companyContactRequestUpdatedViewModel));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_COMPANY_CONTACT)
                .addHeaders("Authorization", "Bearer " + signUpRequest.access_token)
                .addJSONObjectBody(companyContactUpdateJsonObject).build().getAsOkHttpResponse(new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response) {
                Rx2AndroidNetworking.put(BuildConfig.XPTO_BASE + "/api/users/" + signUpRequest.email + "/unlock")
                        .addHeaders("Authorization", "Bearer " + signUpRequest.access_token).build().getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError != null) {

                        }
                    }
                });

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    @Override
    public Single<JsonObject> getCompanyContactInfo(String contactId, String access_token) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_COMPANY_CONTACT + "/" + contactId)
                .addHeaders("Authorization", "Bearer " + access_token)
                .build().getObjectSingle(JsonObject.class);
    }

    @Override
    public void getCompnayListForTest(String access_token) {
//        Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_COMPANY_LIST_FOR_TEST)
//                .addHeaders("Authorization", "Bearer " + access_token)
//                .build().getAsJSONObject(new JSONObjectRequestListener() {
//            @Override
//            public void onResponse(JSONObject response) {
//                if (response != null) {
//
//                }
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                if (anError != null) {
//
//                }
//            }
//        });
    }

    @Override
    public Single<RateUsResponse> postRateUs(RateUsRequest rateUsRequest) {
        JSONObject jsonObject = null;
        try {
            rateUsRequest.userId = mPreferencesHelper.getCurrentUserId();
            rateUsRequest.applicationId="Blood Donor";
            jsonObject = new JSONObject(mGson.toJson(rateUsRequest));
        } catch (JSONException e) {

        }
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RATE_US)
                .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken()).
                        addJSONObjectBody(jsonObject)
                .build().getObjectSingle(RateUsResponse.class);
    }

    @Override
    public Single<RateUsResponse> getRateUs(RateUsRequest rateUsRequest) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RATE_US)
                .addHeaders("Authorization", "Bearer " + mPreferencesHelper.getAccessToken())
                .build().getObjectSingle(RateUsResponse.class);
    }

    @Override
    public Single<JsonObject> getUserInfo(String email, String access_token) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_USERS + "/" + email)
                .addHeaders("Authorization", "Bearer " + access_token)
                .build().getObjectSingle(JsonObject.class);
    }

    //@Override
    public Single<SignUpRequest> postSignUpUser1(SignUpRequest signUpRequest) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mGson.toJson(signUpRequest));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    new CompositeDisposable().add(Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TOKEN)
                            .addUrlEncodeFormBodyParameter("client_id", BuildConfig.CLIENT_ID)
                            .addUrlEncodeFormBodyParameter("client_secret", BuildConfig.CLIENT_SECRET)
                            .addUrlEncodeFormBodyParameter("username", BuildConfig.UUID)
                            .addUrlEncodeFormBodyParameter("password", BuildConfig.UUPD)
                            .addUrlEncodeFormBodyParameter("grant_type", BuildConfig.GRANTTYPE)
                            .addUrlEncodeFormBodyParameter("response_type", BuildConfig.RESOPONSETYPE)
                            .addUrlEncodeFormBodyParameter("scope", BuildConfig.SCOPE)
                            .build().getJSONObjectSingle().subscribe(s -> {
                                if (s != null) {
                                    if (BuildConfig.COMUGID != null) {
                                        String companyId = BuildConfig.COMUGID;
                                        CompanyContactRequestCreatedViewModel companyContactRequestCreatedViewModel = new CompanyContactRequestCreatedViewModel();
                                        companyContactRequestCreatedViewModel.Email = signUpRequest.email;
                                        companyContactRequestCreatedViewModel.IsUser = true;
                                        companyContactRequestCreatedViewModel.Label = "Donor Contact User";
                                        companyContactRequestCreatedViewModel.Name = signUpRequest.name;
                                        companyContactRequestCreatedViewModel.SortOrder = 1;

                                        JSONObject companyContactJsonObject = null;
                                        try {
                                            companyContactJsonObject = new JSONObject(mGson.toJson(companyContactRequestCreatedViewModel));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMPANY_CONTACT)
                                                .addHeaders("Authorization", "Bearer " + s.getString("access_token"))
                                                .addJSONObjectBody(companyContactJsonObject)
                                                .build().getStringSingle().subscribe(cc -> {
                                            if (cc != null) {
                                                String contactId = cc.substring(1, cc.length() - 1).split(" \"")[0];
                                                UserViewModel userViewModel = new UserViewModel();
                                                userViewModel.CompanyId = UUID.fromString(companyId);
                                                userViewModel.XPTOUserId = UUID.fromString(contactId);
                                                userViewModel.UserName = signUpRequest.name;
                                                userViewModel.PasswordHash = signUpRequest.password;
                                                userViewModel.EmailAddress = signUpRequest.email;

                                                userViewModel.Claims = new ArrayList<>();
                                                UserClaimViewModel userClaimViewModel = new UserClaimViewModel();
                                                userClaimViewModel.Type = "DonorUser";
                                                userClaimViewModel.Value = "Donor User";
                                                userViewModel.Claims.add(userClaimViewModel);
                                                userClaimViewModel = new UserClaimViewModel();
                                                userClaimViewModel.Type = "appType";
                                                userClaimViewModel.Value = "Donor Mobile App";
                                                userViewModel.Claims.add(userClaimViewModel);
                                                JSONObject userJsonObject = null;
                                                try {
                                                    userJsonObject = new JSONObject(mGson.toJson(userViewModel));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_USERS)
                                                        .addJSONObjectBody(userJsonObject).build().getJSONObjectSingle().subscribe(u -> {
                                                    if (u != null) {
                                                        String userId = u.getString("id");
                                                        CompanyContactRequestUpdatedViewModel companyContactRequestUpdatedViewModel = new CompanyContactRequestUpdatedViewModel();
                                                        companyContactRequestUpdatedViewModel.Id = UUID.fromString(contactId);
                                                        companyContactRequestUpdatedViewModel.Status = "Active";
                                                        companyContactRequestUpdatedViewModel.Email = signUpRequest.email;
                                                        companyContactRequestUpdatedViewModel.IsUser = true;
                                                        companyContactRequestUpdatedViewModel.Label = "Donor Contact User";
                                                        companyContactRequestUpdatedViewModel.Name = signUpRequest.name;
                                                        companyContactRequestUpdatedViewModel.SortOrder = 1;
                                                        companyContactRequestUpdatedViewModel.AspNetUserId = userId;
                                                        JSONObject companyContactUpdateJsonObject = null;
                                                        try {
                                                            companyContactUpdateJsonObject = new JSONObject(mGson.toJson(companyContactRequestUpdatedViewModel));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_COMPANY_CONTACT)
                                                                .addHeaders("Authorization", "Bearer " + s.getString("access_token"))
                                                                .addJSONObjectBody(companyContactUpdateJsonObject).build().getAsJSONObject(new JSONObjectRequestListener() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                if (response.toString().isEmpty()) {
                                                                    //handle empty body
                                                                } else {
                                                                    //do logic with json value
                                                                }
                                                            }

                                                            @Override
                                                            public void onError(ANError anError) {
                                                                //print log error code
                                                                Log.e("FAN_ERROR", "ERROR CODE : " + anError.getErrorCode());
                                                                //print log error body
                                                                Log.e("FAN_ERROR", "ERROR BODY : " + anError.getErrorBody());
                                                            }
                                                        });
                                                    }
                                                }, throwable -> {
                                                    Log.e("ERROR", throwable.getMessage());
                                                });
                                                //api/users
                                            }
                                        }, throwable -> {
                                            Log.e("ERROR", throwable.getMessage());
                                        });
                                    }

                                }
                            }, throwable -> {
                                Log.e("ERROR", throwable.getMessage());
                            }));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

//        ANResponse<Json> response1 = request1.executeForObjectList(Json.class);
//        if (response1.isSuccess()) {
//            String ds = response1.getResult().toString();
//            if(ds.isEmpty()){
//
//            }
//        }

//        ANRequest request = Rx2AndroidNetworking.get("http://172.16.40.104:5001/companies/0AD2D673-2670-4F1F-9DBD-08D6C26D214D")
//                .addHeaders(mApiHeader.getProtectedApiHeader())
//                .build();
//
//        ANResponse<JsonObject> response = request.executeForObjectList(JsonObject.class);
//        if (response.isSuccess()) {
//            String companyId = response.getResult().get("Id").getAsString();
//            CompanyContactRequestCreatedViewModel companyContactRequestCreatedViewModel = new CompanyContactRequestCreatedViewModel();
//            companyContactRequestCreatedViewModel.Email = signUpRequest.email;
//            companyContactRequestCreatedViewModel.IsUser = true;
//            companyContactRequestCreatedViewModel.Label = "Donor Contact User";
//            companyContactRequestCreatedViewModel.Name = signUpRequest.name;
//            companyContactRequestCreatedViewModel.SortOrder = 1;
//
//            try {
//                request = Rx2AndroidNetworking.post("http://172.16.40.104:5001/companies/" + companyId + "/contacts")
//                        .addHeaders(mApiHeader.getProtectedApiHeader()).addApplicationJsonBody(new JSONObject(mGson.toJson(companyContactRequestCreatedViewModel)))
//                        .build();
//                response = request.executeForObjectList(JsonObject.class);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DONOR_USER_POST)
//                .addHeaders(mApiHeader.getProtectedApiHeader()).addJSONObjectBody(jsonObject)
//                .build()
//                .getObjectSingle(SignUpRequest.class);
        return null;
    }
}
