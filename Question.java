public class Question {
    String text;
    String choiceA;
    String choiceB;
    String choiceC;
    String choiceD;
    char correctChoice;
    int score;

    public Question(String text, String choiceA, String choiceB, String choiceC, String choiceD, char correctChoice, int score) {
        this.text = text;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.correctChoice = Character.toUpperCase(correctChoice);
        this.score = score;
    }

    public Question(String text2, String[] ops, int corr) {
        //TODO Auto-generated constructor stub
    }

    public void printQuestion() {
        System.out.println(text);
        System.out.println("A) " + choiceA);
        System.out.println("B) " + choiceB);
        System.out.println("C) " + choiceC);
        System.out.println("D) " + choiceD);
    }

    public boolean isCorrect(char answer) {
        return Character.toUpperCase(answer) == correctChoice;
    }

    public int getScore() {
        return score;
    }
    public String getQuestionText() {
        return text;
    }
    public void setQuestionText(String text) {
        this.text = text;
    }
    public String getChoiceA() {
        return choiceA;
    }
    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }
    public String getChoiceB() {
        return choiceB;
    }
    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }
    public String getChoiceC() {
        return choiceC;
    }
    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }
    public String getChoiceD() {
        return choiceD;
    }
    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }
    public char getCorrectChoice() {
        return correctChoice;
    }
    public void setCorrectChoice(char correctChoice) {
        this.correctChoice = Character.toUpperCase(correctChoice);
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String toFileFormat() {
        return text + ";" + choiceA + ";" + choiceB + ";" + choiceC + ";" + choiceD + ";" + correctChoice + ";" + score;
    }

    public static Question fromFileFormat(String[] data) {
        String text = data[1];
        String choiceA = data[2];
        String choiceB = data[3];
        String choiceC = data[4];
        String choiceD = data[5];
        char correctChoice = data[6].charAt(0);
        int score = Integer.parseInt(data[7]);
        return new Question(text, choiceA, choiceB, choiceC, choiceD, correctChoice, score);
    }

    public String[] getOptions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOptions'");
    }
}