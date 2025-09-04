package com.example.demo.Controllers;

import com.example.demo.Components.DomainManager;
import com.example.demo.Models.Poll;
import com.example.demo.Models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final DomainManager data;

    public UserController(DomainManager _data) {
        this.data = _data;
    }

    /**
     * Get user by its ID
     * */
    @GetMapping("/user/{name}")
    public User Fetch(@PathVariable String name) {
        return data.getUserByName(name);
    }

    /**
     * Create user
     * */
    @PostMapping("/user/")
    public boolean Create(@RequestBody User user) {
        if (!user.Validate()) return false;

        user.setCreated(new ArrayList<>());
        user.setVoted(new ArrayList<>());

        data.getData().getUsers().add(user);

        return true;
    }

    /**
     * Update user by username
     * */
    @PutMapping("/user/{name}")
    public boolean Update(@PathVariable String name, @RequestBody User user) {
        var _user =  data.getUserByName(name);

        if (_user == null) return false;

        if (!user.Validate()) return false;

        _user.setEmail(user.getEmail());

        if (!name.equals(user.getUsername())) {
            _user.setUsername(user.getUsername());

            for (int i = 0; i < _user.getVoted().size(); ++i) {
                _user.getVoted().get(i).getUser().setUsername(user.getUsername());
            }

            for (int i = 0; i < _user.getCreated().size(); ++i) {
                _user.getCreated().get(i).getCreator().setUsername(user.getUsername());
            }
        }

        return true;
    }

    /**
     * Delete user by name
     * */
    @DeleteMapping("/user/{name}")
    public boolean Delete(@PathVariable String name) {
        var user = data.getUserByName(name);

        if (user == null) return false;

        for (int i = 0; i < user.getCreated().size(); ++i) {
            data.getData().getPolls().remove(user.getCreated().get(i));
        }

        data.getData().getUsers().remove(user);

        return true;
    }
}
