package com.pam.labs.pharma.collaborator.controller;

import com.pam.labs.pharma.collaborator.model.SampleRun;
import com.pam.labs.pharma.collaborator.service.SampleRunService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins={"http://localhost:3000"})
@RestController
@RequestMapping("/sampleRun")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SampleRunTriggerController {

    private final SampleRunService sampleRunService;

    @GetMapping("/find/all")
    public ResponseEntity<List<SampleRun>> getSampleRuns() {
        List<SampleRun> allSampleRuns = sampleRunService.getAllSampleRuns();
        if(CollectionUtils.isNotEmpty(allSampleRuns)) {
            return ResponseEntity.ok(allSampleRuns);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/run", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> setSampleRun(@Valid @RequestBody SampleRun run) {
        return sampleRunService.saveSampleRun(run);
    }
}
