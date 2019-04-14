package com.jhonatasrm.playerdemusica;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private TextView banda, musica, ano;
    private int posicao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = new Intent();
        intent = getIntent();
        init();

        musica.setText(intent.getStringExtra("musica"));
        banda.setText(intent.getStringExtra("banda"));
        ano.setText((intent.getStringExtra("ano")));
        posicao = intent.getIntExtra("posicao", 0);
    }

    public void init(){
        banda = findViewById(R.id.banda);
        musica = findViewById(R.id.musica);
        ano = findViewById(R.id.ano);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                return true;
            case R.id.alterar:
                alterar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alterar, menu);
        return true;
    }

    public void alterar(){
        MainActivity main = new MainActivity();
        String bandaInfo = banda.getText().toString();
        String musicaInfo = musica.getText().toString();
        String anoInfo = ano.getText().toString();
        main.mudarValores(bandaInfo, musicaInfo, anoInfo, posicao);
        this.finish();
    }
}
