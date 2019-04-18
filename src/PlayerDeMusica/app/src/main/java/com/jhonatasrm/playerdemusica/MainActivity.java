package com.jhonatasrm.playerdemusica;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    public TextView autor;
    private ImageView capaAlbum;
    public TextView titulo;
    public TextView anoMusica;
    public int i = 0;
    String bandaA;
    String anoA;
    String musicaA;
    ArrayList<MusicaBandaAno> musicaBandaAno = new ArrayList<>();
    int[] musicas = new int[]{R.raw.back_in_black, R.raw.here_i_go_again, R.raw.high_way_to_hell, R.raw.stairway_to_heaven, R.raw.thunderstruck};
    int[] capa = new int[]{R.drawable.ac_dc, R.drawable.white_snake, R.drawable.ac_dc, R.drawable.led_zeppelin, R.drawable.ac_dc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initArrayList();
        initNavigationAndToolbar();
        init();

        mediaPlayer = MediaPlayer.create(this, musicas[i]);
        final AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volume = amanager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar = findViewById(R.id.seekbar_audio);

        seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        seekBar.setProgress(volume * 100);

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

    public void init() {
        autor = findViewById(R.id.banda);
        titulo = findViewById(R.id.musica);
        autor.setText(musicaBandaAno.get(i).getBanda());
        titulo.setText(musicaBandaAno.get(i).getMusica());
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
        autor.setText(musicaBandaAno.get(i).getBanda());
        titulo.setText(musicaBandaAno.get(i).getMusica());
        capaAlbum.setImageResource(capa[i]);
        anoMusica.setText(musicaBandaAno.get(i).getAno());
        mediaPlayer.start();
    }

    public void initNavigationAndToolbar() {
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

    public void voltar() {
        mediaPlayer.stop();
        if (i == 0) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
            autor.setText(musicaBandaAno.get(i).getBanda());
            titulo.setText(musicaBandaAno.get(i).getMusica());
            capaAlbum.setImageResource(capa[i]);
            anoMusica.setText(musicaBandaAno.get(i).getAno());
        } else {
            i--;
            mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
            autor.setText(musicaBandaAno.get(i).getBanda());
            titulo.setText(musicaBandaAno.get(i).getMusica());
            capaAlbum.setImageResource(capa[i]);
            anoMusica.setText(musicaBandaAno.get(i).getAno());
        }
    }

    @SuppressLint("RestrictedApi")
    public void avancar() {
        mediaPlayer.stop();
        i++;
        if (i == musicaBandaAno.size()) {
            i = 0;
        }
        mediaPlayer = MediaPlayer.create(MainActivity.this, musicas[i]);
        autor.setText(musicaBandaAno.get(i).getBanda());
        titulo.setText(musicaBandaAno.get(i).getMusica());
        capaAlbum.setImageResource(capa[i]);
        anoMusica.setText(musicaBandaAno.get(i).getAno());
    }

    public void passaInformacoes() {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("banda", musicaBandaAno.get(i).getBanda());
        intent.putExtra("musica", musicaBandaAno.get(i).getMusica());
        intent.putExtra("ano", musicaBandaAno.get(i).getAno());
        intent.putExtra("posicao", i);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bandaA = data.getExtras().getString("banda");
        anoA = data.getExtras().getString("ano");
        musicaA = data.getExtras().getString("musica");
        i = data.getExtras().getInt("posicao", 1);
        if (resultCode == -1) {
            musicaBandaAno.set(i, new MusicaBandaAno(musicaA, bandaA, anoA));
            autor.setText(bandaA);
            anoMusica.setText(anoA);
            titulo.setText(musicaA);
        }
    }

    public void initArrayList() {
        musicaBandaAno.add(new MusicaBandaAno("Back In Black", "AC/DC", "1980"));
        musicaBandaAno.add(new MusicaBandaAno("Here I go Again", "White Snake", "1982"));
        musicaBandaAno.add(new MusicaBandaAno("Highway to Hell", "AC/DC", "1979"));
        musicaBandaAno.add(new MusicaBandaAno("Stairway to Heaven", "Led Zeppelin", "1971"));
        musicaBandaAno.add(new MusicaBandaAno("Thunderstruck", "AC/DC", "1990"));
    }
}
