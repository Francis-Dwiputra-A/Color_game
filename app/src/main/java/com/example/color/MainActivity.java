package com.example.color;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView col,timer,judge,target = null;
    ImageView submit;
    EditText answers;
    GridLayout grid,pix_tracker;
    String ans,check ="",pic = "000000000";
    Button play,try_again;
    int i = 12,index;
    boolean t = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = findViewById(R.id.submit);
        answers = findViewById(R.id.answer);
        grid = findViewById(R.id.gridLayout);
        timer = findViewById(R.id.game_timer);
        play = findViewById(R.id.btnPlay);
        try_again = findViewById(R.id.btnTryAgain);
        judge = findViewById(R.id.judgement);
        pix_tracker = findViewById(R.id.gridLayout2);
        grid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();
                if(x < 355){
                    if(y < 274){
                        target = findViewById(R.id.btn1);
                        index = 0;
                    }
                    else if(y < 548){
                        target = findViewById(R.id.btn4);
                        index = 3;
                    }
                    else{
                        target = findViewById(R.id.btn7);
                        index = 6;
                    }
                }
                else if(x < 710){
                    if(y < 274){
                        target = findViewById(R.id.btn2);
                        index = 1;
                    }
                    else if(y < 548){
                        target = findViewById(R.id.btn5);
                        index = 4;
                    }
                    else{
                        target = findViewById(R.id.btn8);
                        index = 7;
                    }
                }
                else{
                    if(y < 274){
                        target = findViewById(R.id.btn3);
                        index = 2;
                    }
                    else if(y < 548){
                        target = findViewById(R.id.btn6);
                        index = 5;
                    }
                    else{
                        target = findViewById(R.id.btn9);
                        index = 8;
                    }
                }
                return false;
            }
        });
        pix_tracker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(i == 0){
                    int x = (int)motionEvent.getX();
                    if(target != null){
                        if(x < 340){
                            target.setBackgroundResource(R.color.red);
                            pic = pic.substring(0, index) + "1" + pic.substring(index+1);
                        }
                        else if(x < 680){
                            target.setBackgroundResource(R.color.green);
                            pic = pic.substring(0, index) + "2" + pic.substring(index+1);
                        }
                        else{
                            target.setBackgroundResource(R.color.blue);
                            pic = pic.substring(0, index) + "3" + pic.substring(index+1);
                        }
                    }
                }
                return false;
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_timer();
                assign_color();
                play.setVisibility(View.INVISIBLE);
            }
        });
        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_timer();
                assign_color();
                judge.setVisibility(View.INVISIBLE);
                try_again.setVisibility(View.INVISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans = answers.getText().toString();
                if(!pic.equals("000000000")){
                    if(pic.contains("0")){
                        Toast.makeText(MainActivity.this, "Color all Cells!", Toast.LENGTH_SHORT).show();
                    }
                    else if(pic.equals(check)){
                        /* Win */
                        judge.setVisibility(View.VISIBLE);
                        judge.setText("WIN");
                        try_again.setVisibility(View.VISIBLE);
                        answers.setText("");
                    }
                    else{
                        /* Lose */
                        judge.setVisibility(View.VISIBLE);
                        judge.setText("LOSE");
                        try_again.setVisibility(View.VISIBLE);
                        answers.setText("");
                    }
                }
                else if(ans.length() == 9){
                    if(ans.equals(check)){
                        /* Win */
                        judge.setVisibility(View.VISIBLE);
                        judge.setText("WIN");
                        try_again.setVisibility(View.VISIBLE);
                        answers.setText("");
                    }
                    else{
                        /* Lose */
                        judge.setVisibility(View.VISIBLE);
                        judge.setText("LOSE");
                        try_again.setVisibility(View.VISIBLE);
                        answers.setText("");
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Incorrect amount of answer! 9 characters only!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void assign_color(){
        for(int i = 0;i<9;i++){
            int rand = new Random().nextInt(3) + 1;
            check += rand;
            View test = grid.getChildAt(i);
            col = (TextView) test;
            switch(rand){
                case 1:
                    col.setBackgroundResource(R.color.red);
                    break;
                case 2:
                    col.setBackgroundResource(R.color.green);
                    break;
                case 3:
                    col.setBackgroundResource(R.color.blue);
                    break;
            }
        }
    }

    public void assign_white(){
        for(int i = 0;i<9;i++){
            View test = grid.getChildAt(i);
            col = (TextView) test;
            col.setBackgroundResource(R.color.white);
        }
    }

    public void start_timer(){
        boolean t = true;
        i = 12;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run(){
                while(t){
                    if(i == 0){
                        assign_white();
                        answers.setText("");
                        break;
                    }
                    try{
                        i--;
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText(""+i);
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }
}