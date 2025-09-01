package com.tallerwebi.integracion;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.RepositorioProyecto;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
@Transactional
public class TareaControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private RepositorioProyecto repositorioProyecto;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void queAlAgregarUnaTareaNoSeCreeDuplicada() throws Exception {
        // Preparación
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto de Prueba");
        repositorioProyecto.guardar(proyecto);

        MvcResult result_inicial = mockMvc.perform(get("/proyectos/" + proyecto.getId() + "/tareas"))
                .andExpect(status().isOk())
                .andReturn();
        List<Tarea> tareas_iniciales = (List<Tarea>) result_inicial.getModelAndView().getModel().get("tareas");


        // Ejecución
        mockMvc.perform(post("/proyectos/" + proyecto.getId() + "/tareas")
                        .param("nombre", "Tarea de prueba"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/proyectos/" + proyecto.getId() + "/tareas"));

        MvcResult result_final = mockMvc.perform(get("/proyectos/" + proyecto.getId() + "/tareas"))
                .andExpect(status().isOk())
                .andReturn();

        // Verificación
        ModelAndView modelAndView = result_final.getModelAndView();
        assertThat(modelAndView.getViewName(), equalTo("tareas"));

        List<Tarea> tareas_finales = (List<Tarea>) modelAndView.getModel().get("tareas");
        assertThat(tareas_finales, hasSize(tareas_iniciales.size() + 1));
        assertThat(tareas_finales.get(tareas_finales.size() - 1).getNombre(), equalTo("Tarea de prueba"));
    }
}
