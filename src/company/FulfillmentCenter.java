package company;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class FulfillmentCenter implements Serializable {
    private String warehouseName;
    private String location;
    private List<Item> itemList=new ArrayList<>();
    private double maksCapacity=0;
    private double nowCapacity=0;

    public List<Item> getItemList() {
        return itemList;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setMaksCapacity(double maksCapacity) {
        this.maksCapacity = maksCapacity;
    }

    public void setNowCapacity(double nowCapacity) {
        this.nowCapacity = nowCapacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public double getMaksCapacity() {
        return maksCapacity;
    }

    public double getNowCapacity() {
        return nowCapacity;
    }


    public FulfillmentCenter(String name,String location, double capacity){
        this.maksCapacity=capacity;
        this.warehouseName=name;
        this.location=location;
        nowCapacity=0;
    }

    public FulfillmentCenter(String warehouseName, String location, double maksCapacity, double nowCapacity) {
        this.warehouseName = warehouseName;
        this.location = location;
        this.maksCapacity = maksCapacity;
        this.nowCapacity = nowCapacity;
    }

    public boolean addProduct(Item i){
        // System.out.println(i.getIlosc()*i.getMasa());
        if(nowCapacity+(i.getAmount()*i.getMasa())<maksCapacity){
            boolean juzIstnieje=false;
            for (Item item : itemList) {
                if (i.compareTo(item) == 0) {                      //gdy nazwy sa takie same
                    item.setAmount(item.getAmount() + i.getAmount());
                    nowCapacity= nowCapacity+(i.getAmount()*i.getMasa());
                    return true;
                }
            }
                itemList.add(i);
                i.setWarehouseName(this.warehouseName);
                nowCapacity= nowCapacity+(i.getAmount()*i.getMasa());
                return true;
        }
        else {
            System.err.println("Brak miejsca w magazynie");
            return false;
        }
    }


    public void getProduct(Item i){
        boolean found=false;
        for (Item item:itemList) {
            if(item.compareTo(i)==0) {
                found = true;
                if ((item.getAmount() - 1) == 0)                 //jesli ilosc po usunieciu wynosi 0 to usuwam przedm.z mag.
                    itemList.remove(item);
                else
                    item.setAmount(item.getAmount() - 1);

                nowCapacity=nowCapacity-item.getMasa();
                break;
            }
        }
        if(found==false)
            System.out.println("Nie znaleziono podanego produktu");
    }



    public boolean removeProduct(Item i){
        for (Item item:itemList) {
            if(i.compareTo(item)==0) {          //uzyty komparator zdefiniowany w klasie item,gdy nazwy te same zwraca 0
                itemList.remove(item);
                return true;
            }
        }
            System.out.println("Nie znaleziono podanego produktu");
            return false;

    }


    public List<Item> filterByState(SalesStatus state){
        List<Item> foundList=new ArrayList<>();
        for (Item item:itemList) {
            if(item.getState().equals(state)){
                foundList.add(item);
            }
        }
        return foundList;
    }


    public List<Item> filterByProduct(String name){
        List<Item> foundList=new ArrayList<>();
        for (Item item:itemList) {
            if(item.getNazwa().equals(name)){
                foundList.add(item);
            }
        }
        return foundList;
    }


    public void summary(){
        double zapelnienie = (nowCapacity/maksCapacity)*100;
        System.out.println("Nazwa magazynu: " + warehouseName);
        System.out.println("Miejscowosc: "+location);

        System.out.println("Obecne zapelnienie: "+nowCapacity);

        System.out.println("Procentowe zapelnienie magazynu: " + zapelnienie);
        System.out.println("Przetrzymywane produkty: ");
        for (Item i:itemList){
            i.summary();
        }
    }

    public boolean getSellingProduct(Item item, int amount) {
        for (Item i:itemList) {
            if(i.getNazwa().equals(item.getNazwa())) {
                if ((i.getAmount() - amount) == 0) {
                    i.setStatus(SalesStatus.SOLD);
                    i.setAmount(0);
                    nowCapacity=nowCapacity-(i.getMasa()*amount);
                    return true;
                }
                else if(i.getAmount()-amount>0) {
                    i.setAmount(i.getAmount() - amount);
                    nowCapacity=nowCapacity-(i.getMasa()*amount);
                    return true;
                }
                else if(i.getAmount()-amount<0){
                    return false;
                }
            }
        }
        return false;
    }


    //-------------------------------DO ZAPISU PLIKUI CSV------------------------
    public String toString(String separator) {
        return warehouseName + separator + location + separator +
                maksCapacity + separator + nowCapacity;
    }



}

