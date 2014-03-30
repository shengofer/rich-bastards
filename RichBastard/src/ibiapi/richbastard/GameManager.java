package ibiapi.richbastard;

import java.util.ArrayList;
import java.util.Collections;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.widget.TextView;

public class GameManager 
{
	private static GameManager mGameManager;
	
    private final long PAUSE_AMOUNT = 5000;
    private final long PAUSE_BETWEEN_QUESTIONS = 7000;
    
    private GameActivity mActivity;
    
    private TextView mQuestionView;
    private TestQuestion mQuestion;
    
	private TextView[] mAnswerViews;
    private String[] mAnswerOptions;
    
    private int mQuestionNumber;
    private int mCorrectId;
    
    private final Handler h = new Handler();
    private boolean canUseLifelines;
    
    private GameManager() {}
    
    public static GameManager getInstance()
    {
    	if (mGameManager == null)
    		mGameManager = new GameManager();
    	return mGameManager;
    }

	public void playQuestion(final int questionNumber)
	{
		clearQuestion();
		mQuestion = new TestQuestion();
		mAnswerOptions = mQuestion.getAnswerOptions();
		mQuestionNumber = questionNumber;
        AudioPlayer.playPreQuestion(questionNumber);
        h.postDelayed(new Runnable() {
			@Override
			public void run() {
		        showQuestion();
		        AudioPlayer.playQuestion(questionNumber);	
			}
		}, PAUSE_AMOUNT);
	}
	
	private void showQuestion()
	{
		mQuestionView.setText(mQuestion.getQuestion());
		char[] letters = {'A','B','C','D'};
		for (int i = 0; i < 4; i++)
		{
			mAnswerViews[i].setBackgroundResource(R.drawable.answer_option);
			mAnswerViews[i].setText(letters[i] + ": " + mAnswerOptions[i]);
			mAnswerViews[i].setClickable(true);
			if (mQuestion.getCorrectAnswer() == mAnswerOptions[i])
				mCorrectId = i;
		}
        makeChoosable(true);
        canUseLifelines = true;
	}
	
	public void setActivity(GameActivity mActivity) 
	{
		this.mActivity = mActivity;
	}
	
	public void initViewReferences()
	{
    	mAnswerViews = new TextView[4];
    	mAnswerViews[0] = (TextView) mActivity.findViewById(R.id.variant_a);
    	mAnswerViews[1] = (TextView) mActivity.findViewById(R.id.variant_b);
    	mAnswerViews[2] = (TextView) mActivity.findViewById(R.id.variant_c);
    	mAnswerViews[3] = (TextView) mActivity.findViewById(R.id.variant_d);
    	mQuestionView = (TextView) mActivity.findViewById(R.id.question_field);
	}
	
	public void chooseAnswer(final int answer)
	{
		makeChoosable(false);
		canUseLifelines = false;
		mAnswerViews[answer].setBackgroundResource(R.drawable.final_answer);
		AudioPlayer.playFinalAnswer(mQuestionNumber);
		h.postDelayed(new Runnable() 
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				verifyAnswer(answer);
				h.postDelayed(new Runnable() {
					@Override
					public void run() {
						playQuestion(7);						
					}
				}, PAUSE_BETWEEN_QUESTIONS);
			}		
		}, PAUSE_AMOUNT);
	}
	
	public boolean useFiftyFifty()
	{
		if (canUseLifelines)
		{
			ArrayList<Integer> a = new ArrayList<Integer>();
			for (int i = 0; i < 4; i++)
				if (i != mCorrectId)
					a.add(i);
			Collections.shuffle(a);
			clearAnswer(a.get(0));
			clearAnswer(a.get(1));
			AudioPlayer.playFiftyFifty();
			return true;
		}
		return false;
	}
	
	public void stop()
	{
		h.removeCallbacksAndMessages(null);
	}
	
	private void verifyAnswer(int answer)
	{
		if (answer == mCorrectId)
		{
			stupidAnimationCorrect(mCorrectId);
			AudioPlayer.playCorrect(mQuestionNumber);
		}
		else
		{
			stupidAnimationWrong(mCorrectId);
			AudioPlayer.playWrong(mQuestionNumber);
		}
	}
	
	private void stupidAnimationCorrect(int answer)
	{
		mAnswerViews[answer].setBackgroundResource(R.drawable.correct_answer_transition);
		AnimationDrawable anim = (AnimationDrawable) mAnswerViews[answer].getBackground();
		anim.start();
	}
	
	private void stupidAnimationWrong(int answer)
	{
		mAnswerViews[answer].setBackgroundResource(R.drawable.wrong_answer_transition);
		AnimationDrawable anim = (AnimationDrawable) mAnswerViews[answer].getBackground();
		anim.start();
	}
	
	private void makeChoosable(boolean choosable)
	{
		for (int i = 0; i < 4; i++)
			mAnswerViews[i].setClickable(choosable);
	}
	
	private void clearQuestion()
	{
		mQuestion = null;
		mQuestionView.setText("");
		for (int i = 0; i < 4; i++)
			clearAnswer(i);
		canUseLifelines = false;
	}
	
	private void clearAnswer(int answer)
	{
		mAnswerViews[answer].setBackgroundResource(R.drawable.answer_option);
		mAnswerViews[answer].setText("");
		mAnswerViews[answer].setClickable(false);
	}
	
}
