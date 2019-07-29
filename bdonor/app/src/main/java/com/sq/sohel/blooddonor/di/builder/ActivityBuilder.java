
package com.sq.sohel.blooddonor.di.builder;


import com.sq.sohel.blooddonor.ui.about.AboutFragmentProvider;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.becomeDonor.BecomeDonorActivity;
import com.sq.sohel.blooddonor.ui.becomeDonor.BecomeDonorActivityModule;
import com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero.HeroFragmentProvider;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogModule;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogProvider;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogView;
import com.sq.sohel.blooddonor.ui.forgotPassword.ForgotPasswordActivity;
import com.sq.sohel.blooddonor.ui.forgotPassword.ForgotPasswordActivityModule;
import com.sq.sohel.blooddonor.ui.login.LoginActivity;
import com.sq.sohel.blooddonor.ui.login.LoginActivityModule;
import com.sq.sohel.blooddonor.ui.main.MainActivity;
import com.sq.sohel.blooddonor.ui.main.MainActivityModule;
import com.sq.sohel.blooddonor.ui.main.advanceSearch.AdvanceSearchDialogProvider;
import com.sq.sohel.blooddonor.ui.main.donor.DonorFragmentProvider;
//import com.sq.sohel.blooddonor.ui.main.newRequestForBlood.NewRequestActivity;
import com.sq.sohel.blooddonor.ui.main.newRequestForBlood.NewRequestActivity;
import com.sq.sohel.blooddonor.ui.main.newRequestForBlood.NewRequestActivityModule;
import com.sq.sohel.blooddonor.ui.main.rating.RateUsDialogProvider;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.RequestAndResponseForBloodActivity;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.RequestAndResponseForBloodActivityModule;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodFragment;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodFragmentProvider;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.requestBloodDialogDetails.RequestBloodDetailsDialogProvider;
import com.sq.sohel.blooddonor.ui.signUp.SignUpActivity;
import com.sq.sohel.blooddonor.ui.signUp.SignUpActivityModule;
import com.sq.sohel.blooddonor.ui.splash.SplashActivity;
import com.sq.sohel.blooddonor.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

//    @ContributesAndroidInjector(modules = {
//            FeedActivityModule.class,
//            BlogFragmentProvider.class,
//            OpenSourceFragmentProvider.class})
//    abstract FeedActivity bindFeedActivity();
//

    @ContributesAndroidInjector(modules = {LoginActivityModule.class, DialogProvider.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {SignUpActivityModule.class, DialogProvider.class})
    abstract SignUpActivity bindSignUpActivity();

    @ContributesAndroidInjector(modules = {ForgotPasswordActivityModule.class, DialogProvider.class})
    abstract ForgotPasswordActivity bindForgotPasswordActivity();

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            DonorFragmentProvider.class,
            AboutFragmentProvider.class,
            RateUsDialogProvider.class,
            AdvanceSearchDialogProvider.class, DialogProvider.class
    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {SplashActivityModule.class, DialogProvider.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {BecomeDonorActivityModule.class, HeroFragmentProvider.class, DialogProvider.class})
    abstract BecomeDonorActivity bindBecomeDonorActivity();

    @ContributesAndroidInjector(modules = {RequestAndResponseForBloodActivityModule.class, RequestBloodFragmentProvider.class, RequestBloodDetailsDialogProvider.class, DialogProvider.class})
    abstract RequestAndResponseForBloodActivity bindRequestAndResponseForBloodActivity();

    @ContributesAndroidInjector(modules = {NewRequestActivityModule.class, DialogProvider.class})
    abstract NewRequestActivity bindNewRequestForBloodActivity();

//    @ContributesAndroidInjector(modules = {DialogModule.class, DialogProvider.class})
//    abstract DialogView bindDialogView();
}
