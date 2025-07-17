package leets.bookmark.domain.file.application.usecase;

import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.application.dto.response.PresignedUrlResponse;
import leets.bookmark.domain.file.application.mapper.FileMapper;
import leets.bookmark.domain.file.application.mapper.PreSignedMapper;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.service.FileSaveService;
import leets.bookmark.domain.file.domain.service.PreSignedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileUseCase {

    private final PreSignedService preSignedService;

    private final FileSaveService fileSaveService;

    private final FileMapper fileMapper;
    private final PreSignedMapper preSignedMapper;

    public PresignedUrlResponse getPreSignedUrl(String fileName){
        return preSignedMapper.toResponse(fileName, preSignedService.createPresignedUrl());
    }

    @Transactional
    public FileResponse saveFile(Long userId, Long bookmarkId, FileSaveRequest fileSaveRequest){
        File file = fileMapper.toFile(userId, 0L, fileSaveRequest);
        fileSaveService.save(file);
        return fileMapper.toFileResponse(file);
    }

}
