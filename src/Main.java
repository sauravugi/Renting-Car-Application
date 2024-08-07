import java.util.List;

public class Main {
    public static void main(String[] args) {
//      Adding Owner data
        CarOwner.addOwner("Saurav","Siwan",500);
        CarOwner.addOwner("Sonu","Chhapra",1000);
        List<CarOwner> owners = CarOwner.getAllOwners();
        for(CarOwner o:owners){
            System.out.println(o);
        }
//      Adding Car data
        Car.addCar(owners.get(0).getOwnerId(),"Swift","Desire","BR29A5281",4,1000);
        Car.addCar(owners.get(1).getOwnerId(),"800","Maruti","BR29A2281",6,800);
        Car.addCar(owners.get(0).getOwnerId(),"Thar","Honda","BR29A9581",4,2000);
        List<Car> cars = Car.getAllCars();
        for(Car car:cars){
            System.out.println(car);
        }
//      Adding Passenger data
        Passenger.addPassenger("Rohan","Siwan",9000);
        Passenger.addPassenger("Sohan","Chhapra",8000);
        Passenger.addPassenger("Mohan","Siwan",20000);
        List<Passenger> passengers = Passenger.getAllPassengers();
        for(Passenger p:passengers){
            System.out.println(p);
        }
//      Adding Booking data
        Booking.bookingCar(passengers.get(0).getPassengerId(),"2024-08-10");
        Booking.bookingCar(passengers.get(2).getPassengerId(),"2024-08-09");
        Booking.bookingCar(passengers.get(1).getPassengerId(),"2024-08-11");
        List<Booking> bookings = Booking.getAllBookings();
        for(Booking b:bookings){
            System.out.println(b);
        }
    }
}
