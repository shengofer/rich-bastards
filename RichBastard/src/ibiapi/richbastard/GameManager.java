package ibiapi.richbastard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import db.Question;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class GameManager 
{
	private static GameManager mGameManager;
	private static AudioPlayer mAudioPlayer;
	private static QuestionsManager mQuestionsManager;
	
    private final long PAUSE_AMOUNT = 5000;
    private final long PAUSE_BETWEEN_QUESTIONS = 7000;
    
    private GameActivity mActivity;
    
    private TextView mQuestionView;
    private TestQuestion mQuestion;
    
	private TextView[] mAnswerViews;
    //private String[] mAnswerOptions;
    
    private TextView[] mPercentageViews;
    
    private int mQuestionNumber = 0;
    private int mCorrectId;
    
    private final Handler h = new Handler();
    // true, when a question and answers are displayed and a user has not selected any of them
    private boolean canUseLifelines;
    
    private static final int numberOfQuestions = 15;
    
    private TestQuestion[] mQuestions;
    
    private void loadQuestions(String topic)
    {
    	mQuestions = mQuestionsManager.retrieveQuestions(topic);
    }
    
    private GameManager()
    {
    }
    
    public static GameManager getInstance()
    {
    	if (mGameManager == null)
    	{
    		mGameManager = new GameManager();
    		mAudioPlayer = AudioPlayer.getInstance();
    		mQuestionsManager = QuestionsManager.getInstance();
    	}
    	return mGameManager;
    }

	public void playCurrentQuestion()
	{
		clearQuestion();
		mQuestion = mQuestions[mQuestionNumber-1]; //new TestQuestion();
		//mAnswerOptions = mQuestion.getAnswerOptions();
        mAudioPlayer.playPreQuestion(mQuestionNumber);
        h.postDelayed(new Runnable() {
			@Override
			public void run() {
		        showQuestion();
		        mAudioPlayer.playQuestion(mQuestionNumber);	
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
			mAnswerViews[i].setText(letters[i] + ": " + mQuestion.getAnswerOption(i));
			mAnswerViews[i].setClickable(true);
			if (mQuestion.getCorrectAnswer().equals(mQuestion.getAnswerOption(i)))
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
		hideVotingResults(false);
		mAnswerViews[answer].setBackgroundResource(R.drawable.final_answer);
		mAudioPlayer.playFinalAnswer(mQuestionNumber);
		h.postDelayed(new Runnable() 
		{
			@Override
			public void run()
			{
				if (verifyAnswer(answer))
				{
					h.postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							if (mQuestionNumber < numberOfQuestions)
							{
								++mQuestionNumber;
								playCurrentQuestion();
							}
							else
							{
								//  congratulate the winner
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
								    public void run() {
								    	mActivity.showWinDialog();
								    }}, 3000); 
								
							}
						}
					}, PAUSE_BETWEEN_QUESTIONS);
				}
				else
				{
					// present our condolences to the user
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
					    public void run() {
					    	mActivity.showCondDialog();
					    }}, 3000);
					
				}
			}		
		}, PAUSE_AMOUNT);
	}
	
	private double getGaussian(double mu, double sigma)
	{
		Random r = new Random();
		double gaussian = -1;
		while (gaussian < 0)
			gaussian = r.nextGaussian() * sigma + mu; // why * sigma + mu?
		return gaussian;
	}
	
	private boolean isAnswerCleared(int answer)
	{
		return (mAnswerViews[answer].getText() == "");
	}
	
	private boolean isAnswerCorrect(int answer)
	{
		return (answer == mCorrectId);
	}
	
	private void simulateVoting()
	{
		int numOfAnsws = 0;
		for (int i = 0; i < 4; ++i)
			if (!isAnswerCleared(i))
				++numOfAnsws;
		double weights[] = new double[numOfAnsws];
		double weightsSum = 0;
		for (int ans = 0, posAns = 0; ans < 4; ++ans)
		{
			if (!isAnswerCleared(ans))
			{
				double mu, sigma = 0.5 + 0.03 * (posAns + 1);
				mu = (isAnswerCorrect(ans)) ? 30 / (1.5 * (posAns + 1) - 0.5) : 1;
				weightsSum += (weights[posAns] = getGaussian(mu, sigma));
				++posAns;
			}
		}
		int [] percents = new int[numOfAnsws];
		int percentsSum = 0;
		for (int ans = 0, posAns = 0; ans < 4; ++ans)
		{
			if (!isAnswerCleared(ans))
			{
				percentsSum += (percents[posAns] = (int) (100 * weights[posAns] / weightsSum));
				++posAns;
			}
		}
		if (percentsSum > 100)
		{
			int diff = percentsSum - 100;
			for (int i = 0; i < numOfAnsws; ++i)
				if (percents[i] > diff)
					percents[i] -= diff;
		}
		else if (percentsSum < 100)
		{
			int diff = 100 - percentsSum;
			percents[0] += diff;
		}
		for (int ans = 0, posAns = 0; ans < 4; ++ans)
			if (!isAnswerCleared(ans))
			{
				mPercentageViews[ans].setText(String.format("%d%%", percents[posAns]));
				++posAns;
			}
	}
	
	private void displayVotingResults()
	{
    	mPercentageViews = new TextView[4];
    	int[] ids = {R.id.percentageTextViewA, R.id.percentageTextViewB,
    			R.id.percentageTextViewC, R.id.percentageTextViewD};
    	for (int i = 0; i < 4; ++i)
    	{
    		mPercentageViews[i] = (TextView) mActivity.findViewById(ids[i]);
    		if (!isAnswerCleared(i))
    			mPercentageViews[i].setVisibility(View.VISIBLE);
    	}
    	simulateVoting();
	}
	
	private void hideVotingResults(boolean fiftyFifty)
	{
		if (mPercentageViews != null)
		{
			for (int i = 0; i < 4; ++i)
				if (!fiftyFifty || (fiftyFifty && isAnswerCleared(i)))
					mPercentageViews[i].setVisibility(View.GONE);
			if (!fiftyFifty)
				mPercentageViews = null;
		}
	}
	
	public boolean useFiftyFifty()
	{
		if (canUseLifelines)
		{
			ArrayList<Integer> a = new ArrayList<Integer>();
			for (int i = 0; i < 4; i++)
				if (!isAnswerCorrect(i))
					a.add(i);
			Collections.shuffle(a);
			clearAnswer(a.get(0));
			clearAnswer(a.get(1));
			hideVotingResults(true);
			mAudioPlayer.playFiftyFifty();
			return true;
		}
		return false;
	}
	
	public boolean useChangeQuestion()
	{
		if (canUseLifelines)
		{
			hideVotingResults(false);
			mQuestions[mQuestionNumber-1] = mQuestionsManager.retrieveAdditionalQuestion(mQuestionNumber);
			playCurrentQuestion();
			return true;
			// TODO: AudioPlayer.playSomething()
		}
		return canUseLifelines;
	}
	
	public boolean useAskForAudience()
	{
		if (canUseLifelines)
		{
			displayVotingResults();
			// TODO: AudioPlayer.playSomethingElse()
			return true;
		}
		return canUseLifelines;
	}
	
	public void stop()
	{
		h.removeCallbacksAndMessages(null);
	}
	
	private boolean verifyAnswer(int answer)
	{
		if (isAnswerCorrect(answer))
		{
			stupidAnimationCorrect(mCorrectId);
			mAudioPlayer.playCorrect(mQuestionNumber);
			return true;
		}
		else
		{
			stupidAnimationWrong(mCorrectId);
			mAudioPlayer.playWrong(mQuestionNumber);
			return false;
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
	
	public void startGame()
	{
    	// without a certain topic yet
    	loadQuestions(null);
		mQuestionNumber = 6;
		playCurrentQuestion();
	}
	
	public static int getNumberOfQuestions()
	{
		return numberOfQuestions;
	}

}
