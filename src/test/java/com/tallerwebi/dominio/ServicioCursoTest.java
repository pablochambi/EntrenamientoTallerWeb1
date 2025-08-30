package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Implementaciones.*;
import com.tallerwebi.dominio.entidades.Curso;
import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.entidades.Inscripcion;
import com.tallerwebi.dominio.entidades.Modulo;
import com.tallerwebi.dominio.interfaces.*;
import com.tallerwebi.infraestructura.RepositorioCursoImpl;
import com.tallerwebi.infraestructura.RepositorioEstudiantesImpl;
import com.tallerwebi.infraestructura.RepositorioInscripcionImpl;
import com.tallerwebi.infraestructura.RepositorioModuloImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

public class ServicioCursoTest {

    private ServicioCurso servicioCurso;
    private ServicioEstudiante servicioEstudiante;
    private ServicioInscripcion servicioInscripcion;
    private RepositorioCurso repositorioCurso;
    private RepositorioModulo moduloRepository;
    private RepositorioEstudiante repositorioEstudiantes;
    private RepositorioInscripcion repositorioInscripcion;

    @BeforeEach
    public void preparacion() {
        repositorioCurso = mock(RepositorioCursoImpl.class);
        moduloRepository = mock(RepositorioModuloImpl.class);
        repositorioEstudiantes = mock(RepositorioEstudiantesImpl.class);
        repositorioInscripcion = mock(RepositorioInscripcionImpl.class);
        servicioCurso = new ServicioCursoImpl(repositorioCurso, moduloRepository,repositorioEstudiantes,repositorioInscripcion);
        servicioEstudiante = new ServicioEstudianteImpl(repositorioEstudiantes);
        servicioInscripcion = new ServicioInscripcionImpl(repositorioInscripcion);
    }

    @Test
    public void queNoSePuedaCrearDosCursosConMismoTitulo_LanzaExcepcion() {
        // Configuración
        Curso curso1 = new Curso(1L,"Spring Boot");
        Curso curso2 = new Curso(2L,"Spring Boot");

        // Configuración de mocks
        when(repositorioCurso.buscarPorId(curso1.getId())).thenReturn(null);
        when(repositorioCurso.buscarPorId(curso2.getId())).thenReturn(curso2);
        when(repositorioCurso.guardar(curso1)).thenReturn(curso1);

        //Ejecucion y verificacion
        assertDoesNotThrow(() -> servicioCurso.registrar(curso1));
        assertThrows(RuntimeException.class, () -> servicioCurso.registrar(curso2));
        verify(repositorioCurso, times(1)).guardar(any(Curso.class));
    }

    @Test
    public void crearCurso_ConModulos_GuardaCorrectamente() {
        // Configuración
        Curso curso = crearCurso(1L,"Spring Boot");

        Modulo modulo1 = crearModulo(1L, "Introduccion", 5);
        Modulo modulo2 = crearModulo(2L, "Spring Data", 6);
        List<Modulo> modulos = List.of(modulo1, modulo2);

        // Configuración de mocks
        when(repositorioCurso.guardar(any(Curso.class))).thenReturn(curso);

        // Ejecución
        Curso cursoObtenido = servicioCurso.crearCurso(curso, modulos);

        // Verificaciones
        assertThat(cursoObtenido, notNullValue());
        verify(repositorioCurso).guardar(any(Curso.class));
        verify(moduloRepository, times(2)).guardar(any(Modulo.class));

        // Verificaciones adicionales recomendadas
        assertEquals(curso.getId(), cursoObtenido.getId());
        assertEquals("Spring Boot", cursoObtenido.getTitulo());
        assertEquals(curso, modulo1.getCurso());
        assertEquals(curso, modulo2.getCurso());
    }

    @Test
    public void inscribirEstudiante_ConDatosValidos_DebeInscribirCorrectamente() {
        // Configuración
        Long cursoId = 1L;
        Long estudianteId = 100L;

        Curso curso = crearCurso(cursoId, "Java Básico");
        Estudiante estudiante = crearEstudiante(estudianteId, "Juan Pérez");

        when(repositorioCurso.buscarPorId(cursoId)).thenReturn(curso);
        when(repositorioEstudiantes.buscarPorId(estudianteId)).thenReturn(estudiante);
        when(repositorioInscripcion.existeInscripcion(cursoId, estudianteId)).thenReturn(false);

        // Ejecución
        servicioCurso.inscribirEstudiante(cursoId, estudianteId);

        // Verificación
        verify(repositorioInscripcion).guardar(argThat(inscripcion ->
                inscripcion.getCurso().equals(curso) &&
                        inscripcion.getEstudiante().equals(estudiante) &&
                        inscripcion.getFechaInscripcion() != null
        ));
    }

