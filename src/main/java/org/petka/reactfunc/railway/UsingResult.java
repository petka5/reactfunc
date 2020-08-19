package org.petka.reactfunc.railway;

import org.petka.reactfunc.persistence.entity.User;

import java.util.List;
import java.util.Objects;

public class UsingResult {

    public static void main(String[] args) {

        User user = User.builder().username("addf").password("afaaaa").age(19).build();
        Result<User> userResult = UsingResult.updateUser(user);


        System.out.println(userResult);
    }

    private static Result<User> updateUser(User user) {
        return Result.ofNullable(user)
                .flatMap(UsingResult::ifUserIsNotEmpty)
                .flatMap(UsingResult::checkPassword)
                .flatMap(UsingResult::checkAge)
                .map(UsingResult::updateUserValue);
    }

    private static User updateUserValue(User user) {
        return user.toBuilder().username("petka").build();
    }

    private static Result<User> ifUserIsNotEmpty(User user) {
        if (Objects.nonNull(user) && Objects.nonNull(user.getUsername()) && user.getPassword().length() > 0) {
            return Result.success(user);
        } else {
            return Result.failure("Missing username");
        }
    }

    private static Result<User> checkPassword(User user) {
        if (Objects.nonNull(user.getPassword()) && user.getPassword().length() > 5) {
            return Result.success(user);
        } else {
            return Result.failure("Short password");
        }
    }

    private static Result<User> checkAge(User user) {
        if (user.getAge() > 18) {
            return Result.success(user);
        } else {
            return Result.failure("Too young.");
        }
    }
}
