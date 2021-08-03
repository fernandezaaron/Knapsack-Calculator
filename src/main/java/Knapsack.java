import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class Knapsack extends JFrame {

    JPanel mother, upper, first, daughter, codesimulation, inside, randomization;
    JScrollPane scroll, scrollMommy, resultScroll, codeSimscroll;
    JTable pw, resulttable;
    DefaultTableModel dtmpw, dtmresult;
    JTextField weight, numberofitems, finalanswer;
    JTextField[] codesim = new JTextField[1000];
    JLabel weightlabel, noitemslabel;
    JButton enter,clear, Calculate, next, prev, random, skip;
    JTextField finalanswer1;
    HashMap<Integer, Integer> data = new HashMap<>();
    int click = 1;
    int click2 = 1;
    int min = 1;
    int max = 100;

    public Knapsack() {
        super("Knapsack");

        int[] profit = new int[1000];
        int[] weightvalue = new int[1000];

        mother = new JPanel(new BorderLayout());
        mother.setBackground(Color.black);

        daughter = new JPanel(new BorderLayout());

        random = new JButton(new AbstractAction("Random") { //random values for the profit and weight table
            @Override
            public void actionPerformed(ActionEvent e) {
                double r1;
                int numberint = Integer.parseInt(numberofitems.getText());
                for(int i = 0; i < numberint; i++) {     //for loop to set random values in the profit column
                    int parsingDouble;
                    r1 = Math.random() * (max-min + 1) + min;
                    parsingDouble = (int) r1;
                    dtmpw.setValueAt(parsingDouble, i,0);
                    
                }
                double r2;
                max = Integer.parseInt(weight.getText());
                for(int j = 0; j < numberint; j++) {    //for loop to set random values in the weight column
                    int parsingInt;                     //where the max value is set to the max capacity of the entered capacity
                    r2 = Math.random() * (max-min + 1) + min;
                    parsingInt = (int) r2;
                    dtmpw.setValueAt(parsingInt, j, 1);
                }
            }
        });

        enter = new JButton(new AbstractAction("Generate Table") { //generates the profit and weight table
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberint = Integer.parseInt(numberofitems.getText());
                if(numberint <= 0){
                    JOptionPane.showMessageDialog(null, "Input a valid number greater than 0", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    first = new JPanel(new GridLayout());
                    first.setBackground(Color.black);

                    codesimulation = new JPanel(new BorderLayout());
                    codesimulation.setBackground(Color.lightGray);

                    randomization = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    randomization.setBackground(Color.lightGray);

                    dtmpw = new DefaultTableModel(numberint, 2);
                    dtmpw.setColumnIdentifiers(new Object[]{"Value", "Weight"});
                    pw = new JTable(dtmpw);
                    scroll = new JScrollPane(pw, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollMommy = new JScrollPane(daughter, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


                    pw.setBackground(new Color(168, 178, 147));
                    scroll.getViewport().setBackground(new Color(254, 194, 65));
                    randomization.setBackground(new Color(254, 194, 65));
                    random.setBackground(new Color(160, 175, 20));
                    next.setBackground(new Color(160, 175, 20));
                    prev.setBackground(new Color(160, 175, 20));
                    skip.setBackground(new Color(160, 175, 20));
                    Calculate.setBackground(new Color(202, 71, 0));
                    randomization.add(random);
                    randomization.add(prev);
                    randomization.add(next);
                    randomization.add(skip);
                    daughter.add(randomization, BorderLayout.NORTH);
                    daughter.add(codesimulation, BorderLayout.EAST);
                    daughter.add(first, BorderLayout.CENTER);
                    daughter.add(scroll, BorderLayout.WEST);
                    daughter.add(Calculate, BorderLayout.SOUTH);
                    mother.add(scrollMommy);
                    mother.setBackground(Color.gray);
                    add(mother, BorderLayout.CENTER);
                    first.setVisible(true);
                    daughter.setVisible(true);
                    mother.setVisible(true);
                    scroll.setVisible(true);
                    scrollMommy.setVisible(true);
                    random.setEnabled(true);

                    mother.updateUI();
                }
            }
        });

        clear = new JButton(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                next.setEnabled(true);
                weight.setText("");
                numberofitems.setText("");
                daughter.removeAll();
                daughter.revalidate();
                daughter.repaint();
                mother.removeAll();
                mother.revalidate();
                mother.repaint();
                click = 1;
                click2 = 1;
            }
        });

        Calculate = new JButton(new AbstractAction("Calculate") { //starts the knapsack algorithm
            @Override
            public void actionPerformed(ActionEvent e) {
                int weightint = Integer.parseInt(weight.getText());
                int numberint = Integer.parseInt(numberofitems.getText());

                inside = new JPanel();
                inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
                dtmresult = new DefaultTableModel(numberint+1, weightint+1);

                resulttable = new JTable(dtmresult);
                resultScroll = new JScrollPane(resulttable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                for(int i=0; i<numberint; i++) {
                    String[] ph = new String[numberint];
                    ph[i] = dtmpw.getValueAt(i,0).toString();
                    if(Integer.valueOf(ph[i]) < 0){
                        dtmpw.setValueAt(0,i,0);
                        profit[i] = Integer.parseInt(ph[i]);
                        JOptionPane.showMessageDialog(null, "Enter a value greater than 0", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    profit[i] = Integer.parseInt(ph[i]);      //stores the values from the profit column to an array
                }

                for(int i=0; i<numberint; i++) {
                    String[] ph2 = new String[numberint];
                    ph2[i] = dtmpw.getValueAt(i,1).toString();
                    if(Integer.valueOf(ph2[i])<0){
                        dtmpw.setValueAt(0,i,1);
                        weightvalue[i] = Integer.parseInt(ph2[i]);
                        JOptionPane.showMessageDialog(null, "Enter a value greater than 0", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    weightvalue[i] = Integer.parseInt(ph2[i]);  //stores the values from the weight column to an array
                }

                Knapsack(weightvalue,profit,numberint,weightint); //does the knapsack function

                String[] placeholder2 = new String[]{
                        "   for(int j=0; j<=col; j++) { ",
                                "            dtmresult.setValueAt(0,0,j);",
                                "        }",
                                "        for(int i=0; i<=row; i++) {",
                                "            dtmresult.setValueAt(0,i,0);",
                                "        }"
                };

                for(int i=0; i<6; i++) {
                    codesim[i] = new JTextField();
                    inside.add(codesim[i]);
                    codesim[i].setBackground(new Color(168,178,147));
                    codesim[i].setText(placeholder2[i]);
                    codesim[i].setEditable(false);
                }

                String[] placeholder = new String[]
                        {"for(int i=1; i<=row; i++){ ",
                                "   for(int j=1; j<=col; j++){",
                                "       if(weight[i-1] > j)",
                                "   values[i][j] = values[i-1][j];",
                                "   else{",
                                "       values[i][j] = Math.max(values[i-1][j], values[i-1][j-weight[i-1]] + profit[i-1]);",
                                "}",
                                "dtmresult.setValueAt(values[i][j],i,j);", "}", "}"};
                for(int i=0; i<10; i++) {
                    codesim[i] = new JTextField();
                    inside.add(codesim[i]);
                    codesim[i].setBackground(new Color(168,178,147));
                    codesim[i].setText(placeholder[i]);
                    codesim[i].setEditable(false);
                }

                for(int i=1; i<=numberint;i++) {
                    for(int j=1; j<=weightint; j++){
                        dtmresult.setValueAt("",i,j);
                    }
                }




                codeSimscroll = new JScrollPane(inside, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                codesimulation.add(codeSimscroll, BorderLayout.NORTH);

                resulttable.setBackground(new Color(168,178,147));
                resultScroll.getViewport().setBackground(new Color(254,194,65));

                first.add(resultScroll);
                resultScroll.setVisible(true);
                random.setEnabled(false);
                first.updateUI();
            }
        });

        next = new JButton(new AbstractAction("Next") { //shows the code simulation, step by step procedure
            @Override
            public void actionPerformed(ActionEvent e) {
                prev.setEnabled(true);
                int weightint = Integer.parseInt(weight.getText());
                int numberint = Integer.parseInt(numberofitems.getText());
                Knapsack(weightvalue,profit,click,click2); //knapsack function that is based on the clicks of next button


                if(click2 < weightint+1) {
                    codesim[0].setBackground(new Color(219,123,101));
                    for(int i=1; i<=7; i++){
                        codesim[i].setBackground(new Color(221,176,167));
                    }
                }

                click2++;
                if(click2 == weightint+1) { //if click2 has reached the last column
                    codesim[0].setBackground(new Color(219,123,101));
                    for(int i=1; i<=6; i++){
                        codesim[i].setBackground(new Color(168,178,147));
                    }
                    codesim[7].setBackground(new Color(219,123,101));
                    click2 = 1; //set click2 back to one and increment click (row)
                    click++;
                }
                System.out.println("click: " + click);
                System.out.println("click2: "+ click2);

                if(click2 == 1 && click == numberint+1){ //if click has reached the last row
                    click = numberint; //set click and click2 to their max values
                    click2 = weightint;
                    next.setEnabled(false);
                    for(int i=0; i<10; i++){
                        codesim[i].setBackground(new Color(243,173,90));
                    }
                    JOptionPane.showMessageDialog(null,"End of the simulation","END",JOptionPane.INFORMATION_MESSAGE);
                }
//                System.out.println(click + " " + click2);
                codesimulation.updateUI();
            }
        });

        prev = new JButton(new AbstractAction("Prev") { //to stepback in the codesimulation
            @Override
            public void actionPerformed(ActionEvent e)
            {
                next.setEnabled(true);
                int weightint = Integer.parseInt(weight.getText());
                int numberint = Integer.parseInt(numberofitems.getText());
                Knapsack(weightvalue,profit,click,click2); //knapsack function based on clicks
                dtmresult.setValueAt("",click,click2); //sets the value of the cell to nothing
                click2--; //stepsback
                if(click2 == 0){        //if click2 is at the first index of the row, move to the previous row
                    if (click == 1){
                        prev.setEnabled(false);
                    }
                    click--;
                    click2 = weightint;
                }

                if(click > numberint+1 && click2 == 1){
                    click--;
                    click2=weightint;
                }

//                System.out.println("prev2: " + click2);
//                System.out.println("prev1: "+ click);
            }
        });

        skip = new JButton(new AbstractAction("Skip") { //skips the codesimulation and display the values in the table
            @Override
            public void actionPerformed(ActionEvent e) {
                int weightint = Integer.parseInt(weight.getText());
                int numberint = Integer.parseInt(numberofitems.getText());
                Knapsack(weightvalue,profit,numberint,weightint);
                for(int i=0; i<10; i++){
                    codesim[i].setBackground(new Color(243,173,90));
                }
                codesimulation.updateUI();

            }
        });

        upper = new JPanel();
        upper.setBackground(new Color(254,194,65));
        upper.setLayout(new GridLayout(3,2));

        weightlabel = new JLabel("Enter Max Capacity: ");
        weightlabel.setForeground(new Color(202,71,0));
        noitemslabel = new JLabel("Enter Number of Items: ");
        noitemslabel.setForeground(new Color(202,71,0));
        weight = new JTextField();
        weight.setBackground(Color.white);
        numberofitems = new JTextField();
        numberofitems.setBackground(Color.white);
        enter.setBackground(new Color(202,71,0));
        clear.setBackground(new Color(202,71,0));

        upper.add(weightlabel);
        upper.add(noitemslabel);
        upper.add(weight);
        upper.add(numberofitems);
        upper.add(enter);
        upper.add(clear);

        add(upper,BorderLayout.NORTH);
        getContentPane().setBackground(new Color(243,173,90));
        setSize(1280,720);
        setVisible(true);
    }
    public void Knapsack(int[] weight, int[] profit, int row, int col) {
        int[][] values = new int[row+1][col+1];

        for(int j=0; j<=col; j++) {
            dtmresult.setValueAt(0,0,j); //if row is 0, set value to 0
        }

        for(int i=0; i<=row; i++) {
            dtmresult.setValueAt(0,i,0); //if col is 0, set value to 0
        }

        for(int i=1; i<=row; i++) {
            for(int j=1; j<=col; j++) { //loop to iterate the whole table
                if(weight[i-1] > j) //checks if the previous weight has more weight than the current index
                    values[i][j] = values[i-1][j]; //sets the previous index to the current index
                else {
                   // System.out.println(values[i][j] + " = " + values[i-1][j] +"," + values[i-1][j-weight[i-1]] + " + "+ profit[i-1] + " " + i);
                    values[i][j] = Math.max(values[i-1][j], values[i-1][j-weight[i-1]] + profit[i-1]); //gets the max value regarding of the weight
                }
                dtmresult.setValueAt(values[i][j],i,j);
                data.put(1, values[i][j]); //puts the data to a hashmap for future references.
                dtmresult.setValueAt(data.get(1),i,j);
            }
        }

        int a = row;
        int b = col;
        for(int i=a; i!=0; i--){
            System.out.println("dumadaan ako dito");
            if(values[a][b] != values[a-1][b]){
                finalanswer = new JTextField();
                finalanswer.setText("Index: " + a);
                inside.add(finalanswer);
            }
            a--;
        }

    }
}