    @Test
    public void inscribirEstudiante_CursoNoExistente_DebeLanzarExcepcion() {
        // Configuración
        Long cursoId = 999L;
        Long estudianteId = 100L;

        when(repositorioCurso.buscarPorId(cursoId)).thenReturn(null);

        // Ejecución y Verificación
        assertThrows(RuntimeException.class, () -> {
            servicioCurso.inscribirEstudiante(cursoId, estudianteId);
        });
    }

    @Test
    public void obtenerEstudiantesDeCurso_ConEstudiantes_DebeRetornarLista() {
        // Configuración
        Curso curso = new Curso("Curso Spring Boot");
        servicioCurso.registrar(curso);

        Estudiante estudiante1 = new Estudiante("Pablo Ariel","pablo@gmail.com");
        estudiante1.setId(1L);
        servicioEstudiante.registrar(estudiante1);

        Estudiante estudiante2 = new Estudiante("Carlos Ruiz", "carlos@gmail.com");
        estudiante2.setId(2L);
        servicioEstudiante.registrar(estudiante2);

        Inscripcion inscripcion1 = new Inscripcion(curso, estudiante1, LocalDate.now());
        Inscripcion inscripcion2 = new Inscripcion(curso, estudiante2, LocalDate.now());

        servicioInscripcion.registrar(inscripcion1);
        servicioInscripcion.registrar(inscripcion2);

        //curso.getInscripciones().addAll(List.of(inscripcion1, inscripcion2));


        when(repositorioCurso.buscarPorId(curso.getId())).thenReturn(curso);
        when(repositorioInscripcion.obtenerEstudiantesPorCurso(curso.getId())).thenReturn(Set.of(estudiante1, estudiante2));

        // Ejecución
        Set<Estudiante> estudiantes = servicioCurso.obtenerEstudiantesDeCurso(curso.getId());


        // Verificación
        //Compara Direcciones de Memoria
        assertEquals(2, estudiantes.size());
        assertTrue(estudiantes.containsAll(Set.of(estudiante1, estudiante2)));

        //Compara solo IDs
        assertTrue(estudiantes.stream()
                .map(Estudiante::getId)
                .collect(Collectors.toSet())
                .containsAll(Set.of(estudiante1.getId(), estudiante2.getId())));


    }

    @Test
    public void crearCurso_SinModulos_DebeGuardarSoloCurso() {
        // Configuración
        Curso curso = crearCurso(1L, "Python Básico");

        when(repositorioCurso.guardar(any(Curso.class))).thenReturn(curso);

        // Ejecución
        Curso resultado = servicioCurso.crearCurso(curso, null);

        // Verificaciones
        assertNotNull(resultado);//
        verify(repositorioCurso).guardar(any(Curso.class));
        verify(moduloRepository, never()).guardar(any(Modulo.class));
    }



    @Test
    public void obtenerEstudiantesDeCurso_CursoVacio_DebeRetornarListaVacia() {
        // Configuración
        Long cursoId = 1L;
        Curso curso = crearCurso(cursoId, "Curso Vacío");

        when(repositorioCurso.buscarPorId(cursoId)).thenReturn(curso);

        // Ejecución
        Set<Estudiante> estudiantes = servicioCurso.obtenerEstudiantesDeCurso(cursoId);

        // Verificación
        assertTrue(estudiantes.isEmpty());//
        assertInstanceOf(Set.class, estudiantes);

    }


    @Test
    public void inscribirEstudiante_YaInscripto_NoDebeGuardarNuevaInscripcion() {
        // Configuración
        Long cursoId = 1L;
        Long estudianteId = 100L;

        Curso curso = crearCurso(cursoId, "Java Intermedio");
        Estudiante estudiante = crearEstudiante(estudianteId, "María Gómez");

        when(repositorioCurso.buscarPorId(cursoId)).thenReturn(curso);
        when(repositorioEstudiantes.buscarPorId(estudianteId)).thenReturn(estudiante);
        when(repositorioInscripcion.existeInscripcion(cursoId, estudianteId)).thenReturn(true);

        // Ejecución y verificacion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCurso.inscribirEstudiante(cursoId, estudianteId);
        });

        // Verificación
        assertThat(exception.getMessage(),equalTo("El estudiante ya está inscrito en este curso."));
        verify(repositorioInscripcion, never()).guardar(any(Inscripcion.class));
    }



    private Modulo crearModulo(Long id, String nombre, Integer duracionHoras) {
        return new Modulo(id, nombre, duracionHoras);
    }

    private Curso crearCurso(Long id, String titulo) {
        Curso curso = new Curso();
        curso.setId(id);
        curso.setTitulo(titulo);
        return curso;
    }

    private Estudiante crearEstudiante(Long id, String nombre) {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(id);
        estudiante.setNombre(nombre);
        return estudiante;
    }
}
