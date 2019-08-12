package com.example.tictac;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons =new Button[3][3];
    public TextView txtwin,txt_score1,txt_score2;
    private Button btn_newgame;
    private boolean player1turn =true,gaming=true;
    public int roundcount=0,score01=0,score02=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer clicksound =MediaPlayer.create(this, R.raw.clicker_sms_tone);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_newgame=findViewById(R.id.newgame);
        txtwin =findViewById(R.id.winner);
        txt_score1=findViewById(R.id.score1);
        txt_score2=findViewById(R.id.score2);
        btn_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                reset();
            }
        });
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                 String buttonid ="button"+i+j;
                 int resid =getResources().getIdentifier(buttonid,"id",getPackageName());
                 buttons[i][j]=findViewById(resid);
                 buttons[i][j].setOnClickListener(this);
            }
        }
    }
    @Override
    public void onClick(View v) {

        final MediaPlayer clicksound =MediaPlayer.create(this, R.raw.clicker_sms_tone);

        if (gaming)
        {
            clicksound.start();
            if (!((Button) v).getText().toString().equals("")){
              return;
            }
            if (player1turn){
                ((Button) v).setText("X");
                txtwin.setText("Second Player's Turn : 'O'");
            }
            else {
                ((Button) v).setText("O");
                txtwin.setText("First Player's Turn : 'X'");
            }
            roundcount++;
            if (ckeckforwin()){
               if (player1turn){
                 player1wins();
                } else {
                player2wins();
               }
            } else if (roundcount==9){
                draw();
            } else{
                player1turn=!player1turn;
            }
        }
    }
    private boolean ckeckforwin(){
        String[][]field = new String[3][3];
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                field[i][j] =buttons[i][j].getText().toString();
            }
        }
        for (int i=0;i<3;i++)
        {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }
        for (int i=0;i<3;i++)
        {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void player1wins(){
        final MediaPlayer winnersound = MediaPlayer.create(this,R.raw.winner_sms);
        txtwin.setText("First Player Win");
        winnersound.start();
        if (gaming){
            score01++;
        }
        gaming=false;
        txt_score1.setText("1st Player : "+score01);
    }
    private void player2wins(){

        final MediaPlayer winnersound = MediaPlayer.create(this,R.raw.winner_sms);
        winnersound.start();
        txtwin.setText("Second Player Win");
        if (gaming){
            score02++;
        }
        gaming=false;
        txt_score2.setText("2nd Player : "+score02);
    }
    private void draw(){
        final MediaPlayer winnersound = MediaPlayer.create(this,R.raw.winner_sms);
        winnersound.start();
        txtwin.setText("Match Draw");
    }
    private void reset(){
        roundcount=0;
        gaming=true;
        player1turn=true;
        txtwin.setText("First Player's Turn : 'X'");
        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }
}
