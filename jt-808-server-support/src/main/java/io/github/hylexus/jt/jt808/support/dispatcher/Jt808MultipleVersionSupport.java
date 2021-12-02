package io.github.hylexus.jt.jt808.support.dispatcher;

import io.github.hylexus.jt.config.Jt808ProtocolVersion;
import io.github.hylexus.jt.core.ReplaceableComponent;
import io.github.hylexus.jt.data.msg.MsgType;

import java.util.Set;

/**
 * @author hylexus
 */
public interface Jt808MultipleVersionSupport extends ReplaceableComponent {

    default Set<Jt808ProtocolVersion> getSupportedVersions() {
        return Jt808ProtocolVersion.unmodifiableSetVersionAutoDetection();
    }

    Set<MsgType> getSupportedMsgTypes();
}