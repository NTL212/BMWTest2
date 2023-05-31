package vn.iotstar.Dao.Impl;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import vn.iotstar.Dao.IUserDao;
import vn.iotstar.Entity.User;
import vn.iotstar.JPAConfig.JpaConfig;

public class UserDaoImpl implements IUserDao {
	
	// Hàm mã hóa mật khẩu
    public static String hashPassword(String password,String salt) {
        // Số vòng lặp (tăng số vòng lặp tăng độ mạnh của thuật toán)

        // Mã hóa mật khẩu bằng thuật toán BCrypt
        String hashedPassword = BCrypt.hashpw(password,salt);

        return hashedPassword;
    }

	@Override
	public void insert(User user) {
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
		trans.begin();
		enma.persist(user);
		trans.commit();
		} catch (Exception e) {
		e.printStackTrace();
		trans.rollback();
		throw e;
		}finally {
		enma.close();
		}
		
	}

	@Override
	public void update(User user) {
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
		trans.begin();
		enma.merge(user);
		trans.commit();
		} catch (Exception e) {
		e.printStackTrace();
		trans.rollback();
		throw e;
		}finally {
		enma.close();
		}	
	}

	@Override
	public void delete(int id) throws Exception {
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
		trans.begin();
		User user = enma.find(User.class,id);
		if(user != null) {
		enma.remove(user);
		}else {
		throw new Exception("Không tìm thấy");
		}
		trans.commit();
		} catch (Exception e) {
		e.printStackTrace();
		trans.rollback();
			throw e;
		}finally {
		enma.close();
		}
	}

	@Override
	public User get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(int id) {
		EntityManager enma = JpaConfig.getEntityManager();
		User user = enma.find(User.class,id);
		return user;
	}

	@Override
	public List<User> findAll() {
		EntityManager enma = JpaConfig.getEntityManager();
		TypedQuery<User> query= enma.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}

	@Override
	public User checkLogin(String username, String password) {
		EntityManager enma = JpaConfig.getEntityManager();
		String username_jpql = "SELECT u FROM User u WHERE u.username like :username";
		TypedQuery<User> query= enma.createQuery(username_jpql, User.class);
		query.setParameter("username",username);
		try {
			 User u = query.getSingleResult();
			 String newpassword = hashPassword(password,u.getSalt());
			 String password_jpql = "SELECT u FROM User u WHERE u.username like :username and u.password like :password";
			 TypedQuery<User> newquery= enma.createQuery(password_jpql, User.class);
			 newquery.setParameter("username",username);
			 newquery.setParameter("password",newpassword);
			 return newquery.getSingleResult();
		}
		catch(Exception e) {	
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public User checkRegister(String username) {
		EntityManager enma = JpaConfig.getEntityManager();
		String jpql = "SELECT u FROM User u WHERE u.username like :username";
		TypedQuery<User> query= enma.createQuery(jpql, User.class);
		query.setParameter("username",username);
		try {
			 return query.getSingleResult();
		}
		catch(Exception e) {	
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public User checkEmail(String email) {
		EntityManager enma = JpaConfig.getEntityManager();
		String jpql = "SELECT u FROM User u WHERE u.email like :email";
		TypedQuery<User> query= enma.createQuery(jpql, User.class);
		query.setParameter("email",email);
		try {
			return query.getSingleResult();
		}catch(Exception e) {
			return null;
		}
	}
	@Override
	public int RegisterUser(User user) {
		String salt =  BCrypt.gensalt(12);
		String password = hashPassword(user.getPassword(),salt);
		User newuser = new User(user.getName(),user.getEmail(),user.getPhone()
				,user.getUsername(),password,user.getCreated());
		newuser.setSalt(salt);
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		int check = 0;
		try {
		User checkregister = checkRegister(newuser.getUsername());
		User checkEmail = checkEmail(newuser.getEmail());
		if(checkregister != null) {
			check = 2;
		}
		else if(checkEmail != null) {
			check = 1;	
			}
		else {
			trans.begin();
			enma.persist(newuser);
			trans.commit();
		}
		return check;
		} catch (Exception e) {
		e.printStackTrace();
		trans.rollback();
		}
		return check;
	}

}
