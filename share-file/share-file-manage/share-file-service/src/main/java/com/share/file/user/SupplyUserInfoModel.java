package com.share.file.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.gillion.ec.core.security.IRole;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.data.ITable;

import com.share.support.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;


/** 
* @version:1.0
* @Description: 继承IUser以关联ds数据服务 通用字段处理
* @author: SYF
* @date: 2020年10月26日上午9:16:54
*/ 
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplyUserInfoModel extends User implements Serializable,IUser,com.gillion.eds.sso.IUser{
	
	/**用于自定义通用字段*/
	private String companyName;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public String getPassword() {
		return null;
	}

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public String getUsername() {
		return this.getName();
	}

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public boolean isEnabled() {
		return false;
	}

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public Collection<IRole> getAuthorities() {
        return Collections.emptyList();
    }

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public Set<String> getHasPermissionUrlPatterns() {
        return Collections.emptySet();
    }

	/** 
	* @Description:
	* @return
	* @author: SYF
	* @date: 2020年11月4日下午6:21:14
	*/
	@Override
	public Collection<ITable> getPermissionTables() {
        return Collections.emptyList();
    }

	/**
     * @Description:
     * @return
     * @author: SYF
     * @date: 2020年11月4日下午6:21:14
     */
    @Override
    public Object getUserId() {
        return this.getUemUserId();
    }

    /**
     * @param arg0
     * @Description:
     * @author: SYF
     * @date: 2020年11月4日下午6:21:14
     */
    @Override
    public void setAuthorities(Iterable<? extends IRole> arg0) {
        throw new UnsupportedOperationException();
    }
	

	


}
