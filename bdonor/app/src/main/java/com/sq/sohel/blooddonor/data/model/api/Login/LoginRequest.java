package com.sq.sohel.blooddonor.data.model.api.Login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sq.sohel.blooddonor.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;


public final class LoginRequest {

    private LoginRequest() {
        // This class is not publicly instantiable
    }

    public static class FacebookLoginRequest {
        @Expose
        @SerializedName("fb_access_token")
        String accessToken;
        @Expose
        @SerializedName("fb_userId")
        String userId;
        @Expose
        @SerializedName("fb_loggedInMode")
        DataManager.LoggedInMode loggedInMode;
        @Expose
        @SerializedName("fb_userName")
        String userName;
        @Expose
        @SerializedName("fb_email")
        String email;
        @Expose
        @SerializedName("fb_profilePicPath")
        String profilePicPath;

        public FacebookLoginRequest(String accessToken, JSONObject fbAccount) {
//            String string = fbAccount.getJSONObject("picture").getJSONObject("data").getString("url");
//            string = fbAccount.getString("name");
//            string = fbAccount.getString("email");
//            string = fbAccount.getString("id");

            this.accessToken = accessToken;
            loggedInMode = DataManager.LoggedInMode.LOGGED_IN_MODE_FB;
            try {
                userId = fbAccount.getString("id");
                userName = fbAccount.getString("name");
                email = fbAccount.getString("email");
                profilePicPath = fbAccount.getJSONObject("picture").getJSONObject("data").getString("url");
            } catch (JSONException e) {

            }
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getUserId() {
            return userId;
        }

        public DataManager.LoggedInMode getLoggedInMode() {
            return loggedInMode;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getProfilePicPath() {
            return profilePicPath;
        }
    }

    public static class GoogleLoginRequest {
        @Expose
        @SerializedName("google_access_token")
        String accessToken;
        @Expose
        @SerializedName("google_userId")
        String userId;
        @Expose
        @SerializedName("google_loggedInMode")
        DataManager.LoggedInMode loggedInMode;
        @Expose
        @SerializedName("google_userName")
        String userName;
        @Expose
        @SerializedName("google_email")
        String email;
        @Expose
        @SerializedName("google_profilePicPath")
        String profilePicPath;


        public GoogleLoginRequest(GoogleSignInAccount account) {
            accessToken =account.getServerAuthCode();// account.getIdToken();
            userId = account.getId();
            loggedInMode = DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE;
            userName = account.getDisplayName();
            email = account.getEmail();
            profilePicPath = String.valueOf(account.getPhotoUrl());
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getUserId() {
            return userId;
        }

        public DataManager.LoggedInMode getLoggedInMode() {
            return loggedInMode;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getProfilePicPath() {
            return profilePicPath;
        }

    }

    public static class ServerLoginRequest {

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("password")
        private String password;

        public ServerLoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            ServerLoginRequest that = (ServerLoginRequest) object;

            if (email != null ? !email.equals(that.email) : that.email != null) {
                return false;
            }
            return password != null ? password.equals(that.password) : that.password == null;
        }

        @Override
        public int hashCode() {
            int result = email != null ? email.hashCode() : 0;
            result = 31 * result + (password != null ? password.hashCode() : 0);
            return result;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
