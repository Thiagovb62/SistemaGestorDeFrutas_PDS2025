package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;
import com.thiago.fruitmanagementsystem.Model.Fruta;
import com.thiago.fruitmanagementsystem.Model.FrutasFindBysDTO;
import com.thiago.fruitmanagementsystem.Model.FrutaRequestDTO;
import com.thiago.fruitmanagementsystem.Repository.FrutaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrutaService {

    private final FrutaRepository frutaRepository;

    public FrutaService(FrutaRepository frutaRepository) {
        this.frutaRepository = frutaRepository;
    }

    public List<Fruta> findFruitByName(FrutasFindBysDTO dto){
        var name = dto.nome();
        List<Fruta> frutas = frutaRepository.findAllByNome(name);
        if (frutas.isEmpty()){
            throw new EntityNotFoundException("Nenhuma fruta encontrada com o nome " + name + "!");
        }
        return frutas;
    }

    public List<Fruta> getAllFruits(){
        return frutaRepository.findAll();
    }

    public List<Fruta> findAllByClassificacaoOrFrescaAndOrderByValorVendaIdAsc(FrutasFindBysDTO dto) {
        List<Fruta> frutas = frutaRepository.findAllByClassificacaoAndFrescaAndOrderByValorVendaIdAsc(ClassificacaoEnum.fromValor(dto.classificacao()), dto.fresca());
        if (frutas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma fruta encontrada com os parâmetros informados!");
        }
        return frutas;
    }

    public List<Fruta> getFruitsByClassification(FrutasFindBysDTO dto){
        ClassificacaoEnum classificacao = ClassificacaoEnum.fromValor(dto.classificacao());
        List<Fruta> frutas = frutaRepository.findAllByClassificacaoAndOrderByNome(classificacao);
        if (frutas.isEmpty()){
            throw new EntityNotFoundException("Nenhuma fruta encontrada com a classificação " + classificacao + "!");
        }
        return frutas;
    }

    public List<Fruta> getFruitsByFreshness(FrutasFindBysDTO dto){
        List<Fruta> frutas = frutaRepository.findAllByFrescaAndOrderByNome(dto.fresca());
        if (frutas.isEmpty()){
            throw new EntityNotFoundException("Nenhuma fruta encontrada com a frescura " + dto.fresca() + "!");
        }
        return frutas;
    }

    public List<Fruta> getFruitsByAvailableQuantity(){
        return frutaRepository.findAllByQtdDisponivelNotNull();
    }

    public List<Fruta> getFruitsBySaleValueAsc(){
        return frutaRepository.findAllByValorVendaAsc();
    }

    public List<Fruta> getFruitsBySaleValueDesc(){
        return frutaRepository.findAllByValorVendaDesc();
    }

    public void saveFruit(FrutaRequestDTO dto){

        Fruta fruta = new Fruta(dto);
        Optional<Fruta> frutaExists = frutaRepository.findByNomeAndClassificacao(fruta.getNome(), fruta.getClassificacao());
        if (frutaExists.isPresent()){
            throw new IllegalArgumentException("Fruta já cadastrada");
        }
        frutaRepository.save(fruta);
    }

    public ResponseEntity<Fruta> updateFruta(Long id, FrutaRequestDTO dto){
        Optional<Fruta> fruta = frutaRepository.findById(id);

        if (fruta.isPresent()){
            if (!dto.nome ().isBlank ()) fruta.get ().setNome (dto.nome ());
            if (dto.classificacao () != null){
                var classificacao = ClassificacaoEnum.fromValor (dto.classificacao ());
                fruta.get ().setClassificacao (classificacao);
            }
            if (dto.fresca () != null) fruta.get ().setFresca (dto.fresca ());
            if (dto.qtdDisponivel () != null) fruta.get ().setQtdDisponivel (dto.qtdDisponivel ());
            if (dto.valorVenda () != null) fruta.get ().setValorVenda (dto.valorVenda ());
        } else {
            throw new EntityNotFoundException("Fruta não encontrada com o id " + id + "!");
        }

        return ResponseEntity.ok(frutaRepository.save(fruta.get()));
    }

    public void deleteFruta(Long id){
       Optional<Fruta> fruta =  frutaRepository.findById(id);
       if (fruta.isEmpty ())
              throw new EntityNotFoundException("Fruta não encontrada com o id " + id + "!");
       frutaRepository.deleteById(id);
    }
}
