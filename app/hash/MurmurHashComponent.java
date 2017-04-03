package hash;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.nio.charset.Charset;

public class MurmurHashComponent {

    private HashFunction murmurHf;

    public int getHashAsInt(String inputString){
        this.murmurHf = Hashing.murmur3_128();
        com.google.common.hash.HashCode hc = this.murmurHf.newHasher().putString(inputString, Charset.defaultCharset()).hash();
        return  hc.asInt();
    }

}
