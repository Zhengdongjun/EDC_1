
public class SimpleReactionController implements Controller
{   //MVC members
    private Gui view;
    private Random random;
    //other members
    private float ticks;
    private int credits=0;
    private int delay;
    private float delay_timer;
    private float time;
    private int score_display_timer;
    private String string_time;
    private boolean timer_on;
    private boolean game_in_progress;
    //constructor
    public SimpleReactionController(){
    }
    //Connect controller to gui
    //(This method will be called before ANY other methods)
    public void connect(Gui gui, Random rng){
        this.random = rng;
	    this.view = gui;
	}
	//Called to initialise the controller
    public void init(){
        view.setDisplay("Insert Coin");
        ticks=0;
        delay=0;
        delay_timer=0 ;
        time=0;
        score_display_timer=0;
        timer_on=false;
        string_time="0.00";
        game_in_progress = false;
    }
    //Called whenever a coin is inserted into the machine
    public void coinInserted(){
        if (credits<10) {
            credits = credits +1;
        }
        if(game_in_progress==false) {
            view.setDisplay("Press GO!");
        }
	}
    //Called whenever the go/stop button is pressed
    public void goStopPressed(){
        //if credits available:
        if (credits>0){
            //if timer has not started, set delay, initialise delay timer, start game timer, game is in progress
            if (timer_on==false) {
                if(game_in_progress==true) {//if button is pressed while game is in progress but timer is off
                game_in_progress=false;
                this.init();
                }else {
                    view.setDisplay("Wait...");
                    delay = random.getRandom(100, 250);//delay in terms of ticks
                    delay_timer = 0;
                    time = 0;
                    timer_on = true;
                    game_in_progress = true;
                }
            }
            //else if timer is running, stop timer, remove a credit
            //keep time on display, reset time
            else if (timer_on==true){
                //if button is pressed before timer starts: reset view, reset game, remove a credit
                if(delay_timer<delay){
                    credits=credits-1;
                    this.init();
                }//did not cheat: stop timer, display final time reset after 3 seconds, remove a credit
                else {
                    timer_on = false;
                    credits = credits - 1;
                }
            }
        }else//if credits<=0
        {
            this.init();
        }
	}
    //Called to deliver a TICK to the controller
    public void tick(){
        if (timer_on==true&& game_in_progress) {
            //if timer hits 2 seconds without response
            if(ticks>=200){
                timer_on = false;
                credits = credits-1;
                score_display_timer=score_display_timer+1;
                view.setDisplay(string_time);
                if (score_display_timer>=300) {
                    this.init();
                }
            }else{//0<time<2 seconds
                //when the delay_timer has reached the random delay period
                //start reaction_timer
                delay_timer=delay_timer+1;
                if(delay_timer>=delay) {
                    view.setDisplay(string_time);
                    ticks=ticks+1; //10ms per tick
                    if(ticks!=0){time=ticks/100;}
                    //time=(ticks*10);//time (milliseconds)
                    //time=time/1000;//time ( seconds)
                    string_time = String.format("%.2f", time);
                    //display the ticks

                }
            }
        }
        else if (timer_on==false&& game_in_progress)//while the timer is off
        {
            score_display_timer=score_display_timer+1;
            //view.setDisplay(string_time);
            if (score_display_timer>=300) {//300 ticks =3000ms
                this.init();
            }
        }
	}
}
