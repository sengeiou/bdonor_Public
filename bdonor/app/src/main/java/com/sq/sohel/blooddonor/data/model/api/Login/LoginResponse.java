

package com.sq.sohel.blooddonor.data.model.api.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public final class LoginResponse extends Single<LoginResponse> {
    private class InnerDisposable implements Disposable{

        private volatile boolean disposed = false;

        @Override
        public void dispose() {
            disposed = true;
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }

    private LoginResponse() {
    }

    public LoginResponse(LoginRequest.GoogleLoginRequest googleLoginRequest) {
        accessToken = googleLoginRequest.getAccessToken();
        userId = googleLoginRequest.getUserId();
        userName = googleLoginRequest.getUserName();
        userEmail = googleLoginRequest.getEmail();
        googleProfilePicUrl = googleLoginRequest.profilePicPath;
    }
    public LoginResponse(LoginRequest.FacebookLoginRequest fbLoginRequest) {
        accessToken = fbLoginRequest.getAccessToken();
        userId = fbLoginRequest.getUserId();
        userName = fbLoginRequest.getUserName();
        userEmail = fbLoginRequest.getEmail();
        googleProfilePicUrl = fbLoginRequest.profilePicPath;
    }


    @Expose
    @SerializedName("access_token")
    private String accessToken;

    @Expose
    @SerializedName("fb_profile_pic_url")
    private String fbProfilePicUrl;

    @Expose
    @SerializedName("google_profile_pic_url")
    private String googleProfilePicUrl;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("server_profile_pic_url")
    private String serverProfilePicUrl;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Expose
    @SerializedName("email")
    private String userEmail;

    @Expose
    @SerializedName("user_id")
    private String userId;

    @Expose
    @SerializedName("user_name")
    private String userName;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        LoginResponse that = (LoginResponse) object;

        if (statusCode != null ? !statusCode.equals(that.statusCode) : that.statusCode != null) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) {
            return false;
        }
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
            return false;
        }
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) {
            return false;
        }
        if (serverProfilePicUrl != null ? !serverProfilePicUrl.equals(that.serverProfilePicUrl)
                : that.serverProfilePicUrl != null) {
            return false;
        }
        if (fbProfilePicUrl != null ? !fbProfilePicUrl.equals(that.fbProfilePicUrl)
                : that.fbProfilePicUrl != null) {
            return false;
        }
        if (googleProfilePicUrl != null ? !googleProfilePicUrl.equals(that.googleProfilePicUrl)
                : that.googleProfilePicUrl != null) {
            return false;
        }
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (serverProfilePicUrl != null ? serverProfilePicUrl.hashCode() : 0);
        result = 31 * result + (fbProfilePicUrl != null ? fbProfilePicUrl.hashCode() : 0);
        result = 31 * result + (googleProfilePicUrl != null ? googleProfilePicUrl.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getFbProfilePicUrl() {
        return fbProfilePicUrl;
    }

    public String getGoogleProfilePicUrl() {
        return googleProfilePicUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getServerProfilePicUrl() {
        return serverProfilePicUrl;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super LoginResponse> observer) {
        if (observer != null) {
            InnerDisposable innerDisposable = new InnerDisposable();
            observer.onSubscribe(innerDisposable);
            observer.onSuccess(this);
        }
    }
}
