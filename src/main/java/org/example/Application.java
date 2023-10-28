package org.example;

import java.util.List;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private DbCompanyDao companyDB;
    public Application() {
        this.companyDB = new DbCompanyDao();
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


        while(input == 1){
            managerMenu();
            input = scanner.nextInt();
            if (input == 1) {
                List<Company> companies = companyDB.findAll();
                if (companies.isEmpty()) {
                    System.out.println("The company list is empty!");
                } else {
                    System.out.println("Company list:");
                    for (int i = 0; i < companies.size(); i++) {
                        System.out.println(i + 1 + ". " + companies.get(i).getName());
                    }
                    System.out.println();
                }
            } else if (input == 2) {
                System.out.println("Enter the company name:");
                scanner.nextLine();
                String name = scanner.nextLine();
                companyDB.add(new Company(name));
                System.out.println("The company was created!\n");
                input = 1;
            }else if(input == 0){
                startApplication();
                break;
            }
        }
    }
}
