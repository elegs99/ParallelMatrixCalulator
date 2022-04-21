import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

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

	private JTextArea AText = new JTextArea(20,20);
	private JTextArea BText = new JTextArea(20,20);
	private JTextArea ResultText = new JTextArea(20,20);

	private JButton AButton = new JButton("Automatic");
	private JButton MButton = new JButton("Manual");
	private JButton calcButton = new JButton("Calculate");
	private JButton clrResButton = new JButton("Clear Results");
	private JButton clrABButton = new JButton("Clear Matricies");
	private JButton AutoResetButton = new JButton("Generate New Matrices");

	private JPanel Mode = new JPanel(new GridBagLayout());
	private JPanel m1 = new JPanel(new GridBagLayout());
	private JPanel m2 = new JPanel(new GridBagLayout());
	private JPanel manOrAuto = new JPanel(new GridBagLayout());
	private JPanel opDropBox = new JPanel(new FlowLayout());
	private JPanel result = new JPanel(new GridBagLayout());
	private JPanel clr = new JPanel(new FlowLayout());
	private JScrollPane scrollA = new JScrollPane(AText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JScrollPane scrollB = new JScrollPane(BText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JScrollPane scrollR = new JScrollPane(ResultText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	private String[] Opts = {"Matrix to Matrix Addition", "Matrix to Matrix Subtraction",
							"Matrix to Matrix Multiplication","Transpose",
							"Matrix to Scalar Multiplication"};
	private JComboBox<String> operations = new JComboBox<>(Opts);

	// These labels are simply to aid in styling purposes
	private JLabel blank = new JLabel("__________");
	private JLabel blank1 = new JLabel(" ");
	private JLabel blank2 = new JLabel(" ");
	private JLabel blank3 = new JLabel(" ");

	Color DarkBlueBG = new Color(44, 51, 58);
	Color OliveText = new Color(96, 158, 71);
	Color GoldInteractable = new Color(255, 181, 32);

	public static int answer[][];
	public static int matA[][];
	public static int matB[][];

	private static StringBuilder a;
	private static StringBuilder b;
	private static StringBuilder r;

	public static int rowA = 0;
	public static int colA = 0;
	public static int rowB = 0;
	public static int colB = 0;

	public static int blockSize;
	public static AtomicInteger row = new AtomicInteger(0);

	private static boolean isAuto = true;
	private static boolean isTransp = false;

	// Populate the text areas with randomly generated compatible matrices
	private void generateAuto()
	{
		rowA = 20;
		colA = 20;
		rowB = 20;
		colB = 20;
		matA = new int[rowA][colA];
		matB = new int[rowB][colB];
		a = new StringBuilder();
		b = new StringBuilder();

		for(int i = 0; i < rowA; i++)
		{
			for(int j = 0; j < colA; j++)
			{
				matA[i][j] = (int) (Math.random() * 10);
			}
		}
		for(int i = 0; i < rowB; i++)
		{
			for(int j = 0; j < colB; j++)
			{
				matB[i][j] = (int) (Math.random() * 10);
			}
		}

		genString(a,rowA,colA,matA);
		AText.setText(a.toString());

		genString(b,rowB,colB,matB);
		BText.setText(b.toString());
	}

	// Generate matrices into string format for textAreas
	private void genString(StringBuilder ex, int row, int col, int[][] mat)
	{
		ex.append("[");
		for(int i = 0; i < row; i++)
		{
			ex.append("[ ");
			for(int j = 0; j < col; j++)
			{
				if(j == col - 1)
				{
					ex.append(mat[i][j]);
				}
				else
				{
					ex.append(mat[i][j] +", ");
				}
			}
			if(i == row - 1)
			{
				ex.append("]");
			}
			else
			{
				ex.append("],\n");
			}
		}
		ex.append("]");
	}

	//configure GUI
	private void setup1()
	{
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ResultText.setEditable(false);

		AButton.setEnabled(false);
		clrABButton.setEnabled(false);
		AButton.setBackground(GoldInteractable);
		MButton.setBackground(Color.LIGHT_GRAY);
		AutoResetButton.setBackground(Color.LIGHT_GRAY);

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

		clr.add(AutoResetButton);
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
		m1.add(scrollA, gc);

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
		m2.add(scrollB, gc);

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


		// Create result component panel
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		result.add(RTag, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.CENTER;
		result.add(scrollR, gc);

		// Add result panel to container
		gc.gridx = 0;
		gc.gridy = 7;
		c.add(blank3, gc);

		gc.gridx = 0;
		gc.gridy = 8;
		c.add(result, gc);

		gc.gridx = 0;
		gc.gridy = 10;
		c.add(clr, gc);

		scrollA.setPreferredSize(new Dimension(500, 100));
		scrollB.setPreferredSize(new Dimension(500, 100));
		scrollR.setPreferredSize(new Dimension(500, 100));
		result.setPreferredSize(new Dimension(700, 200));
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

		operations.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
	        	switch((String)operations.getSelectedItem())
	        	{
	        		case "Matrix to Scalar Multiplication":
	        			int a = (int)Math.random() * 10 + 1;
			        	String s = Integer.toString(a);
			        	BText.setText(s);
			        	isTransp = true;
			        	System.out.println("Request registered");
	        			break;
	        		default:
	        			isTransp = false;
	        			break;
	        	}
		    }
		});

		AButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				AButton.setEnabled(false);
				MButton.setEnabled(true);
				AButton.setBackground(GoldInteractable);
				MButton.setBackground(Color.LIGHT_GRAY);
				isAuto = true;
				clrABButton.setEnabled(false);
				generateAuto();
				AutoResetButton.setEnabled(true);
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
				isAuto = false;
				clrABButton.setEnabled(true);
				AutoResetButton.setEnabled(false);
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

		calcButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				int nThreads = 1;
				int scalar = 0;
				ThreadPool threadPool;
				blockSize = colA / nThreads;
			    String s = (String) operations.getSelectedItem();
			    if(isAuto == true)
			    {
					switch(s)
					{
						case "Matrix to Matrix Addition":
							if (rowA != rowB || colA != colB) {
		                        System.out.println("Matrix addition not possible. Dimensions do not match");
		                    } else {
		                        MatrixAdd add = new MatrixAdd();
		                        threadPool = new ThreadPool(rowA);
		                        answer = new int[rowA][colA];
		                        r = new StringBuilder();
		                        threadPool.run(add);
		                        genString(r,rowA,colA,answer);
		                        ResultText.setText(r.toString());
		                        row.set(0);
		                        threadPool = null;
		                    }
							break;
						case "Matrix to Matrix Subtraction":
							if (rowA != rowB || colA != colB) {
		                        System.out.println("Matrix subtraction not possible. Dimensions do not match");
		                    }
							else
							{
		                        MatrixSubtract sub = new MatrixSubtract();
		                        threadPool = new ThreadPool(rowA);
		                        answer = new int[rowA][colA];
		                        r = new StringBuilder();
		                        threadPool.run(sub);
		                        genString(r,rowA,colA,answer);
		                        row.set(0);
		                        threadPool = null;
		                        ResultText.setText(r.toString());
		                    }
							break;
						case "Transpose":
							MatrixTranspose transposition = new MatrixTranspose();
		                    threadPool = new ThreadPool(nThreads);
		                    answer = new int[colA][rowA];
		                    r = new StringBuilder();
		                    threadPool.run(transposition);
		                    genString(r,colA,rowA,answer);
		                    ResultText.setText(r.toString());
		                    row.set(0);
		                    threadPool = null;
		                    break;
						case "Matrix to Matrix Multiplication":
							if (colA != rowB)
							{
		                        ResultText.setText("Matrix multiplication not possible");
		                    }
							else
							{
								threadPool = new ThreadPool(rowA);
								r = new StringBuilder();
								answer = new int[rowA][colB];
						        MatrixMultiply runnable = new MatrixMultiply();
						        threadPool.run(runnable);
						        genString(r,rowA,colB,answer);
			                    ResultText.setText(r.toString());
			                    row.set(0);
			                    threadPool = null;
		                    }
							break;
						case "Matrix to Scalar Multiplication":
							scalar = Integer.parseInt(BText.getText());
							ScalarMultiply scalarMultiplication = new ScalarMultiply(scalar);
							r = new StringBuilder();
							answer = new int[rowA][colB];
		                    threadPool = new ThreadPool(nThreads);
		                    threadPool.run(scalarMultiplication);
		                    genString(r,rowA,colB,answer);
		                    ResultText.setText(r.toString());
		                    row.set(0);
		                    threadPool = null;
							break;
					}
			    }
			    // Manual Mode. Parse request
			    else
				{
			    	matA = parseMat(AText.getText(), true);
			    	if(!isTransp)
			    	{
			    		matB = parseMat(BText.getText(), false);
			    	}
			    	switch(s)
					{
						case "Matrix to Matrix Addition":
							if (rowA != rowB || colA != colB) {
		                        System.out.println("Matrix addition not possible. Dimensions do not match");
		                    } else {
		                        MatrixAdd add = new MatrixAdd();
		                        threadPool = new ThreadPool(rowA);
		                        answer = new int[rowA][colA];
		                        r = new StringBuilder();
		                        threadPool.run(add);
		                        genString(r,rowA,colA,answer);
		                        ResultText.setText(r.toString());
		                        row.set(0);
		                        threadPool = null;
		                    }
							break;
						case "Matrix to Matrix Subtraction":
							if (rowA != rowB || colA != colB) {
		                        System.out.println("Matrix subtraction not possible. Dimensions do not match");
		                    }
							else
							{
		                        MatrixSubtract sub = new MatrixSubtract();
		                        threadPool = new ThreadPool(rowA);
		                        answer = new int[rowA][colA];
		                        r = new StringBuilder();
		                        threadPool.run(sub);
		                        genString(r,rowA,colA,answer);
		                        row.set(0);
		                        threadPool = null;
		                        ResultText.setText(r.toString());
		                    }
							break;
						case "Transpose":
							MatrixTranspose transposition = new MatrixTranspose();
		                    threadPool = new ThreadPool(nThreads);
		                    answer = new int[colA][rowA];
		                    r = new StringBuilder();
		                    threadPool.run(transposition);
		                    genString(r,colA,rowA,answer);
		                    ResultText.setText(r.toString());
		                    row.set(0);
		                    threadPool = null;
		                    break;
						case "Matrix to Matrix Multiplication":
							if (colA != rowB)
							{
		                        ResultText.setText("Matrix multiplication not possible");
		                    }
							else
							{
								threadPool = new ThreadPool(rowA);
								r = new StringBuilder();
								answer = new int[rowA][colB];
						        MatrixMultiply runnable = new MatrixMultiply();
						        threadPool.run(runnable);
						        genString(r,rowA,colB,answer);
			                    ResultText.setText(r.toString());
			                    row.set(0);
			                    threadPool = null;
		                    }
							break;
						case "Matrix to Scalar Multiplication":
							scalar = Integer.parseInt(BText.getText());
							ScalarMultiply scalarMultiplication = new ScalarMultiply(scalar);
							r = new StringBuilder();
							answer = new int[rowA][colB];
		                    threadPool = new ThreadPool(nThreads);
		                    threadPool.run(scalarMultiplication);
		                    genString(r,rowA,colB,answer);
		                    ResultText.setText(r.toString());
		                    row.set(0);
		                    threadPool = null;
							break;
					}
				}
			}
		});

		AutoResetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				generateAuto();
			}
		});

		generateAuto();
		c.setBackground(DarkBlueBG);
		c.setForeground(DarkBlueBG);
		//display GUI
		window.pack();
		window.setVisible(true);
	}

	public static int[][] parseMat(String input, boolean isMatrixA) {
        char c; char k;
        int row = 0, col = 0, j = 0;
        int countRow = 0, countCol = 0; // used to count rows and columns
        int colPrev = 0; // used for size comparison
        boolean first = true;
        int[] arr = new int[input.length()];

        for (int i = 0; i < input.length(); i++) // let's parse
        {
            c = input.charAt(i);
            if (first)
            { // first row, needed for counting columns
                colPrev = countCol;
            }

            if (c == '[' && countRow == 0)
            { // 1st character
                countRow++;
            }

            else if (c == '[' && countRow == 1)
            { // 2nd and other start row matrix character
                countRow++;
                row++;

            }
            else if (c == ' ' || c == ',' || c =='\n')
            { // we don't want these

            }

            else if ((int) c >= 48 && ((int) c) <= 57)
            {
                arr[j] = ((int) c) - 48; // ASCII, everyone's favorite
                k = input.charAt(i+1);
                while(((int) k) >= 48 && ((int) k) <= 57)
                {
                	i++;
                	arr[j] = (arr[j] * 10) + ((int) k) - 48;
                	k = input.charAt(i+1);
                }
                countCol++; // each number is a new length of columns
                j++;
            }

            else if (c == ']' && countRow == 2)
            {
                countRow--;
                col = countCol;

                if (colPrev != countCol) { // uh oh! bad size matrix
                    return null;
                } else {
                    colPrev = countCol;
                }
                countCol = 0;
                first = false;
            }
        }

        if(isMatrixA)
        {
        	colA = col;
        	rowA = row;
        }
        else
        {
        	colB = col;
        	rowB = row;
        }
        int[][] retval = new int[row][col];
        int z = 0;
        for (int i = 0; i < row; i++) {
            for (int a = 0; a < col; a++) {
                retval[i][a] = arr[z];
                z++; // used to go thru original array.
            }
        }
        return retval;
    }
}
