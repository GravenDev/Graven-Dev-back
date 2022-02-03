package fr.gravendev.gravendevback.service.user.about;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.about.AboutIntro;
import fr.gravendev.gravendevback.model.user.about.AboutIntroModel;
import fr.gravendev.gravendevback.repository.user.about.AboutIntroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AboutIntroService {

    private final AboutIntroRepository aboutIntroRepository;

    @Autowired
    public AboutIntroService(AboutIntroRepository aboutIntroRepository) {
        this.aboutIntroRepository = aboutIntroRepository;
    }

    public void setIntro(User user, String text) {
        user.getAboutIntro().setText(text);

        aboutIntroRepository.save(user.getAboutIntro());
    }

    public AboutIntro create() {
        return AboutIntro.builder()
                .text("")
                .build();
    }

    public AboutIntroModel buildModel(User user) {
        return Optional.ofNullable(user.getAboutIntro())
                .filter(aboutIntro -> !aboutIntro.getText().isEmpty())
                .map(aboutIntro -> AboutIntroModel.builder()
                        .text(aboutIntro.getText())
                        .build())
                .orElse(null);
    }
}
