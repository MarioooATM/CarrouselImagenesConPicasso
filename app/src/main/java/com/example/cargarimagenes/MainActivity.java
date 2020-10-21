package com.example.cargarimagenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    private int countBanner =0;
    private Button permisos=null;
    private Button carga;
    private Button descarga;
    private ImageView imagen;
    private TimerTask timerTask;
    private Timer timer;
    private int tiempo=-1;
    private String [] img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = new String[]{"https://i.imgur.com/rxX5DKs.jpg", "https://image.jimcdn.com/app/cms/image/transf/dimension=372x10000:format=png/path/sc5413d73f050dd1a/image/i2366ae9945aeb1b6/version/1390587477/image.png",
                "https://i.pinimg.com/originals/1f/90/6f/1f906f80e96bcfe2dffae945d94eda83.jpg"};

        imagen = findViewById(R.id.imageView);
        permisos= findViewById(R.id.button);
        permisos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this,
                       Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                   ActivityCompat.requestPermissions(MainActivity.this,
                           new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

               } else {
                   Toast.makeText(getApplicationContext(), "Los permisos fueron aceptados", Toast.LENGTH_LONG).show();
               }
           }
       });
        carga = findViewById(R.id.button2);
        carga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer();
             }
        });
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiempo==0){
                    Uri uri = Uri.parse("https://www.youtube.com/watch?v=IuzX2C3xOeg");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else if(tiempo==1) {
                    Uri uri = Uri.parse("https://www.youtube.com/watch?v=5dbEhBKGOtY");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else if(tiempo==2){
                    Toast.makeText(getApplicationContext(), tiempo+"", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("https://www.youtube.com/watch?v=O4irXQhgMqg");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                Toast.makeText(getApplicationContext(), tiempo+"", Toast.LENGTH_LONG).show();
            }
        });

        descarga= findViewById(R.id.button3);
        descarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiempo==-1){
                    Toast.makeText(getApplicationContext(), "No hay imagen cargado", Toast.LENGTH_LONG).show();

                }else{
                    saveImage(img[tiempo]);
                    Toast.makeText(getApplicationContext(), tiempo+"", Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    public void saveImage(String image) {
        Picasso.get().load(image).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    FileOutputStream fos = new FileOutputStream(new File(directory, new Date().toString().concat(String.valueOf(countBanner)).concat(".jpg")));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    fos.flush();
                    fos.close();
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
    public void chronometer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tiempo++;
                        Picasso.get().load(img[tiempo]).resize(412,412).into(imagen);
                        if (tiempo > img.length-1){
                            tiempo=-1;
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,1,3000);
    }
}