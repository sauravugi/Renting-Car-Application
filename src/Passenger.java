import java.io.*;
import java.util.*;

public class Passenger{
    private String passengerId,passengerName,passengerAddress;
    private int bill;

    public Passenger(String passengerId, String passengerName, String passengerAddress, int bill) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.passengerAddress = passengerAddress;
        this.bill = bill;
    }

    public Passenger() {
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerAddress() {
        return passengerAddress;
    }

    public void setPassengerAddress(String passengerAddress) {
        this.passengerAddress = passengerAddress;
    }

    public int getBill() {
        return this.bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(passengerId).append(",").append(passengerName).append(",").append(passengerAddress).append(",").append(bill);
        return sb.toString();
    }

    public static Passenger fromString(String line) {
        String[] parts = line.split(",");
        String pId = parts[0];
        String pName = parts[1];
        String pAdd = parts[2];
        int pBill = Integer.parseInt(parts[3]);

        return new Passenger(pId, pName, pAdd, pBill);
    }

    public static List<Passenger> getAllPassengers(){
        List<Passenger> passengers = new ArrayList<>();
        File file = new File("src/data/PassengerData.txt");
        if(file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    passengers.add(fromString(line));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return passengers;
        }
        return passengers;
    }

    public static void addPassenger(String name, String add, int bal){
        List<Passenger> passengers = getAllPassengers();
        Set<String> existingIDs = new HashSet<>();
        for(Passenger p : passengers ){
            existingIDs.add(p.getPassengerId());
        }
        String id = generateUniquePID(existingIDs);
        passengers.add(new Passenger(id,name,add,bal));
        File file = new File("src/data/PassengerData.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Passenger p : passengers) {
                writer.write(p.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String generateUniquePID(Set<String> existingIDs) {
        Random random = new Random();
        String newID;

        do {
            int randomNumber = 1000 + random.nextInt(9001);
            newID = "PID" + randomNumber;
        } while (existingIDs.contains(newID));

        return newID;
    }
}
