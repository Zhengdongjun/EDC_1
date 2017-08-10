import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Display extends JFrame implements Gui 
{
	//controller
	private Controller controller;
	//other view variables
	private JTextField text_string;
	private JButton insert_button;
	private JButton go_button;
	private JTextField time_field;

	//constructor
	public Display(){
		super("Title bar");
		//Layout
		setLayout(new FlowLayout());
		//String
		text_string = new JTextField("Waiting...");
		time_field = new JTextField("0.0"+" "+"ms");
		//Buttons
		insert_button	= new JButton("INSERT COIN");
		go_button = new JButton("GO!/STOP!");
		//add ui elements
		add(text_string);
		add(insert_button);
		add(go_button);
		add (time_field);

		//Event Listeners
		//insert_button.addActionListener(connect(Controller controller));
		//go_button.addActionListener(onClick);



		//display properties
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
	}

	//Connect gui to controller
    //(This method will be called before ANY other methods)
    public void connect(Controller controller){
		this.controller = controller;

		//action listeners
		insert_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.coinInserted();
			}
		});

		go_button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.goStopPressed();
		}
		});
	}

    //Initialise the gui
    public void init(){
		text_string.setText("Insert coin");
		insert_button.setText("INSERT COIN");
		go_button.setText("GO/STOP!");
		time_field.setText("0.0"+" "+"ms");
		//event handler
	}

    //Change the displayed text
    public void setDisplay(String s){

    	if (s=="Press GO!") {
			text_string.setText(s);
		}
		else if (s=="Wait...") {
			text_string.setText(s);
		}
		else if (s=="Insert Coin") {
			text_string.setText(s);
		}
		else
		{
			time_field.setText(s);
		}
	}




}