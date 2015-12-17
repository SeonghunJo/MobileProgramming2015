package org.iptime.seonghunjo.finalexam;

/**
 * Created by Jo on 2015. 12. 17..
 */
public class Question {

    static final int NO_ANSWER_STATE = -1;

    String question;
    int correctAnswer;
    int userAnswer;

    public Question(String q, int answer) {
        question = q;
        correctAnswer = answer;
        userAnswer = NO_ANSWER_STATE; // -1은 사용자 답 초기화
    }

    public boolean isCorrect() {
        return correctAnswer == userAnswer;
    }

    // 질문을 리턴 화면에 문제를 보여줄 때 사용한다
    public String getQuestion() {
        return question;
    }

    // 사용자의 답을 세팅한다
    public void setUserAnswer(int answer)
    {
        userAnswer = answer;
    }

    // 사용자의 답을 -1로 초기화 한다.
    public void resetAnswer()
    {
        userAnswer = NO_ANSWER_STATE;
    }
}
