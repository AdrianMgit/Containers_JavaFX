package company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Item implements Comparable<Item>, Serializable {
    private String nazwa;
    private transient SalesStatus status;
    private double masa;
    private int amount;
    private double price;
    private String warehouseName;               //ustawiane podzas dodawania produktu do magazynu

    public Item(String nazwa, SalesStatus status, double masa,double price,int amount) {
        this.nazwa = nazwa;
        this.status = status;
        this.masa = masa;
        this.price=price;
        this.amount=amount;
        this.warehouseName="";
    }

        //konstruktor wykorzystywany w funkcji kupna produktu przez uzytwkownika
    public Item(String nazwa, SalesStatus status, double masa,double price,int amount,String warehouseName) {
        this.nazwa = nazwa;
        this.status = status;
        this.masa = masa;
        this.price=price;
        this.amount=amount;
        this.warehouseName=warehouseName;
    }

    public void summary(){
        System.out.println("Nazwa - "+nazwa+"\nStan - "+status.name()+"\nMasa - "+masa+"\nPrice - "+price+"\nWarehouseName - "+warehouseName+"\nAmount - "+amount);
    }

    @Override
    public int compareTo(Item o) {

        int nazwaa= this.nazwa.compareTo(o.nazwa);
        int statee=this.status.compareTo(o.status);
        if(nazwaa==0 && statee==0 && this.masa==o.masa)
            return 0;
        else return 1;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setStatus(SalesStatus state) {
        this.status = state;
    }

    public void setMasa(double masa) {
        this.masa = masa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public SalesStatus getState() {
        return status;
    }

    public double getMasa() {
        return masa;
    }

    public String toString(){
        return "Nazwa: "+nazwa+",Stan: "+status+",Masa: "+masa;
    }


    //-------------DO SERIALIZACJI,PRZY WCZYTYWANIU USTWIA STAN PRZEDMIOTU JAKO NOWY--------------
    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        this.status=SalesStatus.NEW;
    }

    //-------------------------------DO ZAPISU PLIKU CSV------------------------
    public String toString(String separator) {
        return nazwa + separator + status + separator +
                masa + separator + price + separator + amount+ separator + warehouseName;
    }


}
