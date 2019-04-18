package com.jhonatasrm.playerdemusica;

class MusicaBandaAno {

    private String musica;
    private String banda;
    private String ano;

    MusicaBandaAno(String musica, String banda, String ano) {
        this.setAno(ano);
        this.setMusica(musica);
        this.setBanda(banda);
    }

    String getMusica() {
        return musica;
    }

    private void setMusica(String musica) {
        this.musica = musica;
    }

    String getBanda() {
        return banda;
    }

    private void setBanda(String banda) {
        this.banda = banda;
    }

    String getAno() {
        return ano;
    }

    private void setAno(String ano) {
        this.ano = ano;
    }
}