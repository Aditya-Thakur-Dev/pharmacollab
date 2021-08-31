package com.pam.labs.pharma.collaborator.service.impl;

import com.pam.labs.pharma.collaborator.common.JournalStatus;
import com.pam.labs.pharma.collaborator.common.JournalTypes;
import com.pam.labs.pharma.collaborator.entity.ExploratoryDiscoveryDTO;
import com.pam.labs.pharma.collaborator.entity.JournalsDTO;
import com.pam.labs.pharma.collaborator.entity.LaterStageDiscoveryDTO;
import com.pam.labs.pharma.collaborator.entity.PreclinicalTrialsDTO;
import com.pam.labs.pharma.collaborator.model.ExploratoryDiscovery;
import com.pam.labs.pharma.collaborator.model.Journal;
import com.pam.labs.pharma.collaborator.model.LaterStageDiscovery;
import com.pam.labs.pharma.collaborator.model.PreclinicalTrial;
import com.pam.labs.pharma.collaborator.repository.ExploratoryDiscoveryRepository;
import com.pam.labs.pharma.collaborator.repository.JournalsRepository;
import com.pam.labs.pharma.collaborator.repository.LaterStageDiscoveryRepository;
import com.pam.labs.pharma.collaborator.repository.PreclinicalTrialsRepository;
import com.pam.labs.pharma.collaborator.service.JournalsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JournalsServiceImpl implements JournalsService {

    public final JournalsRepository journalsRepository;
    public final ExploratoryDiscoveryRepository exploratoryDiscoveryRepository;
    public final LaterStageDiscoveryRepository laterStageDiscoveryRepository;
    public final PreclinicalTrialsRepository preclinicalTrialsRepository;

    @Override
    public List<Journal> getJournalsByTopicIdAndUserId(String topicId, String userId) {
        List<List<Object>> journalsDTOs = journalsRepository.getAllJournals(topicId, userId);
        Map<String, ExploratoryDiscoveryDTO> exploratoryDiscoveryDTOS = getTypedObjects(journalsDTOs, ExploratoryDiscoveryDTO.class).stream()
                .collect(Collectors.groupingBy(ExploratoryDiscoveryDTO::getJournalId,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0))));
        Map<String, LaterStageDiscoveryDTO> laterStageDiscoveryDTOS = getTypedObjects(journalsDTOs, LaterStageDiscoveryDTO.class).stream()
                .collect(Collectors.groupingBy(LaterStageDiscoveryDTO::getJournalId,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0))));
        Map<String, PreclinicalTrialsDTO> preclinicalTrialsDTOS = getTypedObjects(journalsDTOs, PreclinicalTrialsDTO.class).stream()
                .collect(Collectors.groupingBy(PreclinicalTrialsDTO::getJournalId,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0))));
        List<JournalsDTO> journalsDTOS = getTypedObjects(journalsDTOs, JournalsDTO.class);
        return journalsDTOS.stream()
                .map(dto -> {
                    String journalType = dto.getType();
                    if(JournalTypes.EXPLORATORY_DISCOVERY.getCode().equalsIgnoreCase(journalType)){
                        Journal journal = getJournal(dto);
                        journal.setExploratoryDiscovery(getExploratoryDiscovery(exploratoryDiscoveryDTOS.get(dto.getJournalId())));
                        return journal;
                    } else if(JournalTypes.LATER_STAGE_DISCOVERY.getCode().equalsIgnoreCase(journalType)){
                        Journal journal = getJournal(dto);
                        journal.setLaterStageDiscovery(getLaterStageDiscovery(laterStageDiscoveryDTOS.get(dto.getJournalId())));
                        return journal;
                    } else if(JournalTypes.PRECLINICAL_TRIAL.getCode().equalsIgnoreCase(journalType)){
                        Journal journal = getJournal(dto);
                        journal.setPreclinicalTrial(getPreclinicalTrial(preclinicalTrialsDTOS.get(dto.getJournalId())));
                        return journal;
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Journal getJournalByJournalId(String journalId) {
        JournalsDTO journalsDTOs = journalsRepository.findByJournalId(journalId);
        return null != journalsDTOs ? getJournal(journalsDTOs) : null;
    }

    @Override
    public Journal updateJournal(Journal journal) {
        if(ObjectUtils.anyNotNull(journal.getExploratoryDiscovery(), journal.getLaterStageDiscovery(), journal.getPreclinicalTrial())){
            JournalsDTO journalsDTO = getJournalsDTO(journal, null);
            JournalsDTO saveJournalsDTO = journalsRepository.save(journalsDTO);
            Journal updatedJournal = getJournal(saveJournalsDTO);
            createOrUpdateTypedJournal(journal, updatedJournal, null, null);
            return updatedJournal;
        }
        return null;
    }

    @Override
    public boolean deleteJournalByJournalIdAndJournalType(String journalId, String journalType){
        if(JournalTypes.EXPLORATORY_DISCOVERY.name().equalsIgnoreCase(journalType)){
            exploratoryDiscoveryRepository.deleteAllByJournalId(journalId);
        } else if(JournalTypes.LATER_STAGE_DISCOVERY.name().equalsIgnoreCase(journalType)){
            laterStageDiscoveryRepository.deleteAllByJournalId(journalId);
        } else if(JournalTypes.PRECLINICAL_TRIAL.name().equalsIgnoreCase(journalType)){
            preclinicalTrialsRepository.deleteAllByJournalId(journalId);
        }
        journalsRepository.deleteAllByJournalId(journalId);
        List<JournalsDTO> journalsDTOs = journalsRepository.findAllByJournalId(journalId);
        return CollectionUtils.isEmpty(journalsDTOs);
    }

    @Override
    public Journal createJournal(Journal journal) {
        if(Objects.nonNull(journal)){
            JournalsDTO journalsDTO = getJournalsDTO(journal, new Date());
            JournalsDTO savedJournalsDTO = journalsRepository.save(journalsDTO);
            Journal updatedJournal = getJournal(savedJournalsDTO);
            createOrUpdateTypedJournal(journal, updatedJournal, new Date(), JournalStatus.TODO.getCode());
            return updatedJournal;
        }
        return null;
    }

    private JournalsDTO getJournalsDTO(Journal journal, Date createDate) {
        String journalId = Objects.nonNull(journal.getJournalId())? journal.getJournalId() :
                StringUtils.join("JOURNAL_ID_", journalsRepository.getJournalIdSeqNextValue().toPlainString());
        JournalsDTO journalsDTO = new JournalsDTO();
        journalsDTO.setJournalId(journalId);
        journalsDTO.setJournalName(journal.getJournalName());
        journalsDTO.setTopicId(journal.getTopicId());
        journalsDTO.setUserId(journal.getUserId());
        journalsDTO.setType(JournalTypes.getJournalTypeCodeByJournalType(journal.getType()));
        if(Objects.nonNull(createDate)){
            journalsDTO.setCreated(createDate);
        }
        journalsDTO.setUpdated(new Date());
        return journalsDTO;
    }

    private Journal getJournal(JournalsDTO journalsDTO) {
        Journal journal = new Journal();
        journal.setJournalId(journalsDTO.getJournalId());
        journal.setTopicId(journalsDTO.getTopicId());
        journal.setUserId(journalsDTO.getUserId());
        journal.setJournalName(journalsDTO.getJournalName());
        journal.setType(JournalTypes.getJournalTypeByCode(journalsDTO.getType()));
        return journal;
    }


    private void createOrUpdateTypedJournal(Journal journal, Journal updatedJournal, Date created, String status) {
        if(JournalTypes.EXPLORATORY_DISCOVERY.name().equalsIgnoreCase(journal.getType())){
            String journalStatus = Objects.nonNull(journal.getExploratoryDiscovery())? JournalStatus.getJournalStatusCodeByJournalStatus(journal.getExploratoryDiscovery().getStatus()): status;
            ExploratoryDiscoveryDTO exploratoryDiscoveryDTO = getExploratoryDiscoveryDTO(journal.getExploratoryDiscovery(), created, updatedJournal.getJournalId(), journalStatus);
            ExploratoryDiscoveryDTO savedExploratoryDiscoveryDTO = exploratoryDiscoveryRepository.save(exploratoryDiscoveryDTO);
            updatedJournal.setExploratoryDiscovery(getExploratoryDiscovery(savedExploratoryDiscoveryDTO));
        } else if(JournalTypes.LATER_STAGE_DISCOVERY.name().equalsIgnoreCase(journal.getType())){
            String journalStatus = Objects.nonNull(journal.getLaterStageDiscovery())? JournalTypes.getJournalTypeCodeByJournalType(journal.getLaterStageDiscovery().getStatus()): status;
            LaterStageDiscoveryDTO laterStageDiscoveryDTO = getLaterStageDiscoveryDTO(journal.getLaterStageDiscovery(), created, updatedJournal.getJournalId(), journalStatus);
            LaterStageDiscoveryDTO saveLaterStageDiscoveryDTO = laterStageDiscoveryRepository.save(laterStageDiscoveryDTO);
            updatedJournal.setLaterStageDiscovery(getLaterStageDiscovery(saveLaterStageDiscoveryDTO));
        } else if(JournalTypes.PRECLINICAL_TRIAL.name().equalsIgnoreCase(journal.getType())){
            String journalStatus = Objects.nonNull(journal.getPreclinicalTrial())? JournalTypes.getJournalTypeCodeByJournalType(journal.getPreclinicalTrial().getStatus()): status;
            PreclinicalTrialsDTO preclinicalTrialsDTO = getPreclinicalTrialDTO(journal.getPreclinicalTrial(),created, updatedJournal.getJournalId(), journalStatus);
            PreclinicalTrialsDTO savePreclinicalTrialsDTO = preclinicalTrialsRepository.save(preclinicalTrialsDTO);
            updatedJournal.setPreclinicalTrial(getPreclinicalTrial(savePreclinicalTrialsDTO));
        }
    }

    private PreclinicalTrialsDTO getPreclinicalTrialDTO(PreclinicalTrial preclinicalTrial, Date created, String journalId, String status) {
        String trialId = Objects.nonNull(preclinicalTrial) && Objects.nonNull(preclinicalTrial.getTrialId())? preclinicalTrial.getTrialId() :
                StringUtils.join("PCD_ID_", preclinicalTrialsRepository.getTrialIdSeqNextValue().toPlainString());
        PreclinicalTrialsDTO preclinicalTrialsDTO = new PreclinicalTrialsDTO();
        preclinicalTrialsDTO.setJournalId(journalId);
        preclinicalTrialsDTO.setTrialId(trialId);
        preclinicalTrialsDTO.setUpdated(new Date());
        if(Objects.nonNull(preclinicalTrial)){
            preclinicalTrialsDTO.setTrialMethod(preclinicalTrial.getTrialMethod());
            preclinicalTrialsDTO.setAcceptanceCriteriaFile(preclinicalTrial.getAcceptanceCriteriaFile());
            preclinicalTrialsDTO.setResult(preclinicalTrial.getResult());
            preclinicalTrialsDTO.setSampleId(preclinicalTrial.getSampleId());
        }
        preclinicalTrialsDTO.setStatus(status);
        if(Objects.nonNull(created)){
            preclinicalTrialsDTO.setCreated(created);
        }
        return preclinicalTrialsDTO;
    }

    private LaterStageDiscoveryDTO getLaterStageDiscoveryDTO(LaterStageDiscovery laterStageDiscovery, Date created, String journalId, String status) {
        String  laterStageDiscoveryId = Objects.nonNull(laterStageDiscovery) && Objects.nonNull(laterStageDiscovery.getLaterStageDiscoveryId())?
                laterStageDiscovery.getLaterStageDiscoveryId() :
                StringUtils.join("LSD_ID_", laterStageDiscoveryRepository.getDiscoveryIdSeqNextValue().toPlainString());
        LaterStageDiscoveryDTO laterStageDiscoveryDTO = new LaterStageDiscoveryDTO();
        laterStageDiscoveryDTO.setJournalId(journalId);
        laterStageDiscoveryDTO.setStatus(status);
        laterStageDiscoveryDTO.setLaterStageDiscoveryId(laterStageDiscoveryId);
        if(Objects.nonNull(laterStageDiscovery)){
            laterStageDiscoveryDTO.setCompoundsFile(laterStageDiscovery.getCompoundsFile());
            laterStageDiscoveryDTO.setFitForTarget(laterStageDiscovery.getFitForTarget());
        }
        laterStageDiscoveryDTO.setUpdated(new Date());
        if(Objects.nonNull(created)){
            laterStageDiscoveryDTO.setCreated(created);
        }
        return laterStageDiscoveryDTO;
    }

    private ExploratoryDiscoveryDTO getExploratoryDiscoveryDTO(ExploratoryDiscovery exploratoryDiscovery, Date created, String journalId, String status) {
        String  exploratoryDiscoveryId = Objects.nonNull(exploratoryDiscovery) && Objects.nonNull(exploratoryDiscovery.getExploratoryDiscoveryId())?
                exploratoryDiscovery.getExploratoryDiscoveryId() :
                StringUtils.join("ED_ID_", exploratoryDiscoveryRepository.getDiscoveryIdSeqNextValue().toPlainString());
        ExploratoryDiscoveryDTO exploratoryDiscoveryDTO = new ExploratoryDiscoveryDTO();
        exploratoryDiscoveryDTO.setJournalId(journalId);
        exploratoryDiscoveryDTO.setExploratoryDiscoveryId(exploratoryDiscoveryId);
        exploratoryDiscoveryDTO.setStatus(status);
        if(Objects.nonNull(exploratoryDiscovery)) {
            exploratoryDiscoveryDTO.setDiseaseOperationFile(exploratoryDiscovery.getDiseaseOperationFile());
            exploratoryDiscoveryDTO.setHypothesisFile(exploratoryDiscovery.getHypothesisFile());
            exploratoryDiscoveryDTO.setTargetFile(exploratoryDiscovery.getTargetFile());
        }
        exploratoryDiscoveryDTO.setUpdated(new Date());
        if(Objects.nonNull(created)){
            exploratoryDiscoveryDTO.setCreated(created);
        }
        return exploratoryDiscoveryDTO;
    }

    private PreclinicalTrial getPreclinicalTrial(PreclinicalTrialsDTO preclinicalTrialDTO) {
        PreclinicalTrial preclinicalTrial = new PreclinicalTrial();
        preclinicalTrial.setJournalId(preclinicalTrialDTO.getJournalId());
        preclinicalTrial.setTrialId(preclinicalTrialDTO.getTrialId());
        preclinicalTrial.setTrialMethod(preclinicalTrialDTO.getTrialMethod());
        preclinicalTrial.setAcceptanceCriteriaFile(preclinicalTrialDTO.getAcceptanceCriteriaFile());
        preclinicalTrial.setResult(preclinicalTrialDTO.getResult());
        preclinicalTrial.setSampleId(preclinicalTrialDTO.getSampleId());
        preclinicalTrial.setStatus(preclinicalTrialDTO.getStatus());
        return preclinicalTrial;
    }

    private LaterStageDiscovery getLaterStageDiscovery(LaterStageDiscoveryDTO laterStageDiscoveryDTO) {
        LaterStageDiscovery laterStageDiscovery = new LaterStageDiscovery();
        laterStageDiscovery.setJournalId(laterStageDiscoveryDTO.getJournalId());
        laterStageDiscovery.setLaterStageDiscoveryId(laterStageDiscoveryDTO.getLaterStageDiscoveryId());
        laterStageDiscovery.setCompoundsFile(laterStageDiscoveryDTO.getCompoundsFile());
        laterStageDiscovery.setFitForTarget(laterStageDiscoveryDTO.getFitForTarget());
        laterStageDiscovery.setStatus(laterStageDiscoveryDTO.getStatus());
        return laterStageDiscovery;
    }

    private ExploratoryDiscovery getExploratoryDiscovery(ExploratoryDiscoveryDTO exploratoryDiscoveryDTO) {
        ExploratoryDiscovery exploratoryDiscovery = new ExploratoryDiscovery();
        exploratoryDiscovery.setJournalId(exploratoryDiscoveryDTO.getJournalId());
        exploratoryDiscovery.setExploratoryDiscoveryId(exploratoryDiscoveryDTO.getExploratoryDiscoveryId());
        exploratoryDiscovery.setDiseaseOperationFile(exploratoryDiscoveryDTO.getDiseaseOperationFile());
        exploratoryDiscovery.setHypothesisFile(exploratoryDiscoveryDTO.getHypothesisFile());
        exploratoryDiscovery.setTargetFile(exploratoryDiscoveryDTO.getTargetFile());
        exploratoryDiscovery.setStatus(exploratoryDiscoveryDTO.getStatus());
        return exploratoryDiscovery;
    }

    private <T> List<T> getTypedObjects(List<List<Object>> journalsDTOs, Class<T> clazz) {
        return CollectionUtils.isNotEmpty(journalsDTOs)? journalsDTOs.stream()
                .flatMap(Collection::stream)
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
