package com.petrodevelopment.copdapp.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.petrodevelopment.copdapp.R;

/**
 * Created by andrey on 29/05/2015.
 */
public class Animation {

    public static void changeImageSlideFromLeftInstant(Context context, final ImageView imageView, final Bitmap newImage) {
        slideThenInstantAnimation(context, imageView, newImage, R.anim.slide_out_right);
    }
    public static void changeImageSlideFromRightInstant(Context context, final ImageView imageView, final Bitmap newImage) {
        slideThenInstantAnimation(context, imageView, newImage, R.anim.slide_out_left);
    }


    public static void changeImageFadeOutFadeIn(Context context, final ImageView imageView, final Bitmap newImage) {
        imageViewAnimatedChange(context, imageView, newImage, android.R.anim.fade_out, android.R.anim.fade_in);
    }

    public static void changeImageSlideFromLeft(Context context, final ImageView imageView, final Bitmap newImage) {
        imageViewAnimatedChange(context, imageView, newImage, R.anim.slide_out_right, R.anim.slide_in_left);
    }
    public static void changeImageSlideFromRight(Context context, final ImageView imageView, final Bitmap newImage) {
        imageViewAnimatedChange(context, imageView, newImage, R.anim.slide_out_left, R.anim.slide_in_right);
    }

    public static void changeImageSlideFromRightFadeIn(Context context, final ImageView imageView, final Bitmap newImage) {
        imageViewAnimatedChange(context, imageView, newImage, R.anim.slide_out_left, R.anim.abc_fade_in);
    }

    public static void changeImageSlideFromLeftFadeIn(Context context, final ImageView imageView, final Bitmap newImage) {
        imageViewAnimatedChange(context, imageView, newImage, R.anim.slide_out_left, R.anim.abc_fade_in);
    }


    /**
     * Similar to the one here:
     * http://stackoverflow.com/questions/7161500/creating-animation-on-imageview-while-changing-image-resource
     *
     * @param c
     * @param v
     * @param new_image
     * @param resourseAnimationOut
     */
    public static void slideThenInstantAnimation(Context c,
                                               final ImageView v,
                                               final Bitmap new_image,
                                               int resourseAnimationOut) {
        final android.view.animation.Animation anim_out = AnimationUtils.loadAnimation(c, resourseAnimationOut);
        anim_out.setAnimationListener(new android.view.animation.Animation.AnimationListener()
        {
            @Override public void onAnimationStart(android.view.animation.Animation animation) {
                v.setImageBitmap(new_image);
            }
            @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}
            @Override public void onAnimationEnd(android.view.animation.Animation animation)
            {

            }
        });
        v.startAnimation(anim_out);
    }



























    /**
     * Similar to the one here:
     * http://stackoverflow.com/questions/7161500/creating-animation-on-imageview-while-changing-image-resource
     *
     * @param c
     * @param v
     * @param new_image
     * @param resourseAnimationOut
     * @param resourceAnimationIn
     */
    public static void imageViewAnimatedChange(Context c,
                                               final ImageView v,
                                               final Bitmap new_image,
                                               int resourseAnimationOut,
                                               int resourceAnimationIn) {
        final android.view.animation.Animation anim_out = AnimationUtils.loadAnimation(c, resourseAnimationOut);
        final android.view.animation.Animation anim_in  = AnimationUtils.loadAnimation(c, resourceAnimationIn);
        anim_out.setAnimationListener(new android.view.animation.Animation.AnimationListener()
        {
            @Override public void onAnimationStart(android.view.animation.Animation animation) {}
            @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}
            @Override public void onAnimationEnd(android.view.animation.Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                    @Override public void onAnimationStart(android.view.animation.Animation animation) {}
                    @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}
                    @Override public void onAnimationEnd(android.view.animation.Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    public static void changeImageSlideFromLeft(Context context,
                                                ImageView frontImageView,
                                                ImageView backImageView,
                                                Bitmap bitmap) {

        imageViewAnimatedChange(context, frontImageView, backImageView, bitmap, R.anim.slide_out_right, android.R.anim.fade_in);
    }

    public static void changeImageSlideFromRight(Context context,
                                                 ImageView frontImageView,
                                                 ImageView backImageView,
                                                 Bitmap bitmap) {
        imageViewAnimatedChange(context, frontImageView, backImageView, bitmap, R.anim.slide_out_left, android.R.anim.fade_in);
    }


    /**
     *
     * @param context
     * @param frontView
     * @param backView
     * @param newBitmap
     * @param resourseAnimationOut
     * @param resourceAnimationIn
     */
    public static void imageViewAnimatedChange(Context context,
                                               final ImageView frontView,
                                               final ImageView backView,
                                               final Bitmap newBitmap,
                                               int resourseAnimationOut,
                                               int resourceAnimationIn) {
        final android.view.animation.Animation anim_out = AnimationUtils.loadAnimation(context, resourseAnimationOut);
        final android.view.animation.Animation anim_in  = AnimationUtils.loadAnimation(context, resourceAnimationIn);

        anim_out.setAnimationListener(new android.view.animation.Animation.AnimationListener()
        {
            @Override public void onAnimationStart(android.view.animation.Animation animation) {
                backView.setImageBitmap(newBitmap);
                backView.setVisibility(View.VISIBLE);
            }
            @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}
            @Override public void onAnimationEnd(android.view.animation.Animation animation)
            {
                frontView.setVisibility(View.GONE);
//                anim_in.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
//                    @Override public void onAnimationStart(android.view.animation.Animation animation) {}
//                    @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}
//                    @Override public void onAnimationEnd(android.view.animation.Animation animation) {}
//                });
//                backView.startAnimation(anim_in);
            }
        });
        //this starts first
        frontView.startAnimation(anim_out);
    }

}
