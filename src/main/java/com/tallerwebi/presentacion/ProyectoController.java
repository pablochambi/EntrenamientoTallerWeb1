package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.interfaces.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProyectoController {

    private final ProyectoService proyectoService;

    @Autowired
    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping("/proyectos")
    public ModelAndView listarProyectos() {
        ModelMap model = new ModelMap();
        model.put("proyectos", proyectoService.obtenerTodos());
        return new ModelAndView("proyectos", model);
    }

    @GetMapping("/proyectos/nuevo")
    public ModelAndView nuevoProyecto() {
        return new ModelAndView("nuevo-proyecto");
    }

    @PostMapping("/proyectos")
    public ModelAndView crearProyecto(@RequestParam String nombre) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyectoService.guardar(proyecto);
        return new ModelAndView("redirect:/proyectos");
    }

    @GetMapping("/proyectos/{id}")
    public ModelAndView verProyecto(@PathVariable("id") Long id) {
        ModelMap model = new ModelMap();
        model.put("proyecto", proyectoService.obtenerPorId(id));
        return new ModelAndView("detalle-proyecto", model);
    }
}
