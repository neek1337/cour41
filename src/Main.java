import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by neek on 06.05.16.
 */
public class Main {
    Scanner scanner;
    int stateNumber;
    int size;
    int[][] transitionMatrix;
    int[][] outMatrix;
    ArrayList<int[]> sequenses = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        new Main().run();

    }

    public void run() throws FileNotFoundException {
        scanner = new Scanner(new File("input.txt"));
        stateNumber = scanner.nextInt();
        size = scanner.nextInt();
        transitionMatrix = new int[stateNumber][size];
        outMatrix = new int[stateNumber][size];
        int k;
        for (int i = 0; i < stateNumber; i++) {
            for (int j = 0; j < size; j++) {
                transitionMatrix[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < stateNumber; i++) {
            for (int j = 0; j < size; j++) {
                outMatrix[i][j] = scanner.nextInt();
            }
        }

        ArrayList<Integer> outputSequence = new ArrayList<>();

        k = scanner.nextInt();


        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0);
        arrayList.add(1);
        findPwd(new int[k], k - 1, arrayList);

        ArrayList<Pair<Double, Double>> dots = new ArrayList<>();
        for (int[] sequense : sequenses) {

            int state = 0;
            for (Integer signal : sequense) {
                Pair<Integer, Integer> pair = step(state, signal);
                state = pair.getKey();
                outputSequence.add(pair.getValue());
            }

            dots.add(new Pair<>(u(sequense) / Math.pow(2, k), u(outputSequence) / Math.pow(2, k)));
            outputSequence = new ArrayList<>();
        }


        JFrame frame = new JFrame("Test");
        frame.setBounds(0, 0, 400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel() {
            Graphics2D g2;

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.drawRect(100, 100, 200, 200);
                g2.setColor(Color.RED);
                for (Pair<Double, Double> dot : dots) {
                    g2.drawOval(100 + (int) (200 * dot.getKey()), 300 - (int) (200 * dot.getValue()), 1, 1);
                }
            }
        };
        frame.setContentPane(contentPane);

    }

    public Double u(ArrayList<Integer> sequence) {
        Double result = 0.0;
        for (int i = 0; i < sequence.size(); i++) {
            result += (sequence.get(i) + 1) / Math.pow(3, i);
        }
        return result;
    }

    public Double u(int[] sequence) {
        Double result = 0.0;
        for (int i = 0; i < sequence.length; i++) {
            result += (sequence[i] + 1) / Math.pow(3, i);
        }
        return result;
    }

    public Pair<Integer, Integer> step(int state, int signal) {
        return new Pair<Integer, Integer>(transitionMatrix[state][signal], outMatrix[state][signal]);
    }

    void findPwd(int[] pw, int pos, ArrayList<Integer> list) {
        if (pos < 0) {
            sequenses.add(Arrays.copyOf(pw, pw.length));
            return;
        }
        for (Integer c : list) {
            pw[pos] = c;
            findPwd(pw, pos - 1, list);
        }

    }


}
