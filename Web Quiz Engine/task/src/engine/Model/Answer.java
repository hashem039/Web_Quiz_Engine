package engine.Model;

import java.util.Arrays;

public class Answer {
    private int[] answer;

    public Answer() {
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
    @Override
    public String toString() {
        return "Answer{" +
                "answer=" + Arrays.toString(answer) +
                '}';
    }
}
