package technexx.android.ManMachine.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { HighScores.class, ScoresMenu.class, ScoreResults.class }, version = 1, exportSchema = false)


public abstract class HighScoresDB extends RoomDatabase {

    private static HighScoresDB INSTANCE;

    public static HighScoresDB getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    HighScoresDB.class, "highScores_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract HighScoresDao highScoresDao();

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
