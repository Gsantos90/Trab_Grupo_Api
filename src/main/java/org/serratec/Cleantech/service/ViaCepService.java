package org.serratec.Cleantech.service;

import org.serratec.Cleantech.dto.EnderecoViaCepDTO;
import org.serratec.Cleantech.exception.InvalidCepException;
import org.serratec.Cleantech.exception.ViaCepNotFoundException;
import org.serratec.Cleantech.exception.ViaCepUnavailableException;
import org.serratec.Cleantech.Domain.CepCacheEntity;
import org.serratec.Cleantech.repository.CepCacheRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate;
    private final String viaCepUrlTemplate;
    private final CepCacheRepository cepCacheRepository;

    public ViaCepService(RestTemplate restTemplate,
                         CepCacheRepository cepCacheRepository,
                         @Value("${viacep.url:https://viacep.com.br/ws/{cep}/json/}") String viaCepUrlTemplate) {
        this.restTemplate = restTemplate;
        this.cepCacheRepository = cepCacheRepository;
        this.viaCepUrlTemplate = viaCepUrlTemplate;
    }

    private String sanitizeCep(String cep) {
        if (cep == null) throw new InvalidCepException(null);
        String onlyDigits = cep.replaceAll("\\D", "");
        if (onlyDigits.length() != 8) throw new InvalidCepException(cep);
        return onlyDigits;
    }

    @Cacheable(value = "ceps", key = "#cep")
    public EnderecoViaCepDTO buscarEnderecoPorCep(String cep) {
        String cepSanitizado = sanitizeCep(cep);

   
        var cacheOpt = cepCacheRepository.findByCep(cepSanitizado);
        if (cacheOpt.isPresent()) {
            CepCacheEntity cache = cacheOpt.get();
            cache.setUltimaConsulta(LocalDateTime.now());
            cepCacheRepository.save(cache); 

            EnderecoViaCepDTO dto = new EnderecoViaCepDTO();
            dto.setCep(cache.getCep());
            dto.setLogradouro(cache.getLogradouro());
            dto.setBairro(cache.getBairro());
            dto.setLocalidade(cache.getLocalidade());
            dto.setUf(cache.getUf());
            return dto;
        }

      
        try {
            EnderecoViaCepDTO resp = restTemplate.getForObject(viaCepUrlTemplate, EnderecoViaCepDTO.class, cepSanitizado);

            if (resp == null) throw new ViaCepUnavailableException("Resposta vazia do ViaCEP");
            if (Boolean.TRUE.equals(resp.getErro())) throw new ViaCepNotFoundException(cepSanitizado);

            CepCacheEntity novoCache = new CepCacheEntity(
                    resp.getCep(), resp.getLogradouro(), resp.getBairro(), resp.getLocalidade(), resp.getUf()
            );
            cepCacheRepository.save(novoCache);

            return resp;
        } catch (ResourceAccessException ex) {
            throw new ViaCepUnavailableException("Não foi possível acessar o ViaCEP: " + ex.getMessage());
        } catch (InvalidCepException | ViaCepNotFoundException | ViaCepUnavailableException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ViaCepUnavailableException("Erro ao consultar ViaCEP: " + ex.getMessage());
        }
    }
}
