package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.dto.ProfessorUpdateDTO;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Adjetivo;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Melhorias;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.AvaliacaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.Image;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.TurmaModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.repository.AvaliacaoRepository;
import dm.java10x.AvaliacaoDeProfessores.repository.ImageRepository;
import dm.java10x.AvaliacaoDeProfessores.repository.ProfessorRepository;
import dm.java10x.AvaliacaoDeProfessores.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ImageRepository image;

    public List<ProfessorModel> findAll(){
        return professorRepository.findAll();
    }

    public ProfessorModel findById(long id){
        Optional<ProfessorModel> professorModel = this.professorRepository.findById(id);
        return professorModel.orElseThrow(() -> new RuntimeException(
                "Professor não encontrado"
        ));
    }

    public UserDetails findByEmail(String email){
        return professorRepository.findByEmail(email);
    }

    public ProfessorModel findProfessorModelByEmail(String email){
        return professorRepository.findProfessorModelByEmail(email);
    }

    @Transactional
    public ProfessorModel create(ProfessorModel obj, List<Turma> turmas, Optional<MultipartFile> file){
        obj = this.professorRepository.save(obj);
        for (Turma t: turmas){
            TurmaModel novaTurma = new TurmaModel(t, obj);
            turmaRepository.save(novaTurma);
        }
        return obj;
    }

    @Transactional
    public ProfessorModel update(ProfessorUpdateDTO obj, Long id){
        ProfessorModel newProfessor = findById(id);
        if (Objects.nonNull(obj.email())){newProfessor.setEmail(obj.email());}
        if (Objects.nonNull(obj.materia())){newProfessor.setMateria(obj.materia());}
        if (Objects.nonNull(obj.nome())){newProfessor.setNome(obj.nome());}
        if (Objects.nonNull(obj.turmas())){atualizaTurma(obj.turmas(), newProfessor);}

        if (Objects.nonNull(obj.file()) && !obj.file().isEmpty()){
            atualizaImage(obj.file(), newProfessor);
        }

        return this.professorRepository.save(newProfessor);
    }

    @Transactional
    public void delete(long id){
        ProfessorModel professor = findById(id);
        try {
            this.avaliacaoRepository.deleteByProfessorModel(professor);
            this.turmaRepository.deleteByProfessorModel(professor);
            this.professorRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas");
        }
    }

    public Integer mediaDoProfessorPorId(long id){
        ProfessorModel professor = findById(id);
        int cont = 0;
        int soma = 0;
        List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findByProfessorModel(professor);
        for (AvaliacaoModel avaliacao: avaliacoes){
            soma += avaliacao.getAulaModel().getNota();
            cont ++;
        }
        if (cont > 0){return soma/cont;}
        else{ return 0;}
    }

    public Map<Adjetivo, Integer> adjetivos(long id){
        ProfessorModel professor = findById(id);
        Map<Adjetivo, Integer> adjMap = new HashMap<>();
        adjMap.put(Adjetivo.OTIMO, 0);
        adjMap.put(Adjetivo.BOM, 0);
        adjMap.put(Adjetivo.MEDIO, 0);
        adjMap.put(Adjetivo.RUIM, 0);
        Adjetivo[] adjetivos = {Adjetivo.OTIMO, Adjetivo.BOM, Adjetivo.MEDIO, Adjetivo.RUIM};
        List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findByProfessorModel(professor);
        for (AvaliacaoModel avaliacao: avaliacoes){
            if (avaliacao.getAulaModel().getAdjetivo().equals(adjetivos[0])){
                adjMap.compute(Adjetivo.OTIMO, (key, value) ->  value + 1);
            }
            else if (avaliacao.getAulaModel().getAdjetivo().equals(adjetivos[1])){
                adjMap.compute(Adjetivo.BOM, (key, value) ->  value + 1);
            }
            else if (avaliacao.getAulaModel().getAdjetivo().equals(adjetivos[2])){
                adjMap.compute(Adjetivo.MEDIO, (key, value) ->  value + 1);
            }
            else {adjMap.compute(Adjetivo.PESSIMO, (key, value) ->  value + 1);}
        }
        return adjMap;
    }

    public List<Melhorias> melhorias(Long id){
        Map<Melhorias, Integer> mapaDeMelhorias = new HashMap<>();
        ProfessorModel professor = professorRepository.findProfessorModelById(id);
        List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findByProfessorModel(professor);
        for (AvaliacaoModel avaliacao: avaliacoes){
            for (Melhorias melhoria: avaliacao.getAulaModel().getMelhorias()){
                if (mapaDeMelhorias.containsKey(melhoria)){
                    mapaDeMelhorias.compute(melhoria, (key, value) -> value ++);
                } else {
                    mapaDeMelhorias.put(melhoria,0);
                }
            }
        }
        List<Melhorias> melhoriasMaisListadas = List.of(null, null, null);
        List<Integer> valoresMaisListados = List.of(0, 0, 0);
        for (Map.Entry<Melhorias, Integer> valor: mapaDeMelhorias.entrySet()){
            if (valor.getValue() > valoresMaisListados.get(0)){
                melhoriasMaisListadas.add(0, valor.getKey());
                valoresMaisListados.add(0, valor.getValue());
            } else if (valor.getValue() > valoresMaisListados.get(1)) {
                melhoriasMaisListadas.add(1, valor.getKey());
                valoresMaisListados.add(1, valor.getValue());
            } else if (valor.getValue() > valoresMaisListados.get(2)) {
                melhoriasMaisListadas.add(2, valor.getKey());
                valoresMaisListados.add(2, valor.getValue());
            }
        }
        return  melhoriasMaisListadas;
    }
    public List<ProfessorModel> filtrarPorTurma(Turma turma){
        List<TurmaModel> turmas = turmaRepository.findTurmaModelByTurma(turma);
        List<ProfessorModel> listaDeProfessores = new ArrayList<>();
        for (TurmaModel turminha: turmas){
            if (! listaDeProfessores.contains(turminha.getProfessorModel())){
                listaDeProfessores.add(turminha.getProfessorModel());}
        }
        return listaDeProfessores;
    }

    public List<ProfessorModel> filtrarProfessoresNaoAvaliadosEstaSemana(List<ProfessorModel> professores, AlunoModel aluno){
        List<ProfessorModel> professoresFiltrados = new ArrayList<>();
        for (ProfessorModel professor: professores){
            List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findByProfessorModelAndAlunoModel(professor, aluno);
            if (!avaliacaoService.foiAvaliadoNessaSemana(avaliacoes)){
                professoresFiltrados.add(professor);
            }
        }
        return  professoresFiltrados;
    }
    @Transactional
    public void atualizaImage(MultipartFile file, ProfessorModel professor){
        try {
            // Verifica se o professor já tem imagem
            Image existingImage = this.image.findImageByProfessorModel(professor);
            if (existingImage != null) {
                this.image.delete(existingImage);
            }
            uploadImage(file, professor);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao atualizar imagem", e);
        }
    }

    @Transactional
    public void atualizaTurma(List<Turma> turmas, ProfessorModel obj){
        turmaRepository.deleteByProfessorModel(obj);
        for (Turma t: turmas){
            TurmaModel novaTurma = new TurmaModel(t, obj);
            turmaRepository.save(novaTurma);
        }
    }

    @Transactional
    public Image uploadImage(MultipartFile file, ProfessorModel professor) throws IOException {
        Image imageData = new Image(professor.getNome(), file.getContentType(), file.getBytes(), professor);
        this.image.save(imageData);
        return imageData;
    }

    public Image downloadImage(Long id) {
        Optional<Image> imagem = this.image.findById(id);
        if (imagem.isPresent()) {
            return imagem.get();
        }
        else {
            return imagem.orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
        }
    }
}
