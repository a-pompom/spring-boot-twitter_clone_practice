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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmUserDao;

/**
 * 入力されたユーザIDがユニークなものか検証する
 * @author aoi
 *
 */
@Constraint(validatedBy = UniqueUserID.UniqueUserIDValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserID {
	String message() default "入力されたユーザIDは既に使用されています";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	@Component
	public class UniqueUserIDValidator implements ConstraintValidator<UniqueUserID, String> {
		
		@Autowired
		TmUserDao dao;
		
		@Override
		public void initialize(UniqueUserID uniqueUserID) {
		}
		
		/**
		 * 入力されたユーザIDがユニークなものか検証する
		 * ユニーク→true ユニークでない→false
		 */
		@Override
		@Transactional
		public boolean isValid(String userId, ConstraintValidatorContext cxt) {
			//DB上に存在しない場合はnullが返るため、nullチェックで検証
			if(dao.findByUserName(userId) == null) {
				return true;
			};
			return false;
		}
	}
}
