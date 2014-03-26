package ibiapi.richbastard;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer
{
    private static MediaPlayer mainMediaPlayer, extraMediaPlayer;
    private static Context mainContext;

    public static void init(Context context)
    {
        mainContext = context;
    }

    public static void playPreQuestion(int question)
    {
    	stopPlaying();
        int soundID = (question == 6 ? R.raw.pre_question6_11 : R.raw.pre_question7);
        play(mainMediaPlayer, soundID);
    }

    public static void playQuestion(int question)
    {
    	stopPlaying();
        int soundID = (question == 6 ? R.raw.question6 : R.raw.question7);
        play(mainMediaPlayer, soundID);
    }
    
    public static void playFinalAnswer(int question)
    {
    	stopPlaying();
        int soundID = (question == 6 ? R.raw.final_answer6 : R.raw.final_answer7_12);
        play(mainMediaPlayer, soundID);
    }

    public static void stopPlaying()
    {
        releasePlayer(mainMediaPlayer);
        releasePlayer(extraMediaPlayer);
    }

    public static void playCorrect(int question)
    {
        stopPlaying();
        int soundID = (question == 6 ? R.raw.correct_answer6 : R.raw.correct_answer7);
        play(mainMediaPlayer, soundID);
    }
    
    public static void playWrong(int question)
    {
        stopPlaying();
        int soundID = (question == 6 ? R.raw.wrong_answer6 : R.raw.wrong_answer7);
        play(mainMediaPlayer, soundID);
    }

    public static void playFiftyFifty()
    {
    	extraMediaPlayer = MediaPlayer.create(mainContext, R.raw.fifty_fifty);
    	extraMediaPlayer.setLooping(false);
    	extraMediaPlayer.start();
    }
    
    private static void play(MediaPlayer player, int soundID)
    {
    	mainMediaPlayer = MediaPlayer.create(mainContext, soundID);
    	mainMediaPlayer.setLooping(false);
    	mainMediaPlayer.start();
    }

    private static void releasePlayer(MediaPlayer player)
    {
        if (mainMediaPlayer != null)
        {
        	mainMediaPlayer.stop();
        	mainMediaPlayer.release();
        	mainMediaPlayer = null;
        }
    }

}

