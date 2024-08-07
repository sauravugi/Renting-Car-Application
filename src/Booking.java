import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Booking {
    private String bookingId,passengerId,carId;
    private int bookingBill;
    private LocalDate bookingDate,returnDate;

    public Booking(String bookingId, String passengerId, String carId, LocalDate bookingDateTime, LocalDate returnDateTime, int bookingBill) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.carId = carId;
        this.bookingDate = bookingDateTime;
        this.returnDate = returnDateTime;
        this.bookingBill = bookingBill;
    }

    public Booking() {
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDateTime) {
        this.bookingDate = bookingDateTime;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDateTime) {
        this.returnDate = returnDateTime;
    }

    public int getBookingBill() {
        return bookingBill;
    }

    public void setBookingBill(int bookingBill) {
        this.bookingBill = bookingBill;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(bookingId).append(",").append(passengerId).append(",").append(carId).append(",").append(bookingDate)
                .append(",").append(returnDate).append(",").append(bookingBill);
        return sb.toString();
    }

    public static Booking fromString(String line) {
        String[] parts = line.split(",");
        String bId = parts[0];
        String pId = parts[1];
        String cId = parts[2];
        LocalDate bDT = LocalDate.parse(parts[3]);
        LocalDate rDT = LocalDate.parse(parts[4]);
        int bBill = Integer.parseInt(parts[5]);

        return new Booking(bId, pId, cId, bDT, rDT, bBill);
    }

    public static List<Booking> getAllBookings(){
        List<Booking> bookings = new ArrayList<>();
        File file = new File("src/data/BookingData.txt");
        if(file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    bookings.add(fromString(line));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bookings;
        }
        return bookings;
    }

    public static Booking bookingCar(String passengerId,String returnDate){
        List<Passenger> passengers = Passenger.getAllPassengers();
        List<Booking> bookings = Booking.getAllBookings();
        Passenger p = null;
        List<Car> cars = Car.getAllCars();
        for(Passenger passenger : passengers){
            if(passenger.getPassengerId().equals(passengerId)){
                p = passenger;
            }
        }

        String carId = "";
        Map<String,LocalDate> map = new HashMap<>();
        Set<String> existingIDs = new HashSet<>();
        Set<String> carIDs = new HashSet<>();
        if(bookings.size() < cars.size()){
            for(Booking booking : bookings){
                carIDs.add(booking.getCarId());
            }
            for(Car car:cars){
                if(!carIDs.contains(car.getCarId())){
                   carId = car.getCarId();
                   break;
                }
            }
        }else {

            for(Booking booking : bookings){
                existingIDs.add(booking.getBookingId());
                String id = booking.getCarId();
                LocalDate date = booking.getReturnDate();
                if(map.containsKey(id)){
                    LocalDate existingDate = map.get(id);
                    if(date.isAfter(existingDate)) {
                        map.put(id, date);
                    }
                }else {
                    map.put(id,date);
                }
            }

            for(Booking booking : bookings){
                String id = booking.getCarId();
                if(map.get(id).isBefore(LocalDate.now())){
                    carId = id;
                    break;
                }
            }
        }
        LocalDate rdate = LocalDate.parse(returnDate);
        if(p != null && (!carId.isEmpty()) && rdate.isAfter(LocalDate.now())){
            String bid  = generateUniqueBID(existingIDs);
            int bill = generateBill(rdate,carId);
            Booking booking = new Booking(bid,passengerId,carId,LocalDate.now(),rdate,bill);
            bookings.add(booking);
            writeBookingsData(bookings);
            return booking;
        }else {
            System.out.println("Passenger Id is Wrong Or Car is Unavailable Or Return Date is not format(YYYY-MM-DD) Or Return Date is Before/Equals Current Date!");
            return null;
        }
    }

    public static String generateUniqueBID(Set<String> existingIDs) {
        Random random = new Random();
        String newID;

        do {
            int randomNumber = 1000 + random.nextInt(9001);
            newID = "BID" + randomNumber;
        } while (existingIDs.contains(newID));

        return newID;
    }

    public static int generateBill(LocalDate returnDate,String carId){
        long days = ChronoUnit.DAYS.between(LocalDate.now(), returnDate);
        List<Car> cars = Car.getAllCars();
        int rentPerDay = 0;
        for(Car car:cars){
            if(car.getCarId().equals(carId)){
                rentPerDay = car.getRentPerDay();
            }
        }
        int bill = (int)days * rentPerDay;
        return bill;
    }

    public static void writeBookingsData(List<Booking> bookings){
        File file = new File("src/data/BookingData.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Booking booking : bookings) {
                writer.write(booking.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
