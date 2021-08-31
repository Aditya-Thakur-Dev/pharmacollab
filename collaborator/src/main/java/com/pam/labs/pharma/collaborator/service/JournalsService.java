package com.pam.labs.pharma.collaborator.service;

import com.pam.labs.pharma.collaborator.model.Journal;

import java.util.List;

public interface JournalsService {

    List<Journal> getJournalsByTopicIdAndUserId(String topicId, String userId);

    Journal getJournalByJournalId(String journalId);

    Journal updateJournal(Journal journal);

    boolean deleteJournalByJournalIdAndJournalType(String journalId, String journalType);

    Journal createJournal(Journal journal);
}
