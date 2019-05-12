package app.tweet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;

import app.tweet.dao.TmUserDao;

@Constraint(validatedBy = UniqueUserID.UniqueUserIDValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserID {
	String message() default "入力されたユーザIDは既に使用されています";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class UniqueUserIDValidator implements ConstraintValidator<UniqueUserID, String> {
		
		@Autowired
		TmUserDao tmUserDao;
		
		@Override
		public void initialize(UniqueUserID uniqueUserID) {
		}
		
		@Override
		public boolean isValid(String userId, ConstraintValidatorContext cxt) {
			if(tmUserDao.findByUserName(userId) == null) {
				return true;
			};
			return false;
		}
	}
}
