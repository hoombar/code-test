package practical.test.com.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private Handler mHandler;
    private int mI;
    private long[] mPattern;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        ((EditText) view.findViewById(R.id.input_text)).setOnKeyListener(new View.OnKeyListener() {
            @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        ((EditText) view.findViewById(R.id.input_text)).addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override public void afterTextChanged(Editable s) {

            }
        });


        view.findViewById(R.id.btn_run).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mPattern = MorseCodeConverter.pattern(((EditText) view.findViewById(R.id.input_text)).getText().toString());

                // Start the vibration
                Vibrator vibrator = (Vibrator) MainActivity.sInstance.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(mPattern, -1);

                mI = 0;
                long blink = mPattern[mI];
                long duration = blink;

                blink(view, duration);
            }
        });

        return view;
    }

    private void blink(final View view, long duration) {

        if (duration != 100) {
            view.findViewById(R.id.blinking_canvas).setBackgroundColor(getResources().getColor(android.R.color.black));
        }
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                view.findViewById(R.id.blinking_canvas).setBackgroundColor(getResources().getColor(android.R.color.white));

                if (mI <= mPattern.length) {
                    mI++;
                    long blink = mPattern[mI];
                    long duration = blink;
                    blink(view, duration);
                }
            }
        }, duration);
    }
}
