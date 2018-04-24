package jdk.proxy.service;

import java.io.Serializable;
import java.util.Map;

public interface UserService extends Serializable {

	Map<String, Object> getUserInfo();
}
