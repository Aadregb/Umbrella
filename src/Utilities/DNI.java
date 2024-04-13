package Utilities;

import java.util.Scanner;

public class DNI {

    private int numeroDNI;
    public DNI (int dni){
        setDNI(dni);
    }
    public int getNumeroDNI(){
        return numeroDNI;
    }
    public String getNIF(){
        String dni = String.valueOf(this.numeroDNI);
        if (String.valueOf(this.numeroDNI).length()<8){
            dni = String.format("%08d",this.numeroDNI);
        }
        return dni + calcularLetraNIF(this.numeroDNI);
    }
    public void setDNI (String NIF) throws IncorrectCharNIF {
        if (!validarNIF(NIF)){
            throw new IncorrectCharNIF("Letra Incorrecta.");
        } else{
            this.numeroDNI = Integer.parseInt(NIF.substring(0,7));
        }
    }
    public void setDNI (int dni) throws IncorrectLengthDNIException {
        if (String.valueOf(dni).length() >= 8){
            throw new IncorrectLengthDNIException("Longitud Incorrecta");
        } else{
            this.numeroDNI = dni;
        }
    }
    private static char calcularLetraNIF(int DNI){
        char[] letrasDNI = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        int posLetraDni = DNI%23;
        return letrasDNI[posLetraDni];
    }
    public static boolean validarNIF (String NIF){
        return NIF.charAt(NIF.length()-1) == calcularLetraNIF(Integer.parseInt(NIF.substring(0,NIF.length()-1)));
    }
    private static char extraerLetraNIF (String NIF){
        return NIF.charAt(NIF.length()-1);
    }
    private static int extraerNumeroNIF (String NIF){
        return Integer.parseInt(NIF.substring(0,NIF.length()-2));
    }
}
