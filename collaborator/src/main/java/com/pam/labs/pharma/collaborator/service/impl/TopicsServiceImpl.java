package com.pam.labs.pharma.collaborator.service.impl;

import com.pam.labs.pharma.collaborator.common.TopicStatus;
import com.pam.labs.pharma.collaborator.common.UserRoles;
import com.pam.labs.pharma.collaborator.common.UserTopicRoles;
import com.pam.labs.pharma.collaborator.entity.ApplicationUsersDTO;
import com.pam.labs.pharma.collaborator.entity.TopicAllocationsDTO;
import com.pam.labs.pharma.collaborator.entity.TopicAllocationsPK;
import com.pam.labs.pharma.collaborator.entity.TopicsDTO;
import com.pam.labs.pharma.collaborator.model.Topic;
import com.pam.labs.pharma.collaborator.model.TopicAllocation;
import com.pam.labs.pharma.collaborator.model.User;
import com.pam.labs.pharma.collaborator.repository.ApplicationUsersRepository;
import com.pam.labs.pharma.collaborator.repository.TopicAllocationsRepository;
import com.pam.labs.pharma.collaborator.repository.TopicsRepository;
import com.pam.labs.pharma.collaborator.service.TopicsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TopicsServiceImpl implements TopicsService {

    private final TopicsRepository topicsRepository;

    private final ApplicationUsersRepository applicationUsersRepository;

    private final TopicAllocationsRepository topicAllocationsRepository;

    @Override
    public List<Topic> getTopicsByUserId(String userId) {
        List<TopicsDTO> topicsDTOs = topicsRepository.getAllTopicsByUserId(userId);
        return CollectionUtils.isNotEmpty(topicsDTOs)? topicsDTOs.stream()
                .map(this::getTopic)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    public List<User> getUsersByTopicId(String topicId) {
        List<TopicAllocationsDTO> topicAllocationsDTOs = topicAllocationsRepository.findAllByIdTopicId(topicId);
        if(CollectionUtils.isNotEmpty(topicAllocationsDTOs)){
            List<String> userIds = topicAllocationsDTOs.stream()
                    .map(dto -> dto.getId().getUserId())
                    .collect(Collectors.toList());
            List<ApplicationUsersDTO> users = applicationUsersRepository.findAllById(userIds);
            return CollectionUtils.isNotEmpty(users)? users.stream()
                    .map(this::getUser)
                    .collect(Collectors.toList()) : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Topic> getTopicsByAdminUserId(String adminUserId) {
        ApplicationUsersDTO user = applicationUsersRepository.findDepartmentIdByUserId(adminUserId);
        String departmentId = Objects.nonNull(user)? user.getDepartmentId() : null;
        List<TopicsDTO> topicsDTOs = topicsRepository.findAllByDepartmentId(departmentId);
        return CollectionUtils.isNotEmpty(topicsDTOs)? topicsDTOs.stream()
                .map(this::getTopic)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    public boolean allocateAndStartTopic(List<TopicAllocation> topicAllocations) {
        if(CollectionUtils.isNotEmpty(topicAllocations)){
            List<TopicAllocationsDTO> topicAllocationsDTOs = topicAllocations.stream()
                    .filter(allocation -> CollectionUtils.isNotEmpty(allocation.getTopicRole()) && StringUtils.isNoneBlank(allocation.getTopicId(), allocation.getUserId()))
                    .map(dto -> getTopicAllocationsDTO(dto, new Date()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(topicAllocationsDTOs)){
                List<TopicAllocationsDTO> savedTopicAllocationsDTOS = topicAllocationsRepository.saveAll(topicAllocationsDTOs);
                return CollectionUtils.isNotEmpty(savedTopicAllocationsDTOS) && savedTopicAllocationsDTOS.size() == topicAllocationsDTOs.size();
            }
        }
        return false;
    }

    @Override
    public Topic createNewTopic(Topic topic) {
        if(Objects.nonNull(topic)){
            TopicsDTO topicsDTO = getTopicsDTO(topic, new Date());
            TopicsDTO savedTopic = topicsRepository.save(topicsDTO);
            return getTopic(savedTopic);
        }
        return null;
    }

    private TopicsDTO getTopicsDTO(Topic topic, Date createDate) {
        TopicsDTO topicsDTO = new TopicsDTO();
        topicsDTO.setTopicId(StringUtils.join("TOPIC_ID_", topicsRepository.getTopicIdSeqNextValue().toPlainString()));
        topicsDTO.setDepartmentId(topic.getDepartmentId());
        topicsDTO.setStatus(TopicStatus.NOT_STARTED.getCode());
        topicsDTO.setTitle(topic.getTitle());
        topicsDTO.setShortDescription(topic.getShortDescription());
        topicsDTO.setDiseaseId(topic.getDiseaseId());
        topicsDTO.setCreated(createDate);
        topicsDTO.setUpdated(new Date());
        return topicsDTO;
    }


    private TopicAllocationsDTO getTopicAllocationsDTO(TopicAllocation topicAllocation, Date createDate) {
        TopicAllocationsDTO topicAllocationsDTO = new TopicAllocationsDTO();
        TopicAllocationsPK topicAllocationsPK = new TopicAllocationsPK(topicAllocation.getTopicId(), topicAllocation.getUserId());
        topicAllocationsDTO.setId(topicAllocationsPK);
        topicAllocationsDTO.setTopicRole(String.join(",", getUserTopicRoleCodes(topicAllocation.getTopicRole())));
        topicAllocationsDTO.setCreated(createDate);
        topicAllocationsDTO.setUpdated(new Date());
        return topicAllocationsDTO;
    }

    private Topic getTopic(TopicsDTO topicsDTO) {
        Topic topic = new Topic();
        topic.setTopicId(topicsDTO.getTopicId());
        topic.setStatus(topicsDTO.getStatus());
        topic.setTitle(topicsDTO.getTitle());
        topic.setShortDescription(topicsDTO.getShortDescription());
        topic.setCreated(topicsDTO.getCreated());
        topic.setUpdated(topicsDTO.getUpdated());
        return topic;
    }

    private User getUser(ApplicationUsersDTO applicationUsersDTO) {
        List<String> userRoles = StringUtils.isNotBlank(applicationUsersDTO.getUserRole())?
                Stream.of(applicationUsersDTO.getUserRole().split(",")).collect(Collectors.toList()) :
                Collections.emptyList();
        User user = new User();
        user.setUserId(applicationUsersDTO.getUserId());
        user.setUserName(applicationUsersDTO.getUserName());
        user.setUserRoles(getUserRoles(userRoles));
        user.setDepartmentId(applicationUsersDTO.getDepartmentId());
        return user;
    }


    private List<String> getUserRoles(List<String> userRoles) {
        return userRoles.stream()
                .map(UserRoles::getUserRoleByCode)
                .collect(Collectors.toList());
    }

    private List<String> getUserTopicRoleCodes(List<String> userRoles) {
        return userRoles.stream()
                .map(UserTopicRoles::getUserTopicRoleCodeByUserTopicRole)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
