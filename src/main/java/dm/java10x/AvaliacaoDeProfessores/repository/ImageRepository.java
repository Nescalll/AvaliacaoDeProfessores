package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.model.abstracte.Image;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findImageByProfessorModel(ProfessorModel professorModel);

}
