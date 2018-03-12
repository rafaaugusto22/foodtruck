#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import java.security.SecureRandom;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;

public class JWTExample {

	public static void main(String[] args) {
		// Create an HMAC-protected JWS object with some payload
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello world!"));

		// We need a 256-bit key for HS256 which must be pre-shared
		byte[] sharedKey = new byte[32];
		new SecureRandom().nextBytes(sharedKey);

		// Apply the HMAC to the JWS object
		try {

			jwsObject.sign(new MACSigner(sharedKey));

		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Output to URL-safe format
		System.out.println(jwsObject.serialize());

	}

}