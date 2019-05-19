package app.tweet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import app.tweet.util.StringUtil;

/**
 * 値が空でないか検証する
 * @author aoi
 *
 */
@Constraint(validatedBy = NotEmpty.NotEmptyValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
	String message() default "入力してください";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {	
		
		@Override
		public void initialize(NotEmpty notEmpty) {
		}
		
		/**
		 * 入力値が空かどうか検証する
		 * 空でないあ→true 空→false
		 */
		@Override
		public boolean isValid(String value, ConstraintValidatorContext cxt) {
			return !StringUtil.isNullOrEmpty(value);
		}
	}
}
