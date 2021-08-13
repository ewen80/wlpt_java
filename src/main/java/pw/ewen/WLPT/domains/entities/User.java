package pw.ewen.WLPT.domains.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统用户
 * 一个用户只能且必须属于一个角色（后期可以扩展至属于多个角色）
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;

	@Id
	private String id;	// 用户ID

	@Column(nullable = false)
	private String name;	//用户姓名

	@ManyToOne
	@JoinColumn(name="role_Id", nullable = false)
	private Role role;	// 用户角色

	@Column(nullable = false)
	private String password = "";

	@Column(nullable = true)
	private String avatar;

	@Column(nullable = false)
	private String qxId = "0";

	private boolean deleted = false;	//软删除标志

	public boolean isDeleted() {
		return deleted;
	}

	protected  User(){}

	public User(String id, String name, Role role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}

	public User(String id, String name, Role role, String qxId){
		this.id = id;
		this.name = name;
		this.role = role;
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

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
 		this.role = role;
	}

	public String getQxId() {
		return qxId;
	}

	public void setQxId(String qxId) {
		this.qxId = qxId;
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
