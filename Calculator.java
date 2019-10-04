/*
Name: Elven Shum, Alex Yuk
Project: Fractions and ComplexNumbers GUI
Description:
A calculator that calculates complex numbers and displays irrational numbers as fractions
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

public class Calculator {
    // Calculator GUI size
    private static final int APPLICATION_WIDTH = 232;
    private static final int APPLICATION_HEIGHT = 322;

    // Font used for calculator GUI
    private static final String FONT_NAME = "Product Sans";

    // Color objects used for calculator GUI
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 252);
    private static final Color SPECIAL_COLOR = new Color(223, 225, 229);
    private static final Color NUMBER_COLOR = new Color(241, 243, 244);
    private static final Color OPERATION_COLOR = new Color(81, 134, 236);

    // Displays calculator GUI
    private JFrame f;
    // Display current number in calculator GUI
    private JLabel tl;

    // ArrayList of JButtons created so no memory leak
    private ArrayList<JButton> button_list = new ArrayList<>();

    // Variables used to do calculations involving order of operations
    private ComplexNumber previous_number = new ComplexNumber(0);
    private ComplexNumber running_total = new ComplexNumber(0);
    private Operation current_operation = Operation.none;
    private Operation previous_operation = Operation.none;

    // If user is entering a complex number
    private boolean in_complex;
    private boolean can_clear = true;

    enum Operation {
        none, add, subtract, multiply, divide
    }

    private Calculator() {
        init();
    }

    // Creates a button with passed in text and color add it to button_list
    private void createButton(final String text, final Color c, final int i, final int j, final int w) {
        JButton button = new JButton(text);
        button.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        button.setOpaque(true);
        // "=" button requires a different text color
        if (c.equals(OPERATION_COLOR))
            button.setForeground(Color.WHITE);
        else
            button.setForeground(Color.BLACK);
        button.setBackground(c);
        button.setBorder(new LineBorder(BACKGROUND_COLOR));
        button.setBounds((i - 1) * 58, APPLICATION_HEIGHT - 22 - j * 48, w, 48);
        button.addActionListener(AL);
        f.getContentPane().add(button);
        // Adds newly created button to button_list
        button_list.add(button);
    }

    // Create all buttons on the calculator
    private void genButtons() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 0; j < 5; j++) {
                // k will be a different value for every button
                int k = j * 4 + i;
                switch (k) {
                    case 1:
                        createButton("0", NUMBER_COLOR, i, j + 1, 116);
                        break;
                    case 3:
                        createButton("=", OPERATION_COLOR, i, j + 1, 58);
                        break;
                    case 4:
                        createButton("1/x", SPECIAL_COLOR, i, j + 1, 58);
                        break;
                    case 8:
                        createButton("+", SPECIAL_COLOR, i, j + 1, 58);
                        break;
                    case 12:
                        createButton("-", SPECIAL_COLOR, i, j + 1, 58);
                        break;
                    case 16:
                        createButton("×", SPECIAL_COLOR, i, j + 1, 58);
                        break;
                    case 17:
                        createButton("AC", SPECIAL_COLOR, i, j + 1, 58);
                        break;
                    case 18:
                        createButton("cpx", SPECIAL_COLOR, i, j + 1, 116);
                        break;
                    case 20:
                        createButton("÷", SPECIAL_COLOR, i, j + 1, 58);
                        break;
                    case 2:
                    case 19:
                        break;
                    default:
                        // (j - 1) * 3 + i will equal to the number needed in that button slot
                        createButton(String.valueOf((j - 1) * 3 + i), NUMBER_COLOR, i, j + 1, 58);
                }
            }
        }
    }

    // Initialises all the graphics of the program
    private void init() {
        // Initialises application window
        f = new JFrame();
        // Customises application window
        f.setTitle("Calculator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBackground(Color.WHITE);
        f.setPreferredSize(new Dimension(APPLICATION_WIDTH, APPLICATION_HEIGHT));
        f.pack();
        f.getContentPane().setLayout(null);
        f.setVisible(true);

        // Initialises text display
        tl = new JLabel("0");
        // Customises text display
        tl.setFont(new Font(FONT_NAME, Font.PLAIN, 40));
        tl.setOpaque(true);
        tl.setForeground(Color.BLACK);
        tl.setBackground(BACKGROUND_COLOR);
        tl.setBorder(new LineBorder(BACKGROUND_COLOR));
        tl.setHorizontalAlignment(SwingConstants.RIGHT);
        tl.setBounds(0, 0, APPLICATION_WIDTH, 60);
        // Adds JLabel to pane
        f.getContentPane().add(tl);

        genButtons();
    }

    // Creates an instance of the ActionListenser class to receive button input
    private ActionListener AL = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String currentText = tl.getText();
            try {
                // ((JButton) e.getSource()).getText() returns the text displayed by the button
                /*
                One of the hardest things in this assignment was doing calculations such as 1+2x3+4.
                So a checkPriority method is created and called every time when a calculation occurs
                 */
                switch (((JButton) e.getSource()).getText()) {
                    case "+":
                        if (in_complex) in_complex = !in_complex;
                        can_clear = true;
                        checkPriority(Operation.add);
                        previous_operation = Operation.add;
                        current_operation = Operation.add;
                        break;
                    case "-":
                        if (in_complex) in_complex = !in_complex;
                        can_clear = true;
                        checkPriority(Operation.subtract);
                        previous_operation = Operation.subtract;
                        current_operation = Operation.subtract;
                        break;
                    case "×":
                        if (in_complex) in_complex = !in_complex;
                        can_clear = true;
                        checkPriority(Operation.multiply);
                        current_operation = Operation.multiply;
                        break;
                    case "÷":
                        if (in_complex) in_complex = !in_complex;
                        can_clear = true;
                        checkPriority(Operation.divide);
                        current_operation = Operation.divide;
                        break;
                    case "1/x":
                        tl.setText(new ComplexNumber(1).divide(new ComplexNumber(tl.getText())).toString());
                        break;
                    case "cpx":
                        /*
                        Goes into complex mode. To use this function. Please choose a number, then click "cpx"
                        then choose the second number for the complex number then enter any operation you want
                        to do with the complex number. For example, 2 -> / -> 3 -> = -> cpx -> 4 -> + -> 3
                        */
                        if (tl.getText().indexOf('i')  == -1) {
                            if (!in_complex)
                                tl.setText(currentText + "+0i");
                            else {
                                int temp = currentText.indexOf('+');
                                tl.setText(tl.getText().substring(0, temp) + "+" + currentText + "i");
                            }
                            in_complex = !in_complex;
                        }
                        break;
                    case "AC":
                        // Resets everything
                        tl.setText(String.valueOf(0));
                        current_operation = Operation.none;
                        previous_operation = Operation.none;
                        previous_number = new ComplexNumber(0);
                        running_total = new ComplexNumber(0);
                        in_complex = false;

                        break;
                    case "=":
                        // Calculates the numbers based on the operations entered
                        if (previous_operation != Operation.none && (current_operation == Operation.multiply || current_operation == Operation.divide)) {
                            // calculate the priority operation
                            previous_number = doCalculation(previous_number, new ComplexNumber(tl.getText()), current_operation);
                            // calculate the previous operation
                            previous_number = doCalculation(running_total, previous_number, previous_operation);
                        } else if (previous_operation != Operation.none)
                            previous_number = doCalculation(previous_number, new ComplexNumber(tl.getText()), previous_operation);
                        else
                            previous_number = doCalculation(previous_number, new ComplexNumber(tl.getText()), current_operation);

                        tl.setText(previous_number.toString());

                        // Reset
                        previous_operation = Operation.none;
                        current_operation = Operation.none;
                        running_total = new ComplexNumber(0);

                        break;
                    default:
                        // Enters number into JLabel
                        if (can_clear) {
                            tl.setText(((JButton) e.getSource()).getText());
                            can_clear = false;
                            // Doesn't enter if it is in complex mode
                        } else if (tl.getText().indexOf('+') != -1 && in_complex) {
                            int i = tl.getText().indexOf('+');
                            if (tl.getText().substring(i + 1).equals("0i"))
                                tl.setText(tl.getText().substring(0, i + 1) + ((JButton) e.getSource()).getText() + "i");
                            else
                                tl.setText(tl.getText().substring(0, tl.getText().length() - 1) + ((JButton) e.getSource()).getText() + "i");
                            // Doesn't enter if you enter 0 after a 0
                        } else if (!tl.getText().equals("0"))
                            tl.setText(tl.getText() + ((JButton) e.getSource()).getText());
                        else
                            tl.setText(((JButton) e.getSource()).getText());
                        break;
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }

