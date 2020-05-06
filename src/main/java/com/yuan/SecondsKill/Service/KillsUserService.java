package com.yuan.SecondsKill.Service;




import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface KillsUserService {


    String login(HttpServletResponse response,LoginVo loginVo);

    KillsUser getByToken(HttpServletResponse response,String token);

    KillsUser getById(long id);

}
