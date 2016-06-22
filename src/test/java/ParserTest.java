import io.tazi.Parser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guemues on 22/06/16.
 */
public class ParserTest {
    @Test
    public void parse() throws Exception {
        String input = "Sep 19 08:26:10 host CEF:0|Security|threatmanager|1.0|100|worm successfully stopped|10|src=10.0.0.1 dst=\"2.1.2.2 tertgb  set s sag \" spt=1232";
        String expectedOutput = "{\"dst\" : \"2.1.2.2 tertgb  set s sag \", \"DeviceProduct\" : \"threatmanager\", \"Name\" : \"worm successfully stopped\", \"SignatureID\" : \"100\", \"DeviceVersion\" : \"1.0\", \"src\" : \"10.0.0.1\", \"CEFVersion\" : \"Sep 19 08:26:10 host CEF:0\", \"DeviceVendor\" : \"Security\", \"spt\" : 1232.0, \"Severity\" : \"10\"}";

        Parser p = new Parser();
        assertEquals(expectedOutput, p.parse(input));

    }
}