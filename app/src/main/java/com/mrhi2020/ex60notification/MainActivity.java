package com.mrhi2020.ex60notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void clickBtn(View view) {

        //알림(Notification)을 관리하는 관리자객체 소환하기
        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //알림객체 생성해주는 건축가(Builder)객체 생성
        NotificationCompat.Builder builder= null;

        //Oreo버전(api 26)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //알림채널 객체 생성
            NotificationChannel channel= new NotificationChannel("ch01", "MyChannel", NotificationManager.IMPORTANCE_HIGH);
            //알림채널에 사운드 설정
            Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.get_gem);
            channel.setSound(uri, new AudioAttributes.Builder().build() );

            notificationManager.createNotificationChannel(channel);
            builder= new NotificationCompat.Builder(this, "ch01");
        }else{
            builder= new NotificationCompat.Builder(this, null);
            //건축가에게 사운드 설정
            Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.get_gem);
            builder.setSound(uri);
        }

        //건축가에게 원하는 알림의 모양을 설정

        //상태표시줄에 보이는 아이콘
        builder.setSmallIcon(R.drawable.ic_noti);

        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.img01);
        builder.setLargeIcon(bm);
        builder.setContentTitle("Title"); //알림창 제목
        builder.setContentText("Message..."); //알림창 메세지..
        builder.setSubText("sub text");

        //알림창 클릭시에 실행할 작업(새로운 화면[SecondActivity] 실행) 설정
        Intent intent= new Intent(this, SecondActivity.class);
        //인텐트(택배기사)객체에게 바로 실행하지 말고 잠시 보류해 달라고...
        //보류중인(Pending) 인텐트 객체로 만들기..
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //알림을 클릭했을때 자동으로 알림아이콘 없애기
        builder.setAutoCancel(true);

        //알림에 진동의 패턴 설정하기 : 진동은 사용자에게 진동을 줄 수 있다는 허가[permission]를 받아야 함
        //애뮬레이터는 진동테스트 불가... 실디바이스에서 테스트..
        builder.setVibrate(new long[]{0, 2000, 1000, 3000});//0초대기, 2초진동, 1초대기, 3초진동


        //요즘들어 종종 보이는 알림창 스타일
        //1. BigPictureStyle
        NotificationCompat.BigPictureStyle picStyle= new NotificationCompat.BigPictureStyle(builder);
        picStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.img02));

        //2. BigTextStyle : 여러줄로 메세지 출력 가능 - 요즘 폰은 그냥 되므로 무의미함

        //3. InboxStyle : 여러메세지를 보여줄 때 적합
        NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle(builder);
        inboxStyle.addLine("first");
        inboxStyle.addLine("second");
        inboxStyle.addLine("안녕하세요..");

        //4. MediaStyle : 플레이와 스톱버튼이 있는 알림창 스타일 [각자 도전]

        //5. 프로그레스 표시..[상태진행줄 표시]
        builder.setProgress(100, 50, true);


        //건축가에게 위 설정대로 알림객체 생성해 달라고...
        Notification notification= builder.build();

        //알람매니저에게 알림을 요청하기!!
        notificationManager.notify(10, notification);//아이디, 알림객체

    }
}