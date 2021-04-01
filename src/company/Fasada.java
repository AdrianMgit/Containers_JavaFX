package company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Fasada {
    private FulfillmentCenterContainer container=new FulfillmentCenterContainer();




    public void setContainer(FulfillmentCenterContainer container){
        this.container=container;
    }

    public FulfillmentCenterContainer getContainer() {
        return container;
    }

    public int getamountofCenters(){
        return getFulfillmentsList().size();
    }

    public List<Item> getItemList(int centerIndex){
        return getCenter(centerIndex).getItemList();
    }

    public List<FulfillmentCenter> getFulfillmentsList(){
        return container.getFulfillments();
    }

    public boolean addCenter(FulfillmentCenter fulfillmentCenter){
        return container.addCenter(fulfillmentCenter);
    }

    public boolean addProduct(int centerIndex,Item item) {
        return getCenter(centerIndex).addProduct(item);
    }

    private FulfillmentCenter getCenter(int index){
        return container.getFulfillments().get(index);
    }
    public FulfillmentCenter getCenter(String CenterName){
        return container.getCenter(CenterName);
    }
    public List<Item> getItemList(String CenterName){
        return getCenter(CenterName).getItemList();
    }
    public List<String> getCentersName(){
        List<String> names=new ArrayList<>();
        for(FulfillmentCenter fulfillmentCenter:container.getFulfillments()){
            names.add(fulfillmentCenter.getWarehouseName());
        }
        return names;
    }

    public void showAllProducts(){
        for(FulfillmentCenter fulfillmentCenter:container.getFulfillments())
            fulfillmentCenter.summary();
    }

    //Zwraca liste produktow o statusie INFULLfilment z magazynu o podanym indeksie
    public List<Item> getProducts(String centerName){
        List<Item>productsMagazine=new ArrayList<>();
        productsMagazine= FXCollections.observableArrayList();
        List<Item> list;
        SalesStatus wmagazynie=SalesStatus.INFULLFILMENT;
        SalesStatus nowy=SalesStatus.NEW;

        if(centerName.equals("Default")) {
            int centerAmount = getamountofCenters();
            for (int i = 0; i < centerAmount; i++) {
                list = getItemList(i);
                for (Item item : list) {
                   // if (item.getState()==wmagazynie || item.getState()==nowy)             //trzeba????? po wczytwaniu i tak wszystkie produkty maja status new
                        if(item.getAmount()>0)
                        productsMagazine.add(item);
                }
            }
        }
        else{
            list = getItemList(centerName);
            for (Item item : list) {
                if(item.getAmount()>0)
                    productsMagazine.add(item);
            }
        }
        return productsMagazine;
    }

    public void updateCenter(FulfillmentCenter fulfillmentCenter){
        //gdy magazyn juz jest na liscie to go usuwam
        container.removeCenter(fulfillmentCenter.getWarehouseName());
        //czy jest czy nie to dodaje
        addCenter(fulfillmentCenter);
    }

}
