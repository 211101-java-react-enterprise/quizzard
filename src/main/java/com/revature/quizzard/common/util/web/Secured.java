package com.revature.quizzard.common.util.web;

import com.revature.quizzard.user.AppUser;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    AppUser.AccountType[] allowedAccountTypes();
}
