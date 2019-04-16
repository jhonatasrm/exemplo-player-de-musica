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

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = new Intent();
        intent = getIntent();
        init();

        musica.setText(intent.getStringExtra("musica"));
        banda.setText(intent.getStringExtra("banda"));
        ano.setText((intent.getStringExtra("ano")));
        posicao = intent.getIntExtra("posicao", 0);

    }

    public void init() {
        banda = findViewById(R.id.banda);
        musica = findViewById(R.id.musica);
        ano = findViewById(R.id.ano);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alterar:
                Intent intent = new Intent();
                intent.putExtra("musica", musica.getText().toString());
                intent.putExtra("ano", ano.getText().toString());
                intent.putExtra("banda", banda.getText().toString());
                intent.putExtra("posicao", posicao);
                setResult(RESULT_OK, intent);
                finish();
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

    public void alterar() {
        Intent intent = new Intent();
        intent.putExtra("musica", musica.getText().toString());
        intent.putExtra("ano", ano.getText().toString());
        intent.putExtra("banda", banda.getText().toString());
        intent.putExtra("posicao", posicao);
        startActivityForResult(intent, 1);
        finish();
    }
}
