package com.pam.labs.pharma.collaborator.controller;

import com.pam.labs.pharma.collaborator.common.JournalTypes;
import com.pam.labs.pharma.collaborator.model.Journal;
import com.pam.labs.pharma.collaborator.service.JournalsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins={"http://localhost:3000"})
@RestController
@RequestMapping("/journals")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Validated
public class JournalsController {

    private final JournalsService journalsService;

    @GetMapping(value = "/getJournalById", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Journal> getJournalById(@Valid @RequestParam String journalId) {
        Journal journalsByJournalId= journalsService.getJournalByJournalId(journalId);
        return Objects.nonNull(journalsByJournalId)? new ResponseEntity<>(journalsByJournalId, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping(value = "/createJournal", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Journal> createJournal(@Valid @RequestBody @NotNull Journal journal) {
        Journal savedJournal= journalsService.createJournal(journal);
        return Objects.nonNull(savedJournal)? new ResponseEntity<>(savedJournal, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping(value = "/updateJournal", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Journal> updateJournal(@Valid @RequestBody @NotNull Journal journal) {
        Journal savedJournal= journalsService.updateJournal(journal);
        return Objects.nonNull(savedJournal)? new ResponseEntity<>(savedJournal, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping(value = "/deleteJournalByJournalIdAndJournalType", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteJournalByJournalIdAndJournalType(@Valid @RequestParam @NotBlank String journalId,
                                                                                   @Valid @RequestParam @NotBlank String journalType) {
        boolean hasDeleted = journalsService.deleteJournalByJournalIdAndJournalType(journalId, journalType);
        return hasDeleted? new ResponseEntity<>("Journal Deleted Successfully", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/getJournalsByTopicIdAndUserId")
    public ResponseEntity<List<Journal>> getJournalsByTopicIdAndUserId(@Valid @RequestParam @NotBlank String topicId,
                                                                       @Valid @RequestParam @NotBlank String userId) {
        List<Journal> journals = journalsService.getJournalsByTopicIdAndUserId(topicId, userId);
        return CollectionUtils.isNotEmpty(journals)? new ResponseEntity<>(journals, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getJournalTypes")
    public ResponseEntity<List<String>> getJournalTypes() {
        List<String> userRoles = Stream.of(JournalTypes.values())
                .map(JournalTypes::name)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }
}
