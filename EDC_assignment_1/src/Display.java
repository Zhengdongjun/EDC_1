import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Display extends JFrame implements Gui 
{
	private JTextField text_string;
	private JButton insert_button;
	private JButton go_button;
	
	public Display(){
	super("Title bar");

	}
	
	  //Connect gui to controller
    //(This method will be called before ANY other methods)
    public void connect(Controller controller){


	}

    //Initialise the gui
    public void init(){
		setLayout(new FlowLayout());
		//string
		text_string = new JTextField("Waiting...");
		add(text_string);
		//buttons
		insert_button	= new JButton("INSERT");
		go_button = new JButton("GO!");
		add(insert_button);
		add(go_button);


		insert_button.addActionListener(onClick);
		go_button.addActionListener(onClick);
		//display properties
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);

		//event handler
		eventHandler onClick = new eventHandler();

	}

    //Change the displayed text
    public void setDisplay(String s){
	text_string = new JTextField("Not Waiting");
	}

	private class eventHandler implements ActionListener


}