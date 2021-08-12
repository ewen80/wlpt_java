package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 系统角色
 */
@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 1888955493407366629L;
	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	@Column
	private String description = "";

	private boolean deleted = false;

	@OneToMany(mappedBy="role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Where(clause="deleted=false")
	private Set<User> users = new HashSet<>();

	@OneToMany(mappedBy = "role")
	private Set<User> allUsers = new HashSet<>();


	@OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ResourceRange>	resourceRanges = new HashSet<>();

	protected Role(){}
	public Role(String id, String name) {
		this.name = name;
		this.id = id;
	}
	public Role(String id, String name, String description) {
		this(id, name);
		this.setDescription(description);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<ResourceRange> getResourceRanges() { return this.resourceRanges;}
	public void setResourceRanges(Set<ResourceRange> resourceRanges) { this.resourceRanges = resourceRanges;}



	/**
	 * @return 获取所有用户包含被软删除的用户
	 */
	public Set<User> getAllUsers() {
		return this.allUsers;
	}

	@Override
	public String toString() {
		return "Role{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		return Objects.equals(id, role.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
