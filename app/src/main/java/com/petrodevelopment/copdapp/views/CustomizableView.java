package com.petrodevelopment.copdapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * DEPRECATED!!!
 * 
 * A view that reads from layouts TODO: make this a util singleton for extracting the data from the attrs.xml. Initialize it with context and attrss in MainApp
 * or in the first activity and then use it in views like FlightItinerary and NumberPicker
 * 
 * @author andrey
 * 
 */
public abstract class CustomizableView extends View {
	private AttributeSet mAttributeSet;
	private Context mContext;
	private int mStylableResourceArray[];

	public CustomizableView(Context context) {
		super(context);
		initMembers(context, null);
	}

	public CustomizableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initMembers(context, attrs);
	}

	public CustomizableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initMembers(context, attrs);
	}

	private void initMembers(Context context, AttributeSet attrs) {
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
	protected int getIntFromXml(int styleableResourceId, int defaultValue) {
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

	protected int getColorFromXml(int styleableResourceId) {
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

	protected Drawable getDrawableFromXml(int styleableResourceId) {
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

	
	protected float getFloatFromXml(int styleableResourceId, float defaultValue) {
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
	
	
	protected String getStringFromXml(int styleableResourceId) {
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
	 * Always call this in the constructor of subclasses
	 * 
	 * @param stylableResourceArray
	 */
	public void setStylableResourceArray(int[] stylableResourceArray) {
		this.mStylableResourceArray = stylableResourceArray;
	}
}
