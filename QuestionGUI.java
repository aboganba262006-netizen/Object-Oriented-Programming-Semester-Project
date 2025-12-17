import javax.swing.*;

public class QuestionGUI extends JFrame {

public QuestionGUI(Quiz quiz) {
setTitle("Add Question");
setSize(400, 300);
setLocationRelativeTo(null);

JTextField qText = new JTextField();
JTextField[] options = new JTextField[4];
for (int i = 0; i < 4; i++) options[i] = new JTextField();

JTextField correct = new JTextField();
JButton add = new JButton("Add Question");

add.addActionListener(e -> {
String[] ops = new String[4];
for (int i = 0; i < 4; i++) ops[i] = options[i].getText();

int corr = Integer.parseInt(correct.getText());
quiz.addQuestion(new Question(qText.getText(), ops, corr));
JOptionPane.showMessageDialog(this, "Question added");
});

setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
add(qText);
for (JTextField f : options) add(f);
add(correct);
add(add);

setVisible(true);
}
}
