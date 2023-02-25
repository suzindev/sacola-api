package com.suzintech.sacola.service.impl;

import com.suzintech.sacola.controller.dto.ItemDTO;
import com.suzintech.sacola.enumeration.FormaPagamento;
import com.suzintech.sacola.model.Item;
import com.suzintech.sacola.model.Restaurante;
import com.suzintech.sacola.model.Sacola;
import com.suzintech.sacola.repository.ItemRepository;
import com.suzintech.sacola.repository.ProdutoRepository;
import com.suzintech.sacola.repository.SacolaRepository;
import com.suzintech.sacola.service.SacolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;

    private final ProdutoRepository produtoRepository;

    private final ItemRepository itemRepository;

    @Override
    public Item incluirItemNaSacola(ItemDTO itemDTO) {
        Sacola sacola = verSacola(itemDTO.getIdSacola());

        if (sacola.isFechada()) {
            throw new RuntimeException("Esta sacola está fechada.");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDTO.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDTO.getIdProduto()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse produto não existe!");
                        }
                ))
                .build();

        List<Item> itensDaSacola = sacola.getItens();
        if (itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();

            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaSacola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferentes. Feche a sacola ou esvazie.");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();
        itensDaSacola.forEach(item -> {
            double valorTotalItem = item.getProduto().getValorUnitario() * item.getQuantidade();
            valorDosItens.add(valorTotalItem);
        });

        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();

        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);

        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Essa sacola não existe!");
                });
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {
        Sacola sacola = verSacola(id);

        if (sacola.getItens().isEmpty()) {
            throw new RuntimeException("Inclua itens na sacola!");
        }

        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);

        return sacolaRepository.save(sacola);
    }
}
