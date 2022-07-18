package com.share.auth.center.model.dto;

import java.io.Serializable;

/**
 * @author liuhao token封装类
 * @version 2020-03-23
 */
public class TokenModel implements Serializable {

	/** 授权令牌 */
	private String accessToken;

	/** 刷新令牌 */
	private String refreshToken;

	/** 用户ID */
	private String uid;

	private String jwt;

	/** 授权码有效时间（秒） */
	private String expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
