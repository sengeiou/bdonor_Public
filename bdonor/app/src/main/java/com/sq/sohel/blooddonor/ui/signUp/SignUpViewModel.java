package com.sq.sohel.blooddonor.ui.signUp;

import android.databinding.ObservableField;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.api.SignUp.SignUpRequest;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

public class SignUpViewModel extends BaseViewModel<SignUpNavigator> {

    public SignUpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }



    private ObservableField<byte[]> imageData = new ObservableField<>();

    public ObservableField<byte[]> getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData.set(imageData);
    }

    private ObservableField<String> imageUrl = new ObservableField<>();

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    public ObservableField<String> getImageUrl() {

        return imageUrl;
    }

    private ObservableField<String> name = new ObservableField<>();

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    private ObservableField<String> email = new ObservableField<>();

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(ObservableField<String> email) {
        this.email = email;
    }

    private ObservableField<String> password = new ObservableField<>();

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    private ObservableField<String> confirmPassword = new ObservableField<>();

    public ObservableField<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(ObservableField<String> confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    private boolean validateViewModel(SignUpViewModel signUpViewModel) {
        boolean returnStatus = true;
        if (getViewModelError().size() > 0) {
            getNavigator().handleError(getViewModelError().get(0));
            return false;
        }

        if (signUpViewModel != null) {
//            if (!StringExtension.isNullOrWhiteSpace(donor.Dob) && !StringExtension.isNullOrWhiteSpace(donor.LastDonationDate)) {
//                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
//                try {
//                    if (sdf.parse(donor.Dob).getTime() >= sdf.parse(donor.LastDonationDate).getTime()) {
//                        getNavigator().handleError(new Throwable("Donation date can't be before date of birth."));
//                        return false;
//                    }
//                } catch (ParseException e) {
//
//                }
//            }
        }

        return returnStatus;
    }

    public void createNewAccount() {

        if (validateViewModel(this)) {
            getNavigator().signUp();
        }

    }

    private void createCompanyContact(SignUpRequest signUpRequest) {
        getCompositeDisposable().add(getDataManager()
                .createCompanyContact(signUpRequest)
                .doOnSuccess(c -> {
                    signUpRequest.contactId = c.substring(1, c.length() - 1).split(" \"")[0];
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    createUser(signUpRequest);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
    private void createUser(SignUpRequest signUpRequest) {
        getCompositeDisposable().add(getDataManager()
                .createUser(signUpRequest)
                .doOnSuccess(c -> {
                    signUpRequest.userId = c.get("id").getAsString();
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getDataManager().updateCompanyContact(signUpRequest);
                    setIsLoading(false);
                    getNavigator().confirmationEmailSend();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }


    public void CreateNewAccount(String name, String email, String password) {
        setIsLoading(true);
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.name = name;
        signUpRequest.email = email;
        signUpRequest.password = password;

        getCompositeDisposable().add(getDataManager()
                .postSignUpUser(signUpRequest)
                .doOnSuccess(s -> {
                    if (s != null) {
                        signUpRequest.access_token = s.access_token;
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    createCompanyContact(signUpRequest);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void closeMe(){
        getNavigator().closeMe();
    }
}
