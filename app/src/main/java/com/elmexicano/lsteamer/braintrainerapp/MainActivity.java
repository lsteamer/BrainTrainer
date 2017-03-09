package com.elmexicano.lsteamer.braintrainerapp;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    //Map<Integer,TextView> mapKeys;

    RelativeLayout glay;
    RelativeLayout wlay;
    RelativeLayout ilay;

    //TextViews in the app
    TextView quizt, timert, scoret;
    TextView opt[];
    TextView sco, tot, resultT;

    //Is the app running?
    boolean running;

    //To see if the button has been clicked at all
    boolean started;

    //Was the latest clicked option a hit or a miss?
    boolean latestOp;

    //Random number tool
    Random randNum;

    //Timer process so we can stop it
    CountDownTimer appRuns;

    //Button
    Button start;


    //Variable that holds the id of the possible 4 variables pressed
    int idOfViews[];

    //Array that holds the results, to check that there are no two alike
    int teNum[];

    int score, total, sec, win;

    protected void appRun(){

        if(running==false){
            //App is running
            running=true;



            //String created to fill in certain things
            String moviendo;

            //Defining the score
            moviendo = " "+score+" /"+total;
            scoret.setText(moviendo);

            newScreen();

            //Set the timer settings
            appRuns = new CountDownTimer(sec*1000,1000) {
                public void onTick(long milisecondsUntilDone){


                    timert.setText(""+String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(sec*1000),
                            TimeUnit.MILLISECONDS.toSeconds(sec*1000) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sec*1000))));

                    //Happens when it's counting down
                    sec--;
                }

                public void onFinish(){
                    sco = (TextView) findViewById(R.id.scorete);
                    tot = (TextView) findViewById(R.id.totalte);

                    sco.setText(String.valueOf(score));
                    tot.setText(String.valueOf(total));

                    resultT.setText("");


                    quizt.setText("QUIZ");
                    timert.setText("Timer");
                    scoret.setText("Score");

                    wlay.animate().alpha(1).start();
                    glay.animate().alpha(0).start();
                    //App is no longer running
                    running = false;


                    start.animate().alpha(1).start();


                }
            };

            //Start the timer (and the app)
            appRuns.start();



        }

        //In case app is running and you want to stop it or so, Below this line start with an else.
        //OR, screen is flipped
        if(running==true){

        }

    }


    protected void newScreen(){
        //Select one of the numbers to be the winner
        win = (randNum.nextInt(9));

        int uno, dos;

        List<Integer> rand = new ArrayList<Integer>();

        //Filling in the TextViews with numbers and colors
        for(int i = 0; i<9; i++){
            uno = (randNum.nextInt(50-1+1)+1);
            dos = (randNum.nextInt(49-1+1)+1);

            //Checking that there aren't repeated numbers
            for(int j = i-1; j>=0;j--){
                    while(teNum[j]==uno+dos){
                        uno = (randNum.nextInt(50-1+1)+1);
                        dos = (randNum.nextInt(49-1+1)+1);
                        j=i-1;
                    }
            }

            //Once that the number is not repeated, we store it for future proofing
            teNum[i] = uno+dos;

            //Setting the 'winner' in the quiz text
            if(i==win)
                quizt.setText(" "+uno+" + "+dos+" ");

            //Giving the single digits a 0 at the right
            if(uno+dos<10)
                opt[i].setText(" 0" +String.valueOf(uno+dos)+" ");
            else
                opt[i].setText(" " +String.valueOf(uno+dos)+" ");





            //Coloring block
            uno = randNum.nextInt(11);
            while(rand.contains(uno)){
                uno = randNum.nextInt(11);
            }
            rand.add(uno);
            opt[i].setTextColor(Color.parseColor("#000000"));

            //Coloring the numbers randomly
            switch(uno){
                //pink
                case 0:
                    opt[i].setBackgroundColor(Color.parseColor("#ffc0cb"));
                    break;
                //teal
                case 1:
                    opt[i].setBackgroundColor(Color.parseColor("#ff4040"));
                    break;
                //Orange
                case 2:opt[i].setBackgroundColor(Color.parseColor("#f9a603"));
                    break;
                //red
                case 3:
                    opt[i].setBackgroundColor(Color.parseColor("#f69454"));
                    break;
                //light blue
                case 4:
                    opt[i].setBackgroundColor(Color.parseColor("#b0e0e6"));
                    break;
                //blue
                case 5:
                    opt[i].setBackgroundColor(Color.parseColor("#3399ff"));
                    break;
                //green light
                case 6:
                    opt[i].setBackgroundColor(Color.parseColor("#b6fcd5"));
                    break;
                //green
                case 7:
                    opt[i].setBackgroundColor(Color.parseColor("#6dc066"));
                    break;
                //dark blue
                case 8:
                    opt[i].setBackgroundColor(Color.parseColor("#3b5998"));
                    break;
                //brown
                case 9:
                    opt[i].setBackgroundColor(Color.parseColor("#c39797"));
                    break;
                case 10:
                    opt[i].setBackgroundColor(Color.parseColor("#999999"));
                    break;
            }
        }

    }



    public void fillText(View view){
        //If app is running
        if(running==true){


            latestOp=false;
            for(int i = 0; i<9; i++)
                if(i==win&&idOfViews[i]==view.getId()) {
                    score++;
                    latestOp=true;
                }

            //Defining the score
            total++;
            scoret.setText(" "+score+" /"+total);

            //Setting the message about the latest one.
            resultT.setText("Missed");
            if(latestOp==true)
                resultT.setText("Correct!");


            //Calling a new screen
            newScreen();
        }


    }

    //this method is run when the user hits the start button
    public void userStart(View view){

        glay.animate().alpha(1).start();
        wlay.animate().alpha(0).start();


        started = true;



        ilay.animate().alpha(0).start();
        //Defining the score and total of exercises
        score = total = 0;

        //set the time
        sec = 60;
        start.animate().alpha(0).start();
        appRun();

    }

    //This is the thing I'll alter so I can refill the app with the previous info
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("AppRunning",running);
        outState.putBoolean("Latest",latestOp);
        outState.putBoolean("Started",started);
        outState.putInt("Seconds",sec);
        outState.putInt("Score",score);
        outState.putInt("Total",total);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        //If the screen is rotated, choose a different layout
        if(Configuration.ORIENTATION_PORTRAIT==this.getResources().getConfiguration().orientation)
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.activity_main_lc);

        //Declaring the  TextViews in the screen
        quizt = (TextView) findViewById(R.id.quiz);
        timert = (TextView) findViewById(R.id.timerTextView);
        scoret = (TextView) findViewById(R.id.scoreTextView);
        resultT = (TextView) findViewById(R.id.resultText);

        glay = (RelativeLayout) findViewById(R.id.gameLayout);

        //Button to Start the app
        start = (Button) findViewById(R.id.start);
        //start.animate().alpha(0).start();
