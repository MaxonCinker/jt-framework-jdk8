package io.github.hylexus.jt.demos.jt808.controller;

import io.github.hylexus.jt.demos.common.model.Resp;
import io.github.hylexus.jt.demos.common.model.dto.jt808.Command9101Dto;
import io.github.hylexus.jt.demos.common.model.dto.jt808.Command9102Dto;
import io.github.hylexus.jt.exception.JtSessionNotFoundException;
import io.github.hylexus.jt.jt808.JtProtocolConstant;
import io.github.hylexus.jt.jt808.spec.Jt808CommandKey;
import io.github.hylexus.jt.jt808.spec.Jt808CommandSender;
import io.github.hylexus.jt.jt808.spec.builtin.msg.resp.BuiltinMsg9101Alias;
import io.github.hylexus.jt.jt808.spec.builtin.msg.resp.BuiltinMsg9102Alias;
import io.github.hylexus.jt.jt808.spec.impl.BuiltinJt808MsgType;
import io.github.hylexus.jt.jt808.spec.session.Jt808Session;
import io.github.hylexus.jt.jt808.spec.session.Jt808SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/jt808")
public class Jt1078Controller {
    private final Jt808CommandSender commandSender;
    private final Jt808SessionManager sessionManager;

    public Jt1078Controller(Jt808CommandSender commandSender, Jt808SessionManager sessionManager) {
        this.commandSender = commandSender;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/send-msg/9101")
    public Mono<Resp<Object>> realtimeTransmissionRequest(@Validated @RequestBody Command9101Dto dto) throws InterruptedException {

        final BuiltinMsg9101Alias msg = new BuiltinMsg9101Alias()
                .setServerIp(dto.getJt1078ServerIp())
                .setServerPortTcp(dto.getJt1078ServerPortTcp())
                .setServerPortUdp(dto.getJt1078ServerPortUdp())
                .setChannelNumber(dto.getChannelNumber().byteValue())
                .setDataType(dto.getDataType().byteValue())
                .setStreamType(dto.getStreamType().byteValue());

        msg.setServerIpLength((byte) msg.getServerIp().getBytes(JtProtocolConstant.JT_808_STRING_ENCODING).length);

        return this.sendAndWait(dto.getSim(), msg, dto.getTimeout());
    }

    @PostMapping("/send-msg/9102")
    public Mono<Resp<Object>> realtimeTransmissionControl(@Validated @RequestBody Command9102Dto dto) throws InterruptedException {

        final BuiltinMsg9102Alias msg = new BuiltinMsg9102Alias()
                .setChannelNumber(dto.getChannelNumber().byteValue())
                .setCommand(dto.getCommand().byteValue())
                .setMediaTypeToClose(dto.getMediaTypeToClose().byteValue())
                .setStreamType(dto.getStreamType().byteValue());

        return this.sendAndWait(dto.getSim(), msg, dto.getTimeout());
    }

    private Mono<Resp<Object>> sendAndWait(String sim, Object msg, Duration timeout) throws InterruptedException {
        final String terminalId = sim;

        final Jt808Session session = sessionManager.findByTerminalId(terminalId).orElseThrow(() -> new JtSessionNotFoundException(terminalId));
        final int nextFlowId = session.nextFlowId();
        final Jt808CommandKey commandKey = Jt808CommandKey.of(terminalId, BuiltinJt808MsgType.CLIENT_COMMON_REPLY, nextFlowId);

        return Mono.fromCallable(() -> {
            final Object resp = this.commandSender.sendCommandAndWaitingForReply(commandKey, msg, timeout.toSeconds(), TimeUnit.SECONDS);
            log.info("RESP::::::: {}", resp);
            if (resp == null) {
                return Resp.sendCommandFailure("未收到终端回复(" + timeout + ")");
            }
            return Resp.success(resp);
        });
    }
}
