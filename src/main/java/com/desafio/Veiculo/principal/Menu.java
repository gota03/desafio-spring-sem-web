package com.desafio.Veiculo.principal;

import com.desafio.Veiculo.model.DadosVeiculo;
import com.desafio.Veiculo.model.Modelos;
import com.desafio.Veiculo.model.Veiculo;
import com.desafio.Veiculo.service.ConsumoApi;
import com.desafio.Veiculo.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private final String API = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){

        System.out.println("Opções de veículos:");
        System.out.println("Carros");
        System.out.println("Motos");
        System.out.println("Caminhões");
        System.out.print("Digite qual opção você deseja: ");
        String opcaoVeiculo = sc.nextLine();
        String endereco;

        if(opcaoVeiculo.contains("carr")){
            endereco = API+"carros/marcas";
        } else if (opcaoVeiculo.contains("mot")) {
            endereco = API+"motos/marcas";
        }else{
            endereco = API+"caminhoes/marcas";
        }

        String json = consumoApi.obtemDados(endereco);
        List<DadosVeiculo> marcas = converteDados.obterLista(json, DadosVeiculo.class);
        marcas.stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        System.out.print("Informe o código da marca para consulta: ");
        String codigoMarca = sc.nextLine();
        endereco += "/" + codigoMarca + "/modelos";
        json = consumoApi.obtemDados(endereco);

        Modelos modeloLista = converteDados.obterDados(json, Modelos.class);
        System.out.println(modeloLista);
        modeloLista.modelos()
                .stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        System.out.print("Informe o nome do veículo para busca: ");
        String nomeVeiculo = sc.nextLine();

        List<DadosVeiculo> modelosFiltrados = modeloLista.modelos()
                .stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.print("Informe o código para consulta: ");
        String codigoModelo = sc.nextLine();
        endereco += "/" + codigoModelo + "/anos";
        json = consumoApi.obtemDados(endereco);

        List<DadosVeiculo> anosModelo = converteDados.obterLista(json, DadosVeiculo.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anosModelo.size(); i++) {
            String enderecoAnos = endereco + "/" + anosModelo.get(i).codigo();
            json = consumoApi.obtemDados(enderecoAnos);
            Veiculo veiculo = converteDados.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("Todos os veículos");
        veiculos.forEach(System.out::println);

    }
}
