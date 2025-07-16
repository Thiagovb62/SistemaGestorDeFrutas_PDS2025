// src/main/java/com/thiago/fruitmanagementsystem/Service/FrutaService.java
        package com.thiago.fruitmanagementsystem.Service;

        import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;
        import com.thiago.fruitmanagementsystem.Model.Fruta;
        import com.thiago.fruitmanagementsystem.Model.FrutaBuilder;
        import com.thiago.fruitmanagementsystem.Model.FrutaRequestDTO;
        import com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO;
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

            public List<FrutaResumoDTO> findFruitByName(String nome) {
                List<FrutaResumoDTO> frutas = frutaRepository.findAllByNomeResumo(nome);
                if (frutas.isEmpty()){
                    throw new EntityNotFoundException("Nenhuma fruta encontrada com o nome " + nome + "!");
                }
                return frutas;
            }

            public List<FrutaResumoDTO> getAllFruits(){
                return frutaRepository.findAllFrutas();
            }

            public List<FrutaResumoDTO> findAllByClassificacaoOrFrescaAndOrderByValorVendaIdAsc(int valorClassificacao, Boolean fresca) {
                List<FrutaResumoDTO> frutas = frutaRepository.findAllByClassificacaoAndFrescaResumo(ClassificacaoEnum.fromValor(valorClassificacao), fresca);
                if (frutas.isEmpty()) {
                    throw new EntityNotFoundException("Nenhuma fruta encontrada com os parâmetros informados!");
                }
                return frutas;
            }

            public List<FrutaResumoDTO> getFruitsByClassification(int Valorclassificacao) {
                ClassificacaoEnum classificacao = ClassificacaoEnum.fromValor(Valorclassificacao);
                List<FrutaResumoDTO> frutas = frutaRepository.findAllByClassificacaoResumo(classificacao);
                if (frutas.isEmpty()){
                    throw new EntityNotFoundException("Nenhuma fruta encontrada com a classificação " + classificacao + "!");
                }
                return frutas;
            }

            public List<FrutaResumoDTO> getFruitsByFreshness(Boolean fresca) {
                List<FrutaResumoDTO> frutas = frutaRepository.findAllByFrescaResumo(fresca);
                if (frutas.isEmpty()){
                    throw new EntityNotFoundException("Nenhuma fruta encontrada com a frescura " + fresca + "!");
                }
                return frutas;
            }

            public List<FrutaResumoDTO> getFruitsBySaleValueAsc(){
                return frutaRepository.findAllByValorVendaAscResumo();
            }

            public List<FrutaResumoDTO> getFruitsBySaleValueDesc(){
                return frutaRepository.findAllByValorVendaDescResumo();
            }

            // Os métodos de cadastro, atualização e exclusão permanecem iguais
            public void saveFruit(FrutaRequestDTO dto){
                Fruta builder = FrutaBuilder.builder()
                        .setNome(dto.nome())
                        .setClassificacao(dto.classificacao())
                        .setFresca(dto.fresca())
                        .setQtdDisponivel(dto.qtdDisponivel())
                        .setValorVenda(dto.valorVenda())
                        .build();
                Optional<Fruta> frutaExists = frutaRepository.findByNomeAndClassificacao(dto.nome(), ClassificacaoEnum.fromValor(dto.classificacao()));
                if (frutaExists.isPresent()){
                    throw new IllegalArgumentException("Fruta já cadastrada");
                }
                frutaRepository.save(builder);
            }

            public ResponseEntity<String> updateFruta(Long id, FrutaRequestDTO dto){
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

                return ResponseEntity.ok("Fruta atualizada com sucesso!");
            }

        }