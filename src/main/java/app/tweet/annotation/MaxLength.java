package app.tweet.annotation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy = MaxLength.MaxLengthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {
	String message() default "文字以下で入力してください";
	int maxLength() default 0;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {
		
		private int maxLength;
		
		@Override
		public void initialize(MaxLength maxLength) {
			this.maxLength = maxLength.maxLength();
		}
		
		@Override
		public boolean isValid(String value, ConstraintValidatorContext cxt) {
			return value.length() <= this.maxLength;
		}
	}
}
