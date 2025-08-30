package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactoController {

    @GetMapping("/contacto/nuevo")
    public ModelAndView mostrarFormulario() {

        ModelMap modelo = new ModelMap();
        Contacto contacto = new Contacto();

        modelo.put("contacto", contacto);

        return new ModelAndView("contacto", modelo);
    }

    @PostMapping("/contacto/guardar")
    public ModelAndView procesarFormulario(@ModelAttribute("contacto") Contacto contacto) {

        ModelMap modelo = new ModelMap();

        modelo.put("contacto", contacto);

        return new ModelAndView("confirmacion",modelo);
    }

}
