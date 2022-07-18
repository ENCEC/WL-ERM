package com.gillion.cache;

import com.gillion.login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wengms
 * @date 2021/1/11 3:56 下午
 * @email wengms@gillion.com.cn
 */
@RestController
@RequestMapping("/cache/users")
public class CacheController {

    @Autowired
    private UserCacheService userCacheService;


    @GetMapping("/{username}")
    public User get(@PathVariable("username")  String username) {
        /*UserVo userVo = userInterface.get(username);

        User user = new User();

        BeanUtils.copyProperties(userVo,user);
        return user;*/
        return userCacheService.get(username);
    }

    @PutMapping("/{username}")
    public User update(@RequestBody User user) {
        /*UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        userVo = userInterface.update(userVo);


        BeanUtils.copyProperties(userVo,user);
        return user;*/
        return userCacheService.update(user);
    }

    @DeleteMapping("/{username}")
    public String remove(@PathVariable("username") String username){
        userCacheService.remove(username);
        return "ok";
    }
}
