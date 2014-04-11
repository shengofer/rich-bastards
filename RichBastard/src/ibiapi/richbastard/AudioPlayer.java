package ibiapi.richbastard;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer
{
	private static AudioPlayer mAudioPlayer = null;
	
    private MediaPlayer musicMediaPlayer, effectMediaPlayer;
    private Context mainContext;
    
    private boolean musicEnabled, effectsEnabled;
    
    public enum Theme
    {
    	Classic,
    	Original
    }
    
    private AudioPlayer() {}
    
    public static AudioPlayer getInstance()
    {
    	if (mAudioPlayer == null)
    	{
    		mAudioPlayer = new AudioPlayer();
    	}
    	return mAudioPlayer;
    }

    public void setContext(Context context)
    {
        mainContext = context;
    }

    public void playPreQuestion(int question)
    {
    	stopPlaying();
        int soundID = (question == 6 ? R.raw.pre_question6_11 : R.raw.pre_question7);
        play(musicMediaPlayer, soundID);
    }

    public void playQuestion(int question)
    {
    	stopPlaying();
        int soundID = (question == 6 ? R.raw.question6 : R.raw.question7);
        play(musicMediaPlayer, soundID);
    }
    
    public void playFinalAnswer(int question)
    {
    	stopPlaying();
        int soundID = (question == 6 ? R.raw.final_answer6 : R.raw.final_answer7_12);
        play(musicMediaPlayer, soundID);
    }

    public void stopPlaying()
    {
        releasePlayer(musicMediaPlayer);
        releasePlayer(effectMediaPlayer);
    }

    public void playCorrect(int question)
    {
        stopPlaying();
        int soundID = (question == 6 ? R.raw.correct_answer6 : R.raw.correct_answer7);
        play(musicMediaPlayer, soundID);
    }
    
    public void playWrong(int question)
    {
        stopPlaying();
        int soundID = (question == 6 ? R.raw.wrong_answer6 : R.raw.wrong_answer7);
        play(musicMediaPlayer, soundID);
    }

    public void playFiftyFifty()
    {
    	effectMediaPlayer = MediaPlayer.create(mainContext, R.raw.fifty_fifty);
    	effectMediaPlayer.setLooping(false);
    	effectMediaPlayer.start();
    }
    
    private void play(MediaPlayer player, int soundID)
    {
    	musicMediaPlayer = MediaPlayer.create(mainContext, soundID);
    	musicMediaPlayer.setLooping(false);
    	musicMediaPlayer.start();
    }

    private void releasePlayer(MediaPlayer player)
    {
        if (musicMediaPlayer != null)
        {
        	musicMediaPlayer.stop();
        	musicMediaPlayer.release();
        	musicMediaPlayer = null;
        }
    }
    
    public void setMusicEnabled(boolean enabled)
    {
    	musicEnabled = enabled;
    }
    
    public void setEffectsEnabled(boolean enabled)
    {
    	effectsEnabled = enabled;
    }

}

