package com.suzintech.sacola.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long idProduto;
    private int quantidade;
    private Long idSacola;
}
