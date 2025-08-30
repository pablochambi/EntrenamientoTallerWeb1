package com.tallerwebi.presentacion;

public class ControladorPassword {

    public String validarFortaleza(String password) {
        if (esInvalida(password)) return "INVALIDA";
        if (esFuerte(password)) return "FUERTE";
        if (esDebil(password)) return "DEBIL";
        return "MEDIANA"; // si no es ninguno de los anteriores, es MEDIANA
    }

    private boolean esFuerte(String password) {
        return password.length() >= 8
                && contieneAlMenos4Numeros(password)
                && contieneAlMenosUnCaracterEspecial(password);
    }

    private boolean esDebil(String password) {
        return password.length() >= 8;
    }

    private boolean esMediana(String password) {
        return password.length() >= 4 || contieneAlMenosUnCaracterEspecial(password);
    }

    private boolean esInvalida(String password) {
        return password.length() < 8 && !contieneAlMenosUnCaracterEspecial(password);
    }

    private boolean contieneAlMenosUnCaracterEspecial(String cadena) {
        int contadorEspeciales = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if(elCaracterDeLaCadenaEsEspecial(cadena,i)){
                contadorEspeciales++;
            }
            if(contadorEspeciales>=1){
                return true;
            }
        }
        return false;
    }



    public boolean contieneAlMenos4Numeros(String cadena) {
        int contadorNumeros = 0;

        for (int i = 0; i < cadena.length(); i++) {

            if (elCaracterDeLaCadenaEsUnNumero(cadena, i)) {
                contadorNumeros++;
            }
            // Si ya encontramos 4 números, no necesitamos seguir buscando
            if (contadorNumeros >= 4) {
                return true;
            }
        }
        return false;
    }

    private boolean elCaracterDeLaCadenaEsUnNumero(String cadena, int i) {
        return Character.isDigit(cadena.charAt(i));
    }

    private boolean elCaracterDeLaCadenaEsEspecial(String cadena, int i) {
        return !Character.isLetterOrDigit(cadena.charAt(i));
    }

}
