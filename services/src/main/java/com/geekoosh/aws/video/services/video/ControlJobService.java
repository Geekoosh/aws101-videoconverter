package com.geekoosh.aws.video.services.video;

import com.geekoosh.aws.video.model.ConvertJob;
import com.geekoosh.aws.video.repository.ConvertJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ControlJobService {
    @Autowired
    private ConvertJobRepository convertJobRepository;

    @Transactional(readOnly = false)
    public ConvertJob createConvertJob(String input) {
        ConvertJob convertJob = new ConvertJob();
        convertJob.setInput(input);
        convertJobRepository.save(convertJob);
        return convertJob;
    }

    @Transactional(readOnly = false)
    public void updateConvertJobThumbnail(Long id, String output) {
        ConvertJob convertJob = convertJobRepository.findOne(id);
        convertJob.setThumbnail(output);
        convertJobRepository.save(convertJob);
    }

    @Transactional(readOnly = false)
    public void updateConvertJobLowRes(Long id, String output) {
        ConvertJob convertJob = convertJobRepository.findOne(id);
        convertJob.setLowRes(output);
        convertJobRepository.save(convertJob);
    }

    @Transactional(readOnly = false)
    public void updateConvertJobHighRes(Long id, String output) {
        ConvertJob convertJob = convertJobRepository.findOne(id);
        convertJob.setHighRes(output);
        convertJobRepository.save(convertJob);
    }

    @Transactional
    public ConvertJob getById(Long id) {
        return convertJobRepository.findOne(id);
    }
}
