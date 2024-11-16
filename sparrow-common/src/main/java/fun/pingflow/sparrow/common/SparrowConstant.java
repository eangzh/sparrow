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

        AUTH_ERROR("1400", "账号或密码错误"),
        AUTH_FREEZE("1401", "账号冻结"),

        ;

        private final String code;
        private final String desc;
    }


}
