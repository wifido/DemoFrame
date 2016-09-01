package com.sf.sfpp.web.user.shiro;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;

public class LdapAuthentication {
	
	private final Logger logger = Logger.getLogger(LdapAuthentication.class);
	
	private String url;
	
	private String domain;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}



	/**
	 * 根据用户名和密码进行域用户验证
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	public boolean authentication(String userName, String passWord) {
		DirContext ctx = null;
		Hashtable<String,String> env = new Hashtable<String,String>();
      	env.put(Context.SECURITY_AUTHENTICATION, "simple");
      	env.put(Context.SECURITY_PRINCIPAL, userName+"@"+this.domain);
      	env.put(Context.PROVIDER_URL, this.url);
      	env.put(Context.SECURITY_CREDENTIALS, passWord);
      	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
      	env.put(Context.OBJECT_FACTORIES, DefaultDirObjectFactory.class.getName());
		try {
			ctx = new InitialLdapContext(env,null);
			logger.info("userName[" + userName + "],domain verify success!");
			return true;
		} catch (Throwable t) {
			logger.info("userName[" + userName + "+"+t+"] or password invalid,domain verify failed!");
		    return false;
		} finally {
			LdapUtils.closeContext(ctx);
		}
	}
}
