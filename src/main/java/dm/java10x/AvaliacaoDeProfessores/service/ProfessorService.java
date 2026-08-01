package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.dto.ProfessorUpdateDTO;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.TipoDaNotificacao;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.AvaliacaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.Image;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.NotificacaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.TurmaModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AulaModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProfessorService {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ImageRepository image;

    @Autowired
    private NotificacaoService notificacaoService;

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

    public List<ProfessorModel> filtrarPorTurma(Turma turma){
        System.out.println("Turma: "+turma);
        List<TurmaModel> turmas = turmaRepository.findTurmaModelByTurma(turma);
        System.out.println("Turma encontrada");
        List<ProfessorModel> listaDeProfessores = new ArrayList<>();
        for (TurmaModel turminha: turmas){
            if (! listaDeProfessores.contains(turminha.getProfessorModel())){
                listaDeProfessores.add(turminha.getProfessorModel());}
        }
        return listaDeProfessores;
    }

    public void reportarComentariosPeloId(Long id){
        Optional<AulaModel> aula = aulaRepository.findById(id);
        if (aula.isPresent()){
            NotificacaoModel notificacaoModel = new NotificacaoModel(TipoDaNotificacao.BLOQUIO_DE_PALAVRA, aula.get().getId(), "Comentario inapropriado");
            notificacaoService.creat(notificacaoModel);
        }
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

    public Map<Long, String> buscarComentariosPeloId(Long id){
        ProfessorModel professor = findById(id);
        List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findByProfessorModel(professor);
        Map<Long, String> comentarios = new HashMap<>();
        for (AvaliacaoModel avaliacao: avaliacoes){
            comentarios.put(avaliacao.getAulaModel().getId() ,avaliacao.getAulaModel().getComentario());
        }
        return comentarios;
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
