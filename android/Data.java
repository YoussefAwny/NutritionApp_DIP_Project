package com.example.spoon;

public class Data {
    private int id;
    private int day;
    private int month;
    private int year;
    private int energy;
    private int protein;
    private int carbs;
    private int fat;
    private int fibre;
    private int sodium;

    public Data(int id,int day, int month, int year, int energy, int protein, int carbs, int fat, int fibre, int sodium) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.energy = energy;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fibre = fibre;
        this.sodium = sodium;
    }
    public Data(int day, int month, int year, int energy, int protein, int carbs, int fat, int fibre, int sodium) {

        this.day = day;
        this.month = month;
        this.year = year;
        this.energy = energy;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fibre = fibre;
        this.sodium = sodium;
    }
    public Data(int id,int day, int month, int year) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public Data()
    {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setFibre(int fibre) {
        this.fibre = fibre;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFat() {
        return fat;
    }

    public int getFibre() {
        return fibre;
    }

    public int getSodium() {
        return sodium;
    }
}
