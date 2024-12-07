import java.util.*;

class Car{
    private String CarId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String CarId,String brand, String model, double basePricePerDay){
        this.CarId = CarId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId(){
        return CarId;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable =false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customer_name;
    private String customer_id;
    private long customer_AadhaarNumber;
    private long customer_phNumber;

    public Customer(String customer_id,String customer_name,long customer_AadhaarNumber,long customer_phNumber){
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_AadhaarNumber = customer_AadhaarNumber;
        this.customer_phNumber = customer_phNumber;
    }
    public String getCustomer_name(){
        return customer_name;
    }
    public String getCustomer_id(){
        return customer_id;
    }
    public long getCustomer_AadhaarNumber(){
        return customer_AadhaarNumber;
    }
    public long getCustomer_phNumber(){
        return customer_phNumber;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer,int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public void rentCar(Car car, Customer customer,int days){
        if(car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
        else{
                System.out.println("Car is not available for Rent!");
            }
    }
    public void returnCar(Car car){

        Rental rentalToRemove = null;
        for (Rental rental: rentals)
        {
            if (rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null)
        {
            rentals.remove(rentalToRemove);
            //System.out.println("Car returned Successfully!");
            car.returnCar();
        }
        else{
            System.out.println("Car was not rented!");
        }
    }

    public void menu(){
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println("====Car Rental System====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1)
            {
                System.out.println("==== Rent a Car ====");

                System.out.println("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("Enter your Aadhaar Number: ");
                long customerAadhaar = Long.parseLong(scanner.next());

                System.out.println("Enter your phone number: ");
                long PhoneNumber = Long.parseLong(scanner.next());

                System.out.println("\n Available Cars: ");
                for (Car car: cars)
                {
                    if(car.isAvailable())
                    {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }
                System.out.println("Enter the Car ID you want to rent: ");
                String carID = scanner.next();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName , customerAadhaar , PhoneNumber);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car: cars)
                {
                    if(car.getCarId().equals(carID) && car.isAvailable())
                    {
                        selectedCar = car;
                        break;
                    }
                }
                if(selectedCar != null)
                {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n == RentalInformation ==");
                    System.out.println("Customer ID: " +newCustomer.getCustomer_id());
                    System.out.println("Customer Name: " +newCustomer.getCustomer_name());
                    System.out.println("Customer Aadhaar Number: " +newCustomer.getCustomer_AadhaarNumber());
                    System.out.println("Customer Phone Number: " +newCustomer.getCustomer_phNumber());
                    System.out.println("Car: " +selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.println("\n Confirm Rental (Y/N) :");
                    String confirm = scanner.next();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\nCar Rented Successfully!");
                    }
                    else {
                        System.out.println("\nRental Canceled.");
                    }
                }
                else {
                    System.out.println("\n Invalid car selection OR car not available for Rent!");
                }

            } else if (choice == 2)
            {
                System.out.println("==== Return a Car ====");
                System.out.println("Enter the car ID you want to return: ");
                String carID = scanner.nextLine();

                Car carToReturn = null;
                for (Car car: cars)
                {
                    if(car.getCarId().equals(carID) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }
                if(carToReturn != null)
                {
                    Customer customer = null;
                    for(Rental rental: rentals)
                    {
                        if(rental.getCar() == carToReturn)
                        {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null)
                    {
                        returnCar(carToReturn);
                        System.out.println(" Car Returned Successfully by: " + customer.getCustomer_name());
                    }
                    else
                    {
                        System.out.println("Car was not rented OR Rental Information is missing!");
                    }
                }
                else
                {
                    System.out.println("Invalid car ID OR Car is not Rented!");
                }
            }
            else if (choice == 3)
            {
                break;
            }
            else
            {
                System.out.println("Invalid Choice.Please enter valid Input!");
            }

            System.out.println("Thank You for Using Car Rental System!");
            System.out.println("Good Day!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001","TATA","PUNCH",85.0);
        Car car2 = new Car("C002","TOYOTA","INNOVA",100.0);
        Car car3 = new Car("C003","KIA","KARENS",90.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}