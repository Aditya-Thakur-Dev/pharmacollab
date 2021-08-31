package com.pam.labs.pharma.collaborator.controller;

import com.pam.labs.pharma.collaborator.common.UserTopicRoles;
import com.pam.labs.pharma.collaborator.model.Topic;
import com.pam.labs.pharma.collaborator.model.TopicAllocation;
import com.pam.labs.pharma.collaborator.model.User;
import com.pam.labs.pharma.collaborator.service.TopicsService;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins={"http://localhost:3000"})
@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Validated
public class TopicsController {

    private final TopicsService topicsService;

    @GetMapping("/getTopicsByUserId")
    public ResponseEntity<List<Topic>> getTopicsByUserId(@Valid @RequestParam @NotBlank String userId){
        List<Topic> topics = topicsService.getTopicsByUserId(userId);
        return getListResponseEntity(topics);
    }

    @GetMapping("/getUsersByTopicId")
    public ResponseEntity<List<User>> getUsersByTopicId(@Valid @RequestParam @NotBlank String topicId){
        List<User> users = topicsService.getUsersByTopicId(topicId);
        return getListResponseEntity(users);
    }

    @GetMapping("/getTopicsByAdminUserId")
    public ResponseEntity<List<Topic>> getTopicsByAdminUserId(@Valid @RequestParam @NotBlank String userId){
        List<Topic> topics = topicsService.getTopicsByAdminUserId(userId);
        return getListResponseEntity(topics);
    }

    @PostMapping(value = "/allocateAndStartTopic", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> allocateAndStartTopic(@Valid @RequestBody @NotEmpty List<TopicAllocation> topicAllocations){
        boolean hasAllocated = topicsService.allocateAndStartTopic(topicAllocations);
        return hasAllocated? new ResponseEntity<>("Topic allocated and started", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping(value = "/createNewTopic", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Topic> createNewTopic(@Valid @RequestBody @NotNull Topic topic){
        Topic saveTopic = topicsService.createNewTopic(topic);
        return Objects.nonNull(saveTopic)? new ResponseEntity<>(saveTopic, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    private <T> ResponseEntity<List<T>> getListResponseEntity(List<T> topics) {
        return CollectionUtils.isNotEmpty(topics) ? new ResponseEntity<>(topics, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getUserTopicRoles")
    public ResponseEntity<List<String>> getUserTopicRoles() {
        List<String> userTopicRoles = Stream.of(UserTopicRoles.values())
                .map(UserTopicRoles::name)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userTopicRoles, HttpStatus.OK);
    }
}
