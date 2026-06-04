//package com.localservice.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class HomeController {
//
//	@GetMapping(value = { "/", "/{path:[^\\.]*}" })
//	public String home(){
//		return "forward:/index.html";
//	}
//}

package com.localservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {
        "/",
        "/login",
        "/user/login",
        "/user/register",
        "/user/dashboard",
        "/services",
        "/my-bookings",
        "/booking/slot",
        "/partner/login",
        "/partner/register",
        "/partner/waiting",
        "/payment",
        "/review",
        "/waiting",
        "/complaint",
        "/service-request"
    })
    public String home() {
        return "forward:/index.html";
    }
}