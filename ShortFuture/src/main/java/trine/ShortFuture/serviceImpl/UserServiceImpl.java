package trine.ShortFuture.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import trine.ShortFuture.entity.User;
import trine.ShortFuture.repository.UserRepository;
import trine.ShortFuture.service.UserService;

public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean checkUser(String userName, String password) {
		User u = userRepository.checkUser(userName, password);
		return u != null;
	}
}
