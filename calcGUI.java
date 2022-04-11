import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calcGUI extends JFrame
{
	//class constants
	private static final int WINDOW_WIDTH = 850;//pixels
	private static final int WINDOW_HEIGHT = 800;//pixels

	private static final GridBagLayout LAYOUT_STYLE =  new GridBagLayout(); //This sets the frame layout
	GridBagConstraints gc = new GridBagConstraints();

	//instance variables
	private JFrame window = new JFrame("Matrix Calculator");

	private JLabel ATag = new JLabel("Matrix A");
	private JLabel BTag = new JLabel("Matrix B");
	private JLabel OTag = new JLabel("Operation:");
	private JLabel RTag = new JLabel("Result");

	private JTextArea AText = new JTextArea();
	private JTextArea BText = new JTextArea();
	private JTextArea ResultText = new JTextArea(10,10);

	private JButton AButton = new JButton("Automatic");
	private JButton MButton = new JButton("Manual");
	private JButton calcButton = new JButton("Calculate");
	private JButton clrResButton = new JButton("Clear Results");
	private JButton clrABButton = new JButton("Clear Matricies");

	private JPanel Mode = new JPanel(new GridBagLayout());
	private JPanel m1 = new JPanel(new GridBagLayout());
	private JPanel m2 = new JPanel(new GridBagLayout());
	private JPanel manOrAuto = new JPanel(new GridBagLayout());
	private JPanel opDropBox = new JPanel(new FlowLayout());
	private JPanel result = new JPanel(new GridBagLayout());
	private JPanel clr = new JPanel(new FlowLayout());


	private String[] Opts = {"Addition", "Subtraction", "Multiply","Transpose"};
	private JComboBox<String> operations = new JComboBox<>(Opts);

	// These labels are simply to aid in styling purposes
	private JLabel blank = new JLabel("_________________");
	private JLabel blank1 = new JLabel(" ");
	private JLabel blank2 = new JLabel(" ");
	private JLabel blank3 = new JLabel(" ");
	private JLabel blank4 = new JLabel(" ");
	private JLabel blank5 = new JLabel(" ");

	Color DarkBlueBG = new Color(44, 51, 58);
	Color OliveText = new Color(96, 158, 71);
	Color GoldInteractable = new Color(255, 181, 32);

	//configure GUI
	private void setup1()
	{
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ResultText.setEditable(false);

		MButton.setEnabled(false);
		MButton.setBackground(GoldInteractable);
		AButton.setBackground(Color.LIGHT_GRAY);

		ATag.setForeground(OliveText);
		BTag.setForeground(OliveText);
		RTag.setForeground(OliveText);
		OTag.setForeground(OliveText);
		m1.setBackground(Color.DARK_GRAY);
		m2.setBackground(Color.DARK_GRAY);
		operations.setBackground(Color.LIGHT_GRAY);
		opDropBox.setBackground(DarkBlueBG);
		Mode.setBackground(DarkBlueBG);
		result.setBackground(DarkBlueBG);
		clr.setBackground(DarkBlueBG);
		calcButton.setBackground(Color.LIGHT_GRAY);
		clrABButton.setBackground(Color.LIGHT_GRAY);
		clrResButton.setBackground(Color.LIGHT_GRAY);
		blank.setForeground(DarkBlueBG);
	}

	private void setup2(Container c)
	{
		// Create the panel components for determining calculator mode and operation
		manOrAuto.add(AButton);
		manOrAuto.add(MButton);

		opDropBox.add(OTag);
		opDropBox.add(operations);

		clr.add(blank4);
		clr.add(clrABButton);
		clr.add(clrResButton);

		// Add panel components to the Mode panel and insert the new panel into the container
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		Mode.add(manOrAuto, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		Mode.add(blank, gc);

		gc.gridx = 2;
		gc.gridy = 0;
		Mode.add(opDropBox);

		gc.gridx = 0;
		gc.gridy = 0;
		c.add(Mode, gc);

		// Create a panel for inputting the first matrix and add to container
		gc.gridx = 0;
		gc.gridy = 0;
		m1.add(ATag, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		m1.add(AText, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		c.add(m1, gc);

		gc.gridx = 0;
		gc.gridy = 2;
		c.add(blank1,gc);

		// Create a panel for inputting the second matrix and add to container
		gc.gridx = 0;
		gc.gridy = 0;
		m2.add(BTag, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		m2.add(BText, gc);

		gc.gridx = 0;
		gc.gridy = 3;
		c.add(m2, gc);

		// add the button Calculate
		gc.gridx = 0;
		gc.gridy = 4;
		c.add(blank2, gc);

		gc.gridx = 0;
		gc.gridy = 5;
		c.add(calcButton, gc);

		gc.gridx = 0;
		gc.gridy = 6;
		c.add(blank2, gc);

		// Create result component panel
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		result.add(RTag, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.CENTER;
		result.add(ResultText, gc);

		// Add result panel to container
		gc.gridx = 0;
		gc.gridy = 7;
		c.add(blank3, gc);

		gc.gridx = 0;
		gc.gridy = 8;
		c.add(result, gc);

		gc.gridx = 0;
		gc.gridy = 9;
		c.add(blank5, gc);

		gc.gridx = 0;
		gc.gridy = 10;
		c.add(clr, gc);

		AText.setPreferredSize(new Dimension(500, 100));
		BText.setPreferredSize(new Dimension(500, 100));
		ResultText.setPreferredSize(new Dimension(500, 100));
		result.setPreferredSize(new Dimension(500, 200));
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

		AButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				AButton.setEnabled(false);
				MButton.setEnabled(true);
				AButton.setBackground(GoldInteractable);
				MButton.setBackground(Color.LIGHT_GRAY);
			}
		});

		MButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				AButton.setEnabled(true);
				MButton.setEnabled(false);
				MButton.setEnabled(false);
				MButton.setBackground(GoldInteractable);
				AButton.setBackground(Color.LIGHT_GRAY);
			}
		});

		clrABButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				AText.setText("");
				BText.setText("");
			}
		});

		clrResButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				ResultText.setText("");
			}
		});

		c.setBackground(DarkBlueBG);
		c.setForeground(DarkBlueBG);
		//display GUI
		window.setVisible(true);
	}

	//main() : application entry point
	public static void main(String[] args)
	{
		calcGUI gui = new calcGUI();
	}
}
