package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileDeleteService {

    private final FileRepository fileRepository;

    public void delete(File file) {
        fileRepository.delete(file);
    }
}
