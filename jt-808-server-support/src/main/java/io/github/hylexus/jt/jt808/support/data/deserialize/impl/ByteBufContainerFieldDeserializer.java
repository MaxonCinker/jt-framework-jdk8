package io.github.hylexus.jt.jt808.support.data.deserialize.impl;

import io.github.hylexus.jt.jt808.support.data.ConvertibleMetadata;
import io.github.hylexus.jt.jt808.support.data.MsgDataType;
import io.github.hylexus.jt.jt808.support.data.RequestMsgConvertibleMetadata;
import io.github.hylexus.jt.jt808.support.data.deserialize.Jt808FieldDeserializer;
import io.github.hylexus.jt.jt808.support.data.type.bytebuf.ByteBufContainer;
import io.github.hylexus.jt.jt808.support.data.type.bytebuf.DefaultByteBufContainer;
import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Set;

public class ByteBufContainerFieldDeserializer implements Jt808FieldDeserializer<ByteBufContainer> {

    private static final Set<RequestMsgConvertibleMetadata> CONVERTIBLE_METADATA_SET = Set.of(
            ConvertibleMetadata.forJt808RequestMsgDataType(MsgDataType.BYTES, ByteBufContainer.class),
            ConvertibleMetadata.forJt808RequestMsgDataType(MsgDataType.BYTE, ByteBufContainer.class),
            ConvertibleMetadata.forJt808RequestMsgDataType(MsgDataType.STRING, ByteBufContainer.class),
            ConvertibleMetadata.forJt808RequestMsgDataType(MsgDataType.BCD, ByteBufContainer.class),
            ConvertibleMetadata.forJt808RequestMsgDataType(MsgDataType.WORD, ByteBufContainer.class),
            ConvertibleMetadata.forJt808RequestMsgDataType(MsgDataType.DWORD, ByteBufContainer.class)
    );

    @Override
    public Set<RequestMsgConvertibleMetadata> getConvertibleTypes() {
        return CONVERTIBLE_METADATA_SET;
    }

    @Override
    public ByteBufContainer deserialize(ByteBuf byteBuf, MsgDataType msgDataType, int start, int length) {
        throw new NotImplementedException();
    }

    @Override
    public ByteBufContainer deserialize(ByteBuf byteBuf, MsgDataType msgDataType, int start, int length, Context context) {
        // TODO release issue ...
        final ByteBuf content = byteBuf.slice(start, length);
        return new DefaultByteBufContainer(content);
    }
}