/*
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userStart(view);
            }
        });
*/
        //Hide the win
        wlay = (RelativeLayout) findViewById(R.id.winLayout);
        wlay.animate().alpha(0).start();


        ilay = (RelativeLayout) findViewById(R.id.introLayout);

        //Hide the TextView that shows if the latest try was a hit or a miss
        resultT.setText("");

        latestOp = false;
        started = false;

        //Restoring some info when flipping the screen (Seconds remaining, the current score and the tries)
        if(savedInstanceState!=null){

            //Check if it WAS running
            running = (boolean) savedInstanceState.get("AppRunning");

            latestOp = (boolean) savedInstanceState.get("Latest");
            started = (boolean) savedInstanceState.get("Started");
            sec = (Integer) savedInstanceState.get("Seconds");
            score = (Integer) savedInstanceState.get("Score");
            total = (Integer) savedInstanceState.get("Total");


            //If it was, set the values to how they were before
            if(running==true&&started==true){

                start.animate().alpha(0).start();

                //Setting the message about the latest one.
                resultT.setText("Missed");
                if(latestOp==true)
                    resultT.setText("Correct!");

            }
            else if (started==true){
                sco = (TextView) findViewById(R.id.scorete);
                tot = (TextView) findViewById(R.id.totalte);

                sco.setText(String.valueOf(score));
                tot.setText(String.valueOf(total));

                //Started is true, Running isn't. Go to the End Game screen
                wlay.animate().alpha(1).start();
                glay.animate().alpha(0).start();
            }
            //running = false;

        } else {

            latestOp = false;



            //Defining the score and total of exercises
            score = total = 0;

            //set the time
            sec = 60;

        }


        //Array that holds the results, to check that there are no two alike
        teNum = new int[9];




        if(started==true)
            ilay.animate().alpha(0).start();

        /*
        //Map
        mapKeys = new HashMap<Integer, TextView>();

        mapKeys.put(R.id.op1, (TextView) findViewById(R.id.op1));
        mapKeys.put(R.id.op2, (TextView) findViewById(R.id.op2));
        mapKeys.put(R.id.op3, (TextView) findViewById(R.id.op3));
        mapKeys.put(R.id.op4, (TextView) findViewById(R.id.op4));
        mapKeys.put(R.id.op5, (TextView) findViewById(R.id.op5));
        mapKeys.put(R.id.op6, (TextView) findViewById(R.id.op6));
        mapKeys.put(R.id.op7, (TextView) findViewById(R.id.op7));
        mapKeys.put(R.id.op8, (TextView) findViewById(R.id.op8));
        mapKeys.put(R.id.op9, (TextView) findViewById(R.id.op9));

        */

        //Variables that hold the different Text Views.
        opt = new TextView[9];
        opt[0] = (TextView) findViewById(R.id.op1);
        opt[1] = (TextView) findViewById(R.id.op2);
        opt[2] = (TextView) findViewById(R.id.op3);
        opt[3] = (TextView) findViewById(R.id.op4);
        opt[4] = (TextView) findViewById(R.id.op5);
        opt[5] = (TextView) findViewById(R.id.op6);
        opt[6] = (TextView) findViewById(R.id.op7);
        opt[7] = (TextView) findViewById(R.id.op8);
        opt[8] = (TextView) findViewById(R.id.op9);



        for(int wa=0; wa<9;wa++){
            opt[wa].setTextColor(Color.parseColor("#Fafafa"));
            opt[wa].setBackgroundColor(Color.parseColor("#fafafa"));
        }


        //Variable that hold the id of the TextViews for when they are being clicked.
        idOfViews = new int[9];
        idOfViews[0] = R.id.op1;
        idOfViews[1] = R.id.op2;
        idOfViews[2] = R.id.op3;
        idOfViews[3] = R.id.op4;
        idOfViews[4] = R.id.op5;
        idOfViews[5] = R.id.op6;
        idOfViews[6] = R.id.op7;
        idOfViews[7] = R.id.op8;
        idOfViews[8] = R.id.op9;

        //Random number toolkit defined
        randNum = new Random();
        if(running==true&&started==true){
            running = false;
            appRun();

        }


    }
}
