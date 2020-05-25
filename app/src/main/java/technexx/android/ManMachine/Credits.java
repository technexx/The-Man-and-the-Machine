package technexx.android.ManMachine;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        TextView credits_one = findViewById(R.id.credits_one);
        TextView credits_Two = findViewById(R.id.credits_two);
        TextView credits_three = findViewById(R.id.credits_three);
        TextView credits_four = findViewById(R.id.credits_four);
        TextView seedLink = findViewById(R.id.seed_link);

        credits_one.setText(R.string.credits_one);
        credits_Two.setText(R.string.credits_two);
        credits_three.setText(R.string.credits_three);
        credits_four.setText(R.string.credits_four);


        seedLink.setText(R.string.test_link);
        seedLink.setMovementMethod(LinkMovementMethod.getInstance());

        seedLink.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());

        seedLink.setTextColor(getResources().getColor(R.color.Light_Green));

    }
}