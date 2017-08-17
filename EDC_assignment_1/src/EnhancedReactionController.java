
public class EnhancedReactionController implements Controller
{   //MVC members
    private Gui view;
    private Random random;
    //other members
    private float ticks;
    private int credits=0;
    private int delay;
    //timers
    private float delay_timer;
    private float time;
    private int score_display_timer;
    private int average_display_timer;
    private int gameReadyTimer;
    private String string_time;
    //States : Waiting for coin, game is ready, game in progress, display wait, display score, display average,
    private boolean waiting_for_coin;
    private boolean gameReady;
    private boolean game_in_progress;
    private boolean waitTimer;
    private boolean scoreTimer;
    private boolean averageTimer;
    //
    private int maxgames=3;
    private int gameCounter;
    private float scoresum;
    private float averageScore;

    //constructor
    public EnhancedReactionController(){
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
        //states
        waiting_for_coin=true;
        gameReady=false;
        game_in_progress = false;
        waitTimer=false;
        scoreTimer=false;
        averageTimer=false;
        //conditions
        delay=0;
        //timers
        gameReadyTimer=0;
        ticks=0;
        delay_timer=0;
        score_display_timer=0;
        average_display_timer=0;
        //display variables
        time=0;
        string_time="0.00";
        scoresum=0;
        averageScore=0;
        gameCounter=0;
    }
    //Called whenever a coin is inserted into the machine
    public void coinInserted(){
        if (credits<10) {
            credits = credits +1;
        }
        if(waiting_for_coin) {
            gameReady=true;
            view.setDisplay("Press GO!");
            waiting_for_coin=false;
        }
    }
    //Called whenever the go/stop button is pressed
    public void goStopPressed(){
        if(gameReady&&credits>0){//if go pressed while game is ready to
            view.setDisplay("Wait...");
            waiting_for_coin=false;
            gameReady=false;
            gameReadyTimer=0;
            delay = random.getRandom(100, 250);//delay in terms of ticks
            delay_timer = 0;
            waitTimer=true;
            gameCounter=gameCounter+1;
        }
        if(waitTimer&&delay_timer>0){//if button pressed while "Wait.." is displayed
            if(credits>0){
                credits=credits-1;
            }
            this.init();
        }
        if(game_in_progress){//if go pressed while game is running
            view.setDisplay(string_time);
            game_in_progress=false; //stop timer on current game
            score_display_timer=0;
            scoreTimer=true;
            scoresum=scoresum+time;
            ticks=0;
        }
        if(scoreTimer&&score_display_timer>0){//if go pressed while score is displayed
            if(gameCounter<maxgames){//next game
                gameReady=true;
                score_display_timer=0;
                scoreTimer=false;
                time=0;
                this.goStopPressed();
            }else if(gameCounter>=maxgames){//end of game 3, go to average score
                gameReady=false;
                scoreTimer=false;
                score_display_timer=0;
                time=0;
                averageTimer=true;
            }
        }
        if(averageTimer&&average_display_timer>0){
            if(credits>0){
                credits=credits-1;
            }
            this.init();
        }
    }
    //Called to deliver a TICK to the controller
    public void tick(){
        if (gameReady){//if coin is inserted or last game ended and credits are available
            gameReadyTimer=gameReadyTimer+1;
            if (gameReadyTimer>=1000){//if 10seconds elapse with no response remove credit and reset machine
                credits=credits-1;
                this.init();
            }
        }
        if(waitTimer){
            view.setDisplay("Wait...");
            delay_timer=delay_timer+1;
            if(delay_timer>=delay){
                game_in_progress=true;
                waitTimer=false;
            }
        }
        if(game_in_progress){
            if(ticks!=0){time=ticks/100;}
            string_time = String.format("%.2f", time);
            view.setDisplay(string_time);
            ticks=ticks+1; //10ms per tick
            if(ticks>=200){
                view.setDisplay(string_time);
                scoresum=scoresum+time;
                scoreTimer=true;
                game_in_progress=false;
                ticks=0;
            }
        }
        if(scoreTimer){
            score_display_timer=score_display_timer+1;
            if(score_display_timer>=300&&gameCounter<maxgames){
                gameReady=true;
                scoreTimer=false;
                score_display_timer=0;
                time=0;
                this.goStopPressed();
            }else if(score_display_timer>=300&&gameCounter>=maxgames){
                gameReady=false;
                scoreTimer=false;
                score_display_timer=0;
                averageTimer=true;
                time=0;
            }

        }
        if(averageTimer){
            averageScore=scoresum/maxgames;
            string_time=String.format("%.2f", averageScore);
            // System.out.println(averageScore);
            view.setDisplay("Average= "+string_time);
            average_display_timer=average_display_timer+1;
            if(average_display_timer>=500){
                if(credits>0){
                    credits=credits-1;
                }
                this.init();
            }
        }
    }
}