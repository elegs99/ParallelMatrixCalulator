import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calcGUI implements ActionListener
{
	//class constants
	private static final int WINDOW_WIDTH = 800;//pixels
	private static final int WINDOW_HEIGHT = 800;//pixels
	private static final int FIELD_WIDTH = 20;//characters
	private static final int AREA_WIDTH = 40;//characters

	private static final GridBagLayout LAYOUT_STYLE =  new GridBagLayout(); //This sets the frame layout
	GridBagConstraints gc = new GridBagConstraints();
	private static final String LEGEND = " This calculator is intended to calculate linear operations between two matricies.";

	//instance variables
	private JFrame window = new JFrame("Matrix Calculator");

	//legend.
	private JTextArea legendArea = new JTextArea(LEGEND, 2, AREA_WIDTH); //distance between the provided text and the input fields

	private JLabel ATag = new JLabel("Matrix A");
	private JLabel BTag = new JLabel("Matrix B");
	private JLabel OTag = new JLabel("Operation:");
	private JLabel RTag = new JLabel("Result");

	private JTextArea AText = new JTextArea();
	private JTextArea BText = new JTextArea();
	private JTextArea ResultText = new JTextArea();

	private JButton AButton = new JButton("Automatic");
	private JButton MButton = new JButton("Manual");
	private JButton calcButton = new JButton("Calculate");
	private JButton clrResButton = new JButton("Clear Results");
	private JButton clrABButton = new JButton("Clear Matricies");

	private JPanel Input = new JPanel(new GridBagLayout());
	private JPanel manOrAuto = new JPanel(new GridBagLayout());
	private JPanel opDropBox = new JPanel(new FlowLayout());
	private JPanel result = new JPanel(new GridBagLayout());
	private JPanel clr = new JPanel(new FlowLayout());


	private String[] Opts = {"Multiply","Transpose"};
	private JComboBox<String> operations = new JComboBox<>(Opts);

	private JLabel blank = new JLabel(" ");
	private JLabel blank1 = new JLabel(" ");
	private JLabel blank2 = new JLabel(" ");

	//configure GUI
	private void setup1()
	{
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ResultText.setEditable(false);

		legendArea.setEditable(false); //Allows user to edit the legend above...probably want this off at most times
		legendArea.setLineWrap(true); //Allows lines to move to the next line if they don't fit the width.
		legendArea.setWrapStyleWord(true);
		legendArea.setBackground(window.getBackground()); //background of legend

		//Drivers.setBounds(80, 50, 140, 20);
		//URLs.setBounds(80, 50, 140, 20);
	}

	private void setup2(Container c)
	{
		manOrAuto.add(AButton);
		manOrAuto.add(MButton);

		opDropBox.add(OTag);
		opDropBox.add(operations);

		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		result.add(RTag, gc);
		gc.gridx = 0;
		gc.gridy = 1;
		result.add(ResultText, gc);

		clr.add(blank2);
		clr.add(clrABButton);
		clr.add(clrResButton);

		gc.gridx = 0;
		gc.gridy = 0;
		Input.add(manOrAuto, gc);


		gc.gridx = 2;
		gc.gridy = 0;
		Input.add(opDropBox, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		Input.add(blank, gc);

		gc.gridx = 0;
		gc.gridy = 2;
		Input.add(ATag, gc);

		gc.gridx = 2;
		gc.gridy = 2;
		Input.add(BTag, gc);

		gc.ipadx = 250;
		gc.ipady = 100;
		gc.gridx = 0;
		gc.gridy = 3;
		Input.add(AText, gc);

		gc.ipadx = 250;
		gc.ipady = 100;
		gc.gridx = 2;
		gc.gridy = 3;
		Input.add(BText, gc);

		gc.ipadx = 0;
		gc.ipady = 0;
		gc.gridx = 1;
		gc.gridy = 5;
		Input.add(blank1, gc);

		gc.gridx = 1;
		gc.gridy = 6;
		Input.add(calcButton, gc);

		gc.gridx = 0;
		gc.gridy = 0;
		c.add(Input, gc);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 1;
		result.setPreferredSize(new Dimension(550, 350));
		ResultText.setPreferredSize(new Dimension(550, 200));
		c.add(result, gc);

		gc.gridx = 0;
		gc.gridy = 2;
		gc.ipady = 50;
		c.add(clr, gc);

	}

	//MatrixGUI(): constructor
	public calcGUI()
	{
		//configure GUI
		setup1();

		//add components to container
		Container c = window.getContentPane();
		c.setLayout(LAYOUT_STYLE);
		setup2(c);

		//display GUI
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{


	}

	//main() : application entry point
	public static void main(String[] args)
	{
		calcGUI gui = new calcGUI();
	}
}
