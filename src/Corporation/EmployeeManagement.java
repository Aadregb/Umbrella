package Corporation;

import Utilities.IncorrectLengthDNIException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeManagement {
    private static List<Employee> employeeList = new ArrayList<>();
    private static Scanner scannerStr = new Scanner(System.in);
    private static Scanner scannerNumb = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exceptionCaught;
        int option;
        do{
            try{
                exceptionCaught = false;
                do {
                    displayMenu();
                    option = scannerStr.nextInt();
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
            }catch (InputMismatchException e){
                System.out.println(e.getMessage());
                exceptionCaught = true;
                scannerStr.nextLine();
            }
        } while(exceptionCaught);
    }

    private static void displayMenu() {
        System.out.println("Menú de opciones:");
        System.out.println("[1]➜ Mostrar todos los empleados (en formato reducido)");
        System.out.println("[2]➜ Dar de alta un nuevo empleado");
        System.out.println("[3]➜ Buscar un empleado por su código");
        System.out.println("[4]➜ Buscar todos los empleados de un departamento");
        System.out.println("[5]➜ Borrar un empleado indicando su código");
        System.out.println("[6]➜ Subir el sueldo a un empleado");
        System.out.println("[7]➜ Mostrar el salario del mes actual de un empleado");
        System.out.println("[8]➜ Salir del programa");
        System.out.print("Seleccione una opción: ");
    }
    private static void displayAllEmployees() {
        for (Employee employee : employeeList) {
            System.out.println(employee.toStringV2());
        }
    }
    private static String dateVerifier(String str){
        String dateToVerify;
        Pattern dateRegExp = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/(19|20)\\d{2}$");
        Matcher dateVerifier;
        do{
            System.out.print("> Introduzca la fecha de " + str + " [dd/mm/aaa]:");
            dateToVerify = scannerStr.nextLine();
            dateVerifier = dateRegExp.matcher(dateToVerify);
            if (!dateVerifier.matches()){
                System.out.println("Formato incorrecto. Tiene que ser -> [dd/mm/aaa]");
            }
        }while(!dateVerifier.matches());
        return dateToVerify;
    }
    private static String stringVerifier(String str){
        String stringToVerify;
        Pattern stringRegExp = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+([- ]?[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$");
        Matcher stringVerifier;
        do{
            System.out.print("> Introduzca "+str+":");
            stringToVerify = scannerStr.nextLine();
            stringVerifier = stringRegExp.matcher(stringToVerify);
            if (!stringVerifier.matches()){
                System.out.println("Formato incorrecto. Solo pueden contener letras con/sin guiones");
            }
        }while(!stringVerifier.matches());
        return stringToVerify;
    }
    private static int dniVerifier(){
        scannerStr.nextLine();
        String dniStr;
        Pattern dniRegExp = Pattern.compile("\\d{8}");
        Matcher dniVerifier;
        do{
            System.out.print("> Introduce los dígitos del DNI:");
            dniStr= scannerStr.nextLine();
            dniVerifier = dniRegExp.matcher(dniStr);
            if(!dniVerifier.matches()){
                System.out.println("Formato incorrecto. Debe contener 8 dígitos");
            }
        }while (!dniVerifier.matches());
        return Integer.parseInt(dniStr);
    }
    private static double salaryVerifier (){
        String salaryStr;
        Pattern salaryRegExp = Pattern.compile("^(0*(?:[1-5]\\d{0,4}|60000)(\\.\\d{1,2})?|0*\\.\\d{1,2}|60000)$");
        Matcher salaryVerifier;
        do{
            System.out.print("> Introduce el Salario Anual:");
            salaryStr = scannerStr.nextLine();
            salaryVerifier = salaryRegExp.matcher(salaryStr);
            if (!salaryVerifier.matches()){
                System.out.println("No se puede sobrepasar los 60.000");
            }
        }while(!salaryVerifier.matches());
        return Double.parseDouble(salaryStr);
    }
    private static void addNewEmployee() {
        boolean verifier;
        do{
            try {
                int dni = dniVerifier();
                String name = stringVerifier("Nombre");
                String surnames = stringVerifier("Apellidos");
                String department = stringVerifier("Departamento");
                double anualSalary = salaryVerifier();
                LocalDate contract;
                LocalDate birth;
                do {
                    String contractDate = dateVerifier("Contrato");
                    String birthDate = dateVerifier("Nacimiento");

                    String[] contractArrayDate = contractDate.split("/");
                    String[] birthArrayDate = birthDate.split("/");

                    contract = LocalDate.of(Integer.parseInt(contractArrayDate[2]), Integer.parseInt(contractArrayDate[1]), Integer.parseInt(contractArrayDate[0]));
                    birth = LocalDate.of(Integer.parseInt(birthArrayDate[2]), Integer.parseInt(birthArrayDate[1]), Integer.parseInt(birthArrayDate[0]));

                    if(contract.isAfter(LocalDate.now())){
                        System.out.println("FECHA DE CONTRATO POSTERIOR A LA FECHA ACTUAL");
                    }
                    if (contract.isBefore(birth)){
                        System.out.println("FECHA DE CONTRATO ANTERIOR A LA FECHA DE NACIMIENTO");
                    }
                    if (ChronoUnit.YEARS.between(birth, LocalDate.now()) < 18){
                        System.out.println("EMPLEADO MENOR DE EDAD");
                    }
                } while ((contract.isAfter(LocalDate.now())) || contract.isBefore(birth) || ChronoUnit.YEARS.between(birth, LocalDate.now()) < 18);

                Employee employeeToAdd = new Employee(dni, name, surnames, department, anualSalary, contract, birth, true);
                employeeList.add(employeeToAdd);
                verifier = true;
            } catch (SalaryOver60000Exception | IncorrectLengthDNIException | DateBeforeCreationException | ContractDateBeforeBirthDateException |
                     InputMismatchException e){
                verifier = false;
                scannerNumb.nextLine();
                System.out.println(e.getMessage());
            }
        }while(!verifier);
    }
    private static void searchEmployeeByCode() {
        String employeesCode;
        Pattern codeRegExp = Pattern.compile("UMBRE"+"\\d{4}");
        Matcher codeVerifier;
        do{
            employeesCode = scannerStr.nextLine();
            codeVerifier = codeRegExp.matcher(employeesCode);
            if(!codeVerifier.matches()){
                System.out.println("Formato Incorrecto. Vuelve a intentarlo");
            }
        }while(!codeVerifier.matches());
        Employee tempEmployee = new Employee(employeesCode);
        if (employeeList.contains(tempEmployee)){
            System.out.println(employeeList.get(employeeList.indexOf(tempEmployee)).toString());
        }
    }

    private static void searchEmployeesByDepartment() {
        String dptToSearch = stringVerifier("Departamento");
        for (int i=0;i<employeeList.size();i++){
            if(employeeList.get(i).getDepartment().equals(dptToSearch)){
                System.out.println(employeeList.get(i).toStringV2());
            }
        }
    }

    private static void deleteEmployee() {
        String employeesCode;
        Pattern codeRegExp = Pattern.compile("UMBRE"+"\\d{4}");
        Matcher codeVerifier;
        do{
            employeesCode = scannerStr.nextLine();
            codeVerifier = codeRegExp.matcher(employeesCode);
            if(!codeVerifier.matches()){
                System.out.println("Formato Incorrecto. Vuelve a intentarlo");
            }
        }while(!codeVerifier.matches());
        Employee tempEmployee = new Employee(employeesCode);
        if (employeeList.contains(tempEmployee)){
            employeeList.remove(employeeList.indexOf(tempEmployee));
        }
    }

    private static void increaseSalary() {

        boolean exceptionCaught;

        String employeesCode;
        Pattern codeRegExp = Pattern.compile("UMBRE"+"\\d{4}");
        Matcher codeVerifier;
        double percentage = 0;
        do{
            try{
                System.out.print("> Introduce el % a subir:");
                percentage = scannerStr.nextDouble();
                exceptionCaught = false;
            }catch(InputMismatchException e){
                System.out.println(e.getMessage());
                exceptionCaught = true;
                scannerStr.nextLine();
            }
        }while(exceptionCaught);
        do{
            scannerStr.nextLine();
            System.out.print("> Introduce el Código de Empleado:");
            employeesCode = scannerStr.nextLine();
            codeVerifier = codeRegExp.matcher(employeesCode);
            if(!codeVerifier.matches()){
                System.out.println("Formato Incorrecto. Vuelve a intentarlo");
            }
        }while(!codeVerifier.matches());
        Employee tempEmployee = new Employee(employeesCode);
        if (employeeList.contains(tempEmployee)){
            employeeList.get(employeeList.indexOf(tempEmployee)).raiseSalary(percentage);
        } else {
            System.out.println("Empleado con el codigo ["+employeesCode+"] no encontrado.");
        }
    }

    private static void displayMonthlySalary() {
        String employeesCode;
        Pattern codeRegExp = Pattern.compile("UMBRE"+"\\d{4}");
        Matcher codeVerifier;
        do{
            employeesCode = scannerStr.nextLine();
            codeVerifier = codeRegExp.matcher(employeesCode);
            if(!codeVerifier.matches()){
                System.out.println("Formato Incorrecto. Vuelve a intentarlo");
            }
        }while(!codeVerifier.matches());
        Employee tempEmployee = new Employee(employeesCode);
        if (employeeList.contains(tempEmployee)){
            System.out.println("El sueldo mensual del empleado con Código ["+employeeList.get(employeeList.indexOf(tempEmployee)).getEmployeesCode()+"]:"+employeeList.get(employeeList.indexOf(tempEmployee)).monthlySalary());
        }
    }
}
