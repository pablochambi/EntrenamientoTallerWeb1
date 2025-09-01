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

    @GetMapping("/proyectos/{proyectoId}/tareas")
    public ModelAndView mostrarTareasPorProyecto(@PathVariable("proyectoId") Long proyectoId) {
        ModelMap model = new ModelMap();
        model.put("tareas", tareaService.obtenerPorProyecto(proyectoId));
        return new ModelAndView("tareas", model);
    }

    @PostMapping("/proyectos/{proyectoId}/tareas")
    public ModelAndView agregarTarea(@PathVariable("proyectoId") Long proyectoId, @RequestParam String nombre) {
        Tarea tarea = new Tarea();
        tarea.setNombre(nombre);
        tarea.setCompletada(false);
        tareaService.agregar(proyectoId, tarea);
        return new ModelAndView("redirect:/proyectos/" + proyectoId + "/tareas");
    }

    @PostMapping("/tareas/{id}/completar")
    public ModelAndView marcarComoCompletada(@PathVariable("id") Long id) {
        Tarea tarea = tareaService.marcarComoCompletada(id);
        return new ModelAndView("redirect:/proyectos/" + tarea.getProyecto().getId() + "/tareas");
    }

    @GetMapping("/tareas/{id}")
    public ModelAndView obtenerTareaPorId(@PathVariable("id") Long id) {
        ModelMap model = new ModelMap();
        Tarea tarea = tareaService.obtenerPorId(id);
        model.put("tarea", tarea);
        return new ModelAndView("detalle-tarea", model);
    }

    @PostMapping("/tareas/{id}/eliminar")
    public ModelAndView eliminarTarea(@PathVariable("id") Long id) {
        Long proyectoId = tareaService.eliminar(id);
        return new ModelAndView("redirect:/proyectos/" + proyectoId + "/tareas");
    }

    @PostMapping("/tareas/{id}/actualizar")
    public ModelAndView actualizarTarea(@PathVariable("id") Long id, @RequestParam String nombre, @RequestParam boolean completada) {
        Tarea tareaExistente = tareaService.obtenerPorId(id);
        tareaExistente.setNombre(nombre);
        tareaExistente.setCompletada(completada);
        tareaService.actualizar(tareaExistente);
        return new ModelAndView("redirect:/proyectos/" + tareaExistente.getProyecto().getId() + "/tareas");
    }
}
