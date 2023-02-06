package technexx.android.ManMachine;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {

    OnGameStart mOnGameStart;
    private int splashCounter;

    private String[] splashArray;

    private int mPhoneHeight;
    private int mPhoneWidth;

    //Callback to begin the game.
    //Can be used for end game as well.
    public interface OnGameStart {
        void onFirstDay();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnGameStart = (OnGameStart) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement OnGameStart Callback");
        }
    }

    private void setPhoneDimensions() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mPhoneHeight = metrics.heightPixels;
        mPhoneWidth = metrics.widthPixels;
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View root;

        setPhoneDimensions();

        if (mPhoneHeight <=1920) {
            root = inflater.inflate(R.layout.splash_fragment, container, false);
        } else {
            root = inflater.inflate(R.layout.splash_fragment_h1920, container, false);
        }

        final Button b1 = root.findViewById(R.id.splash_button);
        final Button b2 = root.findViewById(R.id.splash_start);
        final Button b3 = root.findViewById(R.id.skip_intro);
        final TextView splashScreen = root.findViewById(R.id.splash_text);
        final TextView splashTwo = root.findViewById(R.id.splash_text_two);
        final TextView splashThree = root.findViewById(R.id.splash_text_three);
        final TextView splashFour = root.findViewById(R.id.splash_text_four);
        final TextView splashFive = root.findViewById(R.id.splash_text_five);
        final TextView splashSix = root.findViewById(R.id.splash_text_six);

        splashTwo.setVisibility(View.GONE);
        splashThree.setVisibility(View.GONE);
        splashFour.setVisibility(View.GONE);
        splashFive.setVisibility(View.GONE);
        splashSix.setVisibility(View.GONE);


        splashArray = getResources().getStringArray(R.array.intro);

        b1.setText(R.string.next);
        b2.setText(R.string.next);
        b2.setVisibility(View.GONE);

        final Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        splashScreen.startAnimation(fadeIn);
        splashScreen.setText(splashArray[splashCounter]);
        splashCounter = 1;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (splashCounter < splashArray.length) {
                    switch (splashCounter) {
                        case 1:
                            splashTwo.setVisibility(View.VISIBLE);
                            splashTwo.startAnimation(fadeIn);
                            splashTwo.setText(splashArray[1]);
                            splashCounter = 2;
                            return;
                        case 2:
                            splashThree.setVisibility(View.VISIBLE);
                            splashThree.startAnimation(fadeIn);
                            splashThree.setText(splashArray[2]);
                            splashCounter = 3;
                            return;
                        case 3:
                            splashFour.setVisibility(View.VISIBLE);
                            splashFour.startAnimation(fadeIn);
                            splashFour.setText(splashArray[3]);
                            splashCounter = 4;
                            return;
                        case 4:
                            splashFive.setVisibility(View.VISIBLE);
                            splashFive.startAnimation(fadeIn);
                            splashFive.setText(splashArray[4]);
                            splashCounter = 5;
                            return;
                        case 5:
                            splashSix.setVisibility(View.VISIBLE);
                            splashSix.startAnimation(fadeIn);
                            splashSix.setText(splashArray[5]);
                            b1.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                mOnGameStart.onFirstDay();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnGameStart.onFirstDay();
            }
        });

        return root;
    }
}