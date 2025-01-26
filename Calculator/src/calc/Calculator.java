package calc;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField; 

public class Calculator {
    private JFrame frame; 
    private JTextField textField; 
    private JTextField textPrev;
    private String operator = ""; 
    private double firstNumber, secondNumber, result; 

    public Calculator() {
        setupFrame();
        setupTextFields();
        setupButtons();
        frame.setVisible(true);
    }

    // 메인 프레임 설정
    private void setupFrame() {
        frame = new JFrame();
        frame.setBounds(100, 100, 350, 405);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Calculator");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    // 텍스트 필드 설정
    private void setupTextFields() {
        textPrev = new JTextField();
        textPrev.setBorder(null);
        textPrev.setHorizontalAlignment(JTextField.RIGHT);
        textPrev.setBounds(10, 10, 320, 30);
        textPrev.setForeground(Color.GRAY);
        textPrev.setFont(new Font("Arial", Font.ITALIC, 15));
        textPrev.setEditable(false);
        textPrev.setFocusable(false);
        frame.add(textPrev);
        
        textField = new JTextField();
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setBounds(10, 50, 320, 50);
        textField.setFont(new Font("Arial", Font.BOLD, 55));
        frame.add(textField);
        
        //숫자와 "."만 키보드로 입력 가능.
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
            }
        });
    }

    // 버튼 패널 및 버튼 설정
    private void setupButtons() {
        JPanel panel = new JPanel();
        panel.setBounds(8, 110, 320, 250);
        panel.setLayout(new GridLayout(4, 4, 5, 5));
        frame.add(panel);

        String[] button_name = { "C", "÷", "×", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0" };
        JButton[] buttons = new JButton[button_name.length];

        for (int i = 0; i < button_name.length; i++) {
            buttons[i] = new JButton(button_name[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 17));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorderPainted(false);

            if (button_name[i].equals("C")) buttons[i].setBackground(Color.RED);
            else if ((i >= 4 && i <= 6) || (i >= 8 && i <= 10) || (i >= 12 && i <= 14)) buttons[i].setBackground(Color.BLACK);
            else buttons[i].setBackground(Color.GRAY);
            
            buttons[i].addActionListener(new ButtonClickListener());
            panel.add(buttons[i]);
        }
    }

    // 버튼 클릭 리스너 클래스
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (Character.isDigit(command.charAt(0))) { // 0~9
                textField.setText(textField.getText() + command);
            } else if (command.equals("C")) {
                resetCalculator();
            } else if (command.equals("=")) {
                calculateResultAndDisplay();
            } else { //사칙연산 입력받을 경우
                handleOperator(command);
            }
        }

        // 계산기 초기화
        private void resetCalculator() {
            textField.setText("");
            textPrev.setText("");
            firstNumber = 0;
            secondNumber = 0;
            operator = "";
        }

        // 결과 계산 및 표시
        private void calculateResultAndDisplay() {
            secondNumber = Double.parseDouble(getLastNumber(textField.getText()));
            result = calculateResult(firstNumber, secondNumber, operator);
            textPrev.setText(firstNumber + operator + secondNumber + "=");
            textField.setText(String.valueOf(result));
            firstNumber = result;
            operator = "";
        }

        // 연산자 처리
        private void handleOperator(String command) {
            try {
                if (!operator.isEmpty()) {
                    secondNumber = Double.parseDouble(getLastNumber(textField.getText()));
                    result = calculateResult(firstNumber, secondNumber, operator);
                    operator = command;
                    textPrev.setText(String.valueOf(result) + operator);
                    firstNumber = result;
                } else {
                    operator = command;
                    firstNumber = Double.parseDouble(textField.getText());
                    textPrev.setText(firstNumber + operator);
                }
                textField.setText("");
            } catch (NumberFormatException ex) { //연속해서 사칙기호 클릭시
                operator = command;
                textPrev.setText(firstNumber + operator);
            }
        }

        // 텍스트 필드에서 마지막 숫자 값만 리턴
        private String getLastNumber(String input) {
            StringBuilder lastNumber = new StringBuilder();
            for (int i = input.length() - 1; i >= 0; i--) {
                char ch = input.charAt(i);
                if (Character.isDigit(ch) || ch == '.') {
                    lastNumber.insert(0, ch);
                } else {
                    break;
                }
            }
            return lastNumber.toString();
        }

        // 결과 계산 메서드
        private double calculateResult(double firstNumber, double secondNumber, String operator) {
            switch (operator) {
                case "÷":
                    return firstNumber / secondNumber;
                case "×":
                    return firstNumber * secondNumber;
                case "+":
                    return firstNumber + secondNumber;
                case "-":
                    return firstNumber - secondNumber;
                default:
                    return 0;
            }
        }
    }

    // 메인 메서드
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new Calculator();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}



