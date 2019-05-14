package com.jhonatasrm.playerdemusica.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.content.Context;
import android.widget.TextView;

import com.jhonatasrm.playerdemusica.Model.ListaMusicasBandas;
import com.jhonatasrm.playerdemusica.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    public TextView autor;
    private ImageView capaAlbum;
    public TextView titulo;
    public TextView anoMusica;
    public int musicaTocando;
    public int capaDoAlbum;
    public int i = 0;
    int anoA;
    String bandaA;
    String musicaA;
    List<ListaMusicasBandas> listaMusicasBandas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaMusicasBandas = initList();
        initNavigationAndToolbar();
        init();

        mediaPlayer = MediaPlayer.create(this, listaMusicasBandas.get(i).getMusicaTocar());
        final AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // inicializa os findViewById em um método separado
    public void init() {
        autor = findViewById(R.id.banda);
        titulo = findViewById(R.id.musica);
        autor.setText(listaMusicasBandas.get(i).getBanda());
        titulo.setText(listaMusicasBandas.get(i).getMusica());
        capaAlbum = findViewById(R.id.capa);
        anoMusica = findViewById(R.id.ano);
    }

    // salva a atual posição da música selecionada
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("posicaoAlbum", i);
        outState.putLong("posicaoSalva", mediaPlayer.getCurrentPosition());
    }

    // recupera o estado anterior que foi salvo com o método onSaveInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        i = savedInstanceState.getInt("posicaoAlbum");
    }

    // pausa a música quando o método onPause é chamado no ciclo de vida da Activity
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    /* continua a tocar a música quando o método onRestart é chamado no ciclo de vida da Activity usando a posição salva
     no onSaveInstanceState (i) */
    @Override
    protected void onRestart() {
        super.onRestart();
        autor.setText(listaMusicasBandas.get(i).getBanda());
        titulo.setText(listaMusicasBandas.get(i).getMusica());
        capaAlbum.setImageResource(listaMusicasBandas.get(i).getCapaAlbum());
        anoMusica.setText(String.valueOf(listaMusicasBandas.get(i).getAno()));
        mediaPlayer.start();
    }

    // inicializa a Toolbar com as opções de cor do background e do texto
    public void initNavigationAndToolbar() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
    }

    // inicializa o menu criado
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    /* verifica qual a opção selecionada.. se a opção com o id R.id.info no menu é tocado, então é chamado o 
    método passaInformações, retornando "true" (dizendo que já foi tratado o toque)*/
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

    /* escuta o NavigationView na parte inferior da tela do aplicativo, respondendo conforme a opção tocada e retornando "true",
    dizendo que o toque no menu já foi tratado */
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

    // método que trata quando a opção voltar é clicada no NavigationView
    public void voltar() {
        mediaPlayer.stop();
        if (i == 0) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, listaMusicasBandas.get(i).getMusicaTocar());
            autor.setText(listaMusicasBandas.get(i).getBanda());
            titulo.setText(listaMusicasBandas.get(i).getMusica());
            capaAlbum.setImageResource(listaMusicasBandas.get(i).getCapaAlbum());
            anoMusica.setText(String.valueOf(listaMusicasBandas.get(i).getAno()));
            i = 4;
        } else {
            i--;
            mediaPlayer = MediaPlayer.create(MainActivity.this, listaMusicasBandas.get(i).getMusicaTocar());
            autor.setText(listaMusicasBandas.get(i).getBanda());
            titulo.setText(listaMusicasBandas.get(i).getMusica());
            capaAlbum.setImageResource(listaMusicasBandas.get(i).getCapaAlbum());
            anoMusica.setText(String.valueOf(listaMusicasBandas.get(i).getAno()));
        }
    }

    // método que trata quando a opção avançar é clicada no NavigationView
    @SuppressLint("RestrictedApi")
    public void avancar() {
        mediaPlayer.stop();
        i++;
        if (i == listaMusicasBandas.size()) {
            i = 0;
        }
        mediaPlayer = MediaPlayer.create(MainActivity.this, listaMusicasBandas.get(i).getMusicaTocar());
        autor.setText(listaMusicasBandas.get(i).getBanda());
        titulo.setText(listaMusicasBandas.get(i).getMusica());
        capaAlbum.setImageResource(listaMusicasBandas.get(i).getCapaAlbum());
        anoMusica.setText(String.valueOf(listaMusicasBandas.get(i).getAno()));
    }

    // método que ira passar as informações da música atual no Array
    public void passaInformacoes() {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("banda", listaMusicasBandas.get(i).getBanda());
        intent.putExtra("musica", listaMusicasBandas.get(i).getMusica());
        intent.putExtra("ano", listaMusicasBandas.get(i).getAno());
        intent.putExtra("posicao", i);
        startActivityForResult(intent, 1);
    }

    // esse método irá tratar as informações recebidas da InfoActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bandaA = data.getExtras().getString("banda");
        anoA = Integer.valueOf(data.getExtras().getString("ano"));
        musicaA = data.getExtras().getString("musica");
        i = data.getExtras().getInt("posicao", 1);
        musicaTocando = listaMusicasBandas.get(i).getMusicaTocar();
        capaDoAlbum = listaMusicasBandas.get(i).getCapaAlbum();
        if (resultCode == -1) {
            listaMusicasBandas.set(i, new ListaMusicasBandas(musicaA, bandaA, anoA, musicaTocando, capaDoAlbum));
            autor.setText(bandaA);
            anoMusica.setText(String.valueOf(anoA));
            titulo.setText(musicaA);
        }
    }
    
    // método que irá inicializar: nome da música, autor, ano, música em si (mp3) e capa do album na lista 
    public List<ListaMusicasBandas> initList() {
        return new ArrayList<>(Arrays.asList(new ListaMusicasBandas("Back In Black", "AC/DC", 1980, R.raw.back_in_black, R.drawable.ac_dc),
                new ListaMusicasBandas("Here I go Again", "White Snake", 1982, R.raw.here_i_go_again, R.drawable.white_snake),
                new ListaMusicasBandas("Highway to Hell", "AC/DC", 1979, R.raw.high_way_to_hell, R.drawable.ac_dc),
                new ListaMusicasBandas("Stairway to Heaven", "Led Zeppelin", 1971, R.raw.stairway_to_heaven, R.drawable.led_zeppelin),
                new ListaMusicasBandas("Thunderstruck", "AC/DC", 1990, R.raw.stairway_to_heaven, R.drawable.ac_dc)));
    }
}
