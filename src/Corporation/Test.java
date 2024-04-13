package Corporation;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean verifier;
        System.out.print("Codigo de Empleado a buscar:");
        do{
            try {
                String code = sc.nextLine();
                Employee emp1 = new Employee(code);
                verifier = true;
            } catch (IncorrectEmployeesCodeFormatException e){
                verifier = false;
                System.out.println(e.getMessage());
            }
        }while(!verifier);
    }
}
