package technexx.android.ManMachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean gameIsActive;

    Handler handler = new Handler();
    Runnable runnable;
    private boolean yesConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.removeCallbacksAndMessages(runnable);

        final TextView title = findViewById(R.id.title_text);
        final Button startButton = findViewById(R.id.start_game);
        final Button continueButton = findViewById(R.id.continue_game);
        Button previousGames = findViewById(R.id.previous_games);
        Button credits = findViewById(R.id.credits);

        final TextView activeGame = findViewById(R.id.active_game);

        title.setText(R.string.title);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        startButton.setText(R.string.start);

        startButton.setVisibility(View.VISIBLE);
        activeGame.setVisibility(View.INVISIBLE);

        //Greys out and inactivates "Continue" button if a game is not in progress.
        gameIsActive = pref.getBoolean("isActive", false);
        if (!gameIsActive) {
            continueButton.getBackground().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.MULTIPLY);
            continueButton.setTextColor(getResources().getColor(R.color.Light_Grey));
            continueButton.setEnabled(false);
        } else {
            continueButton.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.MULTIPLY);
            continueButton.setTextColor(getResources().getColor(R.color.White));
            continueButton.setEnabled(true);
        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, technexx.android.ManMachine.Game.class);
                intent.putExtra("isContinued", 0);
                gameIsActive = pref.getBoolean("isActive", false);

                if (yesConfirm) {
                    startActivity(intent);
                    finish();
                }
                if (gameIsActive) {
                    startButton.setText(R.string.confirm);
                    activeGame.setVisibility(View.VISIBLE);
                    yesConfirm = true;
                } else {
                    startActivity(intent);
                    editor.apply();
                    finish();
                }
                editor.putBoolean("isActive", true);
                editor.apply();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, technexx.android.ManMachine.Game.class);
                intent.putExtra("isContinued", 1);
                startActivity(intent);
                finish();
            }
        });

        previousGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Layout for both score fragments. These are separate from the RecyclerView and item pop lists.
                Intent intent = new Intent(MainActivity.this, technexx.android.ManMachine.HighScoresMenu.class);
                startActivity(intent);
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, technexx.android.ManMachine.Credits.class);
                startActivity(intent);
            }
        });
    }
}
