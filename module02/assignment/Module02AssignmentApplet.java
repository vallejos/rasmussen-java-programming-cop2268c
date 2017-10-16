/**
 * This program will create a web applet to calculate the area of different figures.
 * By Leonardo Vallejos
 */
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Module02AssignmentApplet extends Applet implements ActionListener {

   // declare variables
   double area, length, width, radius, base, height, circumference;
   double pi = 3.141592;

   // home screen components   
   Label appTitleLbl = new Label("Module 02 Assignment Applet\n", Label.CENTER);
   Label helpLbl = new Label("Select an option:");
   Button squareBtn = new Button("Square");
   Button triangleBtn = new Button("Triangle");
   Button circleBtn = new Button("Circle");
   Button rectangleBtn = new Button("Rectangle");
   Button otherBtn = new Button("Other Calculation");
   Button exitAppBtn = new Button("Exit Program");

   // square screen components
   Label squareTitleLbl = new Label("Calculate Square Area\n", Label.CENTER);
   Label squareLengthLbl = new Label("Length:\n");
   TextField squareLengthInputField = new TextField(10);
   Button squareCalcBtn = new Button("Calculate");
   Button squareBackBtn = new Button("<< Back");
   Label squareResultLbl = new Label();

   // circle screen components
   Label circleTitleLbl = new Label("Calculate Circle Circumference\n", Label.CENTER);
   Label circleRadiusLbl = new Label("Radius:\n");
   TextField circleRadiusInputField = new TextField(10);
   Button circleCalcBtn = new Button("Calculate");
   Button circleBackBtn = new Button("<< Back");
   Label circleResultLbl = new Label();

   // triangle screen components
   Label triangleTitleLbl = new Label("Calculate Triangle Area\n", Label.CENTER);
   Label triangleBaseLbl = new Label("Base:\n");
   Label triangleHeightLbl = new Label("Height:\n");
   TextField triangleBaseInputField = new TextField(10);
   TextField triangleHeightInputField = new TextField(10);
   Button triangleCalcBtn = new Button("Calculate");
   Button triangleBackBtn = new Button("<< Back");
   Label triangleResultLbl = new Label();

   // rectangle screen components
   Label rectangleTitleLbl = new Label("Calculate Rectangle Area\n", Label.CENTER);
   Label rectangleLengthLbl = new Label("Length:\n");
   Label rectangleWidthLbl = new Label("Width:\n");
   TextField rectangleLengthInputField = new TextField(10);
   TextField rectangleWidthInputField = new TextField(10);
   Button rectangleCalcBtn = new Button("Calculate");
   Button rectangleBackBtn = new Button("<< Back");
   Label rectangleResultLbl = new Label();

   // other screen components
   Label otherTitleLbl = new Label("Calculate Other Area\n", Label.CENTER);
   Button otherCalcBtn = new Button("Calculate");
   Button otherBackBtn = new Button("<< Back");
   Label otherResultLbl = new Label();


   // print square screen
   public void addSquareScreenComponents() {
      setSize(300, 150);

      add(squareTitleLbl);
      add(squareLengthLbl);
      add(squareLengthInputField);
      add(squareBackBtn);
      add(squareCalcBtn);
      add(squareResultLbl);
      
      squareBackBtn.addActionListener(this);
      squareCalcBtn.addActionListener(this);
   }

   // print circle screen
   public void addCircleScreenComponents() {
      setSize(300, 150);

      add(circleTitleLbl);
      add(circleRadiusLbl);
      add(circleRadiusInputField);
      add(circleBackBtn);
      add(circleCalcBtn);
      add(circleResultLbl);
      
      circleBackBtn.addActionListener(this);
      circleCalcBtn.addActionListener(this);
   }

   // print rectangle screen
   public void addRectangleScreenComponents() {
      setSize(300, 150);

      add(rectangleTitleLbl);
      add(rectangleLengthLbl);
      add(rectangleLengthInputField);
      add(rectangleWidthLbl);
      add(rectangleWidthInputField);
      add(rectangleBackBtn);
      add(rectangleCalcBtn);
      add(rectangleResultLbl);
      
      rectangleBackBtn.addActionListener(this);
      rectangleCalcBtn.addActionListener(this);
   }

   // print triangle screen
   public void addTriangleScreenComponents() {
      setSize(300, 150);

      add(triangleTitleLbl);
      add(triangleBaseLbl);
      add(triangleBaseInputField);
      add(triangleHeightLbl);
      add(triangleHeightInputField);
      add(triangleBackBtn);
      add(triangleCalcBtn);
      add(triangleResultLbl);
      
      triangleBackBtn.addActionListener(this);
      triangleCalcBtn.addActionListener(this);
   }

   // print other screen
   public void addOtherScreenComponents() {
      setSize(300, 150);

      add(otherTitleLbl);
      add(otherBackBtn);
      add(otherCalcBtn);
      
      otherBackBtn.addActionListener(this);
      otherCalcBtn.addActionListener(this);
   }

   // print home screen
   public void addHomeScreenComponents() {
      setSize(400, 250);

      add(appTitleLbl);
      add(helpLbl);
      add(squareBtn);
      add(triangleBtn);
      add(circleBtn);
      add(rectangleBtn);
//       add(otherBtn);
      add(exitAppBtn);
      
      squareBtn.addActionListener(this);
      triangleBtn.addActionListener(this);
      circleBtn.addActionListener(this);
      rectangleBtn.addActionListener(this);
      otherBtn.addActionListener(this);
      exitAppBtn.addActionListener(this);
   }

   // remove square screen
   public void removeSquareScreenComponents() {
      remove(squareTitleLbl);
      remove(squareLengthLbl);
      remove(squareLengthInputField);
      remove(squareBackBtn);
      remove(squareCalcBtn);
      remove(squareResultLbl);
   }

   // remove circle screen
   public void removeCircleScreenComponents() {
      remove(circleTitleLbl);
      remove(circleRadiusLbl);
      remove(circleRadiusInputField);
      remove(circleBackBtn);
      remove(circleCalcBtn);
      remove(circleResultLbl);
   }

   // remove rectangle screen
   public void removeRectangleScreenComponents() {
      remove(rectangleTitleLbl);
      remove(rectangleLengthLbl);
      remove(rectangleWidthLbl);
      remove(rectangleLengthInputField);
      remove(rectangleWidthInputField);
      remove(rectangleBackBtn);
      remove(rectangleCalcBtn);
      remove(rectangleResultLbl);
   }

   // remove triangle screen
   public void removeTriangleScreenComponents() {
      remove(triangleTitleLbl);
      remove(triangleBaseLbl);
      remove(triangleHeightLbl);
      remove(triangleBaseInputField);
      remove(triangleHeightInputField);
      remove(triangleBackBtn);
      remove(triangleCalcBtn);
      remove(triangleResultLbl);
   }

   // remove other screen
   public void removeOtherScreenComponents() {
      remove(otherTitleLbl);
      remove(otherBackBtn);
      remove(otherCalcBtn);
   }

   // remove home screen
   public void removeHomeScreenComponents() {
      remove(appTitleLbl);
      remove(squareBtn);
      remove(triangleBtn);
      remove(circleBtn);
      remove(rectangleBtn);
//       remove(otherBtn);
      remove(exitAppBtn);
      remove(helpLbl);
   }

   // perform action based on the button clicked
   public void actionPerformed(ActionEvent e) {

      if (e.getSource() == squareBtn) {
         // square
         removeHomeScreenComponents();
         addSquareScreenComponents();
      
      } else if (e.getSource() == triangleBtn) {
         // triangle
         removeHomeScreenComponents();
         addTriangleScreenComponents();
      
      } else if (e.getSource() == circleBtn) {
         // circle
         removeHomeScreenComponents();
         addCircleScreenComponents();
      
      } else if (e.getSource() == rectangleBtn) {
         // rectangle
         removeHomeScreenComponents();
         addRectangleScreenComponents();
      
      } else if (e.getSource() == otherBtn) {
         // other
         removeHomeScreenComponents();
         addOtherScreenComponents();
      
      } else if (e.getSource() == exitAppBtn) {
         // exit
         finalize();
          
      } else if (e.getSource() == squareCalcBtn) {
         // calculate square area
         length = Double.parseDouble(squareLengthInputField.getText());
         area = length * length;
         squareResultLbl.setText("Area: " + area);
      
      } else if (e.getSource() == triangleCalcBtn) {
         // caculate triangle area
         base = Double.parseDouble(triangleBaseInputField.getText());
         height = Double.parseDouble(triangleHeightInputField.getText());
         area = base * height / 2;
         triangleResultLbl.setText("Area: " + area);
      
      } else if (e.getSource() == circleCalcBtn) {
         // calculate circle circumference
         radius = Double.parseDouble(circleRadiusInputField.getText());
         circumference = radius * 2 * pi;
         circleResultLbl.setText("Circumference: " + circumference);
      
      } else if (e.getSource() == rectangleCalcBtn) {
         // calculate rectangle area
         length = Double.parseDouble(rectangleLengthInputField.getText());
         width = Double.parseDouble(rectangleWidthInputField.getText());
         area = length * width;
         rectangleResultLbl.setText("Area: " + area);
      
      } else if (e.getSource() == otherCalcBtn) {
         // calculate other area
      
      } else if (e.getSource() == squareBackBtn) {
         // back to home screen from square screen
         removeSquareScreenComponents();
         addHomeScreenComponents();
      
      } else if (e.getSource() == triangleBackBtn) {
         // back to home screen from triangle screen
         removeTriangleScreenComponents();
         addHomeScreenComponents();
      
      } else if (e.getSource() == circleBackBtn) {
         // back to home screen from circle screen
         removeCircleScreenComponents();
         addHomeScreenComponents();
      
      } else if (e.getSource() == rectangleBackBtn) {
         // back to home screen from rectangle screen
         removeRectangleScreenComponents();
         addHomeScreenComponents();
      
      } else if (e.getSource() == otherBackBtn) {
         // back to home screen from other screen
         removeOtherScreenComponents();
         addHomeScreenComponents();
      
      } else {
         // I shouldn't be here
         System.out.println("Unknown option: " + e);
      
      } // if

   
   } // actionPerformed

   // initialize applet
   public void init() {
     	setLayout(new GridLayout(8, 1));
      addHomeScreenComponents();
   }

   public void finalize() {
      System.out.println("Bye!");
      removeHomeScreenComponents();
      setSize(200, 100);
      add(new Label("Bye!", Label.CENTER));
      destroy();
   }

}