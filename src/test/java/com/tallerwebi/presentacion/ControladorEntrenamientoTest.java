package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasItem;

public class ControladorEntrenamientoTest {

    /*
    *Validador de Password
    *
    * Que sea Invalida si tine Menos de 8 caracteres y no tiene caracteres especiales
    *
    * Que sea DEBIL si tiene al menos 8 caracteres, pero no cumple con los requisitos de 'FUERTE'.
    *
    * Que sea MEDIANA si Tiene al menos 4 caracteres y al menos 1 carácter especial
    *
    * Que sea - FUERTE:
        - Al menos 8 caracteres.
        - Al menos 4 números.
        - Al menos 1 carácter especial (no letra ni número)
    *
    * */

    //Confirmar funcionamiento correcto - Caso FUERTE
    @Test
    public void queSeaFuerteSiCumpleTodosLosRequisitos() {
        String passwordFuerte = "1234@abc";
        String nivelDeFortaleza = whenValidarFortaleza(passwordFuerte);
        thenLaPasswordTieneTalNivelDeFortaleza(nivelDeFortaleza,"FUERTE");
    }

    //Confirmar niveles intermedios - Casos DÉBIL

    @Test
    public void queSeaDebilSiTieneAlMenos8CaracteresUnEspecial_PeroTieneMenosDe4numeros() {
        String passwordDebil = "123abc@d";
        String nivelDeFortaleza = whenValidarFortaleza(passwordDebil);
        thenLaPasswordTieneTalNivelDeFortaleza(nivelDeFortaleza,"DEBIL");
    }

    @Test
    public void queSeaDebilSiTieneAlMenos8CaracteresAlMenos4Numeros_PeroLeFaltaUnCaracterEspecial() {
        String passwordDebil = "1234abcd";
        String nivelDeFortaleza = whenValidarFortaleza(passwordDebil);
        thenLaPasswordTieneTalNivelDeFortaleza(nivelDeFortaleza, "DEBIL");
    }

    @Test
    public void queSeaDebilSiTieneAlMenos8CaracteresMenosDe4numerosYSinCaracterEspecial() {
        String passwordDebil = "123abcde";
        String nivelDeFortaleza = whenValidarFortaleza(passwordDebil);
        thenLaPasswordTieneTalNivelDeFortaleza(nivelDeFortaleza, "DEBIL");
    }

    // Confirmar errores graves - Caso INVALÍDA

    @Test
    public void queSeaInvalidaSiTieneMenosDe8CaracteresSinCaracterEspecial() {
        String passwordInvalida = "abc123";
        String nivelDeFortaleza = whenValidarFortaleza(passwordInvalida);
        thenLaPasswordTieneTalNivelDeFortaleza(nivelDeFortaleza, "INVALIDA");
    }

    @Test
    public void queSeaMedianaSiTieneAlMenos4Caracteres_PeroTieneUnCaracterEspecial() {
        String passwordMediana = "ab@c";
        String nivelDeFortaleza = whenValidarFortaleza(passwordMediana);
        thenLaPasswordTieneTalNivelDeFortaleza(nivelDeFortaleza, "MEDIANA");
    }

    @Test
    public void queSeaMedianaSiTieneMenosDe4Caracteres_PeroConCaracterEspecial() {
        String passwordMediana = "ab@";
        String nivelDeFortaleza = whenValidarFortaleza(passwordMediana);
        thenLaPasswordTieneTalNivelDeFortaleza(nivelDeFortaleza, "MEDIANA");
    }

    private String whenValidarFortaleza(String password) {
        ControladorPassword controladorPassword = new ControladorPassword();
        return controladorPassword.validarFortaleza(password);
    }

    private void thenLaPasswordTieneTalNivelDeFortaleza(String nivelDeFortalezaObtenida, String nivelDeFortalezaEsperada) {
        assertThat(nivelDeFortalezaObtenida, equalToIgnoringCase(nivelDeFortalezaEsperada));
    }


}



