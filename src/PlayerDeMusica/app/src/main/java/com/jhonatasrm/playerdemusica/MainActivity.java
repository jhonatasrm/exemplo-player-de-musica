package com.jhonatasrm.playerdemusica;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.content.Context;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView autor;
    private  TextView titulo;
    private int i;
    int[] musicas = new int[]{R.raw.back_in_black, R.raw.here_i_go_again, R.raw.high_way_to_hell, R.raw.stairway_to_heaven, R.raw.thunderstruck };
    String[] banda = new String[]{"AC/DC", "White Snake","AC/DC","Led Zeppelin","AC/DC"};
    String[] nomeMusica = new String[]{"Back in Black","Here I Go Again","High Way To Hell","Stairway to Heaven","AC/DC"};
    int[] cover = new int[]{R.drawable.ac_dc,R.drawable.white_snake, R.drawable.ac_dc, R.drawable.led_zeppelin,R.drawable.ac_dc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);

        mediaPlayer = MediaPlayer.create(this, musicas[i]);
        seekBar = findViewById(R.id.seekbar_audio);
        i = 0;

        seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        int volume = amanager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(volume);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int newVolume, boolean b) {
                amanager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        init();
    }


    public void init(){
        autor = findViewById(R.id.autor);
        titulo = findViewById(R.id.titulo);
        autor.setText(banda[i]);
        titulo.setText(nomeMusica[i]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                startActivity(new Intent(this, InfoActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.stop:
                    mediaPlayer.stop();
                    return true;
                case R.id.play:
                    mediaPlayer.start();
                    return true;
                case R.id.avancar:
                    i++;
                    mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
                    autor.setText(banda[i]);
                    titulo.setText(nomeMusica[i]);
                    return true;
                case R.id.pause:
                    mediaPlayer.pause();
                    return true;
                case R.id.voltar:
                    if(i == 0) {
                        mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
                        autor.setText(banda[i]);
                        titulo.setText(nomeMusica[i]);
                    }else{
                        i--;
                        mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
                        autor.setText(banda[i]);
                        titulo.setText(nomeMusica[i]);
                    }
                    return true;
            }
            return false;
        }
    };
}
