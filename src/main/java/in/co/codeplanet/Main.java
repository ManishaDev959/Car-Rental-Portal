package in.co.codeplanet;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String car_id;
    private String brand;
    private String model;
    private double basePricePerDay;

    private boolean isAvailable;

    public String getCar_id() {
        return car_id;
    }

    public Car(String car_id, String brand, String model, double basePricePerDay) {
        this.car_id = car_id;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable=true;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getBasePricePerDay() {
        return basePricePerDay;
    }

    public void setBasePricePerDay(double basePricePerDay) {
        this.basePricePerDay = basePricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public double calculateRent(int rentalDays)
    {
        return basePricePerDay*rentalDays;
    }

    public void rent()
    {
        isAvailable=false;
    }
    public void returnCar()
    {
        isAvailable=true;
    }



}
class Customer{
    private String customer_id;
    private String customer_name;

    public Customer(String customer_id, String customer_name) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
class Rental{

     private Car car;
     private Customer customer;
     private int Days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        Days = days;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }
}
class CarRentalSystem{
      private List<Car> cars;
      private List<Customer> customers;
      private List<Rental> rentals;

    public CarRentalSystem() {
      cars=new ArrayList<>();
      customers=new ArrayList<>();
      rentals=new ArrayList<>();
    }
    public void addCar(Car car)
    {
        cars.add(car);
    }
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    public void rentCar(Car car, Customer customer, int days)
    {
        if(car.isAvailable())
        {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
        else{
            System.out.println("Car is not available for rent");
        }

    }
    public void returnCar(Car car)
    {

        Rental rentalCarRemove=null;
        for(Rental rental:rentals){
            if(rental.getCar()==car)
            {
                rentalCarRemove=rental;
                break;
            }
        }
        if(rentalCarRemove!=null)
        {
            rentals.remove(rentalCarRemove);

        }
        else{
            System.out.println("Car was not rented");
        }
        car.returnCar();

    }
    public void menu()
    {
        Scanner scanner=new Scanner(System.in);
        while(true)
        {
            System.out.println("Welcome to Car Rental Portal");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice");
            int choice=scanner.nextInt();
            scanner.nextLine();


            if(choice==1)
            {
                System.out.println("\n <<<<<<<<<<< Rent a Car>>>>>>>>>> \n");
                System.out.println("Enter your name:");
                String customerName=scanner.nextLine();

                System.out.println("\n Available Cars");
                for(Car car:cars)
                {
                    if(car.isAvailable())
                    {
                        System.out.println(car.getCar_id() + "-" + car.getBrand() + "-" + car.getModel());
                    }
                }

                System.out.println("\n Enter the Car Id you want to rent: ");
                String carId=scanner.nextLine();

                System.out.println("Enter the number of days for which you want to rent the car");
                int rentaldays=scanner.nextInt();
                scanner.nextLine();

                Customer newcustomer=new Customer( "CUS"+(customers.size()+1) , customerName);
                addCustomer(newcustomer);

                Car selectedCar=null;
                for(Car car:cars)
                {
                    if(car.getCar_id().equals(carId) && car.isAvailable())
                    {
                        selectedCar=car;
                        break;
                    }
                }
                if(selectedCar!=null)
                {
                    double totalPrice=selectedCar.calculateRent(rentaldays);
                    System.out.println("====== Rental Information=====");
                    System.out.println("CustomerId:" + newcustomer.getCustomer_id());
                    System.out.println("Customer Name:"+ newcustomer.getCustomer_name());
                    System.out.println("Car :" + selectedCar.getBrand() + " "+  selectedCar.getModel());
                    System.out.println("Rental Days:" + rentaldays);
                    System.out.println("Total Price: Rs." +totalPrice);

                    System.out.println("\nConfirm(Y/N)");
                    String confirm=scanner.nextLine();

                    if(confirm.equalsIgnoreCase("y"))
                    {
                        rentCar(selectedCar, newcustomer, rentaldays);
                        System.out.println("\nCar Rented successfully");
                    }
                    else{
                        System.out.println("\nRental Cancelled");
                    }
                }
                else{
                    System.out.println("Invalid Car select or car not available for rent");
                }

            }
            else if(choice==2)
            {
                System.out.println("Return a Car>>>>>>");
                System.out.println("Enter the Car id you want to return");
                String carId=scanner.nextLine();

                Car carToReturn=null;
                for(Car car:cars)
                {
                    if(car.getCar_id().equals(carId) && !car.isAvailable())
                    {
                        carToReturn=car;
                    }
                }

                if(carToReturn!=null)
                {
                    Customer customer=null;
                    for(Rental rental:rentals)
                    {
                        if(rental.getCar()==carToReturn)
                        {
                            customer=rental.getCustomer();
                            break;
                        }
                    }
                    if(customer!=null)
                    {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by :" +customer.getCustomer_name());
                    }
                    else{
                        System.out.println("Car was not rented or rental information is missing");
                    }
                }
                else{
                    System.out.println("Invalid car Id or car is not rented");
                }

            }
            else if(choice==3)
            {
                break;
            }
            else{
                System.out.println("Invalid choice!!! Please enter a valid choice");
            }

        }
        System.out.println("Thankyou for using the Car Rental System");

    }

}
public class Main {
    public static void main(String[] args) {


        CarRentalSystem carRentalSystem=new CarRentalSystem();
        Car car1=new Car("C001","Toyota","Camry",60.0);
        Car car2=new Car("C002","Honda","Accord",70.0);
        carRentalSystem.addCar(car1);
        carRentalSystem.addCar(car2);
        carRentalSystem.menu();

    }
}














