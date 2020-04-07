package com.example.tic_tac_toe_my_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Locale;

public class canvasViewPlay extends View { //you have to create a new java file and then insert the same file in the xml of the page in which you want the canvas
   // Button restart_game_btn;
    Paint paint = new Paint();
    Paint paintx = new Paint();
    Paint painto = new Paint();
    Paint painto1 = new Paint();
    boolean oncewin = false;
    boolean oncedrawen = false;
    int sum = 0;
    int[][] a = new int[3][3];
    float[][] midx = new float[3][3];
    float[][] midy = new float[3][3];
    int turn = 0;
    MainActivity context;
    Context ctx;
    float canvasSide, cellSide;
    someService zxc;
    Subject subject;

    public void init() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
                a[row][col] = 0;
                midx[row][col] = ((px / 6) + (col * (px / 3)));
                midy[row][col] = ((px / 6) + (row * (px / 3)));
            }
        }

        oncewin = false;
        oncedrawen = false;
        turn = 0;
    }

    public canvasViewPlay(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context=(MainActivity) context;
        zxc = this.context.service;
        subject = new Subject();
        subject.attach(new Observer() {
            @Override
            public void act() {
                a = zxc.getA();
                turn = zxc.getTurn();
                sum = zxc.getSum();
            }
        }, "StepMade");
        subject.attach(new Observer() {
            @Override
            public void act() {
                zxc.setA(a);
                zxc.setTurn(turn);
                zxc.setSum(sum);
            }
        }, "Synchro");
        zxc.setSubject(subject);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.rgb(245, 125, 10));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        paintx.setStrokeWidth(15f);
        paintx.setColor(Color.rgb(105, 240, 174));
        paintx.setStyle(Paint.Style.STROKE);
        paintx.setStrokeJoin(Paint.Join.ROUND);

        painto.setColor(Color.rgb(10, 125, 245));
        painto.setStyle(Paint.Style.FILL);

        painto1.setColor(Color.rgb(255, 224, 178));
        painto1.setStyle(Paint.Style.FILL);

        init();
        firstTurn();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Resources r = getResources();
        float pxi = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
        canvasSide = pxi;
        cellSide = canvasSide / 3;
        canvas.drawLine(cellSide, 0, cellSide, canvasSide, paint);
        canvas.drawLine(2 * cellSide, 0, 2 * cellSide, canvasSide, paint);
        canvas.drawLine(0, cellSide, canvasSide, cellSide, paint);
        canvas.drawLine(0, 2 * cellSide, canvasSide, 2 * cellSide, paint);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (a[row][col] == 1) {
                    canvas.drawLine((midx[row][col] - ((4 * cellSide) / 11)), (midy[row][col] - ((4 * cellSide) / 11)), (midx[row][col] + ((4 * cellSide) / 11)), (midy[row][col] + ((4 * cellSide) / 11)), paintx);
                    canvas.drawLine((midx[row][col] + ((4 * cellSide) / 11)), (midy[row][col] - ((4 * cellSide) / 11)), (midx[row][col] - ((4 * cellSide) / 11)), (midy[row][col] + ((4 * cellSide) / 11)), paintx);
                } else if (a[row][col] == 2) {
                    canvas.drawCircle(midx[row][col], midy[row][col], (4 * cellSide) / 11, painto);
                    canvas.drawCircle(midx[row][col], midy[row][col], (13 * cellSide) / 44, painto1);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (oncedrawen || oncewin) {
                // Game.play.finish();
            }
            float touchX = event.getX();
            float touchY = event.getY();
            if (touchX < canvasSide && touchX > 0 && touchY < canvasSide && touchX > 0) {
                int col = (int) (touchX / cellSide);
                int row = (int) (touchY / cellSide);
                if (a[row][col] == 0) {
                    turn++;
                    if (turn % 2 == 0) {
                        a[row][col]++;
                        a[row][col]++;
                    }

                    postInvalidate();
                    subject.notifyObservers("Synchro");
                    check();
                    if (!oncedrawen && !oncewin) {
                        sum = 0;
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                sum += a[i][j];
                            }
                        }
                        zxc.makeOwn();
                        postInvalidate();
                        subject.notifyObservers("Synchro");
                        check();
                    }
                }
            }
        }
        return true;
    }

    public void firstTurn() {

        turn += 1;
        zxc.setTurn(zxc.getTurn()+1);
        a[0][0]++;
        int[][] l = zxc.getA();
        l[0][0]++;
        zxc.setA(l);
        invalidate();
    }

    /*public void makeOwn() {

        if (a[0][0] == a[0][1] && a[0][0] == 1 && a[0][2] == 0) {
            a[0][2]++;
            turn++;
        }//1

        else if (a[0][1] == a[0][2] && a[0][1] == 1 && a[0][0] == 0) {
            a[0][0]++;
            turn++;
        }//2

        else if (a[0][0] == a[0][2] && a[0][0] == 1 && a[0][1] == 0) {
            a[0][1]++;
            turn++;
        }//3

        else if (a[1][0] == a[1][1] && a[1][0] == 1 && a[1][2] == 0) {
            a[1][2]++;
            turn++;
        }//4

        else if (a[1][1] == a[1][2] && a[1][1] == 1 && a[1][0] == 0) {
            a[1][0]++;
            turn++;
        }//5

        else if (a[1][0] == a[1][2] && a[1][0] == 1 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//6

        else if (a[2][0] == a[2][1] && a[2][0] == 1 && a[2][2] == 0) {
            a[2][2]++;
            turn++;
        }//7

        else if (a[2][1] == a[2][2] && a[2][1] == 1 && a[2][0] == 0) {
            a[2][0]++;
            turn++;
        }//8

        else if (a[2][0] == a[2][2] && a[2][0] == 1 && a[2][1] == 0) {
            a[2][1]++;
            turn++;
        }//9

        else if (a[0][0] == a[1][0] && a[0][0] == 1 && a[2][0] == 0) {
            a[2][0]++;
            turn++;
        }//10

        else if (a[1][0] == a[2][0] && a[1][0] == 1 && a[0][0] == 0) {
            a[0][0]++;
            turn++;
        }//11

        else if (a[0][0] == a[2][0] && a[0][0] == 1 && a[1][0] == 0) {
            a[1][0]++;
            turn++;
        }//12

        else if (a[0][1] == a[1][1] && a[0][1] == 1 && a[2][1] == 0) {
            a[2][1]++;
            turn++;
        }//13

        else if (a[1][1] == a[2][1] && a[1][1] == 1 && a[0][1] == 0) {
            a[0][1]++;
            turn++;
        }//14

        else if (a[0][1] == a[2][1] && a[0][1] == 1 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//15

        else if (a[0][2] == a[1][2] && a[0][2] == 1 && a[2][2] == 0) {
            a[2][2]++;
            turn++;
        }//16

        else if (a[1][2] == a[2][2] && a[1][2] == 1 && a[0][2] == 0) {
            a[0][2]++;
            turn++;
        }//17

        else if (a[0][2] == a[2][2] && a[0][2] == 1 && a[1][2] == 0) {
            a[1][2]++;
            turn++;
        }//18

        else if (a[0][0] == a[1][1] && a[0][0] == 1 && a[2][2] == 0) {
            a[2][2]++;
            turn++;
        }//19

        else if (a[1][1] == a[2][2] && a[1][1] == 1 && a[0][0] == 0) {
            a[0][0]++;
            turn++;
        }//20

        else if (a[0][0] == a[2][2] && a[0][0] == 1 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//21

        else if (a[0][2] == a[1][1] && a[0][2] == 1 && a[2][0] == 0) {
            a[2][0]++;
            turn++;
        }//22

        else if (a[1][1] == a[2][0] && a[1][1] == 1 && a[0][2] == 0) {
            a[0][2]++;
            turn++;
        }//23

        else if (a[0][2] == a[2][0] && a[0][2] == 1 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//24

        else {
            prevent();
        }
    }

    public void prevent() {
        if (a[0][0] == a[0][1] && a[0][0] == 2 && a[0][2] == 0) {
            a[0][2]++;
            turn++;
        }//1

        else if (a[0][1] == a[0][2] && a[0][1] == 2 && a[0][0] == 0) {
            a[0][0]++;
            turn++;
        }//2

        else if (a[0][0] == a[0][2] && a[0][0] == 2 && a[0][1] == 0) {
            a[0][1]++;
            turn++;
        }//3

        else if (a[1][0] == a[1][1] && a[1][0] == 2 && a[1][2] == 0) {
            a[1][2]++;
            turn++;
        }//4

        else if (a[1][1] == a[1][2] && a[1][1] == 2 && a[1][0] == 0) {
            a[1][0]++;
            turn++;
        }//5

        else if (a[1][0] == a[1][2] && a[1][0] == 2 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//6

        else if (a[2][0] == a[2][1] && a[2][0] == 2 && a[2][2] == 0) {
            a[2][2]++;
            turn++;
        }//7

        else if (a[2][1] == a[2][2] && a[2][1] == 2 && a[2][0] == 0) {
            a[2][0]++;
            turn++;
        }//8

        else if (a[2][0] == a[2][2] && a[2][0] == 2 && a[2][1] == 0) {
            a[2][1]++;
            turn++;
        }//9

        else if (a[0][0] == a[1][0] && a[0][0] == 2 && a[2][0] == 0) {
            a[2][0]++;
            turn++;
        }//10

        else if (a[1][0] == a[2][0] && a[1][0] == 2 && a[0][0] == 0) {
            a[0][0]++;
            turn++;
        }//11

        else if (a[0][0] == a[2][0] && a[0][0] == 2 && a[1][0] == 0) {
            a[1][0]++;
            turn++;
        }//12

        else if (a[0][1] == a[1][1] && a[0][1] == 2 && a[2][1] == 0) {
            a[2][1]++;
            turn++;
        }//13

        else if (a[1][1] == a[2][1] && a[1][1] == 2 && a[0][1] == 0) {
            a[0][1]++;
            turn++;
        }//14

        else if (a[0][1] == a[2][1] && a[0][1] == 2 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//15

        else if (a[0][2] == a[1][2] && a[0][2] == 2 && a[2][2] == 0) {
            a[2][2]++;
            turn++;
        }//16

        else if (a[1][2] == a[2][2] && a[1][2] == 2 && a[0][2] == 0) {
            a[0][2]++;
            turn++;
        }//17

        else if (a[0][2] == a[2][2] && a[0][2] == 2 && a[1][2] == 0) {
            a[1][2]++;
            turn++;
        }//18

        else if (a[0][0] == a[1][1] && a[0][0] == 2 && a[2][2] == 0) {
            a[2][2]++;
            turn++;
        }//19

        else if (a[1][1] == a[2][2] && a[1][1] == 2 && a[0][0] == 0) {
            a[0][0]++;
            turn++;
        }//20

        else if (a[0][0] == a[2][2] && a[0][0] == 2 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//21

        else if (a[0][2] == a[1][1] && a[0][2] == 2 && a[2][0] == 0) {
            a[2][0]++;
            turn++;
        }//22

        else if (a[1][1] == a[2][0] && a[1][1] == 2 && a[0][2] == 0) {
            a[0][2]++;
            turn++;
        }//23

        else if (a[0][2] == a[2][0] && a[0][2] == 2 && a[1][1] == 0) {
            a[1][1]++;
            turn++;
        }//24

        else {
            specialMove();
        }
    }

    public void specialMove() {
        if (a[0][0] == 1 && a[1][1] == 2 && sum == 3) {
            a[2][2]++;
            turn++;
        }//5

        else if (a[0][0] == 1 && a[2][1] == 2 && sum == 3) {
            a[0][2]++;
            turn++;
        }//4.1

        else if (a[0][0] == 1 && a[1][2] == 2 && sum == 3) {
            a[2][0]++;
            turn++;
        }//4.2

        else if (a[0][0] == 1 && a[2][2] == 2 && sum == 3) {
            a[2][0]++;
            turn++;
        }//3_1

        else if (a[2][0] == 1 && a[1][0] == 2 && a[0][0] == 1 && a[2][2] == 2 && sum == 6) {
            a[0][2]++;
            turn++;
        }//3_2

        else if (a[0][0] == 1 && (a[0][2] == 2 || a[2][0] == 2) && sum == 3) {
            a[2][2]++;
            turn++;
        }//2.1 and 2.2

        else if (a[0][0] == 1 && a[0][1] == 2 && sum == 3) {
            a[2][0]++;
            turn++;
        }//1.1_1

        else if (a[0][0] == 1 && a[0][1] == 2 && a[2][0] == 1 && a[1][0] == 2 && sum == 6) {
            a[1][1]++;
            turn++;
        }//1.1_2

        else if (a[0][0] == 1 && a[1][0] == 2 && sum == 3) {
            a[0][2]++;
            turn++;
        }//1.2_1

        else if (a[0][0] == 1 && a[1][0] == 2 && a[0][2] == 1 && a[0][1] == 2 && sum == 6) {
            a[1][1]++;
            turn++;
        }//1.2_2

        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (a[i][j] == 0) {
                        a[i][j]++;
                        turn++;
                        i = 3;
                        j = 3;
                    }
                }
            }
        }
    }*/

    public void showAlert(String str) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE: {

                       // context.finish();
                        //context.startActivity(());
                        FragmentGay.restart_game_btn.performClick();
                        break;
                    }
                    case DialogInterface.BUTTON_NEUTRAL: {
                        // context.finish();
                        //System.exit(0);
                        context.moveTaskToBack(true);
                        context.finish();
                        break;
                    }
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(str).setPositiveButton("Ok!", dialogClickListener);
        builder.setNeutralButton("Выход", dialogClickListener).show();

    }

    public void check() {
        if (!oncewin) {
            if (a[0][0] == a[0][1] && a[0][1] == a[0][2]) {
                if (a[0][0] == 1) {
                    showAlert("Ви програли! ");
                    oncewin = true;
                } else if (a[0][0] == 2) {
                    showAlert("Ви виграли! ");
                    oncewin = true;
                }
            }

            if (a[1][0] == a[1][1] && a[1][1] == a[1][2]) {
                if (a[1][0] == 1) {
                    showAlert("Ви програли! ");
                    oncewin = true;
                } else if (a[1][0] == 2) {
                    showAlert("Ви виграли! ");
                    oncewin = true;
                }
            }

            if (a[2][0] == a[2][1] && a[2][1] == a[2][2]) {
                if (a[2][0] == 1) {
                    showAlert("Ви програли! ");
                    oncewin = true;
                } else if (a[2][0] == 2) {
                    showAlert("Ви виграли! ");
                    oncewin = true;
                }
            }

            if (a[0][0] == a[1][0] && a[1][0] == a[2][0]) {
                if (a[0][0] == 1) {
                    //Toast.makeText(getContext(),"You loose! Better luck next time!! ",Toast.LENGTH_SHORT).show();
                    showAlert("Ви програли! ");
                    oncewin = true;
                } else if (a[0][0] == 2) {
                    //Toast.makeText(getContext(),"You Win! ",Toast.LENGTH_SHORT).show();
                    showAlert("Ви виграли! ");
                    oncewin = true;
                }
            }

            if (a[0][1] == a[1][1] && a[1][1] == a[2][1]) {
                if (a[0][1] == 1) {
                    showAlert("Ви програли!! ");
                    oncewin = true;
                } else if (a[0][1] == 2) {
                    //Toast.makeText(getContext(),"You Win! ",Toast.LENGTH_SHORT).show();
                    showAlert("Ви виграли! ");
                    oncewin = true;
                }
            }

            if (a[0][2] == a[1][2] && a[1][2] == a[2][2]) {
                if (a[0][2] == 1) {
                    //Toast.makeText(getContext(),"You loose! Better luck next time!! ",Toast.LENGTH_SHORT).show();
                    showAlert("Ви програли!! ");
                    oncewin = true;
                } else if (a[0][2] == 2) {
                    //Toast.makeText(getContext(),"You Win! ",Toast.LENGTH_SHORT).show();
                    showAlert("Ви виграли! ");
                    oncewin = true;
                }
            }

            if (a[0][0] == a[1][1] && a[1][1] == a[2][2]) {
                if (a[0][0] == 1) {
                    //Toast.makeText(getContext(),"You loose! Better luck next time!! ",Toast.LENGTH_SHORT).show();
                    showAlert("Ви програли!! ");
                    oncewin = true;
                } else if (a[0][0] == 2) {
                    //Toast.makeText(getContext(),"You Win! ",Toast.LENGTH_SHORT).show();
                    showAlert("Ви виграли!");
                    oncewin = true;
                }
            }

            if (a[0][2] == a[1][1] && a[1][1] == a[2][0]) {
                if (a[0][2] == 1) {
                    showAlert("Ви програли! ");
                    oncewin = true;
                } else if (a[0][2] == 2) {
                    showAlert("Ви виграли! ");
                    oncewin = true;
                }
            }

            if (turn == 9 && !oncewin) {
                showAlert("Нічия!");
                oncedrawen = true;
            }
        }
    }
}