//            System.out.println("Total: " + running_total);
//            System.out.println("PreviousN: " + previous_number);
//            System.out.println("CurrentO: " + current_operation);
//            System.out.println("PreviousO: " + previous_operation);
        }

        // Check priority of the calculation and simplifies it
        private void checkPriority(Operation o) {
            if ((o == Operation.add || o == Operation.subtract)) {
                if (current_operation == Operation.none) {
                    previous_number = new ComplexNumber(tl.getText());
                    running_total = previous_number;
                }
                if ((previous_operation != Operation.none) && (current_operation == Operation.multiply || current_operation == Operation.divide)) {
                    previous_number = doCalculation(previous_number, new ComplexNumber(tl.getText()), current_operation);
                    previous_number = doCalculation(running_total, previous_number, previous_operation);
                } else if (previous_operation != Operation.none) {
                    previous_number = doCalculation(previous_number, new ComplexNumber(tl.getText()), o);
                    running_total = previous_number;
                }
                else {
                    previous_number = doCalculation(previous_number, new ComplexNumber(tl.getText()), current_operation);
                    running_total = previous_number;
                }
            } else {
                if (current_operation == Operation.multiply || current_operation == Operation.divide)
                    previous_number = doCalculation(previous_number, new ComplexNumber(tl.getText()), current_operation);
                else
                    previous_number = new ComplexNumber(tl.getText());
            }
        }
    };

    // Calculates two complex numbers
    private ComplexNumber doCalculation(ComplexNumber x, ComplexNumber y, Operation o) {
        switch (o) {
            case none:
                return y;
            case add:
                return x.add(y);
            case subtract:
                return x.subtract(y);
            case multiply:
                return x.multiply(y);
            case divide:
                // Can't divide if denominator is 0
                if (!(y.multiply(y).toString().equals("0")))
                    return x.divide(y);
                else
                    System.out.println("Value undefined");
                break;
        }
        return x;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Calculator application_window = new Calculator();
                application_window.f.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
