package app.tweet.annotation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy = AuthInputType.AuthInputTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthInputType {
	String message() default "半角英数、「-, _」のみ使用できます";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class AuthInputTypeValidator implements ConstraintValidator<AuthInputType, String> {
		
		
		@Override
		public void initialize(AuthInputType authInputType) {
		}
		
		@Override
		public boolean isValid(String value, ConstraintValidatorContext cxt) {
			if (value.matches("[a-zA-Z0-9-_]")) {
				return true;
			}
			return false;
		}
	}
}
