package fun.pingflow.sparrow.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 响应包装
 *
 * @author eang-zh at 2024-11-07 20:39
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SparrowResult<T> {

    /** 响应码 */
    private String code;
    /** 响应描述 */
    private String desc;
    /** 响应数据 */
    private T data;

    public SparrowResult<T> ok() {
        return SparrowResult.<T>builder()
                .code(SparrowConstant.Response.SUCCESS.getCode())
                .desc(SparrowConstant.Response.SUCCESS.getDesc())
                .build();
    }

    public SparrowResult<T> ok(T data) {
        return SparrowResult.<T>builder()
                .code(SparrowConstant.Response.SUCCESS.getCode())
                .desc(SparrowConstant.Response.SUCCESS.getDesc())
                .data(data)
                .build();
    }

    public SparrowResult<T> error() {
        return SparrowResult.<T>builder()
                .code(SparrowConstant.Response.SERVICE_ERROR.getCode())
                .desc(SparrowConstant.Response.SERVICE_ERROR.getDesc())
                .build();
    }

}
