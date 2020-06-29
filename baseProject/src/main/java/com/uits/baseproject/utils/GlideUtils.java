package com.uits.baseproject.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.uits.baseproject.R;

public class GlideUtils {

    /**
     * show image view
     *
     * @param context
     * @param mImgAvatar
     * @param url
     */
    public static final void showImageView(Context context, ImageView mImgAvatar, String url, int drawableError) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.color.colorPrimary);
        options.dontAnimate();
        options.dontTransform();
        options.centerCrop();
        options.error(drawableError);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(mImgAvatar);
    }

    /**
     * drawable
     *
     * @param context
     * @param mImgAvatar
     * @param url
     */
    public static final void showImageView(Context context, ImageView mImgAvatar, int url, int drawableError) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.color.colorPrimary);
        options.dontAnimate();
        options.dontTransform();
        options.centerCrop();
        options.error(drawableError);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(mImgAvatar);
    }

}
