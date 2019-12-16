package com.example.newstation.ui.main;

import com.example.newstation.R;

import java.util.ArrayList;

public class Items {
    private String productName;
    private String productDescription;
    private int imageID;

    public Items() {
    }

    public Items(String productName, String productDescription) {

        this.productName = productName;
        this.productDescription = productDescription;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public static ArrayList<Items> getData() {
        ArrayList<Items> productList = new ArrayList<Items>();
        int productImages[] = {};
        String[] productNames = {"Geleceği Yazanlar", "Paycell", "Tv+", "Dergilik", "Bip", "GNC", "Hesabım", "Sim", "LifeBox", "Merhaba Umut", "Yaani", "Hayal Ortağım", "Goller Cepte", "Piri"};

        for (int i = 0; i < productImages.length; i++) {
            Items temp = new Items();
            temp.setImageID(productImages[i]);
            temp.setProductName(productNames[i]);
            temp.setProductDescription("Turkcell");

            productList.add(temp);

        }


        return productList;


    }

}
