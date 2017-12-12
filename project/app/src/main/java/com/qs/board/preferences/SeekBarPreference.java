package com.qs.board.preferences;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.qs.board.R;

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

    public static int getSeekBarValue(Context context) {

        int DEFAULT_VALUE = 200;

        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PreferenceKeys.ALPHA_KEY, DEFAULT_VALUE);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        int MAX = 255;

        int progress = getSeekBarValue(mContext);
        SeekBar seekBar = view.findViewById(R.id.seekbar);
        mValue = view.findViewById(R.id.summary);

        mValue.setText(mContext.getString(R.string.board_alpha) + String.valueOf(progress));
        seekBar.setMax(MAX);

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
