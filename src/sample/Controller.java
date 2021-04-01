package sample;


import company.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.util.Optional;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //----W TABELI SA TYLKO TE PRODUKTY Z MAGAZYNOW KTORYCH STAN WYNOSI INFULFILLMENT-BO TYLKO TE MOZNA KUPIC



    //--------------------------------------------------------  DANE
    //static Fasada fasada=DataGenerator.INSTANCE.f;        //gdy chce miec juz jakies gotowe dane
    static  Fasada fasada=new Fasada();
    static User user=new User();
    ObservableList<String> warehouseNames= FXCollections.observableArrayList(fasada.getCentersName());
    ObservableList<Item> purchased= FXCollections.observableArrayList(user.getPurchasedProducts());
    ObservableList<Item> productsMagazine= FXCollections.observableArrayList();




    //-----------------------------------------------------  KOMPONENTY

    //TABELA PRODUKTOW W MAGAZYNACH
    @FXML private TableView<Item> productCentTable;
    @FXML private TableColumn<Item,String> productNameColumn;
    @FXML private TableColumn<Item,Integer> productAmountColumn;
    @FXML private TableColumn<Item,Double> productPriceColumn;
    @FXML private TableColumn<Item,String> productCenterNameColumn;
    //TABELA PRODUKTOW KUPIONYCH PRZEZ UZYTKOWNIKA
    @FXML private TableView<Item> purchasedProductsTable;
    @FXML private TableColumn<Item,String> productNameColumnn;
    @FXML private TableColumn<Item,Integer> productAmountColumnn;
    @FXML private TableColumn<Item,Double> productPriceColumnn;
    @FXML private TableColumn<Item,String> productCenterNameColumnn;
    //COMBOBOX WYBOR MAGAZYNU
    @FXML private ComboBox centerChoiceBox;
    //COMBOBOX SORTOWANIE LISTY PRODUKTOW
    @FXML private ComboBox productsSortBox;
    //TEXTFIELD SZUKANIE PRODUKTU PO NAZWIE
    @FXML private TextField searchProductField;
    //LABEL WYPISUJE INFORMACJE O ZAZNACZONYM PRODUKCIE
    @FXML private Label chosenProductLabel;
    String name="Name:  ";
    String amount="Amount:  ";
    String price="Price:  ";
    String warehouseName="Warehouse name:  ";
    //TEXTFIELD PODANIE ILOSCI KUPOWANEGO PRODUKTU
    @FXML private TextField quantityProductField;
    //BUTTON KUPNO
    @FXML private Button buyButton;
    //BUTTON SERIALIZACJA UZYTKOWNIKA
    @FXML private Button serialUserButton;
    //BUTTON DESERIALIZACJA UZYTKOWNIKA
    @FXML private Button deserialUserButton;
    //BUTTON SERIALIZACJA MAGAZYNU GLOWNEGO
    @FXML private Button serialContainerButton;
    //BUTTON DESERIALIZACJA MAGAZYNU GLOWNEGO
    @FXML private Button deserialContainerButton;
    //BUTTON CSV SAVE UZYTKOWNIKA
    @FXML private Button csvSaveUserButton;
    //BUTTON CSV READ UZYTKOWNIKA
    @FXML private Button csvReadUserButton;
    //BUTTON CSV SAVE UZYTKOWNIKA
    @FXML private Button csvSaveMagazineButton;
    //BUTTON CSV READ UZYTKOWNIKA
    @FXML private Button csvReadMagazineButton;

    //-----------------------------------------  METODY OBSLUGUJACE KOMPONENTY






    //------------------------WARTOSCI POCZATKOWE
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(deserializationContainer()==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Read Error");
            alert.setHeaderText("");
            alert.setContentText("Can not read magazines file. You need to complete the data");
            alert.showAndWait();
        }


        //TABELA PRODUKTOW
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("nazwa"));      //musza byc takie same jak pola w Item
        productAmountColumn.setCellValueFactory(new PropertyValueFactory<Item,Integer>("amount"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Item,Double>("price"));
        productCenterNameColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("warehouseName"));

        productsMagazine=FXCollections.observableArrayList(fasada.getProducts("Default"));
        productCentTable.setItems(productsMagazine);          //DEFAULT TO WSZYSTKIE MAGAZYNY


        //Dodanie Tooltipa
        for (TableColumn<Item, ?> column : productCentTable.getColumns()) {
            addTooltip(column);
        }


        //TABELA KUPIONYCH
        productNameColumnn.setCellValueFactory(new PropertyValueFactory<Item,String>("nazwa"));      //musza byc takie same jak pola w Item
        productAmountColumnn.setCellValueFactory(new PropertyValueFactory<Item,Integer>("amount"));
        productPriceColumnn.setCellValueFactory(new PropertyValueFactory<Item,Double>("price"));
        productCenterNameColumnn.setCellValueFactory(new PropertyValueFactory<Item,String>("warehouseName"));

        purchasedProductsTable.setItems(purchased);


        //Dodanie Tooltipa
        for (TableColumn<Item, ?> column : purchasedProductsTable.getColumns()) {
            addTooltip(column);
        }

        //COMBOBOX WYBORU MAGAZYNU
        warehouseNames.add("Default");                  //DO NAZW DOSTEPNYCH MAGAZYNOW DODAJE DEFAULT
        centerChoiceBox.setValue("Default");            //WARTOSC POCZATKOWA
        centerChoiceBox.setItems(warehouseNames);

        //COMBOBOX SORTOWANIA
        productsSortBox.getItems().addAll("Name","Price","Amount");
        productsSortBox.setValue("Name");

        //LABEL ZAZNACZONEGO PRODUKTU
        chosenProductLabel.setText(name+"\n"+amount+"\n"+price+"\n"+warehouseName+"\n\n");

    }



    //--------CSV SAVE UZYTKOWNIKA
    public void csvReadMagazine(){

        //-------------------OKNO DO WPROWADZANIA NAZWY MAGAZYNU---------------
        TextInputDialog dialog = new TextInputDialog("Center name");
        dialog.initStyle(StageStyle.DECORATED);
        dialog.setTitle("Confirm");
        dialog.setHeaderText("Enter warehouse name you want to read");
        dialog.setGraphic(null);
        Optional<String> result;
        result=dialog.showAndWait();
        if (result.isPresent()) {
            if(SaveObjectStatus.readMagazineCSV(result.get())!=null) {
                fasada.updateCenter(SaveObjectStatus.readMagazineCSV(result.get()));
                productsMagazine.retainAll();           //inaczej amount sie nie zmienia
                productsMagazine=FXCollections.observableArrayList(fasada.getProducts("Default"));
                productCentTable.setItems(productsMagazine);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.show();
                alert.setHeaderText("Wrong warehouse name");
            }
        }

    }




    //--------CSV SAVE WYBRANEGO MAGAZYNU
    public void csvSaveMagazine(){

        //-------------------OKNO DO WPROWADZANIA NAZWY MAGAZYNU---------------
        TextInputDialog dialog = new TextInputDialog("Center name");
        dialog.initStyle(StageStyle.DECORATED);
        dialog.setTitle("Confirm");
        dialog.setHeaderText("Enter warehouse name you want to save");
        dialog.setGraphic(null);
        Optional<String> result;
        result=dialog.showAndWait();
        if (result.isPresent()) {
            if(fasada.getCenter(result.get())!=null)
                SaveObjectStatus.saveMagazineCSV(fasada.getCenter(result.get()));
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.show();
               alert.setHeaderText("Wrong warehouse name");
            }
        }

    }



    //--------CSV SAVE UZYTKOWNIKA
    public void csvReadUser(){

        user=SaveObjectStatus.readUserProductsCSV();
        purchasedProductsTable.setItems(FXCollections.observableArrayList(user.getPurchasedProducts()));

    }

    //--------CSV SAVE UZYTKOWNIKA
    public void csvSaveUser(){
        SaveObjectStatus.saveUserProductsCSV(user);
    }


    //-----SERIALIZACJA UZYTKOWNIKA
    public void serializationContainer(){
        SaveObjectStatus.serialSaveContainer(fasada.getContainer());
    }

    //-----DESERIALIZACJA UZYTKOWNIKA----WCZYTANIE Z PLIKU
    public boolean deserializationContainer(){
        if(SaveObjectStatus.serialReadContainer()==null)
            return false;
        fasada.setContainer(SaveObjectStatus.serialReadContainer());
        productsMagazine.retainAll();           //inaczej amount sie nie zmienia
        productsMagazine=FXCollections.observableArrayList(fasada.getProducts("Default"));
        productCentTable.setItems(productsMagazine);
        return true;

    }


    //-----SERIALIZACJA UZYTKOWNIKA
    public void serializationUser(){
        SaveObjectStatus.serialSaveUser(user);
    }

    //-----DESERIALIZACJA UZYTKOWNIKA----WCZYTANIE Z PLIKU
    public void deserializationUser(){
        user=SaveObjectStatus.serialReadUser();
        purchasedProductsTable.setItems(FXCollections.observableArrayList(user.getPurchasedProducts()));
        //user.getPurchasedProducts().get(0).summary();

    }




    //----PRZYCISK KUPNA
    public void buyProduct() {

        Item selectedItem = productCentTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                int productAmount = Integer.parseInt(quantityProductField.getText());
                if (productAmount < 0)
                    ExceptonHandler.show("Ilosc produktu musi byc wieksza od 0");
                else {
                    if (user.buyProduct(selectedItem, productAmount, fasada.getContainer()) == false)
                        ExceptonHandler.show("W magazynie nie ma wystarczjacej ilosci produktu");
                    else {
                        productsMagazine.retainAll();           //inaczej amount sie nie zmienia
                        productsMagazine=FXCollections.observableArrayList(fasada.getProducts(centerChoiceBox.getValue().toString()));
                        productCentTable.setItems(productsMagazine);
                        purchasedProductsTable.setItems(FXCollections.observableArrayList(user.getPurchasedProducts()));
                        chosenProductLabel.setText(name + "\n" + amount + "\n" + price + "\n" + warehouseName + "\n\n");   //po retainAll nie mam zaznaczonego eleementu dlatego zeruje
                    }
                }
                } catch(NumberFormatException exceptionDouble){
                    ExceptonHandler.show("Podano błędną ilość produktu");
                }

        }
        quantityProductField.clear();
    }


    //-------------------WYSWITLENIE ZAZNACZONEGO PRODUKTU W TABELI
    public void chosenProductList(){
    Item selectedItem=productCentTable.getSelectionModel().getSelectedItem();
    if(selectedItem!=null) {
        String chosenName = name + selectedItem.getNazwa() + "\n";
        String chosenAmount = amount + selectedItem.getAmount() + "\n";
        String chosenPrice = price + selectedItem.getPrice() + "\n";
        String chosenWarehouseName = warehouseName + selectedItem.getWarehouseName() + "\n\n";

        chosenProductLabel.setText(chosenName + chosenAmount + chosenPrice + chosenWarehouseName);
    }
    }


    //---------------------WYBOR MAGAZYNU W COMBOBOXIE
    public void choseWarehouse(){
        productsMagazine=FXCollections.observableArrayList(fasada.getProducts(centerChoiceBox.getValue().toString()));
        productCentTable.setItems(productsMagazine);
    }


    //---------------------SZUKANIE PRODUKTU PO NAZWIE
    public void searchProduct(){
        if(searchProductField.getText().equals("")) {                    //GDY POLE PUSTE TO WYSWIETLA WSZYSTKIE
            productsMagazine = FXCollections.observableArrayList(fasada.getProducts("Default"));
            productCentTable.setItems(productsMagazine);
        }
        else {
            ObservableList<Item> list = FXCollections.observableArrayList();
            for (Item item : productCentTable.getItems()) {
                if (item.getNazwa().equals(searchProductField.getText())) {
                    list.add(item);
                }
            }
            productCentTable.setItems(list);
        }
    }


    //--------------------COMBOBOX SORTOWANIA PRODUKTOW
    public void sortProducts(){
        switch(productsSortBox.getValue().toString()){
            case "Name":
                productCentTable.getSortOrder().add(productNameColumn);
                productCentTable.sort();
                productCentTable.getSortOrder().remove(productNameColumn);
                break;
            case "Amount":
                productCentTable.getSortOrder().add(productAmountColumn);
                productCentTable.sort();
                productCentTable.getSortOrder().remove(productAmountColumn);
                break;
            case "Price":
                productCentTable.getSortOrder().add(productPriceColumn);
                productCentTable.sort();
                productCentTable.getSortOrder().remove(productPriceColumn);
                break;
        }
    }


    // -----------------------funkcja dodajca toolTipa do kazdej kolumny
    private <T> void addTooltip(TableColumn<Item,T> column){
        column.setCellFactory(new Callback<TableColumn<Item, T>, TableCell<Item, T>>() {
            @Override
            public TableCell<Item, T> call(TableColumn<Item, T> itemStringTableColumn) {
                return new TableCell<Item, T>() {
                    @Override
                    public void updateItem(T t, boolean empty) {
                        super.updateItem(t, empty);
                        if (t == null) {
                            setTooltip(null);
                            setText(null);
                        } else {
                            Item rowItem = getTableView().getItems().get(getTableRow().getIndex());
                            setTooltip(new Tooltip(getMagazineInformation(rowItem.getWarehouseName())));
                            setText(t.toString());
                        }
                    }
                };
            }

        });
  }


  //---------------------ETYKIETA DO TOOLTIPA
  private String getMagazineInformation(String magazineName){
      FulfillmentCenter center=fasada.getCenter(magazineName);
      String label;
      label="Magazine information: \n";
      label=label+"WarehouseName: "+center.getWarehouseName()+"\n";
      label=label+"Location: "+center.getLocation()+"\n";
      label=label+"MaksCapacity: "+center.getMaksCapacity()+"\n";
      label=label+"NowCapacity: "+center.getNowCapacity()+"\n";

      return label;
  }

}
