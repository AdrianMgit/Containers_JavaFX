package company;


public class DataGenerator {
    public static final DataGenerator INSTANCE = new DataGenerator();
    public Fasada f=new Fasada();

    private DataGenerator() {
        f.addCenter(new FulfillmentCenter("Spozywczy","Lokalizacja1",4000));
        f.addCenter(new FulfillmentCenter("Sportowy","Lokalizacja2",3040));
        f.addCenter(new FulfillmentCenter("Chemiczny","Lokalizacja3",3008.1));
        f.addCenter(new FulfillmentCenter("Ogrodniczy","Lokalizacja4",2000));
        f.addCenter(new FulfillmentCenter("Budowlany","Lokalizacja5",2002.4));

        f.addProduct(0,new Item("Sok", SalesStatus.ORDERED,22.5,23,2));
        f.addProduct(0,new Item("Chleb", SalesStatus.SOLD,58.5,11,1));
        f.addProduct(0,new Item("Jablko", SalesStatus.INFULLFILMENT,12.5,9.99,5));
        f.addProduct(0,new Item("Ryz", SalesStatus.ORDERED,18.5,100.5,9));
        f.addProduct(0,new Item("Ziemniak", SalesStatus.INFULLFILMENT,2.5,24.3,5));

        f.addProduct(1,new Item("Plka", SalesStatus.INFULLFILMENT,22.5,45.89,6));
        f.addProduct(1,new Item("Dres", SalesStatus.SOLD,69.5,78.9,14));
        f.addProduct(1,new Item("Narty", SalesStatus.INFULLFILMENT,12.5,33,18));
        f.addProduct(1,new Item("Buty", SalesStatus.ORDERED,2.5,1,8));
        f.addProduct(1,new Item("Czapka", SalesStatus.SOLD,22.5,25,6));

        f.addProduct(2,new Item("Proszek", SalesStatus.INFULLFILMENT,22.5,14,8));
        f.addProduct(2,new Item("Plyn", SalesStatus.SOLD,32.5,15,2));
        f.addProduct(2,new Item("Denaturat", SalesStatus.SOLD,12.5,5,1));
        f.addProduct(2,new Item("Nawoz", SalesStatus.INFULLFILMENT,2.5,90,5));

        f.addProduct(3,new Item("Lopata", SalesStatus.INFULLFILMENT,12.5,89,2));
        f.addProduct(3,new Item("Ogrodzenie", SalesStatus.SOLD,50.5,96,1));

        f.addProduct(4,new Item("Cement", SalesStatus.SOLD,18.5,56.68,1));
        f.addProduct(4,new Item("Farba", SalesStatus.INFULLFILMENT,89,43,2));
        f.addProduct(4,new Item("Kilof", SalesStatus.INFULLFILMENT,19,35,6));
        f.addProduct(4,new Item("Piasek", SalesStatus.INFULLFILMENT,49,99.99,4));


    }
}
