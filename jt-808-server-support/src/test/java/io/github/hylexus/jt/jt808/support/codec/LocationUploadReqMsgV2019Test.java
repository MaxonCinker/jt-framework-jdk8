package io.github.hylexus.jt.jt808.support.codec;

import io.github.hylexus.jt.jt808.spec.Jt808MsgHeaderSpec;
import io.github.hylexus.jt.jt808.support.annotation.msg.basic.BasicField;
import io.github.hylexus.jt.jt808.support.annotation.msg.splice.SlicedFrom;
import io.github.hylexus.jt.jt808.support.annotation.msg.splice.SplittableField;
import io.github.hylexus.jt.jt808.support.data.Jt808HeaderSpecAware;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.github.hylexus.jt.jt808.support.data.MsgDataType.*;

/**
 * @author hylexus
 * Created At 2020-01-29 12:03 下午
 */
@Slf4j
@Data
@Accessors(chain = true)
//@Jt808ReqMsgBody(msgType = 0x0200, version = Jt808ProtocolVersion.VERSION_2019)
public class LocationUploadReqMsgV2019Test implements Jt808HeaderSpecAware {

    @ToString.Exclude
    private Jt808MsgHeaderSpec headerSpec;

    public void setHeaderSpec(Jt808MsgHeaderSpec headerSpec) {
        this.headerSpec = headerSpec;
    }

    // (1). 报警标志
    @BasicField(order = 0, startIndex = 0, dataType = DWORD)
    private int alarmFlag;
    // (2). 状态
    @BasicField(order = 1, startIndex = 4, dataType = DWORD)
    @SplittableField(splitPropertyValueIntoNestedBeanField = "statusInfo")
    private int status;
    private LocationUploadStatus statusInfo;

    // 将上面的 status 字段的第0位取出转为 int 类型
    @SlicedFrom(sourceFieldName = "status", bitIndex = 0)
    private int accIntStatus;
    // 将上面的 status 字段的第0位取出转为 boolean 类型
    @SlicedFrom(sourceFieldName = "status", bitIndex = 0)
    private Boolean accBooleanStatus;
    // 0 北纬;1 南纬
    // 将上面的 status 字段的第2位取出转为 int 类型
    @SlicedFrom(sourceFieldName = "status", bitIndex = 2)
    private int latType;
    // 纬度(尚未除以 10^6)
    @BasicField(order = 2, startIndex = 8, dataType = DWORD)
    private Integer intLat;
    // (3). 纬度(使用转换器除以10^6转为Double类型)
    // @BasicField(startIndex = 8, dataType = DWORD, customerDataTypeConverterClass = LngLatReqMsgFieldConverter.class)
    // private Double lat;
    // (4). 经度(尚未除以 10^6)
    @BasicField(order = 3, startIndex = 12, dataType = DWORD)
    private Integer intLng;
    // 经度(使用转换器除以10^6转为Double类型)
    // @BasicField(startIndex = 12, dataType = DWORD, customerDataTypeConverterClass = LngLatReqMsgFieldConverter.class)
    // private Double lng;
    // 经度(startIndexMethod使用示例)
    // @BasicField(startIndexMethod = "getLngStartIndex", dataType = DWORD, customerDataTypeConverterClass = LngLatReqMsgFieldConverter.class)
    //  private Double lngByStartIndexMethod;

    // (5). 高度
    @BasicField(order = 4, startIndex = 16, dataType = WORD)
    private Integer height;
    // (6). 速度
    @BasicField(order = 5, startIndex = 18, dataType = WORD)
    private int speed;
    // (7). 方向
    @BasicField(order = 6, startIndex = 20, dataType = WORD)
    private Integer direction;

    // (8). 时间
    @BasicField(order = 7, startIndex = 22, dataType = BCD, length = 6)
    private String time;

    @BasicField(order = 8, startIndex = 28, dataType = LIST, byteCountMethod = "getExtraInfoLength")
    private List<ExtraItem> extraItemList;

    public int getLngStartIndex() {
        log.info("消息体总长度:{}", headerSpec.msgBodyProps().msgBodyLength());
        return 12;
    }

    public int getExtraInfoLength() {
        int totalLength = headerSpec.msgBodyProps().msgBodyLength();
        return totalLength - 28;
    }

    @Data
    public static class ExtraItem {
        @BasicField(order = 0, startIndex = 0, dataType = BYTE)
        private byte id;
        @BasicField(order = 1, startIndex = 1, dataType = BYTE)
        private byte contentLength;

        @BasicField(order = 3, startIndex = 2, byteCountMethod = "getContentLengthMethod", dataType = BYTES)
        private byte[] content;

        public int getContentLengthMethod() {
            return contentLength;
        }
    }

    @Data
    public static class LocationUploadStatus {
        @SplittableField.BitAt(bitIndex = 0)
        private boolean accStatus; // acc开?

        @SplittableField.BitAt(bitIndex = 1)
        private int bit1; //1:定位, 0:未定位

        @SplittableField.BitAt(bitIndex = 2)
        private Boolean isSouthLat;// 是否南纬? 0:北纬 1:南纬

        // 0:东经 1:西经
        @SplittableField.BitAt(bitIndex = 3)
        private Integer lngType;

        // 0: 运营  1:停运
        @SplittableField.BitAt(bitIndex = 4)
        private int operationStatus;

        // 0: 经纬度未经过保密插件加密  1:经纬度已经保密插件加密
        @SplittableField.BitAt(bitIndex = 5)
        private int latLngEncryptStatus;

        // 1: 紧急刹车系统采集的前撞预警
        @SplittableField.BitAt(bitIndex = 6)
        private int bit6;

        // 扯到偏移预警
        @SplittableField.BitAt(bitIndex = 7)
        private int bit7;
    }
}