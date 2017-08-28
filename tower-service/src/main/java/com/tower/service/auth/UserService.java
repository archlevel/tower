package com.tower.service.auth;

import java.math.BigInteger;
import java.util.Map;

import com.tower.service.domain.auth.AuthResponse;
import com.tower.service.domain.auth.AuthUserDto;

public interface UserService<T extends AuthUserDto> {
	public AuthResponse<T> selectList(Map<String, Object> params);

	public AuthResponse<T> insert(Map<String, Object> params);

	public AuthResponse<T> updateById(Map<String, Object> params);

	public AuthResponse<T> deleteById(BigInteger id);

	public AuthResponse<T> selectList(Map<String, Object> params, int offset,
			int pageSize);

	public boolean isUserNameExist(String userName);

	public AuthResponse<T> selectById(BigInteger id);

	public boolean isAllowDelele(BigInteger id);

	public T selectByTicket(String ticketValidateUrl, String ticket);

	public String selectFullNameByUserName(String userName);

	public String selectFullNameByUserId(BigInteger userId);
}
