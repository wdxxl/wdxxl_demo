package com.wdxxl.restful.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @RequestMapping(value = "/{user}")
    public User retrieveUser(
            @PathVariable Long user,
            @RequestParam(value = "name", defaultValue = "World") String name) {
        return new User(user, name);
    }

    @RequestMapping(value = "/{user}/customers", method = RequestMethod.GET)
    public List<Customer> getUserCustomers(@PathVariable Long user) {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "jake"));
        customers.add(new Customer(2, "kingson"));
        customers.add(new Customer(3, "james"));
        return customers;
    }

    /*
     * http://localhost:8080/users/createUser
     * Restful Browser Headers (Content-Type:application/json)
     * Request Body ({"id":1,"name":"king"})
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return new User(user.getId(), user.getName());
    }

    @RequestMapping(value = "/modifyUser", method = RequestMethod.PUT)
    public User modifyUser(@RequestBody User user) {
        return new User(user.getId(), user.getName());
    }

    @RequestMapping(value = "/patchUser", method = RequestMethod.PATCH)
    public User patchUser(@RequestBody User user) {
        return new User(user.getId(), user.getName());
    }

    @RequestMapping(value = "/{user}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable Long user) {
        return true;
    }
}
