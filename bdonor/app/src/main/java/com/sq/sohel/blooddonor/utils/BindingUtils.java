

package com.sq.sohel.blooddonor.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.ui.custom.RoundedImageView;
import com.sq.sohel.blooddonor.ui.main.donor.DonorAdapter;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodAdapter;

import java.util.List;

import static com.sq.sohel.blooddonor.utils.AppConstants.BLOOD_GROUP_LIST;
import static com.sq.sohel.blooddonor.utils.CommonUtils.getImageFromDrawable;


public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }


    @BindingAdapter("android:numStars")
    public static void setRatingBarInt(RatingBar view, Integer value) {
        view.setRating(value);
    }

    @InverseBindingAdapter(attribute = "android:numStars", event = "android:numStarsAttrChanged")
    public static Integer getRatingBarInt(RatingBar view) {
        return (int) view.getRating();
    }
    @BindingAdapter({"android:numStarsAttrChanged"})
    public static void setNumStarsAttrChanged(RatingBar view, final InverseBindingListener listener) {
       view.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               listener.onChange();
           }
       });
    }

    @BindingAdapter("android:text")
    public static void setEditTextInt(EditText view, int value) {
        view.setText(Integer.toString(value));
    }


    @BindingAdapter("android:text")
    public static void setTextViewInt(TextView view, int value) {
        view.setText(Integer.toString(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getTextViewInt(TextView view) {
        if (view.getText() != null && (!view.getText().toString().isEmpty())) {
            return Integer.parseInt(view.getText().toString());
        } else {
            return Integer.valueOf(0);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addDonorCardItems(RecyclerView recyclerView, List<DonorCardData> donors) {
        DonorAdapter adapter = (DonorAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(donors);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRequestBloodCardItems(RecyclerView recyclerView, List<RequestBloodCardData> requestBloodCardData) {
        RequestBloodAdapter adapter = (RequestBloodAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(requestBloodCardData);
        }
    }

    @BindingAdapter({"imageUrl", "imageData", "defaultImage"})
    public static void setImageUrl(ImageView imageView, ObservableField<String> url, ObservableField<byte[]> imageData, String defaultImage) {
        Context context = imageView.getContext();
        if (imageData != null && imageData.get() != null) {
            Glide.with(context).load(imageData.get()).into(imageView);
        } else {
            if (url != null && url.get() != null) {
                if (URLUtil.isValidUrl(url.get())) {
                    Glide.with(context).load(url.get()).into(imageView);
                } else {
                    Glide.with(context).load(context.getResources().getDrawable(getImageFromDrawable(context, String.valueOf(defaultImage)))).into(imageView);
                }
            } else {
                Glide.with(context).load(context.getResources().getDrawable(getImageFromDrawable(context, String.valueOf(defaultImage)))).into(imageView);
            }
        }

    }

    @BindingAdapter({"imageUrl", "imageData", "defaultImage"})
    public static void setImageUrl(RoundedImageView imageView, ObservableField<String> url, ObservableField<byte[]> imageData, String defaultImage) {
        Context context = imageView.getContext();
        if (imageData != null && imageData.get() != null) {
            Glide.with(context).load(imageData.get()).into(imageView);
        } else {
            if (url != null && url.get() != null) {
                if (URLUtil.isValidUrl(url.get())) {
                    Glide.with(context).load(url.get()).into(imageView);
                } else {
                    Glide.with(context).load(context.getResources().getDrawable(getImageFromDrawable(context, String.valueOf(defaultImage)))).into(imageView);
                }
            } else {
                Glide.with(context).load(context.getResources().getDrawable(getImageFromDrawable(context, String.valueOf(defaultImage)))).into(imageView);
            }
        }

    }


    @BindingAdapter({"bloodGroup", "isCharImage"})
    public static void setImageForBloodGroup(ImageView imageView, String bloodGroup, String isCharImage) {
        if (CommonUtils.contains2(BLOOD_GROUP_LIST, bloodGroup.toUpperCase())) {
            Context context = imageView.getContext();
            switch (bloodGroup.toUpperCase()) {
                case "A+":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_a_positive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.a_positive).into(imageView);
                    }
                    break;
                case "B+":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_b_positive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.b_positive).into(imageView);
                    }

                    break;
                case "AB+":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_ab_positive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.ab_positive).into(imageView);
                    }
                    break;
                case "O+":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_o_positive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.o_positive).into(imageView);
                    }
                    break;
                case "A-":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_a_negetive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.a_negetive).into(imageView);
                    }
                    break;
                case "B-":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_b_negetive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.b_negetive).into(imageView);
                    }
                    break;
                case "AB-":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_ab_negetive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.ab_negetive).into(imageView);
                    }
                    break;
                case "O-":
                    if (Boolean.valueOf(isCharImage)) {
                        Glide.with(context).load(R.drawable.char_o_negetive).into(imageView);
                    } else {
                        Glide.with(context).load(R.drawable.o_negetive).into(imageView);
                    }
                    break;
            }
        }
    }


}
