package com.qs.board.preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.preference.Preference;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qs.board.R;
import com.qs.board.utils.ThemeUtils;

public class ThemePreference extends Preference {

    private String[] themeOptions;
    private String[] themeValues;
    private Context mContext;

    public ThemePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private ThemePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.preference_theme);
        setSummary(ThemeUtils.resolveColor(context));
        mContext = context;
    }

    private static int resolveColor(int pos) {

        switch (pos) {
            default:
            case 0:
                return R.color.accent;

            case 1:
                return R.color.material_red_400;

            case 2:
                return R.color.material_pink_400;

            case 3:
                return R.color.material_purple_400;

            case 4:
                return R.color.material_deepPurple_400;

            case 5:
                return R.color.material_indigo_400;

            case 6:
                return R.color.material_blue_400;

            case 7:
                return R.color.material_lightBlue_400;

            case 8:
                return R.color.material_cyan_400;

            case 9:
                return R.color.material_teal_400;

            case 10:
                return R.color.material_green_400;

            case 11:
                return R.color.material_amber_400;

            case 12:
                return R.color.material_orange_400;

            case 13:
                return R.color.material_deepOrange_400;

            case 14:
                return R.color.material_brown_400;

            case 15:
                return R.color.material_blueGrey_400;

        }
    }

    public static void createCircularPreferenceBitmap(Boolean isImage, Preference preference, ImageView imageView, Context context, int color) {

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        int dimen = (int) context.getResources().getDimension(android.R.dimen.app_icon_size);
        Bitmap bmp = Bitmap.createBitmap(dimen, dimen, conf);

        if (isImage) {
            imageView.setBackground(createRoundedBitmapDrawable(bmp, color, context.getResources()));
        } else {
            preference.setIcon(createRoundedBitmapDrawable(bmp, color, context.getResources()));

        }
    }

    private static RoundedBitmapDrawable createRoundedBitmapDrawable(Bitmap bitmap, int color, Resources mResources) {

        bitmap.eraseColor(color);

        // Create a new RoundedBitmapDrawable
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, bitmap);

        roundedBitmapDrawable.setCircular(true);

        roundedBitmapDrawable.setAntiAlias(true);

        // Return the RoundedBitmapDrawable
        return roundedBitmapDrawable;
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        init();
        return super.onCreateView(parent);
    }

    @Override
    protected void onClick() {
        super.onClick();
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final ThemeAdapter adapter = new ThemeAdapter(mContext, themeValues, themeOptions, getPersistedString("0"));

        builder.setTitle(R.string.pref_theme_title);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                String item = adapter.getItem(position);
                persistString(item);
            }
        });

        builder.show();
    }

    private void init() {

        themeOptions = mContext.getResources().getStringArray(R.array.theme_options);
        themeValues = mContext.getResources().getStringArray(R.array.pref_theme_list_values);
        int color = ContextCompat.getColor(mContext, ThemeUtils.resolveColor(mContext));
        createCircularPreferenceBitmap(false, this, null, mContext, color);

        setDefaultValue(0);
    }


    private static class ThemeAdapter extends BaseAdapter {

        LayoutInflater mLayoutInflater;
        String[] themeValues;
        Context context;
        String selectedTheme;
        String[] themeOptions;

        ThemeAdapter(Context context, String[] themeValues, String[] themeOptions, String currentTheme) {

            mLayoutInflater = LayoutInflater.from(context);
            this.themeValues = themeValues;
            this.themeOptions = themeOptions;
            this.context = context;
            selectedTheme = currentTheme;
        }

        @Override
        public int getCount() {
            return themeOptions.length;
        }

        @Override
        public String getItem(int position) {
            return themeValues[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                final ViewGroup nullParent = null;
                convertView = mLayoutInflater.inflate(R.layout.theme_dialog, nullParent);
            }

            String theme = themeOptions[position];

            TextView txtView = convertView.findViewById(R.id.title);
            txtView.setText(theme);

            RadioButton radioButton = convertView.findViewById(R.id.radio);

            int itemColor = ContextCompat.getColor(context, resolveColor(position));

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_enabled}, //enabled
                            new int[]{android.R.attr.state_enabled} //disabled

                    },
                    new int[]{itemColor, itemColor}
            );

            txtView.setShadowLayer(1.5f, -1, 1, itemColor);
            radioButton.setButtonTintList(colorStateList);

            radioButton.setChecked(themeValues[position].equals(selectedTheme));
            return convertView;
        }
    }
}

