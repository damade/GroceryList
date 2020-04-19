package com.example.android.grocerylist.Database.RoomModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grocery_table")
public class Grocery {
    @PrimaryKey(autoGenerate = true)
    private int nid;

    @ColumnInfo(name = "grocery_title")
    private String Grocery_Title;

    @ColumnInfo(name = "grocery_price")
    private int Grocery_Price;

    @ColumnInfo(name = "grocery_quantity")
    private int Grocery_Quantity;

    public Grocery(String Grocery_Title, int Grocery_Price , int Grocery_Quantity) {
        this.Grocery_Title = Grocery_Title;
        this.Grocery_Price = Grocery_Price ;
        this.Grocery_Quantity = Grocery_Quantity;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setGrocery_Title(String grocery_Title) {
        this.Grocery_Title = grocery_Title;
    }

    public void setGrocery_Price(int grocery_Price) {
        this.Grocery_Price = grocery_Price;
    }

    public void setGrocery_Quantity(int grocery_Quantity) {
        this.Grocery_Quantity = grocery_Quantity;
    }

    public String getGrocery_Title() {
        return Grocery_Title;
    }

    public int getGrocery_Price() {
        return Grocery_Price;
    }

    public int getGrocery_Quantity() {
        return Grocery_Quantity;
    }
}
