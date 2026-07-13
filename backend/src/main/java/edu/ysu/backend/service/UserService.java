package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Role;
import edu.ysu.backend.entity.vo.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface UserService {

    Result getLoginUserinfo();

    Result addRole(Role role);

    Result deleteRole(Integer rid);

    Result getRolePaged(String keyword,int pageNum,int pageSize);

    Result getRoleFormData(Integer rid);

    Result changeRole(Role role);

    Result logout();

    Result getUserinfoPaged(String keyword,int pageNum,int pageSize);

    Result uploadResult(MultipartFile avatar);

    Result addUserinfo(User user);

    Result deleteUserinfo(User user);

    Result getUserinfoFormData(Integer id);

    Result changeUserinfo(User user);

    Result getUserRecordInfo(Integer sid);

    Result changeUserRecordInfo(User user);

    Result setUserAvatar(MultipartFile avatar);

    Result deleteUserTrash();

}
