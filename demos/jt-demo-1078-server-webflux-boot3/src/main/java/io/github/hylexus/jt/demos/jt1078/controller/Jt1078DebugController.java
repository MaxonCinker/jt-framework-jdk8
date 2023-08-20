package io.github.hylexus.jt.demos.jt1078.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.hylexus.jt.demos.common.model.dto.SimplePageableDto;
import io.github.hylexus.jt.jt1078.spec.Jt1078PublisherManager;
import io.github.hylexus.jt.jt1078.spec.Jt1078Session;
import io.github.hylexus.jt.jt1078.spec.Jt1078SessionManager;
import io.github.hylexus.jt.jt1078.spec.Jt1078SubscriberDescriptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class Jt1078DebugController {
    private final Jt1078PublisherManager publisherManager;
    private final Jt1078SessionManager sessionManager;

    public Jt1078DebugController(Jt1078PublisherManager publisherManager, Jt1078SessionManager sessionManager) {
        this.publisherManager = publisherManager;
        this.sessionManager = sessionManager;
    }

    @RequestMapping("/jt1078/subscriber/list")
    public List<Jt1078SubscriberDescriptor> publisherList(SimplePageableDto pageable) {
        return this.publisherManager.list()
                .sorted(Comparator.comparing(Jt1078SubscriberDescriptor::getId))
                .skip((long) (pageable.getPage() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }

    @RequestMapping("/jt1078/session/list")
    public List<Jt1078SessionVo> sessionList(SimplePageableDto pageable) {
        return this.sessionManager.list()
                .sorted(Comparator.comparing(Jt1078Session::sim))
                .skip((long) (pageable.getPage() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .map(it -> new Jt1078SessionVo(it.sessionId(), it.sim(), new Date(it.lastCommunicateTimestamp())))
                .collect(Collectors.toList());
    }

    public record Jt1078SessionVo(String id, String sim, @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date lastCommunicateTime) {
    }
}
