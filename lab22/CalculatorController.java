package lab22;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Модель для хранения данных и выполнения операций
class CalculatorModel {
    private Stack<Double> stack;

    CalculatorModel() {
        stack = new Stack<>();
    }

    // Метод для вычисления выражения в обратной польской нотации
    List<Double> calculateRPN(String expression) {
        List<Double> results = new ArrayList<>();
        String[] tokens = expression.split(" ");
        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                if (!stack.isEmpty()) {
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    switch (token) {
                        case "+":
                            stack.push(operand1 + operand2);
                            break;
                        case "-":
                            stack.push(operand1 - operand2);
                            break;
                        case "*":
                            stack.push(operand1 * operand2);
                            break;
                        case "/":
                            stack.push(operand1 / operand2);
                            break;
                    }
                    // Добавляем текущий результат в список промежуточных результатов
                    results.add(stack.peek());
                }
            }
        }
        return results;
    }

    // Проверка, является ли строка числом
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

// Представление - графический интерфейс
class CalculatorView extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton calculateButton;

    CalculatorView() {
        setTitle("RPN Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        JPanel panel = new JPanel();
        inputField = new JTextField(20);
        outputArea = new JTextArea(10, 20);
        outputArea.setEditable(false);
        calculateButton = new JButton("Calculate");

        // Обработчик для кнопки "Calculate"
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText();
                CalculatorModel model = new CalculatorModel();
                List<Double> results = model.calculateRPN(expression);

                // Выводим все промежуточные результаты в текстовую область
                StringBuilder sb = new StringBuilder();
                for (Double result : results) {
                    sb.append(result).append("\n");
                }
                outputArea.setText(sb.toString());
            }
        });

        panel.add(inputField);
        panel.add(calculateButton);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }
}

// Контроллер - запуск приложения
public class CalculatorController {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalculatorView();
            }
        });
    }
}
