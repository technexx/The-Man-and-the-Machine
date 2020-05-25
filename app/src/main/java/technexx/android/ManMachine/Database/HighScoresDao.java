package technexx.android.ManMachine.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao

public interface HighScoresDao {

    //Getting from Table "tableName" in HighScores class.

//    @Query ("SELECT * from HIGHSCORES WHERE id =:highScoreID")
//    int getHighScore(int highScoreID);

    @Query ("SELECT * from HIGHSCORES WHERE id =:highScoreID")
    List<HighScores> loadScores(int highScoreID);

    @Query ("SELECT * from SCORESMENU WHERE id =:scoresMenuID")
    List<ScoresMenu> getScoresMenu(int scoresMenuID);

    @Query ("SELECT * from SCORERESULTS WHERE id=:scoreResultsID")
    List<ScoreResults> getScoreResults(int scoreResultsID);

    @Query ("SELECT * from HIGHSCORES")
    List<HighScores> loadAllScores();

    @Query ("SELECT * from SCORESMENU")
    List<ScoresMenu> scoreList();

    @Query ("SELECT * from SCORESMENU order by totalScore desc")
    List<ScoresMenu> sortedScoreList();

    @Query ("SELECT * from SCORERESULTS")
    List<ScoreResults> scoreResults();

    @Query ("SELECT creatureStats from SCORERESULTS WHERE id=:scoreResultsID")
    String creatureStats(int scoreResultsID);

    @Query ("SELECT strValue from SCORERESULTS WHERE id=:scoreResultsID")
    int str(int scoreResultsID);

    @Query ("SELECT dexValue from SCORERESULTS WHERE id=:scoreResultsID")
    int dex(int scoreResultsID);

    @Query ("SELECT intValue from SCORERESULTS WHERE id=:scoreResultsID")
    int intel(int scoreResultsID);

    @Query ("SELECT obedValue from SCORERESULTS WHERE id=:scoreResultsID")
    int obed(int scoreResultsID);

    @Query ("SELECT beaValue from SCORERESULTS WHERE id=:scoreResultsID")
    int bea(int scoreResultsID);

    @Query ("SELECT quirkOne from SCORERESULTS WHERE id=:scoreResultsID")
    int q1(int scoreResultsID);

    @Query ("SELECT quirkTwo from SCORERESULTS WHERE id=:scoreResultsID")
    int q2(int scoreResultsID);

    @Query ("SELECT quirkThree from SCORERESULTS WHERE id=:scoreResultsID")
    int q3(int scoreResultsID);


    @Query("SELECT strScore from HIGHSCORES WHERE id=:scoreResultsID")
    int strScore(int scoreResultsID);

    @Query("SELECT dexScore from HIGHSCORES WHERE id=:scoreResultsID")
    int dexScore(int scoreResultsID);

    @Query("SELECT intScore from HIGHSCORES WHERE id=:scoreResultsID")
    int intScore(int scoreResultsID);

    @Query("SELECT obedScore from HIGHSCORES WHERE id=:scoreResultsID")
    int obedScore(int scoreResultsID);

    @Query("SELECT beaScore from HIGHSCORES WHERE id=:scoreResultsID")
    int beaScore(int scoreResultsID);

    @Query("SELECT sanScore from HIGHSCORES WHERE id=:scoreResultsID")
    int sanScore(int scoreResultsID);

    @Query("SELECT endScore from HIGHSCORES WHERE id=:scoreResultsID")
    int endScore(int scoreResultsID);

    @Query("SELECT comScore from HIGHSCORES WHERE id=:scoreResultsID")
    int comScore(int scoreResultsID);

    @Query("SELECT anonScore from HIGHSCORES WHERE id=:scoreResultsID")
    int anonSCore(int scoreResultsID);

    @Query("SELECT launchSuccess from HIGHSCORES WHERE id=:scoreResultsID")
    int launchSuccess(int scoreResultsID);

    @Query("SELECT terScore from HIGHSCORES WHERE id=:scoreResultsID")
    int terScore(int scoreResultsID);

    @Query("SELECT supScore from HIGHSCORES WHERE id=:scoreResultsID")
    int supScore(int scoreResultsID);

    @Query("SELECT endSceneScore from HIGHSCORES WHERE id=:scoreResultsID")
    int endSceneScore(int scoreResultsID);

    @Query("SELECT totalScore from HIGHSCORES WHERE id=:scoreResultsID")
    int totalScore(int scoreResultsID);

    @Query("SELECT summary from HIGHSCORES WHERE id=:scoreResultsID")
    String summary(int scoreResultsID);

    @Query("SELECT endRank from HIGHSCORES WHERE id=:scoreResultsID")
    String endRank(int scoreResultsID);

    @Insert
    void insert(HighScores highScores);

    @Update
    void update(HighScores highScores);

    @Delete
    void delete(HighScores highScores);

    @Insert
    void insert(ScoresMenu scoresMenu);

    @Update
    void update(ScoresMenu scoresMenu);

    @Delete
    void delete(ScoresMenu scoresMenu);

    @Insert
    void insert (ScoreResults scoreResults);

    @Update
    void update (ScoreResults scoreResults);

    @Delete
    void delete (ScoreResults scoreResults);

}

