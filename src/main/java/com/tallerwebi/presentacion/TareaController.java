package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TareaController {

    private final TareaService tareaService;

    @Autowired
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping("/tareas")
    public ModelAndView mostrarTareas() {
        ModelMap model = new ModelMap();
        model.put("tareas", tareaService.obtenerTodas());
        return new ModelAndView("tareas", model);
    }

    @PostMapping("/tareas")
    public ModelAndView agregarTarea(@RequestParam String nombre) {
        Tarea tarea = new Tarea();
        tarea.setNombre(nombre);
        tarea.setCompletada(false);
        tareaService.agregar(tarea);
        return new ModelAndView("redirect:/tareas");
    }

    @PostMapping("/tareas/{id}/completar")
    public ModelAndView marcarComoCompletada(@PathVariable("id") Long id) {
        tareaService.marcarComoCompletada(id);
        return new ModelAndView("redirect:/tareas");
    }
}
