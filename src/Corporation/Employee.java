package Corporation;

import Utilities.DNI;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {
    private String employeesCode; //UMBRE0001 ... UMBRE9999
    private static int nEmployee = 0;
    private DNI dni;
    private String name;
    private String surname;
    private String department;
    private double salary;
    private LocalDate contractDate;
    private LocalDate birthDate;
    private boolean isActiveWorker = true;
    private int antiquity;
    static double foodRemuneration = 110;
    static double commitment = 20;
    final static LocalDate COMPANY_CREATION = LocalDate.of(2016,5,2);
    final static String COMPANY_NAME = "UMBRELLA";

    public Employee(int dni, String name, String surname, String department, double salary, LocalDate contractDate, LocalDate birthDate, boolean isActiveWorker) {
        this.employeesCode = String.format("UMBRE"+"%04d",nEmployee);
        this.dni = new DNI(dni);
        this.name = name;
        this.surname = surname;
        this.department = department;
        if (salary > 60000){
            throw new SalaryOver60000Exception("SALARY OVER 60.000");
        } else  {
            this.salary = salary;
        }
        if ((contractDate.isBefore(COMPANY_CREATION)) || (contractDate.isAfter(LocalDate.now())) || (ChronoUnit.YEARS.between(birthDate,LocalDate.now()) < 18)){
            throw new DateBeforeCreationException("CONTRACT DATE IS BEFORE COMPANY CREATION DATE / IS AFTER CURENT DATE / EMPLOYEE IS UNDER 18yo");
        } else {
            this.contractDate = contractDate;
        }
        if (contractDate.isBefore(birthDate)){
            throw new ContractDateBeforeBirthDateException("CONTRACT DATE IS BEFORE BIRTHDATE");
        } else {
            this.birthDate = birthDate;
        }
        this.antiquity = (int) ChronoUnit.YEARS.between(contractDate,LocalDate.now());
        nEmployee++;
    }
    public Employee(String employeesCode) {
        Pattern codeRegExp = Pattern.compile("UMBRE"+"\\d{4}");
        Matcher verifier = codeRegExp.matcher(employeesCode);
        if (!verifier.matches()){
            throw new IncorrectEmployeesCodeFormatException("INCORRECT CODE FORMAT");
        }
        this.employeesCode = employeesCode;
    }
    public DNI getDni() {
        return dni;
    }

    public int employeesAntiquity () {
        return (int) ChronoUnit.YEARS.between(contractDate,LocalDate.now());
    }
    public boolean itsBirthdaysMonth () {
        return birthDate.getMonth().equals(LocalDate.now().getMonth());
    }
    @Override
    public String toString() {
        return "Employee's Code:" + employeesCode + "\n" +
                "·DNI:" + dni.getNIF() + "\n" +
                "·Name:" + name + "\n"  +
                "·Surnames:" + surname + "\n"  +
                "·Department:" + department + "\n"  +
                "·Salary:" + salary + "\n" +
                "·Contract Date:" + contractDate + "\n" +
                "·BirthDate:" + birthDate + "\n" +
                "·Antiquity:" + antiquity;
    }
    public String toStringV2() {
        return "·Employee's Code:" + employeesCode + "\n" +
                "·Name:" + name + "\n" +
                "·Surnames:" + surname + "\n" +
                "·Department:" + department + "\n";
    }
    public double monthlySalary () {
        if (LocalDate.now().getMonth().equals(birthDate.getMonth())){
            return salary/12 + (antiquity*commitment) + foodRemuneration + 50;
        } else {
            return salary/12 + (antiquity*commitment) + foodRemuneration;
        }
    }
    public String getEmployeesCode() {
        return employeesCode;
    }

    public String getDepartment() {
        return department;
    }

    public void raiseSalary (double percentage){
        if (percentage < 0){
            percentage *= -1;
        }
        this.salary = this.salary*(1+(percentage/100));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee emp = (Employee) o;
        return Objects.equals(employeesCode, emp.employeesCode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(employeesCode);
    }
}
