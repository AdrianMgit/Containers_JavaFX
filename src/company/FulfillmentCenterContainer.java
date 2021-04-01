package company;

import java.io.Serializable;
import java.util.*;



public class FulfillmentCenterContainer implements Serializable {

    private List<FulfillmentCenter> fulfillments=new ArrayList<>();


    public List<FulfillmentCenter> getFulfillments() {
        return fulfillments;
    }

    public boolean addCenter(FulfillmentCenter f){
        if(f!=null) {
            fulfillments.add(f);
            return true;
        }
        else
            return false;
    }

    public boolean removeCenter(String name) {
        for (FulfillmentCenter f:fulfillments) {
            if(f.getWarehouseName().equals(name)) {
                fulfillments.remove(f);
                return true;
            }
        }
        System.out.println("Nie znaleziono magazynu o podanej nazwie");
        return false;
    }



    public void summary() {
        for(FulfillmentCenter f:fulfillments){
            f.summary();
            System.out.println("\n");
        }
    }

    public FulfillmentCenter getCenter(String name){
        for(FulfillmentCenter fulfillmentCenter:fulfillments){
            if(fulfillmentCenter.getWarehouseName().equals(name))
                return fulfillmentCenter;
        }
        return null;
    }


    public boolean sort(){
        if(fulfillments.isEmpty()==false){
            Collections.sort(fulfillments, new Comparator<FulfillmentCenter>() {
                public int compare(FulfillmentCenter o1, FulfillmentCenter o2) {
                    if (o1.getNowCapacity() > o2.getNowCapacity())
                        return 1;
                    else if (o1.getNowCapacity() < o2.getNowCapacity())
                        return -1;
                    return 0;
                }
            });
            return true;
        }
        System.out.println("Lista magazynow jest pusta");
        return false;
    }

    public boolean sellProduct(Item item, int amount) {
        for(FulfillmentCenter fulfillmentCenter:fulfillments){
            if(fulfillmentCenter.getWarehouseName().equals(item.getWarehouseName())){
                if(fulfillmentCenter.getSellingProduct(item,amount)==true)
                    return true;
            }
        }
        return false;
    }


}
