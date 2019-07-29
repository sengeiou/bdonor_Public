

package com.sq.sohel.blooddonor.data;

import android.content.Context;
import android.databinding.ObservableField;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Types;
import com.sq.sohel.blooddonor.data.local.db.DbHelper;
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
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;
import com.sq.sohel.blooddonor.data.model.db.User;
import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.data.remote.ApiHeader;
import com.sq.sohel.blooddonor.data.remote.ApiHelper;
import com.sq.sohel.blooddonor.utils.NetworkUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;


@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return mApiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Observable<List<Donor>> getAllDonors() {
        return mDbHelper.getAllDonors();
    }

    @Override
    public Observable<List<RequestBlood>> getAllRequestForBloodData(String bloodType, String email) {
        return mDbHelper.getAllRequestForBloodData(bloodType, email);
    }

    @Override
    public Observable<Donor> getDonorByEmail(String email) {
        return mDbHelper.getDonorByEmail(email);
    }

    @Override
    public Observable<Donor> getDonorById(String id) {
        return mDbHelper.getDonorById(id);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }


    @Override
    public Single<DonorListResponse> getDonorApiCall() {
        return mApiHelper.getDonorApiCall();
    }

    @Override
    public Single<UserResponse> postUserInfo(UserRequest userRequest) {
        return mApiHelper.postUserInfo(userRequest);
    }

    @Override
    public Single<DonorResponse> saveDonorInfo(DonorRequest donor) {
        return mApiHelper.saveDonorInfo(donor);
    }

    @Override
    public Single<DonorResponse> saveDonorImageInfo(DonorImageRequest donor) {
        return mApiHelper.saveDonorImageInfo(donor);
    }

    @Override
    public Single<RequestBloodResponse> saveRequestBloodInfo(RequestBloodRequest requestBloodRequest) {
        return mApiHelper.saveRequestBloodInfo(requestBloodRequest);
    }

    @Override
    public Single<SignUpRequest> postSignUpUser(SignUpRequest signUpRequest) {
        return mApiHelper.postSignUpUser(signUpRequest);
    }

    @Override
    public Single<String> createCompanyContact(SignUpRequest signUpRequest) {
        return mApiHelper.createCompanyContact(signUpRequest);
    }

    @Override
    public Single<JsonObject> createUser(SignUpRequest signUpRequest) {
        return mApiHelper.createUser(signUpRequest);
    }

    @Override
    public void forgotPassword(String email) {
        mApiHelper.forgotPassword(email);
    }

    @Override
    public void updateCompanyContact(SignUpRequest signUpRequest) {
        mApiHelper.updateCompanyContact(signUpRequest);
    }

    @Override
    public Single<JsonObject> getCompanyContactInfo(String contactId, String access_token) {
        return mApiHelper.getCompanyContactInfo(contactId, access_token);
    }

    @Override
    public void getCompnayListForTest(String access_token) {
        mApiHelper.getCompnayListForTest(access_token);
    }

    @Override
    public Single<JsonObject> getUserInfo(String email, String access_token) {
        return mApiHelper.getUserInfo(email, access_token);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public byte[] getCurrentUserProfilePicData() {
        return mPreferencesHelper.getCurrentUserProfilePicData();
    }

    @Override
    public void setCurrentUserProfilePicData(byte[] profilePicData) {
        mPreferencesHelper.setCurrentUserProfilePicData(profilePicData);
    }

    @Override
    public String getCurrentUserContactNumber() {
        return mPreferencesHelper.getCurrentUserContactNumber();
    }

    @Override
    public void setCurrentUserContactNumber(String contactNumber) {
        mPreferencesHelper.setCurrentUserContactNumber(contactNumber);
    }

    @Override
    public String[] getCurrentUserLatLong() {
        return mPreferencesHelper.getCurrentUserLatLong();
    }

    @Override
    public void setCurrentUserLatLong(String[] LatLong) {
        mPreferencesHelper.setCurrentUserLatLong(LatLong);
    }

    @Override
    public Observable<List<DonorCardData>> getDonorCardData() {
        return mDbHelper.getAllDonors()
                .flatMap(questions -> Observable.fromIterable(questions)).map(item -> new DonorCardData(item))
                .toList()
                .toObservable();
    }

    @Override
    public Observable<List<RequestBloodCardData>> getRequestBloodCardData(String bloodType, boolean showOnlyMyRequest) {
        String myEmail = "";
        if (showOnlyMyRequest) {
            myEmail = mPreferencesHelper.getCurrentUserEmail();
        }
        return mDbHelper.getAllRequestForBloodData(bloodType, myEmail)
                .flatMap(questions -> Observable.fromIterable(questions)).map(item -> new RequestBloodCardData(item))
                .toList()
                .toObservable();
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<User> getUserFromDBByEmail(String email) {
        return mDbHelper.getUserFromDBByEmail(email);
    }

    @Override
    public Observable<Boolean> isDonorEmpty() {
        return mDbHelper.isDonorEmpty();
    }

    @Override
    public Observable<Boolean> isBloodRequestEmpty() {
        return mDbHelper.isDonorEmpty();
    }

    @Override
    public Observable<Boolean> saveDonorList(List<Donor> donorList) {
        return mDbHelper.saveDonorList(donorList);
    }

    @Override
    public Observable<Boolean> saveRequestBloodList(List<RequestBlood> requestBloodList) {
        return mDbHelper.saveRequestBloodList(requestBloodList);
    }

    @Override
    public Observable<Boolean> saveDonor(Donor donor) {
        return mDbHelper.saveDonor(donor);
    }

    @Override
    public Observable<Boolean> saveRequestBlood(RequestBlood requestBlood) {
        return mDbHelper.saveRequestBlood(requestBlood);
    }

    @Override
    public Observable<Boolean> updateDonor(Donor donor) {
        return mDbHelper.saveDonor(donor);
    }

    @Override
    public Observable<Boolean> seedBloodDonorDatabase() {
//        return Observable.just(true);
        return mDbHelper.isDonorEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        final ArrayList<Donor>[] donorList = new ArrayList[1];
                        final ArrayList<RequestBlood>[] requestBloodList = new ArrayList[1];
                        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, Donor.class);
                        Type typeRequestBlood = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, RequestBlood.class);
                        new CompositeDisposable().add(mApiHelper.getDonorApiCall().subscribe(donorResponse -> {
                            //Log.i("Sucess", donorResponse.getMessage());
                            if (donorResponse != null && donorResponse.getBloodDonorData() != null && donorResponse.getRequestBloodData() != null) {
                                donorList[0] = ApiDonorToDbDonor(donorResponse.getBloodDonorData());
                                requestBloodList[0] = mGson.fromJson(mGson.toJson(donorResponse.getRequestBloodData()), typeRequestBlood);
                            }
                        }, throwable -> {
                            //Log.e("ERROR", throwable.getMessage());
                        }));
                        return Observable.merge(saveDonorList(donorList[0]), saveRequestBloodList(requestBloodList[0]));
                    }
                    return Observable.just(false);

                });
    }

    private ArrayList<Donor> ApiDonorToDbDonor(List<DonorListResponse.Donor> apiDonor) {
        ArrayList<Donor> dbDonor = new ArrayList<>();
        if (apiDonor != null) {
            for (DonorListResponse.Donor d : apiDonor) {
                Donor dd = new Donor();
                dd.AddedBy = d.getAddedBy();
                dd.Address = d.getAddress();
                dd.BloodType = d.getBloodType();
                dd.City = d.getCity();
                dd.ContactNumber = d.getContactNumber();
                dd.Country = d.getCountry();
                dd.Dob = d.getDob();
                dd.DonorId_ServerSite = d.getDonorId_ServerSite();
                dd.Email = d.getEmail();
                dd.Gender = d.getGender();
                dd.HeightIN = d.getHeightIN();
                dd.Id = d.getId();

                dd.ImageData = d.getImageData();
                dd.IsLocallyAdd = d.isLocallyAdd();
                dd.IsSyncWithServer = d.isSyncWithServer();
                dd.LastDonationDate = d.getLastDonationDate();
                dd.Latitude = d.getLatitude();
                dd.Longitude = d.getLongitude();
                dd.Name = d.getName();
                dd.WeightLBS = d.getWeightLBS();
                dbDonor.add(dd);
            }
        }
        return dbDonor;
    }

    private ArrayList<RequestBlood> ApiRequestBloodToDbRequestBlood(List<DonorListResponse.RequestBlood> apiRequestBlood) {
        ArrayList<RequestBlood> dbDonor = new ArrayList<>();
        if (apiRequestBlood != null) {
            for (DonorListResponse.RequestBlood d : apiRequestBlood) {
                RequestBlood dd = new RequestBlood();
                dd.AddedBy = d.AddedBy;
                dd.Address = d.Address;
                dd.BloodType = d.BloodType;
                dd.BloodNeedDate = d.BloodNeedDate;
                dd.ContactNumber1 = d.ContactNumber1;
                dd.ContactNumber2 = d.ContactNumber2;
                dd.ContactNumber3 = d.ContactNumber3;
                dd.ContactPerson1 = d.ContactPerson1;
                dd.ContactPerson2 = d.ContactPerson2;
                dd.ContactPerson3 = d.ContactPerson3;
                dd.Comments = d.Comments;
                dd.City = d.City;
                dd.Country = d.Country;
                dd.Id = d.Id;
                dd.Id_ServerSite = d.Id_ServerSite;
                dd.IsSyncWithServer = d.IsSyncWithServer;
                dd.IsLocallyAdd = d.IsLocallyAdd;
                dd.Latitude = d.Latitude;
                dd.Longitude = d.Longitude;
                dd.NoOfBags = d.NoOfBags;
                dd.RequestFor = d.RequestFor;

                dbDonor.add(dd);
            }
        }
        return dbDonor;
    }

    @Override
    public void setUserAsLoggedOut() {
        if (LoggedInMode.LOGGED_IN_MODE_FB.getType() == getCurrentUserLoggedInMode()) {
            LoginManager.getInstance().logOut();
        }
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null, null);
    }

    @Override
    public void updateApiHeader(String userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Single<RateUsResponse> postRateUs(RateUsRequest rateUsRequest) {
        return null;
    }

    @Override
    public Single<RateUsResponse> getRateUs(RateUsRequest rateUsRequest) {
        return null;
    }

    @Override
    public void updateUserProfilePicture(byte[] profilePicBytes) {
        setCurrentUserProfilePicData(profilePicBytes);
    }

    @Override
    public void updateUserInfo(String accessToken, String userId, LoggedInMode loggedInMode, String userName, String email, String profilePicPath, byte[] profilePicBytes) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);
        setCurrentUserProfilePicData(profilePicBytes);
        updateApiHeader(userId, accessToken);

        if (loggedInMode != LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT) {
            User user = new User();
            user.email = email;
            user.loggedInMode = String.valueOf(loggedInMode);
            user.profilePicImageData = null;
            user.profilePicUrl = profilePicPath;
            user.userId = userId;
            user.userName = userName;
            user.accessToken = accessToken;
            Observable<Boolean> isInsert = insertUser(user);

            ObservableField<byte[]> imgObsFld = new ObservableField<>();

            CompositeDisposable compositeDisposable = new CompositeDisposable();

            imgObsFld.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                    if (imgObsFld != null) {
                        user.profilePicImageData = imgObsFld.get();
                        setCurrentUserProfilePicData(user.profilePicImageData);
                        Observable<Boolean> isInsert11 = insertUser(user);
                        compositeDisposable.add(isInsert11.subscribe(aBoolean -> {
                            if (aBoolean) {

                            }
                        }));
                    }
                }
            });

            compositeDisposable.add(isInsert.subscribe(aBoolean -> {
                final byte[][] imageBytes = {null};
                Thread thread = new Thread(() -> {
                    try {
                        if (!StringExtension.isNullOrWhiteSpace(profilePicPath)) {
                            imageBytes[0] = NetworkUtils.downloadFile(profilePicPath);
                            imgObsFld.set(imageBytes[0]);
                        } else {
                            imgObsFld.set(null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                if (aBoolean) {
                    thread.start();
                }
            }));

//            UserRequest userRequest = new UserRequest();
//            userRequest.userName = user.userName;
//            userRequest.accessToken = accessToken;
//            userRequest.email = user.email;
//            userRequest.loggedInMode = user.loggedInMode;
//            userRequest.profilePicPath = user.profilePicUrl;
//            userRequest.userId = user.userId;
            //Single<DonorResponse> userResponse = postUserInfo(userRequest);

//            compositeDisposable.add(mApiHelper.postUserInfo(userRequest).subscribe(a -> {
//                //Log.i("POST_USER_INFO", a.message);
//            }, throwable -> {
//                //Log.e("ERROR_POST_USER_INFO", throwable.getMessage());
//
//            }));
            compositeDisposable.dispose();
        }
    }


}
