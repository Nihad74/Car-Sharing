package org.example;

import java.util.List;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private DbCompanyDao companyDB;
    private DbCarDao carDB;
    public Application() {
        this.companyDB = new DbCompanyDao();
        this.carDB = new DbCarDao();
        this.scanner = new Scanner(System.in);
    }

    public void printMenu(){
        System.out.println("1. Log in as a manager");
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
            level2();
        }
    }

    public void level2(){
        managerMenu();
        int managerMenu_Input = scanner.nextInt();
        if(managerMenu_Input == 1){
            printCompanies();

        }else if (managerMenu_Input == 2) {
            addCompany();
            level2();
        }else{
            startApplication();
        }
    }

    private void level3(int input) {
        printCompanyMenu(input);
        int company_menu_input = scanner.nextInt();
        if(company_menu_input == 0){
            level2();
        }else if (company_menu_input == 1){
            printCarList(input);
            level3(input);
        }else if(company_menu_input ==2){
            addCar(input);
            level3(input);
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
            level2();
        } else {
            System.out.println("Choose the company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println(i + 1 + ". " + companies.get(i).getName());
            }
            System.out.println("0. Back");
            int printCompanies_Input = scanner.nextInt();
            if(printCompanies_Input == 0){
                level2();
            }else{
                level3(printCompanies_Input);
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
