package vn.iotstar.UrlUtil;
import org.apache.commons.text.StringEscapeUtils;

import java.io.UnsupportedEncodingException;

public class UrlEncoded {
	@SuppressWarnings("null")
	public static String sanitizeURLParameter(String parameter) throws UnsupportedEncodingException {
	    if (isSafeParameter(parameter)) {
	    	// Kiểm tra và loại bỏ các ký tự không hợp lệ trong URL
            String sanitizedParameter = parameter.replaceAll("[^a-zA-Z0-9-._~:/?#\\[\\]@!$&'()*+,;=%]", "")
            		.replace("<", "").replace(">", "");
            
            return StringEscapeUtils.escapeHtml4(sanitizedParameter);

	    }
	    return "";
	}
	public static boolean isSafeParameter(String parameter) {
	    // Kiểm tra nếu tham số là null hoặc rỗng
	    if (parameter == null || parameter.isEmpty()) {
	        return false;
	    }

	    // Kiểm tra các ký tự không an toàn
	    String unsafeCharacters = "<>\\\"'&%;()";
	    for (char c : unsafeCharacters.toCharArray()) {
	        if (parameter.indexOf(c) != -1) {
	            return false;
	        }
	    }

	    return true;
	}

}
