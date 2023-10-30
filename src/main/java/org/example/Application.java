package org.example;

import java.util.List;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private DbCompanyDao companyDB;
    private DbCarDao carDB;

    private DbCustomerDao customerDB;
    public Application() {
        this.companyDB = new DbCompanyDao();
        this.carDB = new DbCarDao();
        this.customerDB = new DbCustomerDao();
        this.scanner = new Scanner(System.in);
    }

    public void printMenu(){
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }

    public void managerMenu(){
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    public void startApplication(){
        printMenu();
        int input = scanner.nextInt();
        if(input == 1){
            level2Manager();
        }else if(input == 2){
            level2Customer();
        }else if (input ==3){
            System.out.println("Enter the customer name:");
            scanner.nextLine();
            String name = scanner.nextLine();
            Customer customer = new Customer(name);
            customerDB.add(customer);
            System.out.println("The customer was added!\n");
            startApplication();
        }
    }

    private void level2Customer() {
        List<Customer> customers = customerDB.findAll();
        if(customers.isEmpty()){
            System.out.println("The customer list is empty!\n");
            startApplication();
        }else {
            System.out.println("Choose a customer:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println(customers.get(i).getId() + ". " + customers.get(i).getName());
            }
            System.out.println("0. Back");
            int customerLevel2input = scanner.nextInt();
            if (customerLevel2input == 0) {
                startApplication();
            }else{
                level3Customer(customerLevel2input);
            }
        }
    }

    private void level3Customer(int customerLevel2input) {
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
        int customerLevel3input = scanner.nextInt();
        if(customerLevel3input == 0){
            startApplication();
        }else if(customerLevel3input == 1){
            rentACar(customerLevel2input);
        }else if(customerLevel3input == 2){
            returnRentedCar(customerLevel2input);
        }else{
            myRentedCar(customerLevel2input);
            level3Customer(customerLevel2input);
        }
    }

    private void rentACar(int customerLevel2input) {
        List<Company> companies = companyDB.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            level3Customer(customerLevel2input);
        } else {
            System.out.println("Choose a company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println(i + 1 + ". " + companies.get(i).getName());
            }
            int companychosen = scanner.nextInt();
            if(customerDB.controlRentedCar(customerLevel2input).getRented_car_id() != 0){
                System.out.println("You've already rented a car!");
                level3Customer(customerLevel2input);
            }else {
                List<Car> cars = showAvailableCars(companychosen);
                int carChosen = scanner.nextInt();
                if(carChosen == 0){
                    level3Customer(customerLevel2input);
                }else{
                    customerDB.updateRentedCar(cars.get(carChosen).getId(),customerLevel2input);
                }
            }
        }
    }

    private List<Car> showAvailableCars(int customerLevel2input) {
        System.out.println("Choose a car:");
        List<Car> cars = carDB.availableCars(customerLevel2input);
        if(cars.isEmpty()){
            Company company = companyDB.findById(customerLevel2input);
            System.out.println("No available cars in the '" + company.getName() + "' company");
        }else{
            for(int i = 0 ; i < cars.size() ; i++){
                System.out.println(i + 1 + ". " + cars.get(i).getName());
            }
            System.out.println("0. Back");
        }
        return cars;
    }

    private void returnRentedCar(int customerLevel2input) {
        if(customerDB.controlRentedCar(customerLevel2input).getRented_car_id() == 0){
            System.out.println("You didn't rent a car!");
        }else {
            customerDB.updateRentedCar_ToNull(customerLevel2input);
            System.out.println("You've returned a rented car!");
        }
    }

    private void myRentedCar(int customerLevel2input) {
        List<Car> cars = customerDB.rentedCars(customerLevel2input);
        if(cars.isEmpty()){
            System.out.println("You didn't rent a car!");
        }else{
            System.out.println("Your rented car:");
            for(Car car : cars){
                System.out.println(car.getName());
                Company company = companyDB.findById(car.getCompany_id());
                System.out.println("Company:");
                System.out.println(company.getName());
            }
        }
    }

    public void level2Manager(){
        managerMenu();
        int managerMenu_Input = scanner.nextInt();
        if(managerMenu_Input == 1){
            printCompanies();

        }else if (managerMenu_Input == 2) {
            addCompany();
            level2Manager();
        }else{
            startApplication();
        }
    }

    private void level3Manager(int input) {
        printCompanyMenu(input);
        int company_menu_input = scanner.nextInt();
        if(company_menu_input == 0){
            level2Manager();
        }else if (company_menu_input == 1){
            printCarList(input);
            level3Manager(input);
        }else if(company_menu_input ==2){
            addCar(input);
            level3Manager(input);
        }
    }

    public void addCompany(){
        System.out.println("Enter the company name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        companyDB.add(new Company(name));
        System.out.println("The company was created!\n");
    }


    public void printCompanies(){
        List<Company> companies = companyDB.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            level2Manager();
        } else {
            System.out.println("Choose the company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println(i + 1 + ". " + companies.get(i).getName());
            }
            System.out.println("0. Back");
            int printCompanies_Input = scanner.nextInt();
            if(printCompanies_Input == 0){
                level2Manager();
            }else{
                level3Manager(printCompanies_Input);
            }
        }
    }

    public void printCarList(int input){
        Company company = companyDB.findById(input);
        //String company_name = company.getName();
        List<Car> cars = carDB.findByCompanyID(company.getId());
        if(cars.isEmpty()){
            System.out.println("The car list is empty!");
        }else {
            System.out.println("Car list:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(i + 1 + ". " + cars.get(i).getName());
            }
        }
    }

    public void addCar(int input){
        System.out.println("Enter the car name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        carDB.add(new Car(name, input));
        System.out.println("The car was created! \n");
    }

    public void printCompanyMenu(int input){
        Company company = companyDB.findById(input);
        String company_name = company.getName();
        System.out.println(company_name + " company");
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }
}
