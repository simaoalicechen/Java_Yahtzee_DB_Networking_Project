

public class User {

	int u_id;
	String name;
	int points;

	public User(int u_id, String name, int points) {
		this.u_id = u_id;
		this.name = name;
		this.points = points;
	}

	public User() {
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "User{" + "u_id=" + u_id + ", name='" + name + '\'' + ", points=" + points + '}';
	}
}
