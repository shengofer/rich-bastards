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
    
    private Theme curTheme;
    
    private final int[] classicPreQuestionId = {
    	0, 0, 0, 0, 0,
    	R.raw.pre_question6_11, R.raw.pre_question7, 0, 0, 0,
    	R.raw.pre_question6_11, 0, 0, 0, 0};
    
    private final int[] originalPreQuestionId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicQuestionId = {
    	0, 0, 0, 0, 0,
    	R.raw.question6, R.raw.question7, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] originalQuestionId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicFinalAnswerId = {
    	0, 0, 0, 0, 0,
    	R.raw.final_answer6, R.raw.final_answer7_12, 0, 0, 0,
    	0, R.raw.final_answer7_12, 0, 0, 0};
    
    private final int[] originalFinalAnswerId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicCorrectAnswerId = {
    	R.raw.correct1to4, R.raw.correct1to4, R.raw.correct1to4, R.raw.correct1to4, 0,
    	R.raw.correct_answer6, R.raw.correct_answer7, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] originalCorrectAnswerId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicWrongAnswerId = {
    	0, 0, 0, 0, 0,
    	R.raw.wrong_answer6, R.raw.wrong_answer7, 0, 0, 0,
    	0, 0, 0, 0, 0};
        
    private final int[] originalWrongAnswerId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
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
    	--question;
    	if (musicEnabled)
    	{
	    	stopPlaying();
	        int soundID = (curTheme == Theme.Classic 
	        		? classicPreQuestionId[question]
	        		: originalPreQuestionId[question]);
	        play(musicMediaPlayer, soundID);
    	}
    }

    public void playQuestion(int question)
    {
    	--question;
    	if (musicEnabled)
    	{
	    	stopPlaying();
	        int soundID = (curTheme == Theme.Classic 
	        		? classicQuestionId[question]
	        		: originalQuestionId[question]);
	        play(musicMediaPlayer, soundID);
    	}
    }
    
    public void playFinalAnswer(int question)
    {
    	--question;
    	if (effectsEnabled)
    	{
	    	stopPlaying();
	        int soundID = (curTheme == Theme.Classic 
	        		? classicFinalAnswerId[question]
	        		: originalFinalAnswerId[question]);
	        play(musicMediaPlayer, soundID);
    	}
    }
    
    public void playCorrect(int question)
    {
    	--question;
    	if (effectsEnabled)
    	{
	    	stopPlaying();
	        int soundID = (curTheme == Theme.Classic 
	        		? classicCorrectAnswerId[question]
	        		: originalCorrectAnswerId[question]);
	        play(musicMediaPlayer, soundID);
    	}
    }
    
    public void playWrong(int question)
    {
    	--question;
    	if (effectsEnabled)
    	{
	    	stopPlaying();
	        int soundID = (curTheme == Theme.Classic 
	        		? classicWrongAnswerId[question]
	        		: originalWrongAnswerId[question]);
	        play(effectMediaPlayer, soundID);
    	}
    }
    
    public void playFiftyFifty()
    {
    	if (effectsEnabled)
    	{
        	effectMediaPlayer = MediaPlayer.create(mainContext, R.raw.fifty_fifty);
        	effectMediaPlayer.setLooping(false);
        	effectMediaPlayer.start();   		
    	}
    }

    public void stopPlaying()
    {
        releasePlayer(musicMediaPlayer);
        releasePlayer(effectMediaPlayer);
    }
    
    private void play(MediaPlayer player, int soundID)
    {
    	player = MediaPlayer.create(mainContext, soundID);
    	player.setLooping(false);
    	player.start();
    }

    private void releasePlayer(MediaPlayer player)
    {
        if (player != null)
        {
        	player.stop();
        	player.release();
        	player = null;
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

    public void setTheme(Theme theme)
    {
    	curTheme = theme;
    }
}

