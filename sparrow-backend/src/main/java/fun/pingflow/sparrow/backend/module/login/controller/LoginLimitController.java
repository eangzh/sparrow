package fun.pingflow.sparrow.backend.module.login.controller;

import fun.pingflow.sparrow.backend.module.login.service.ILoginLimitService;
import fun.pingflow.sparrow.backend.module.login.vo.LoginVO;
import fun.pingflow.sparrow.common.SparrowResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 登录限制 前端控制器
 *
 * @author eang-zh
 * @since 2024-11-15 22:40
 */
@RestController
@RequestMapping("/login")
public class LoginLimitController {

    @Resource
    private ILoginLimitService loginLimitService;

    @PostMapping("/limit/test")
    public SparrowResult<String> login(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        // 从请求中获取IP
        String ip = request.getRemoteAddr();
        return loginLimitService.login(ip, loginVO.getUsername(), loginVO.getPassword());
    }


}
