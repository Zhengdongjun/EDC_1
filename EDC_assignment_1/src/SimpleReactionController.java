
public class SimpleReactionController implements Controller
{   //MVC members

    private Gui view;
    private Random random;
    //other members
    private int credits;
    private int delay=0;
    private double delay_timer=0;
    private double time=0;
    private int score_display_timer=0;
    private String string_time;
    private boolean timer_on=false;
    private boolean game_in_progress = false;


    //Connect controller to gui
    //(This method will be called before ANY other methods)
	
	public SimpleReactionController(){

	}
	
    public void connect(Gui gui, Random rng){
        this.random = rng;
	    this.view = gui;
	}
	//Called to initialise the controller
    public void init(){
        delay=0;
        delay_timer=0 ;
        time=0;
        score_display_timer=0;
        timer_on=false;
        game_in_progress = false;

    }
    //Called whenever a coin is inserted into the machine
    public void coinInserted(){
        credits = credits +1;
        view.setDisplay("Press GO!");
	}

    //Called whenever the go/stop button is pressed
    public void goStopPressed(){
        //if credits available:
        if (credits>0){
            //if timer has not started, start timer
            if (timer_on==false) {
                view.setDisplay("Wait...");
                delay=random.getRandom(1000,2500);
                delay_timer=0;
                time=0;
                timer_on=true;
                game_in_progress=true;

            }
            //if timer is running, stop timer, remove a credit
            //keep time on display, reset time
            else if (timer_on==true){
                //if button is pressed before timer starts
                if(delay_timer<delay){
                    credits=credits-1;
                    view.init();
                    this.init();
                }//did not cheat: stop timer, display final time reset after 3 seconds
                else {
                    timer_on = false;
                    credits = credits - 1;
                    view.setDisplay(string_time);
                }
            }
        }
	}

    //Called to deliver a TICK to the controller
    public void tick(){

        if (timer_on==true) {
           //if timer hits 2 seconds without response
            if(time>=1.99){
                timer_on = false;
                credits = credits-1;
                score_display_timer=score_display_timer+10;
                view.setDisplay("2.00");
                if (score_display_timer>=3000) {
                    this.init();
                    view.init();
                }
            }
            //when the delay_timer has reached the random delay period
            //start reaction_timer
            delay_timer=delay_timer+10;
            if(delay_timer>=delay) {
                time = time + 0.01;
                string_time = String.format("%.2f", time);
                //display the ticks
                view.setDisplay(string_time);
            }
        }
        else if (timer_on==false&& game_in_progress)//while the timer is off
        {
            score_display_timer=score_display_timer+10;
            //view.setDisplay(string_time);
            if (score_display_timer>=3000) {
                this.init();
                view.init();
            }
        }
	}
}
