import java.io.*;
import java.util.*;

public class CarOwner{
    private String ownerId,ownerName,ownerAddress;
    private int balance;

    public CarOwner(String ownerId, String ownerName, String ownerAddress, int balance) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.balance = balance;
    }

    public CarOwner() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ownerId).append(",").append(ownerName).append(",").append(ownerAddress).append(",").append(balance);
        return sb.toString();
    }

    public static CarOwner fromString(String line) {
        String[] parts = line.split(",");
        String oId = parts[0];
        String oName = parts[1];
        String oAdd = parts[2];
        int oBill = Integer.parseInt(parts[3]);

        return new CarOwner(oId, oName, oAdd, oBill);
    }

    public static List<CarOwner> getAllOwners(){
        List<CarOwner> owners = new ArrayList<>();
        File file = new File("src/data/OwnerData.txt");
        if(file.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    owners.add(fromString(line));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return owners;
        }
        return owners;
    }

    public static void addOwner(String name, String add, int bal){
        List<CarOwner> owners = getAllOwners();
        Set<String> existingIDs = new HashSet<>();
        for(CarOwner owner : owners ){
            existingIDs.add(owner.getOwnerId());
        }
        String id = generateUniqueOID(existingIDs);
        CarOwner owner = new CarOwner(id,name,add,bal);
        File file = new File("src/data/OwnerData.txt");
        owners.add(owner);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(CarOwner carOwner:owners){
                writer.write(carOwner.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String generateUniqueOID(Set<String> existingIDs) {
        Random random = new Random();
        String newID;

        do {
            int randomNumber = 1000 + random.nextInt(9001);
            newID = "OID" + randomNumber;
        } while (existingIDs.contains(newID));

        return newID;
    }

}
