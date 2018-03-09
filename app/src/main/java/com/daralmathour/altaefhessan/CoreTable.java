package com.daralmathour.altaefhessan;

/**
 * Created by codelab on 1/5/2018.
 */

public class CoreTable {
    public int Id;
    public int FK_FesresId;
    public  int AyahId;
    public  String ContentText;
    public  String Audio_Url_Sodes;
    public  String Audio_Url_Hozify;
    public  String Audio_Url_Menshawy;
    public  String Audio_Url_Abdelbaset;
    public  int Image_Url;

    public CoreTable(int id, int FK_FesresId, int ayahId, String contentText, String audio_Url_Sodes, String audio_Url_Hozify, String audio_Url_Menshawy, String audio_Url_Abdelbaset, int image_Url) {
        Id = id;
        this.FK_FesresId = FK_FesresId;
        AyahId = ayahId;
        ContentText = contentText;
        Audio_Url_Sodes = audio_Url_Sodes;
        Audio_Url_Hozify = audio_Url_Hozify;
        Audio_Url_Menshawy = audio_Url_Menshawy;
        Audio_Url_Abdelbaset = audio_Url_Abdelbaset;
        Image_Url = image_Url;
    }

    public CoreTable() {
    }
}
