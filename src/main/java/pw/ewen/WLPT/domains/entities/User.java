package pw.ewen.WLPT.domains.entities;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 系统用户
 */
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;

	@Id
	private String id;	// 用户ID

	@Column(nullable = false)
	private String name;	//用户姓名

//	@ManyToOne
//	@JoinColumn(name="role_Id", nullable = false)
//	private Role role;	// 用户角色

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "role_user",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>(); //用户所属角色

	/**
	 * 默认使用的角色
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private Role defaultRole;

	/**
	 * 当前使用的角色
	 */
	@Transient
	private Role currentRole;

	@Column(nullable = false)
	private String password = "";

	@Column
	private String avatar;

	@Column(nullable = false)
	private String qxId = "0";

	@Column
	private boolean deleted = false;	//软删除标志

	public boolean isDeleted() {
		return deleted;
	}

	protected  User(){}

	public User(String id, String name) {
		this.id = id;
		this.name = name;
//		this.roles = roles;
	}

	public User(String id, String name, String qxId){
		this.id = id;
		this.name = name;
//		this.roles = roles;
		this.qxId = qxId;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword(){ return password;}
	public void setPassword(String password){ this.password = password;}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
 		this.roles = roles;
	}

	public String getQxId() {
		return qxId;
	}

	public void setQxId(String qxId) {
		this.qxId = qxId;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Role getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(Role defaultRole) {
		this.defaultRole = defaultRole;
	}

	public Role getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(Role currentRole) {
		this.currentRole = currentRole;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	// 用户id相同则认为两个user相同
	public boolean equals(Object obj) {
		if(obj instanceof  User){
			return this.id.equals(((User) obj).getId());
		}else{
			return false;
		}
	}
}
