package com.desafio.Veiculo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverterDados{
    ObjectMapper mapper = new JsonMapper();
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try{
            return mapper.readValue(json, classe);
        }catch (JsonProcessingException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);
        try{
            return mapper.readValue(json, lista);
        }catch (JsonProcessingException exception){
            throw new RuntimeException(exception);
        }
    }
}
