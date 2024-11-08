package fun.pingflow.sparrow.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常量统一定义
 *
 * @author eang-zh at 2024-11-07 20:35
 * @version 1.0.0
 */
public class SparrowConstant {


    @Getter
    @AllArgsConstructor
    public enum Response {
        SUCCESS("0000", "操作成功"),
        PARAM_ERROR("1000", "参数有误"),
        SERVICE_ERROR("9090", "服务异常，请联系管理员"),


        ;

        private final String code;
        private final String desc;
    }


}
