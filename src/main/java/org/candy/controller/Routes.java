package org.candy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Maps all AngularJS routes to index so that they work with direct linking.
 */
@Controller
public class Routes {

@RequestMapping({
	"/one",
	"/two",
	"/three"
})
public String index() {
	return "forward:/index.html";
}
}