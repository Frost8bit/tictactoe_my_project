package com.example.tic_tac_toe_my_project;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class someService extends Service {
    private static final String TAG = someService.class.getSimpleName();
    private someServiceBinder binder = new someServiceBinder();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: " + hashCode());
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags,
                              int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
        //return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    int sum = 0;
    int[][] a = new int[3][3];
    int turn = 0;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int[][] getA() {
        return a;
    }

    public void setA(int[][] a) {
        this.a = a;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    Subject subject;

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public Subject getSubject(){return subject;}

    public void makeOwn() {

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
        subject.notifyObservers("StepMade");
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
    }

    public class someServiceBinder extends Binder {
        public someService getService() {
            return someService.this;
        }
    }
}
