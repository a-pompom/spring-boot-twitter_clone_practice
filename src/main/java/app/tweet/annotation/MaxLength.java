package app.tweet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 入力値の最大長に制限を課す
 * @author aoi
 */
@Constraint(validatedBy = MaxLength.MaxLengthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {
	String message() default "文字以下で入力してください";
	int maxLength() default 0;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {
		
		/**
		 * 個別のパラメータに与えられる最大長
		 */
		private int maxLength;
		
		/**
		 * アノテーションに与えられたvalue値で最大長を設定
		 */
		@Override
		public void initialize(MaxLength maxLength) {
			this.maxLength = maxLength.maxLength();
		}
		
		/**
		 * 検査対象値が最大長以下で収まっているか検証する
		 * @param 検査対象値
		 * @return 最大長以下→true 超過→false
		 */
		@Override
		public boolean isValid(String value, ConstraintValidatorContext cxt) {
			return value.length() <= this.maxLength;
		}
	}
}
