package com.tower.service.auth;

import java.math.BigInteger;
import java.util.Map;

import com.tower.service.domain.auth.AuthResponse;
import com.tower.service.domain.auth.AuthRoleDto;

public interface RoleService<T extends AuthRoleDto> {
    
    public AuthResponse<T> selectList(Map<String, Object> params);

    public AuthResponse<T> insert(Map<String, Object> params);
    
    public AuthResponse<T> updateById(Map<String, Object> params);
    
    public AuthResponse<T> deleteById(BigInteger id) ;
    
    public AuthResponse<T> selectList(Map<String, Object> params, int offset,
        int pageSize);
    
    public AuthResponse<T> selectByUserId(BigInteger userId);
    
    public AuthResponse<T> selectById(BigInteger id);
    
    public AuthResponse<T> addUserRole(BigInteger userId, BigInteger roleId);
    
    public AuthResponse<T> delUserRole(BigInteger userId, BigInteger roleId);
}
