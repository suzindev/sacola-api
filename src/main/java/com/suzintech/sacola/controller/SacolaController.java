package com.suzintech.sacola.controller;

import com.suzintech.sacola.controller.dto.ItemDTO;
import com.suzintech.sacola.model.Item;
import com.suzintech.sacola.model.Sacola;
import com.suzintech.sacola.service.SacolaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Sacola")
@RequestMapping("/sacola")
public class SacolaController {

    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirItemNaSacola(@RequestBody ItemDTO itemDTO) {
        return sacolaService.incluirItemNaSacola(itemDTO);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id) {
        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{idSacola}")
    public Sacola fecharSacola(@PathVariable("idSacola") Long idSacola,
                               @RequestParam("formaPagamento") int formaPagamento) {
        return sacolaService.fecharSacola(idSacola, formaPagamento);
    }
}
