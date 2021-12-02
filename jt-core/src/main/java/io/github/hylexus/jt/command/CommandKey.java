package io.github.hylexus.jt.command;

/**
 * @author hylexus
 * Created At 2019-10-09 9:19 下午
 */
public interface CommandKey {
    String getTerminalId();

    int getMsgId();

    Integer getServerFlowId();

    default String getKeyAsString() {
        return getServerFlowId() == null
                ? String.format("%s_%s", getTerminalId(), getMsgId())
                : String.format("%s_%s_%s", getTerminalId(), getMsgId(), getServerFlowId());
    }

    default String getWaitingFlag() {
        return "_" + getKeyAsString();
    }
}
