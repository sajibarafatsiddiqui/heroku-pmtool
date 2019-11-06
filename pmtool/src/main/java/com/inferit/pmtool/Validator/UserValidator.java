package com.inferit.projectmanagementtool.Validator;

import com.inferit.projectmanagementtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user=(User) object;
        if(user.getPassword().length()<=6){
            errors.rejectValue("password","Length","Password should be more than six charecters");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match","Confirm password must contain the same value as password!");
        }
    }
}
