package com.darwin.galindo.agenda25.controller;

import com.darwin.galindo.agenda25.exception.EntityNotFoundException;
import com.darwin.galindo.agenda25.model.Contacto;
import com.darwin.galindo.agenda25.repo.ContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoRepository contactoRepository;

    @GetMapping("/")
    ModelAndView index(@PageableDefault(sort = "nombre", size = 5) Pageable pageable,
                       @RequestParam(required = false) String search) {
        Page<Contacto> contactos = contactoRepository
                .findByHack(search, pageable);

        return new ModelAndView("index")
                .addObject("contactos", contactos);
    }

    @GetMapping("/nuevo")
    ModelAndView nuevo() {
        return new ModelAndView("form")
                .addObject("contacto", new Contacto());
    }

    @PostMapping("/nuevo")
    ModelAndView crear(@Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("form")
                    .addObject("contacto", contacto);
        }
        contactoRepository.save(contacto);
        ra.addFlashAttribute("msgExito", "El contacto ha sido registrado correctamente");
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/{id}/editar")
    ModelAndView editar(@PathVariable Integer id) {
        Contacto contacto = contactoRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return new ModelAndView("form")
                .addObject("contacto", contacto);
    }

    @PostMapping("/{id}/editar")
    ModelAndView actualizar(
            @PathVariable Integer id,
            @Validated Contacto contacto,
            BindingResult bindingResult,
            RedirectAttributes ra
    ) {
        Contacto contactoFromDB = contactoRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("form")
                    .addObject("contacto", contacto);
        }
        contactoFromDB.setCelular(contactoFromDB.getCelular());
        contactoFromDB.setFechaNacimiento(contactoFromDB.getFechaNacimiento());
        contactoFromDB.setNombre(contactoFromDB.getNombre());
        contactoFromDB.setEmail(contactoFromDB.getEmail());
        contactoRepository.save(contactoFromDB);

        ra.addFlashAttribute("msgExito", "El contacto ha sido actualizado correctamente");
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/{id}/eliminar")
    String eliminar(
            @PathVariable Integer id,
            RedirectAttributes ra
    ) {
        Contacto contacto = contactoRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        contactoRepository.delete(contacto);
        ra.addFlashAttribute("msgExito", "El contacto ha sido eliminado");

        return "redirect:/";
    }
}
