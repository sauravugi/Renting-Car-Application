import java.io.*;
import java.util.*;

public class Car {
    private String carId, carName, carModel, regNo;
    private int seat, rentPerDay;
    private String carOwnerId;

    public Car(String carId, String carName, String carModel, String regNo, int seat, int rentPerHour, String carOwnerId) {
        this.carId = carId;
        this.carName = carName;
        this.carModel = carModel;
        this.regNo = regNo;
        this.seat = seat;
        this.rentPerDay = rentPerHour;
        this.carOwnerId = carOwnerId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(carId).append(",").append(carName).append(",").append(carModel).append(",").append(regNo)
                .append(",").append(seat).append(",").append(rentPerDay).append(",").append(carOwnerId);
        return sb.toString();
    }


    public Car() {
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(int rentPerHour) {
        this.rentPerDay = rentPerHour;
    }

    public String getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(String carOwnerId) {
        this.carOwnerId = carOwnerId;
    }

    public static List<Car> getAllCars(){
        List<Car> cars = new ArrayList<>();
        File file = new File("src/data/CarsData.txt");

        if(file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    cars.add(fromString(line));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cars;
        }
        return cars;
    }

    public static Car fromString(String line) {
        String[] parts = line.split(",");
        String carId = parts[0];
        String carName = parts[1];
        String carModel = parts[2];
        String regNo = parts[3];
        int seat = Integer.parseInt(parts[4]);
        int rentPerDay = Integer.parseInt(parts[5]);
        String carOwnerId = parts[6];

        return new Car(carId, carName, carModel, regNo, seat, rentPerDay, carOwnerId);
    }

    public static void addCar(String ownerId, String name, String model, String regNo, int seat, int rentPerDay){
        List<Car> cars = getAllCars();
        if(validateOwnerId(ownerId) && (!name.isEmpty()) && (!regNo.isEmpty()) && (rentPerDay > 0)){
            Set<String> existingIDs = new HashSet<>();
            for(Car car : cars ){
                existingIDs.add(car.getCarId());
            }
            String id = generateUniqueCID(existingIDs);
            cars.add(new Car(id,name,model,regNo,seat,rentPerDay,ownerId));
            File file = new File("src/data/CarsData.txt");
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
                for (Car c : cars) {
                    writer.write(c.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Data is not valid.");
        }

    }

    private static boolean validateOwnerId(String id) {
        List<CarOwner> owners = CarOwner.getAllOwners();
        for(CarOwner owner : owners){
            if(owner.getOwnerId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static String generateUniqueCID(Set<String> existingIDs) {
        Random random = new Random();
        String newID;

        do {
            int randomNumber = 1000 + random.nextInt(9001);
            newID = "CID" + randomNumber;
        } while (existingIDs.contains(newID));

        return newID;
    }

}

