package company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String login;
    private String password;
    List<Item> purchasedProducts;

    public User(){
        purchasedProducts=new ArrayList<>();

    }

    public User(String login,String password){
        purchasedProducts=new ArrayList<>();
        this.login=login;
        this.password=password;
    }

    public boolean buyProduct(Item item,int amount,FulfillmentCenterContainer container){
        if(container.sellProduct(item,amount)==true){
            purchasedProducts.add(new Item(item.getNazwa(),SalesStatus.ORDERED,item.getMasa(),item.getPrice(),amount,item.getWarehouseName()));
            return true;
        }
        return false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Item> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<Item> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public void ShowPurchasedProducts(){
        for(Item item:purchasedProducts){
            item.summary();
        }
    }
}
