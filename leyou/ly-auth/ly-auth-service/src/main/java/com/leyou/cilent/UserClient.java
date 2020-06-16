package com.leyou.cilent;

import com.leyou.client.UserClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserClient extends UserClientServer {


}
