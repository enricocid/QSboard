package com.qs.board.preferences;

import android.content.Context;
import android.graphics.PorterDuff;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.qs.board.R;
import com.qs.board.utils.ThemeUtils;

public class SeekBarPreference extends Preference implements OnSeekBarChangeListener {

    private Context mContext;
    private TextView mValue;

    public SeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setLayoutResource(R.layout.preference_seekbar);
    }

    public static int getAlphaValue(Context context) {

        int DEFAULT_VALUE = 200;

        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PreferenceKeys.ALPHA_KEY, DEFAULT_VALUE);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        int progress = getAlphaValue(mContext);
        SeekBar seekBar = view.findViewById(R.id.seekbar);
        mValue = view.findViewById(R.id.summary);

        int accentColor = ContextCompat.getColor(mContext, ThemeUtils.resolveColor(mContext));
        mValue.setTextColor(accentColor);
        seekBar.getProgressDrawable().setColorFilter(accentColor, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(accentColor, PorterDuff.Mode.SRC_IN);

        mValue.setText(mContext.getString(R.string.board_alpha) + String.valueOf(progress));
        seekBar.setMax(255);

        seekBar.setProgress(progress);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        mValue.setText(mContext.getString(R.string.board_alpha) + String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // not used
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        mValue.setText(mContext.getString(R.string.board_alpha) + String.valueOf(seekBar.getProgress()));

        setValue(seekBar.getProgress());
    }

    private void setValue(int value) {
        PreferenceManager.getDefaultSharedPreferences(mContext).edit().putInt(PreferenceKeys.ALPHA_KEY, value).apply();
    }
}
