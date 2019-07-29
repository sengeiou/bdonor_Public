//
//package com.sq.sohel.blooddonor.ui.main;
//
//import android.content.res.Resources;
//import android.graphics.Color;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.androidnetworking.widget.ANImageView;
//import com.sq.sohel.blooddonor.R;
////import com.sq.sohel.blooddonor.data.model.db.Option;
//import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
//import com.mindorks.placeholderview.annotations.Click;
//import com.mindorks.placeholderview.annotations.Layout;
//import com.mindorks.placeholderview.annotations.NonReusable;
//import com.mindorks.placeholderview.annotations.Resolve;
//import com.mindorks.placeholderview.annotations.View;
//import com.sq.sohel.blooddonor.utils.AppConstants;
//
//
//@NonReusable
//@Layout(R.layout.card_layout)
//public class QuestionCard {
//
//    @View(R.id.btn_option_1)
//    private Button mOption1Button;
//
//    @View(R.id.btn_option_2)
//    private Button mOption2Button;
//
//    @View(R.id.btn_option_3)
//    private Button mOption3Button;
//
//    @View(R.id.iv_pic)
//    private ANImageView mPicImageView;
//
//    private DonorCardData mQuestionCardData;
//
//    @View(R.id.tv_question_txt)
//    private TextView mQuestionTextView;
//
//    public QuestionCard(DonorCardData questionCardData) {
//        mQuestionCardData = questionCardData;
//    }
//
//    @Click(R.id.btn_option_1)
//    public void onOption1Click() {
//        showCorrectOptions();
//    }
//
//    @Click(R.id.btn_option_2)
//    public void onOption2Click() {
//        showCorrectOptions();
//    }
//
//    @Click(R.id.btn_option_3)
//    public void onOption3Click() {
//        showCorrectOptions();
//    }
//
//    @Resolve
//    private void onResolved() {
//        mQuestionTextView.setText(mQuestionCardData.donor.Name);
//        if (mQuestionCardData.mShowCorrectOptions) {
//            showCorrectOptions();
//        }
//
//        for (int i = 0; i < 3; i++) {
//            Button button = null;
//            switch (i) {
//                case 0:
//                    button = mOption1Button;
//                    break;
//                case 1:
//                    button = mOption2Button;
//                    break;
//                case 2:
//                    button = mOption3Button;
//                    break;
//            }
//
//            if (button != null) {
//                //button.setText(mQuestionCardData.options.get(i).optionText);
//                button.setText(mQuestionCardData.donor.ContactNumber);
//            }
//
////            if (mQuestionCardData.question.imgUrl != null) {
//                mPicImageView.setImageUrl(AppConstants.DUMMY_IMAGE);
////            }
//        }
//    }
//
//    private void showCorrectOptions() {
//        mQuestionCardData.mShowCorrectOptions = true;
////        for (int i = 0; i < 3; i++) {
////            Option option = mQuestionCardData.options.get(i);
////            Button button = null;
////            switch (i) {
////                case 0:
////                    button = mOption1Button;
////                    break;
////                case 1:
////                    button = mOption2Button;
////                    break;
////                case 2:
////                    button = mOption3Button;
////                    break;
////            }
////            if (button != null) {
////                if (option.isCorrect) {
////                    button.setBackgroundColor(Color.GREEN);
////                } else {
////                    button.setBackgroundColor(Color.RED);
////                }
////            }
////        }
//    }
//}
