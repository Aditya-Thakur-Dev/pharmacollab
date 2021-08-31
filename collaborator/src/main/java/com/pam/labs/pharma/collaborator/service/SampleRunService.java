package com.pam.labs.pharma.collaborator.service;

import com.pam.labs.pharma.collaborator.model.SampleRun;
import com.pam.labs.pharma.collaborator.entity.SampleRunsDTO;
import com.pam.labs.pharma.collaborator.repository.SampleRunsRepository;
import com.pam.labs.pharma.collaborator.util.ConversionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SampleRunService {
    private final SampleRunsRepository sampleRunsRepository;
    private final ConversionUtil conversionUtil;

    public List<SampleRun> getAllSampleRuns() {
        List<SampleRunsDTO> allSampleRuns = sampleRunsRepository.findAll();
        return allSampleRuns.stream().map(sampleRunDTO -> SampleRun.builder()
                .userName(sampleRunDTO.getTestRunUser())
                .runtime(conversionUtil.convertUtilDateToLocalDateTime(sampleRunDTO.getTestRuntime()))
                .build()).collect(Collectors.toList());
    }

    public ResponseEntity<String> saveSampleRun(SampleRun run) {
        SampleRunsDTO sampleRunsDTO = new SampleRunsDTO();
        sampleRunsDTO.setTestRuntime(conversionUtil.convertLocalDateTimeToUtilDate(LocalDateTime.now()));
        sampleRunsDTO.setTestRunUser(run.getUserName());
        sampleRunsRepository.save(sampleRunsDTO);
        return ResponseEntity.ok("Saved sample run for " + run.getUserName());
    }
}
