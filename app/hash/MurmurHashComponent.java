package hash;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashCode;
import java.nio.charset.Charset;
import javax.inject.*;

@Singleton
public class MurmurHashComponent {

    private HashFunction murmurHf;

    public MurmurHashComponent(){
        this.murmurHf = Hashing.murmur3_128();
    }

    public int getHashAsInt(String inputString){
        HashCode hashCode = this.murmurHf.newHasher().putString(inputString, Charset.defaultCharset()).hash();
        return  hashCode.asInt();
    }
}

