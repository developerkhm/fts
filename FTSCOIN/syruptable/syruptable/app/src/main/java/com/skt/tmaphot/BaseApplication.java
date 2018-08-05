package com.skt.tmaphot;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.tsengvn.typekit.Typekit;

import io.fabric.sdk.android.Fabric;

public class BaseApplication extends Application {

    private AppCompatDialog progressDialog;
    private static BaseApplication baseApplication;

    public static BaseApplication getInstance() {
        return baseApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        baseApplication = this;

        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.ttf"));
    }


    public void ActivityStart(Intent intent, Bundle bundle) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        baseApplication.startActivity(intent, bundle);
    }

    public void loadImage(Context context, Object res, ImageView view, boolean isRound) {

        RequestOptions requestOptions = null;
        if(isRound){
            requestOptions = new RequestOptions().transform(new RoundedCorners(100)).diskCacheStrategy(DiskCacheStrategy.NONE);
        }else{
            requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop();
        }

        Glide.with(context)
                .load(res)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(requestOptions)
                .into(view);
    }

//    public static void loadResRoundImage(final Context context, int res, ImageView view) {
//        Glide.with(context)
//                .load(res)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
////                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
//                .apply(new RequestOptions().transform(new RoundedCorners(100)).diskCacheStrategy(DiskCacheStrategy.ALL))
////                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
//                .into(view);
//    }
//
//    public static void loadUriImage(final Context context, Uri uri, ImageView view) {
//        Glide.with(context)
//                .load(uri)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
//                .into(view);
//    }
//
//    public static void loadUrlImage(final Context context, Object url, ImageView view) {
//        Glide.with(context)
//                .load(url)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop())
//                .into(view);
//    }
//
//    public static void loadUrlRoundImage(final Context context, String url, ImageView view) {
//
////        RequestOptions requestOptions = new RequestOptions();
////        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
//
//        Glide.with(context)
//                .load(url)
//
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//
//
////                        Glide.with(context).load(model).into(target);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
////                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop())
//                .apply(new RequestOptions().transform(new RoundedCorners(100)).diskCacheStrategy(DiskCacheStrategy.NONE))
//                .into(view);
//    }

    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();
        }

        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });
//
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_progressbar_loading);
//        img_loading_frame.setAnimation(animation);

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressSET(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
