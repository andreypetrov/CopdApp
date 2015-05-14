package com.petrodevelopment.copdapp.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * A utility class that allows us to extract attributes for our custom views from the xml layouts of the fragments and activities. All attributes extracted in
 * this way should be first defined in values/attrs.xml
 *
 * @author Andrey
 */
public class ViewAttrsUtil {
    private AttributeSet mAttributeSet;
    private Context mContext;
    private int mStylableResourceArray[];

    public ViewAttrsUtil() {
        super();
    }

    public ViewAttrsUtil(Context context, AttributeSet attrs, int[] stylableResourceArray) {
        super();
        init(context, attrs, stylableResourceArray);
    }

    public void init(Context context, AttributeSet attrs, int[] stylableResourceArray) {
        setStylableResourceArray(stylableResourceArray);
        mAttributeSet = attrs;
        mContext = context;
    }

    /**
     * Get an integer from the xml. If it is missing, then return the default value instead
     *
     * @param styleableResourceId
     * @param defaultValue
     * @return
     */
    public int getIntFromXml(int styleableResourceId, int defaultValue) {
        int intFromXml = 0;

        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                intFromXml = attributes.getInt(styleableResourceId, defaultValue);
            } finally {
                attributes.recycle();
            }
        }
        return intFromXml;
    }

    /**
     * Get a boolean from the xml. If it is missing, then return the default value instead
     *
     * @param styleableResourceId
     * @param defaultValue
     * @return
     */
    public boolean getBooleanFromXml(int styleableResourceId, boolean defaultValue) {
        boolean result = false;
        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                result = attributes.getBoolean(styleableResourceId, defaultValue);
            } finally {
                attributes.recycle();
            }
        }
        return result;
    }

    /**
     * Return a dimension pixel size from xml. Assures the dimension size is at least 1px and truncates the float dimension. For more information see
     * getDimensionPixelSize documentation
     *
     * @param styleableResourceId
     * @param defaultValue
     * @return the size in integer pixels
     */
    public int getDimensionPixelSizeFromXml(int styleableResourceId, int defaultValue) {
        int dimensionPixelSizeFromXml = 0;

        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                dimensionPixelSizeFromXml = attributes.getDimensionPixelSize(styleableResourceId, defaultValue);
            } finally {
                attributes.recycle();
            }
        }
        return dimensionPixelSizeFromXml;
    }

    /**
     * Get a resource id from the xml. If it is missing, then return the default value instead
     *
     * @param styleableResourceId
     * @param defaultValue
     * @return resource id as int
     */
    public int getResourceIdFromXml(int styleableResourceId, int defaultValue) {
        int resourceIdFromXml = 0;

        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                resourceIdFromXml = attributes.getResourceId(styleableResourceId, defaultValue);
            } finally {
                attributes.recycle();
            }
        }
        return resourceIdFromXml;
    }

    /**
     * Get a color from the xml. Default is Color.BLACK
     *
     * @param styleableResourceId
     * @return
     */
    public int getColorFromXml(int styleableResourceId) {
        int intFromXml = 0;

        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                intFromXml = attributes.getColor(styleableResourceId, Color.BLACK);
            } finally {
                attributes.recycle();
            }
        }
        return intFromXml;
    }

    /**
     * Get a drawable resource from the xml
     *
     * @param styleableResourceId
     * @return
     */
    public Drawable getDrawableFromXml(int styleableResourceId) {
        Drawable background = null;

        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                background = attributes.getDrawable(styleableResourceId);
            } finally {
                attributes.recycle();
            }
        }
        return background;
    }

    /**
     * Get a float from the xml. If it is not defined in xml, then a default value is used
     *
     * @param styleableResourceId
     * @param defaultValue
     * @return
     */
    public float getFloatFromXml(int styleableResourceId, float defaultValue) {
        float result = 0.0f;

        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                result = attributes.getFloat(styleableResourceId, defaultValue);
            } finally {
                attributes.recycle();
            }
        }
        return result;
    }

    /**
     * Get a string from the xml
     *
     * @param styleableResourceId
     * @return
     */
    public String getStringFromXml(int styleableResourceId) {
        String result = "";

        if (mAttributeSet != null) {
            TypedArray attributes = mContext.getTheme().obtainStyledAttributes(mAttributeSet, mStylableResourceArray, 0, 0);
            try {
                result = attributes.getString(styleableResourceId);
            } finally {
                attributes.recycle();
            }
        }
        return result;
    }

    /**
     * The name of the corresponding stylable resource array from the attrs.xml file. To be set by subclasses in their constructors, before the methods for xml
     * attribute retrieval (e.g. getDrawableFromXml) are used
     *
     * @return
     */
    public int[] getStylableResourceArray() {
        return mStylableResourceArray;
    }

    /**
     * Always call this in the constructor of views that use this helper class
     *
     * @param stylableResourceArray
     */
    public void setStylableResourceArray(int[] stylableResourceArray) {
        this.mStylableResourceArray = stylableResourceArray;
    }
}