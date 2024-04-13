package Corporation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
    private static List<Employee> employeeList = new ArrayList<>();
    private static Scanner scannerStr = new Scanner(System.in);
    private static Scanner scannerNumb = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        do {
            displayMenu();
            option = scannerStr.nextInt();
            scannerStr.nextLine();
            switch (option) {
                case 1:
                    displayAllEmployees();
                    break;
                case 2:
                    addNewEmployee();
                    break;
                case 3:
                    searchEmployeeByCode();
                    break;
                case 4:
                    searchEmployeesByDepartment();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    increaseSalary();
                    break;
                case 7:
                    displayMonthlySalary();
                    break;
                case 8:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (option != 8);
    }

    private static void displayMenu() {
        System.out.println("Menú de opciones:");
        System.out.println("[1]-> Mostrar todos los empleados (en formato reducido)");
        System.out.println("[2]-> Dar de alta un nuevo empleado");
        System.out.println("[3]-> Buscar un empleado por su código");
        System.out.println("[4]-> Buscar todos los empleados de un departamento");
        System.out.println("[5]-> Borrar un empleado indicando su código");
        System.out.println("[6]-> Subir el sueldo a un empleado");
        System.out.println("[7]-> Mostrar el salario del mes actual de un empleado");
        System.out.println("[8]-> Salir del programa");
        System.out.print("Seleccione una opción: ");
    }

    private static void displayAllEmployees() {
        for (Employee employee : employeeList) {
            System.out.println(employee.toStringV2());
        }
    }

    private static void addNewEmployee() {
        boolean verifier;
        do{
            try {
                System.out.print("> Introduzca los números del DNI:");
                int dni = scannerNumb.nextInt();
                System.out.print("> Introduzca el nombre del empleado:");
                String name = scannerStr.nextLine();
                System.out.print("> Introduzca los apellidos del empleado:");
                String surnames = scannerStr.nextLine();
                System.out.print("> Introduzca el departamento del empleado:");
                String department = scannerStr.nextLine();
                System.out.print("> Introduzca el salario anual:");
                double anualSalary = scannerNumb.nextDouble();
                System.out.print("> Introduzca la fecha de contrato [dd/mm/aaa]:");
                String contractDate = scannerStr.nextLine();
                String[] contractArrayDate = contractDate.split("/");
                System.out.print("> Introduzca la fecha de nacimiento del empleado [dd/mm/aaa]:");
                String birthDate = scannerStr.nextLine();
                String[] birthArrayDate = birthDate.split("/");
                Employee employeetToAdd = new Employee(dni, name, surnames, department, anualSalary,
                        LocalDate.of(Integer.parseInt(contractArrayDate[2]),Integer.parseInt(contractArrayDate[1]),Integer.parseInt(contractArrayDate[0])),
                        LocalDate.of(Integer.parseInt(birthArrayDate[2]),Integer.parseInt(birthArrayDate[1]),Integer.parseInt(birthArrayDate[0])),true);
                verifier = true;
            } catch (IncorrectEmployeesCodeFormatException | SalaryOver60000Exception e){
                verifier = false;
                System.out.println(e.getMessage());
            }
        }while(!verifier);
    }

    private static void searchEmployeeByCode() {
        // Implementar lógica para buscar un empleado por su código
    }

    private static void searchEmployeesByDepartment() {
        // Implementar lógica para buscar empleados por departamento
    }

    private static void deleteEmployee() {
        // Implementar lógica para borrar un empleado
    }

    private static void increaseSalary() {
        // Implementar lógica para subir el sueldo de un empleado
    }

    private static void displayMonthlySalary() {
        // Implementar lógica para mostrar el salario mensual de un empleado
    }
}
