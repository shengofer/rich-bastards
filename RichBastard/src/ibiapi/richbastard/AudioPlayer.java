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
    	R.raw.pre_question1, 0, 0, 0, 0,
    	R.raw.pre_question6_11, R.raw.pre_question7, R.raw.pre_question8, R.raw.pre_question9, R.raw.pre_question10,
    	R.raw.pre_question6_11, R.raw.pre_question12, R.raw.pre_question13, R.raw.pre_question14, R.raw.pre_question15};
    
    private final int[] originalPreQuestionId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicQuestionId = {
    	R.raw.question1to5, R.raw.question1to5, R.raw.question1to5, R.raw.question1to5, R.raw.question1to5,
    	R.raw.question6, R.raw.question7, R.raw.question8, R.raw.question9, R.raw.question10,
    	R.raw.question11, R.raw.question12, R.raw.question13, R.raw.question14, R.raw.question15};
    
    private final int[] originalQuestionId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicFinalAnswerId = {
    	0, 0, 0, 0, 0,
    	R.raw.final_answer6, R.raw.final_answer7_12, R.raw.final_answer8, R.raw.final_answer9, R.raw.final_answer10,
    	R.raw.final_answer11, R.raw.final_answer7_12, R.raw.final_answer13, R.raw.final_answer14, R.raw.final_answer15};
    
    private final int[] originalFinalAnswerId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicCorrectAnswerId = {
    	R.raw.correct_answer1to4, R.raw.correct_answer1to4, R.raw.correct_answer1to4, R.raw.correct_answer1to4, R.raw.correct_answer5,
    	R.raw.correct_answer6, R.raw.correct_answer7, R.raw.correct_answer8, R.raw.correct_answer9, R.raw.correct_answer10,
    	R.raw.correct_answer11, R.raw.correct_answer12, R.raw.correct_answer13, R.raw.correct_answer14, R.raw.correct_answer15};
    
    private final int[] originalCorrectAnswerId = {
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0};
    
    private final int[] classicWrongAnswerId = {
    	R.raw.wrong_answer1to4, R.raw.wrong_answer1to4, R.raw.wrong_answer1to4, R.raw.wrong_answer1to4, R.raw.wrong_answer1to4,
    	R.raw.wrong_answer6, R.raw.wrong_answer7, R.raw.wrong_answer8, R.raw.wrong_answer9, R.raw.wrong_answer10,
    	R.raw.wrong_answer11, R.raw.wrong_answer12, R.raw.wrong_answer13, R.raw.wrong_answer14, R.raw.wrong_answer15};
        
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
    		if (curTheme == Theme.Classic && question > 0 && question < 5)
    			return;
	    	stopPlaying();
	        int soundID = (curTheme == Theme.Classic 
	        		? classicPreQuestionId[question]
	        		: originalPreQuestionId[question]);
	        playMusic(soundID);
    	}
    }

    public void playQuestion(int question)
    {
    	--question;
    	if (musicEnabled)
    	{
    		if (curTheme == Theme.Classic && question > 0 && question < 5)
    			return;
	    	stopPlaying();
	        int soundID = (curTheme == Theme.Classic 
	        		? classicQuestionId[question]
	        		: originalQuestionId[question]);
	        playMusic(soundID);
    	}
    }
    
    public void playFinalAnswer(int question)
    {
    	--question;
    	if (effectsEnabled)
    	{
    		if (curTheme == Theme.Classic && question >= 0 && question < 5)
    			return;
    		else
    		{
		    	stopPlaying();
		        int soundID = (curTheme == Theme.Classic 
		        		? classicFinalAnswerId[question]
		        		: originalFinalAnswerId[question]);
		        playMusic(soundID);
    		}
    	}
    }
    
    public void playCorrect(int question)
    {
    	--question;
    	if (effectsEnabled)
    	{
    		if (curTheme == Theme.Classic && ((question >= 0 && question < 4) || question == 14))
    		{
    			int soundID = classicCorrectAnswerId[question];
    			playEffect(soundID);
    		}
    		else
    		{
    			stopPlaying();
    			int soundID = (curTheme == Theme.Classic 
    	        		? classicCorrectAnswerId[question]
    	        		: originalCorrectAnswerId[question]);
    	        playMusic(soundID);
    		}
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
	        playEffect(soundID);
    	}
    }
    
    public void playFiftyFifty()
    {
    	if (effectsEnabled)
    	{
    		releaseEffectsPlayer();
        	effectMediaPlayer = MediaPlayer.create(mainContext, R.raw.fifty_fifty);
        	effectMediaPlayer.setLooping(false);
        	effectMediaPlayer.start();   		
    	}
    }

    public void stopPlaying()
    {
        releaseMusicPlayer();
        releaseEffectsPlayer();
    }
    
    private void playMusic(int soundID)
    {
    	musicMediaPlayer = MediaPlayer.create(mainContext, soundID);
    	musicMediaPlayer.setLooping(true);
    	musicMediaPlayer.start();
    }
    
    private void playEffect(int soundID)
    {
    	effectMediaPlayer = MediaPlayer.create(mainContext, soundID);
    	effectMediaPlayer.setLooping(false);
    	effectMediaPlayer.start();
    }

    private void releaseMusicPlayer()
    {
        if (musicMediaPlayer != null)
        {
        	musicMediaPlayer.stop();
        	musicMediaPlayer.release();
        	musicMediaPlayer = null;
        }
    }
    
    private void releaseEffectsPlayer()
    {
        if (effectMediaPlayer != null)
        {
        	effectMediaPlayer.stop();
        	effectMediaPlayer.release();
        	effectMediaPlayer = null;
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

