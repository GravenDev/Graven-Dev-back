package fr.gravendev.gravendevback.service.user.about;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.about.AboutInfo;
import fr.gravendev.gravendevback.entity.user.about.AboutInfoEntry;
import fr.gravendev.gravendevback.model.user.about.AboutInfoModel;
import fr.gravendev.gravendevback.repository.user.about.AboutInfoEntryRepository;
import fr.gravendev.gravendevback.repository.user.about.AboutInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AboutInfoService {

    private final AboutInfoRepository aboutInfoRepository;
    private final AboutInfoEntryRepository aboutInfoEntryRepository;

    @Autowired
    public AboutInfoService(AboutInfoRepository aboutInfoRepository,
                            AboutInfoEntryRepository aboutInfoEntryRepository) {
        this.aboutInfoRepository = aboutInfoRepository;
        this.aboutInfoEntryRepository = aboutInfoEntryRepository;
    }

    public Optional<AboutInfo> get(User user, Long AboutInfoId) {
        return aboutInfoRepository.findByUserAndAboutInfoEntryId(user, AboutInfoId);
    }

    public void set(User user, AboutInfoModel aboutInfoModel) {
        AboutInfo aboutInfo = aboutInfoRepository.findByUserAndAboutInfoEntryId(user, aboutInfoModel.getId())
                .orElseGet(() -> create(user, aboutInfoModel));

        aboutInfo.setValue(aboutInfoModel.getValue());

        aboutInfoRepository.save(aboutInfo);
    }

    public void remove(User user, Long AboutInfoId) {
        AboutInfo aboutInfo = aboutInfoRepository.findByUserAndAboutInfoEntryId(user, AboutInfoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AboutInfoEntry not found"));

        aboutInfoRepository.delete(aboutInfo);
    }

    public AboutInfo create(User user, AboutInfoModel aboutInfoModel) {
        AboutInfoEntry aboutInfoEntry = aboutInfoEntryRepository.findById(aboutInfoModel.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AboutInfoEntry not found"));

        AboutInfo aboutInfo = AboutInfo.builder()
                .user(user)
                .aboutInfoEntry(aboutInfoEntry)
                .value(aboutInfoModel.getValue())
                .build();

        user.getAboutInfo().add(aboutInfo);

        return aboutInfoRepository.save(aboutInfo);
    }

    public List<AboutInfoModel> buildModel(User user) {
        return buildModel(user.getAboutInfo());
    }

    public List<AboutInfoModel> buildModel(Collection<AboutInfo> aboutInfos) {
        return aboutInfos.stream()
                .distinct()
                .sorted(Comparator.comparing(aboutInfo -> aboutInfo.getAboutInfoEntry().getPosition()))
                .map(this::buildModel)
                .toList();
    }

    public AboutInfoModel buildModel(AboutInfo aboutInfo) {
        return AboutInfoModel.builder()
                .name(aboutInfo.getAboutInfoEntry().getName())
                .icon(aboutInfo.getAboutInfoEntry().getIcon())
                .value(aboutInfo.getValue())
                .build();
    }
}
