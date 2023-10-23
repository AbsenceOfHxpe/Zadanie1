package com.example.zadanie1;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button hintButton;


    private TextView questionTextViev;

    private String TAG = "log_tag";

    private String QUIZ_TAG = "quiz_tag";

    private int currentIndex = 0;

    private int curentPoints = 0;

    private static final int REQUEST_CODE_PROMPT= 0;
    private boolean answerWasShown;
    private Question[] questions = new Question[]{
            new Question(R.string.q_1,true),
            new Question(R.string.q_2,false),
            new Question(R.string.q_3,true),
            new Question(R.string.q_4,true),
            new Question(R.string.q_5,false)
    };

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Aplikacja została uruchomiona");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Aplikacja została wzonowiona");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Aplikacja została zapauzowana"/*częściowo aktywna*/);
    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Aplikacja została zatrzymana");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Aplikacja została zniszczona");  
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia:onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        hintButton = findViewById(R.id.hint_button);
        questionTextViev = findViewById(R.id.question_text_view);


        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        setNextQuestion();

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnwser();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });

    }


    private void checkAnswerCorrectness (boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnwser();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }else{

        if (userAnswer == correctAnswer){
            resultMessageId = R.string.correct_answer;
            curentPoints++;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }}
        String pointsMessage = getString(R.string.points, curentPoints);
        Toast.makeText(this, resultMessageId,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,pointsMessage,Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        questionTextViev.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null)
                return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}