import com.sungjun.cloud.service.SubscriptionApi;
import com.sungjun.cloud.service.UserService;
import com.sungjun.cloud.service.impl.CloudSubscriptionServiceImpl;
import com.sungjun.cloud.service.impl.CloudUserServiceImpl;

module jmp.cloud.service {
  requires lombok;
  requires spring.context;
  requires spring.data.jpa;
  requires jakarta.persistence;
  requires jmp.service.api;
  requires transitive jmp.dto;
  provides SubscriptionApi with CloudSubscriptionServiceImpl;
  provides UserService with CloudUserServiceImpl;
}