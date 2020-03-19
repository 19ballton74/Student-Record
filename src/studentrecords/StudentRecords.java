/*
 * File: StudentRecords.java
 * Author: Brock A. Allton
 * Date: 9 October 2016
 * Purpose: Create a GUI to input student id, name, major and GPA into hashmap
 */
package studentrecords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Grade{
    private double grade;
    private int credit;
    
    public Grade(double grade, int credit){
        this.grade = grade;
        this.credit = credit;
    }
    
      public double getGrade(){
        return grade;
    }
    
    public void setGrade(double grade){
        this.grade = grade;
    }
    
    public int getCredit(){
        return credit;
    }
    
    public void setCredit(int credit){
        this.credit = credit;
    }
}


class Student{
    private String studentName;
    private String studentMajor;
    private ArrayList<Grade> list;
    
    public Student (String studentName, String studentMajor){
        this.studentName = studentName;
        this.studentMajor = studentMajor;
        list = new ArrayList<Grade>();
    }
    
    public String getName(){
        return studentName;
    }
   
    public void setName (String studentName){
        this.studentName = studentName;
    }
    
    public String getMajor(){
        return studentMajor;
    }
    
    public void setMajor(String studentMajor){
        this.studentMajor = studentMajor;
    }
    
    public void addGrade(double grade, int credits){
        list.add(new Grade(grade, credits));
    }
    
    //Return GPA
    public double getGPA(){
        int n = 0;
        double sum = 0;
        for(Grade g : list){
            n += g.getCredit();
            sum += g.getGrade()*g.getCredit();
        }
        if(n == 0){
            return 4;
        }
        else{
            return sum / n;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Name: ").append(studentName).append("\n");
        s.append("Major: ").append(studentMajor).append("\n");
        s.append("GPA: ").append(getGPA()).append("\n");
        return s.toString();
    }
          
}//End Student Class

public class StudentRecords extends JFrame {
    private final JLabel idLabel, nameLabel, majorLabel, selectionLabel;
    private final JTextField idField, nameField, majorField;
    private final JButton processButton;
    private final JComboBox selectionBox;
    private String[] actionSelections = {"Insert", "Delete", "Find", "Update"};
    private static final int WIDTH = 300, HEIGHT = 250;
    private HashMap<String, Student>students = new HashMap<String, Student>();
    public StudentRecords(){
       
        setTitle("Student Records");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        
        //Set so window opens in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setLocation(x,y);
        
        //Close window upon exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set up panel for button, text fields, and combo box to be placed on
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets (0,0,10,5);
        
        idLabel = new JLabel("Id:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(idLabel, constraints);
        
        nameLabel = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(nameLabel, constraints);
        
        majorLabel = new JLabel("Major:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(majorLabel, constraints);
        
        selectionLabel = new JLabel("Choose Selection:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(selectionLabel, constraints);
        
        processButton = new JButton("Process Request");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(processButton, constraints);
        processButton.addActionListener (new ButtonListener());
        
        constraints.insets = new Insets (10,0,10,0);
        idField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(idField, constraints);
        
        nameField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(nameField, constraints);
        
        majorField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(majorField, constraints);
        
        selectionBox = new JComboBox(actionSelections);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(selectionBox, constraints);
        selectionBox.addActionListener(new ButtonListener());
       
        setVisible(true);
    }//End public StudentRecords
    
    private class ButtonListener implements ActionListener{
           @Override
        public void actionPerformed (ActionEvent e){
       
            String selection = selectionBox.getSelectedItem().toString();
            String studentID = idField.getText();
            String studentName = nameField.getText();
            String studentMajor = majorField.getText();
            Object source = e.getSource();
            
            if(source == processButton){
            try{
                switch(selection){
                    case "Insert":
                        if (students.containsKey(studentID)){
                            JOptionPane.showMessageDialog(null, "ID " + studentID
                            + " Already Exists!");
                        }
                        else{
                            students.put(studentID, new Student(studentName, studentMajor));
                            JOptionPane.showMessageDialog(null, "Student Added: \n"  
                            + "ID: " + studentID + "\nName: " + studentName + 
                                    "\nMajor: " + studentMajor);
                            idField.setText("");
                            nameField.setText("");
                            majorField.setText("");
                        }
                        break;
                    case "Find":
                        nameField.setEditable(false);
                        majorField.setEditable(false);
                        Student student = students.get(studentID);
                        if(student != null){
                           JOptionPane.showMessageDialog(null, student.toString());
                           idField.setText("");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "ID " + studentID
                            + " Not Found!");
                        }
                        break;
                    case "Delete":
                        nameField.setEditable(false);
                        majorField.setEditable(false);
                        if(students.remove(studentID) != null){
                            JOptionPane.showMessageDialog(null, "Student ID " +
                                    studentID + " Removed Successfully");
                            idField.setText("");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Student ID " +
                                    studentID + " Does Not Exist!");
                        }
                        break;
                    case "Update":
                        double grade = 0;
                        String[] Grade = {"A", "B","C","D","F"}; 
                           String studentGrade = 
                                   (String) JOptionPane.showInputDialog(null, 
                                           "Choose Grade:","",
                                           JOptionPane.QUESTION_MESSAGE,null,Grade, Grade[0]);
                        if(studentGrade.equals("A")){
                            grade = 4;
                        }
                        else if(studentGrade.equals("B")){
                            grade = 3;
                        }
                        else if(studentGrade.equals("C")){
                            grade = 2;
                        }
                        else if (studentGrade.equals("D")){
                            grade = 1;
                        }
                        else{
                            grade = 0;
                        }
                        String[] Credits = {"2","3","4","5","6"};
                        int credits = Integer.parseInt((String)
                                JOptionPane.showInputDialog(null,"Choose Credits:","",
                                        JOptionPane.QUESTION_MESSAGE,null, Credits,Credits[0]));   
                        if(students.containsKey(studentID)){
                            student = students.get(studentID);
                            student.addGrade(grade, credits);
                            students.put(studentID, student);
                            JOptionPane.showMessageDialog(null, student.toString());
                            idField.setText("");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Student ID "
                                    + studentID + " Doese Not Exist!");
                        }
                           
                }//End Switch
            }//End try
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Error while " + selection +
                        ": " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                         ex.printStackTrace();
            }//End catch
            }
        }//End actionPerformed      
    }//End ButtonListener

    public static void main(String[] args) {
       new StudentRecords();
       
    }//End main    
}//End class
