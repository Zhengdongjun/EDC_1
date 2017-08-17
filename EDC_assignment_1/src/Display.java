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
		text_string = new JTextField();
		//Buttons
		insert_button	= new JButton();
		go_button = new JButton();
		//add ui elements
		add(text_string);
		add(insert_button);
		add(go_button);

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
    	insert_button.setText("Insert Coin");
    	go_button.setText("GO/STOP!");
		this.setDisplay("Insert coin");

	}
    //Change the displayed text
    public void setDisplay(String s){
		text_string.setText(s);
	}
}