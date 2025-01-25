package calc;

import java.awt.Color;
import java.awt.EventQueue; // 이벤트 큐 클래스
import java.awt.Font; // 폰트 클래스
import java.awt.GridLayout; // 그리드 레이아웃 클래스
import java.awt.event.ActionEvent; // 액션 이벤트 클래스
import java.awt.event.ActionListener; // 액션 리스너 클래스

import javax.swing.JButton; // GUI 버튼 클래스
import javax.swing.JFrame; // GUI 프레임 클래스
import javax.swing.JPanel; // GUI 패널 클래스
import javax.swing.JTextField; // GUI 텍스트 필드 클래스

	public class Calculator {
	private JFrame frame; 										// 메인 프레임
    private JTextField textField; 								// 결과 텍스트 필드
    private JTextField textPrev;
    private String operator=""; 								// 현재 연산자
    private double firstNumber, secondNumber, result; 			// 숫자 및 결과

    public Calculator() {
        // 프레임 설정
        frame = new JFrame();
        frame.setBounds(100, 100, 350, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //계산 중간값 표시창 설정
        textPrev = new JTextField();
        textPrev.setHorizontalAlignment(JTextField.RIGHT);
        textPrev.setBounds(10, 10, 320, 40);
        textPrev.setForeground(Color.GRAY);
        textPrev.setFont(new Font("Arial", Font.ITALIC, 20));
        frame.add(textPrev);
        
        // 텍스트 필드 설정
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setBounds(10, 60, 320, 40);
        textField.setFont(new Font("Arial", Font.BOLD, 45));
        frame.add(textField);

        // 버튼 패널 설정
        JPanel panel = new JPanel();
        panel.setBounds(8, 110, 320, 250);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        frame.add(panel);

        // 버튼 생성 및 패널에 추가
        String[] button_name = { "C", "÷", "×", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0" };
        JButton buttons[] = new JButton[button_name.length];
        
        for (int i=0; i<button_name.length; i++) {
            buttons[i] = new JButton(button_name[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 17));
            if (button_name[i] == "C") buttons[i].setBackground(Color.RED);
			else if ((i >= 4 && i <= 6) || (i >= 8 && i <= 10) || (i >= 12 && i <= 14)) buttons[i].setBackground(Color.BLACK);
			else buttons[i].setBackground(Color.GRAY);
            buttons[i].setForeground(Color.WHITE);
			buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(new ButtonClickListener());
            panel.add(buttons[i]);
        }
        
        
        // 프레임 추가 설정
        frame.setTitle("Calculator");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    // 버튼 클릭 리스너
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // 숫자 입력 처리
            if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                textField.setText(textField.getText() + command);
            } else if (command.equals("C")) { // 초기화 처리
                textField.setText("");
                textPrev.setText("");
                firstNumber = 0;
                secondNumber = 0;
                operator = "";
            } else if (command.equals("=")) { // 결과 계산
                secondNumber = Double.parseDouble(getLastNumber(textField.getText()));
                result = calculateResult(firstNumber, secondNumber, operator);
                textPrev.setText(firstNumber + operator + secondNumber + "=");
                textField.setText(String.valueOf(result));
                firstNumber = result;
                operator = "";
            } else { // 연산자 처리  
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
   	            } catch (NumberFormatException ex) {
   	            	operator = command;
   	            	textPrev.setText(firstNumber + operator);
    	        }
            }
        }

        //textField.getText에서 마지막 숫자값만 리턴
        public String getLastNumber(String input) {
            StringBuilder lastNumber = new StringBuilder();
            for (int i = input.length() - 1; i >= 0; i--) {
                char ch = input.charAt(i);
                if (Character.isDigit(ch)) {
                    lastNumber.insert(0, ch);
                } else {
                    break;
                }
            }
            return lastNumber.toString();
        }
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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Calculator window = new Calculator();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}



