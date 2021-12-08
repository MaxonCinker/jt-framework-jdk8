package io.github.hylexus.jt.jt808.samples.mixedversion.handler;

import io.github.hylexus.jt.jt808.request.Jt808Request;
import io.github.hylexus.jt.jt808.samples.mixedversion.entity.req.*;
import io.github.hylexus.jt.jt808.samples.mixedversion.entity.resp.ServerCommonReplyMsg;
import io.github.hylexus.jt.jt808.samples.mixedversion.entity.resp.TerminalRegisterReplyMsg;
import io.github.hylexus.jt.jt808.support.annotation.handler.Jt808RequestMsgHandler;
import io.github.hylexus.jt.jt808.support.annotation.handler.Jt808RequestMsgHandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.github.hylexus.jt.config.Jt808ProtocolVersion.VERSION_2011;
import static io.github.hylexus.jt.config.Jt808ProtocolVersion.VERSION_2019;

/**
 * @author hylexus
 */
@Slf4j
@Component
@Jt808RequestMsgHandler
public class CommonHandler {

    @Jt808RequestMsgHandlerMapping(msgType = 0x0100, versions = VERSION_2011)
    public TerminalRegisterReplyMsg processTerminalRegisterMsgV2011(Jt808Request request, TerminalRegisterMsgV2011 body) {

        log.info("V2011--TerminalRegister : {}", body);
        return new TerminalRegisterReplyMsg()
                .setFlowId(request.flowId())
                .setResult((byte) 0)
                .setAuthCode("authCode2011-admin")
                ;
    }

    @Jt808RequestMsgHandlerMapping(msgType = 0x0100, versions = VERSION_2019)
    public TerminalRegisterReplyMsg processTerminalRegisterMsgV2019(Jt808Request request, TerminalRegisterMsgV2019 body) {

        log.info("V2019--TerminalRegister : {}", body);
        return new TerminalRegisterReplyMsg()
                .setFlowId(request.flowId())
                .setResult((byte) 0)
                .setAuthCode("authCode2019-admin")
                ;
    }

    @Jt808RequestMsgHandlerMapping(msgType = 0x0102, versions = VERSION_2011)
    public ServerCommonReplyMsg authMsgV2011(Jt808Request request, TerminalAuthMsgV2011 body) {
        log.info("V2011--auth : {}", body);
        return new ServerCommonReplyMsg()
                .setReplyFlowId(request.flowId())
                .setReplyMsgId(request.msgType().getMsgId())
                .setResult(0);
    }

    @Jt808RequestMsgHandlerMapping(msgType = 0x0102, versions = VERSION_2019)
    public ServerCommonReplyMsg authMsgV2019(Jt808Request request, TerminalAuthMsgV2019 body) {
        log.info("V2019--auth : {}", body);
        return new ServerCommonReplyMsg()
                .setReplyFlowId(request.flowId())
                .setReplyMsgId(request.msgType().getMsgId())
                .setResult(0);
    }

    @Jt808RequestMsgHandlerMapping(msgType = 0x0200, versions = VERSION_2011)
    public ServerCommonReplyMsg processLocationMsgV2011(Jt808Request request, LocationInfoUploadMsgV2011 body) {
        log.info("V2011--LocationUpload : {}", body);
        return new ServerCommonReplyMsg()
                .setReplyFlowId(request.flowId())
                .setReplyMsgId(request.msgType().getMsgId())
                .setResult(0);
    }

    @Jt808RequestMsgHandlerMapping(msgType = 0x0200, versions = VERSION_2019)
    public ServerCommonReplyMsg processLocationMsgV2019(Jt808Request request, LocationInfoUploadMsgV2019 body) {
        log.info("V2019--LocationUpload : {}", body);
        return new ServerCommonReplyMsg()
                .setReplyFlowId(request.flowId())
                .setReplyMsgId(request.msgType().getMsgId())
                .setResult(0);
    }
}