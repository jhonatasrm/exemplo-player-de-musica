package com.jhonatasrm.playerdemusica;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView autor;
    private ImageView capaAlbum;
    private  TextView titulo;
    private TextView anoMusica;
    private int posicao = -1;
    private int i = 0;
    List<String> banda = new ArrayList<>();
    List<String> nomeMusica = new ArrayList<>();
    List<String> ano = new ArrayList<>();
    int[] musicas = new int[]{R.raw.back_in_black, R.raw.here_i_go_again, R.raw.high_way_to_hell, R.raw.stairway_to_heaven, R.raw.thunderstruck};
    int[] capa = new int[]{R.drawable.ac_dc,R.drawable.white_snake, R.drawable.ac_dc, R.drawable.led_zeppelin,R.drawable.ac_dc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initArrays();
        initNavigationAndToolbar();
        init();
        mediaPlayer = MediaPlayer.create(this, musicas[i]);
        final AudioManager amanager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int volume = amanager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar = findViewById(R.id.seekbar_audio);

        seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
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
    }

    public void init(){
        autor = findViewById(R.id.banda);
        titulo = findViewById(R.id.musica);
        autor.setText(banda.get(i));
        titulo.setText(nomeMusica.get(i));
        capaAlbum = findViewById(R.id.capa);
        anoMusica = findViewById(R.id.ano);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("posicaoAlbum", i);
        outState.putLong("posicaoSalva", mediaPlayer.getCurrentPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        i = savedInstanceState.getInt("posicaoAlbum");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        autor.setText(banda.get(i));
        titulo.setText(nomeMusica.get(i));
        capaAlbum.setImageResource(capa[i]);
        anoMusica.setText(ano.get(i));
        mediaPlayer.start();
    }

    public void initNavigationAndToolbar(){
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
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
                passaInformacoes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    avancar();
                    return true;
                case R.id.pause:
                    mediaPlayer.pause();
                    return true;
                case R.id.voltar:
                    voltar();
                    return true;
            }
            return onNavigationItemSelected(item);
        }
    };

    public void voltar(){
        mediaPlayer.stop();
        if(i == 0) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
            autor.setText(banda.get(i));
            titulo.setText(nomeMusica.get(i));
            capaAlbum.setImageResource(capa[i]);
            anoMusica.setText(ano.get(i));
        }else{
            i--;
            mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
            autor.setText(banda.get(i));
            titulo.setText(nomeMusica.get(i));
            capaAlbum.setImageResource(capa[i]);
            anoMusica.setText(ano.get(i));
        }
    }

    @SuppressLint("RestrictedApi")
    public void avancar(){
        mediaPlayer.stop();
        i++;
        if(i == banda.size()){
            i = 0;
        }
        mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
        autor.setText(banda.get(i));
        titulo.setText(nomeMusica.get(i));
        capaAlbum.setImageResource(capa[i]);
        anoMusica.setText(ano.get(i));
    }

    public void passaInformacoes(){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("banda", banda.get(i));
        intent.putExtra("musica", nomeMusica.get(i));
        intent.putExtra("ano", ano.get(i));
        intent.putExtra("posicao", i);
        startActivity(intent);
    }

    public void mudarValores(String bandaRecebida, String musicaRecebida, String anoRecebido, int posicao){
        if(!banda.isEmpty() && !nomeMusica.isEmpty() && !ano.isEmpty()){
            banda.set(posicao, bandaRecebida);
            nomeMusica.set(posicao, musicaRecebida);
            ano.set(posicao, anoRecebido);
            Toast.makeText(this, "Valores alterados ! Na posição "+posicao, Toast.LENGTH_LONG).show();
        }
    }

    public void initArrays(){
        banda.add("AC/DC");
        banda.add("White Snake");
        banda.add("AC/DC");
        banda.add("Led Zeppelin");
        banda.add("AC/DC");

        nomeMusica.add("Back In Black");
        nomeMusica.add("Here I Go Again");
        nomeMusica.add("Highway To Hell");
        nomeMusica.add("Stairway to Heaven");
        nomeMusica.add("Thunderstruck");

        ano.add("1980");
        ano.add("1982");
        ano.add("1979");
        ano.add("1971");
        ano.add("1990");
    }
}
