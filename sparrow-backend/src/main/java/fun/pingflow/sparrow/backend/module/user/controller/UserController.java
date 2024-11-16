package fun.pingflow.sparrow.backend.module.user.controller;

import cn.hutool.core.bean.BeanUtil;
import fun.pingflow.sparrow.backend.module.user.entity.UserEntity;
import fun.pingflow.sparrow.backend.module.user.service.IUserService;
import fun.pingflow.sparrow.backend.module.user.vo.RegisterVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户 前端控制器
 *
 * @author eang-zh
 * @since 2024-11-15 22:41
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 用户注册
     *
     * @param vo vo
     * @author eang-zh at 2024-11-16 00:23
     */
    @PostMapping("/register")
    public void register(@RequestBody RegisterVO vo) {
        UserEntity userEntity = BeanUtil.copyProperties(vo, UserEntity.class);
        userService.save(userEntity);
    }

}
