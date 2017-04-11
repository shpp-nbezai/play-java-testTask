package hash;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashCode;
import java.nio.charset.Charset;

public class MurmurHashComponent {
    private HashFunction murmurHf;

    public MurmurHashComponent(){
        this.murmurHf = Hashing.murmur3_128();
    }

    public int getHashAsInt(String inputString){
        HashCode hc = this.murmurHf.newHasher().putString(inputString, Charset.defaultCharset()).hash();
        return  hc.asInt();
    }

}

