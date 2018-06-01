package com.daralmathour.altaefhessan.AyatInfo;

/**
 * Created by codelab on 4/27/2018.
 */

public class AyahInformation {

    public int ayahNumber;
    public int soraNumber;
    public String ayahContent;
    public int xStart;
    public int yStart;
    public int xEnd;
    public int yEnd;



    public AyahInformation(int ayahNumber, int soraNumber, String ayahContent, int xStart, int yStart, int xEnd, int yEnd) {
        this.ayahNumber = ayahNumber;
        this.soraNumber = soraNumber;
        this.ayahContent = ayahContent;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    public int getAyahNumber() {
        return ayahNumber;
    }

    public void setAyahNumber(int ayahNumber) {
        this.ayahNumber = ayahNumber;
    }

    public int getSoraNumber() {
        return soraNumber;
    }

    public void setSoraNumber(int soraNumber) {
        this.soraNumber = soraNumber;
    }

    public String getAyahContent() {
        return ayahContent;
    }

    public void setAyahContent(String ayahContent) {
        this.ayahContent = ayahContent;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }
}
