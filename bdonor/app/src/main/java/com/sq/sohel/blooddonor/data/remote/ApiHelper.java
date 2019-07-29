package com.sq.sohel.blooddonor.data.remote;

import com.google.gson.JsonObject;
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

import io.reactivex.Single;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Single<LogoutResponse> doLogoutApiCall();

    Single<DonorListResponse> getDonorApiCall();

    Single<UserResponse> postUserInfo(UserRequest userRequest);

    Single<DonorResponse> saveDonorInfo(DonorRequest donor);

    Single<DonorResponse> saveDonorImageInfo(DonorImageRequest donor);

    Single<RequestBloodResponse> saveRequestBloodInfo(RequestBloodRequest requestBloodRequest);

    Single<SignUpRequest> postSignUpUser(SignUpRequest signUpRequest);

    Single<String> createCompanyContact(SignUpRequest signUpRequest);

    void updateCompanyContact(SignUpRequest signUpRequest);

    Single<JsonObject> getCompanyContactInfo(String contactId, String access_token);

    Single<JsonObject> createUser(SignUpRequest signUpRequest);

    void forgotPassword(String email);

    void getCompnayListForTest(String access_token);

    Single<JsonObject> getUserInfo(String email, String access_token);

    Single<RateUsResponse> postRateUs(RateUsRequest rateUsRequest);
    Single<RateUsResponse> getRateUs(RateUsRequest rateUsRequest);
}
