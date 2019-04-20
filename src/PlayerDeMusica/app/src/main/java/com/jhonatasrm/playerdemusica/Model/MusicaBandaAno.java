package com.jhonatasrm.playerdemusica.Model;

public class MusicaBandaAno {

    String musica;
    String banda;
    int ano;
    int musicaTocar;
    int capaAlbum;

    public MusicaBandaAno(String musica, String banda, int ano, int musicaTocar, int capaAlbum) {
        this.ano = ano;
        this.musica = musica;
        this.banda = banda;
        this.musicaTocar = musicaTocar;
        this.capaAlbum = capaAlbum;
    }

    public int getMusicaTocar() {
        return musicaTocar;
    }

    public void setMusicaTocar(int musicaTocar) {
        this.musicaTocar = musicaTocar;
    }

    public int getCapaAlbum() {
        return capaAlbum;
    }

    public void setCapaAlbum(int capaAlbum) {
        this.capaAlbum = capaAlbum;
    }

    public String getMusica() {
        return musica;
    }

    public void setMusica(String musica) {
        this.musica = musica;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}