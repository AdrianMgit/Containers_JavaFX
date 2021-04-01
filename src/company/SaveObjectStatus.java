package company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveObjectStatus {




    static public FulfillmentCenter readMagazineCSV(String magazineName) {

        FulfillmentCenter center =null;

        Path sciezkaDoPliku = Paths.get(magazineName);
        // Lista będzie przechowywała kolejne linie odczytane z pliku jako String
        ArrayList<String> odczyt = new ArrayList();
        try {
            // Wczytanie wszystkich linii do listy
            odczyt = (ArrayList) Files.readAllLines(sciezkaDoPliku);
            String linMag= odczyt.get(0);          //linia w ktorej sa informacje tylko o magazynie/bez produktow
            String[] podzielone=linMag.split(";");
            center=new FulfillmentCenter(podzielone[0],podzielone[1],Double.parseDouble(podzielone[2]),Double.parseDouble(podzielone[3]));
            odczyt.remove(0);
            for (String linia : odczyt) {
                // Dzielenie na poszczególne pola (oodzielone przecinkami)
                String[] l = linia.split(",");
                // Tworzenie obiektu i wywołanie konstruktora, który przyjmuje
                // jako argumenty dane
                Item item = new Item(l[0], SalesStatus.valueOf(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3]), Integer.parseInt(l[4]), l[5]);
                center.getItemList().add(item);
            }
        } catch (IOException ex) {
            System.out.println("Brak pliku!");
        }

        return center;
    }



    static public void saveMagazineCSV(FulfillmentCenter center){


        ArrayList out = new ArrayList();
            out.add(center.toString(";"));
        for (Item item : center.getItemList()) {
            out.add(item.toString(","));
        }
        try {
            String fileName=center.getWarehouseName();
            Files.write(Paths.get(fileName), out);
        } catch (IOException ex) {
            System.out.println("Niestety, nie mogę utworzyć pliku!");
        }
    }




    static public User readUserProductsCSV() {

        User user = new User();

        Path sciezkaDoPliku = Paths.get("UserCSV");
        // Lista będzie przechowywała kolejne linie odczytane z pliku jako String
        ArrayList<String> odczyt = new ArrayList();
        try {
            // Wczytanie wszystkich linii do listy
            odczyt = (ArrayList) Files.readAllLines(sciezkaDoPliku);
            for (String linia : odczyt) {
                // Dzielenie na poszczególne pola (oodzielone przecinkami)
                String[] l = linia.split(",");
                // Tworzenie obiektu i wywołanie konstruktora, który przyjmuje
                // jako argumenty dane
                Item item = new Item(l[0], SalesStatus.valueOf(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3]), Integer.parseInt(l[4]), l[5]);
                user.purchasedProducts.add(item);

            }
        } catch (IOException ex) {
            System.out.println("Brak pliku!");
        }

            return user;
    }

    static public void saveUserProductsCSV(User user){


        ArrayList out = new ArrayList();
        for (Item item : user.getPurchasedProducts()) {
            out.add(item.toString(","));
        }
        try {
            Files.write(Paths.get("UserCSV"), out);
        } catch (IOException ex) {
            System.out.println("Niestety, nie mogę utworzyć pliku!");
        }
    }



    static public FulfillmentCenterContainer serialReadContainer(){

        FulfillmentCenterContainer container=null;

        try
        {
            FileInputStream file = new FileInputStream("ContainerState.bin");
            ObjectInputStream in = new ObjectInputStream(file);

            container = (FulfillmentCenterContainer) in.readObject();

            in.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
        return container;

    }


    static public void serialSaveContainer(FulfillmentCenterContainer container) {
        try
        {
            FileOutputStream file = new FileOutputStream("ContainerState.bin");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(container);

            out.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
    }

        static public void serialSaveUser(User user){
        try
        {
            FileOutputStream file = new FileOutputStream("UserState.bin");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(user);

            out.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

    }


    static public User serialReadUser(){

        User user=null;

        try
        {
            FileInputStream file = new FileInputStream("UserState.bin");
            ObjectInputStream in = new ObjectInputStream(file);

            user = (User)in.readObject();

            in.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
        return user;

    }

}
