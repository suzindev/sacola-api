package com.suzintech.sacola.service;

import com.suzintech.sacola.controller.dto.ItemDTO;
import com.suzintech.sacola.model.Item;
import com.suzintech.sacola.model.Sacola;

public interface SacolaService {

    Item incluirItemNaSacola(ItemDTO itemDTO);

    Sacola verSacola(Long id);

    Sacola fecharSacola(Long id, int formaPagamento);
}
