package org.iptime.seonghunjo.finalexam;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Layout Variables
    TextView questionTextView;
    EditText inputEditText;
    Button resultButton, leftButton, rightButton, insertButton;
    Intent resultIntent;

    ArrayList<Question> questions;

    int currentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentQuestionIndex = 0;
        // ArrayList Initialize
        questions = new ArrayList<Question>();
        // Question, Answer Initialize
        questions.add(new Question(("1. 보기 중 가장 큰 수를 고르시오\n1)0 2)4 3)50"), 3));
        questions.add(new Question(("2. 보기 중 가장 작은 수를 고르시오\n1)0 2)4 3)50"), 1));
        questions.add(new Question(("3. 보기 중 가장 음수를 고르시오\n1)-5 2)4 3)50"), 1));
        questions.add(new Question(("4. 보기 중 알파벳을 고르시오\n1)0 2)A 3)50"), 2));


        // Layout Initialize
        questionTextView = (TextView) findViewById(R.id.tv_questions);
        inputEditText = (EditText) findViewById(R.id.et_input);

        rightButton = (Button) findViewById(R.id.bt_right);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Right Button Logic
                // 0 1 2 3 // 4
                if(currentQuestionIndex + 1 > questions.size() - 1 ) {
                    Toast.makeText(getApplicationContext(), "현재 마지막 문제입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentQuestionIndex++;
                    setCurrentQuestionText();
                }
            }
        });

        leftButton = (Button) findViewById(R.id.bt_left);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Left Button Logic
                if(currentQuestionIndex - 1 < 0) {
                    Toast.makeText(getApplicationContext(), "현재 첫 번째 문제입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentQuestionIndex--;
                    setCurrentQuestionText();
                }
            }
        });

        insertButton = (Button) findViewById(R.id.bt_insert);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int answer = Integer.parseInt(inputEditText.getText().toString());
                questions.get(currentQuestionIndex).setUserAnswer(answer);

                // Insert Button Logic
                Toast.makeText(getApplicationContext(), currentQuestionIndex + 1 + "번 문제 " + answer + "보기 선택", Toast.LENGTH_SHORT).show();
            }
        });



        resultButton = (Button) findViewById(R.id.bt_result);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Result Button Logic
                int correctCounter = 0;
                String resultString = new String();

                for (int i = 0; i < questions.size(); i++) {
                    resultString += (i + 1) + "번 문제 ";

                    if (questions.get(i).isCorrect()) {
                        correctCounter++;
                        resultString += "맞음\n";
                    } else {
                        resultString += "틀림\n";
                    }
                }

                Toast.makeText(getApplicationContext(), "현재 맞힌 갯수는 " + correctCounter + "개 입니다", Toast.LENGTH_SHORT).show();

                resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
                resultIntent.putExtra("result", resultString);
                startActivity(resultIntent);
            }
        });

        setCurrentQuestionText();
    }

    public void setCurrentQuestionText()
    {
        inputEditText.setText(""); // clear edit text
        questionTextView.setText(questions.get(currentQuestionIndex).getQuestion());
    }
}
