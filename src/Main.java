import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String args[]){
        Random rand1 = new Random(System.currentTimeMillis());
        Random rand2 = new Random(System.currentTimeMillis()*10);

        int pubkey = 2;

        BigInteger p = BigInteger.probablePrime(32, rand1);
        BigInteger q = BigInteger.probablePrime(32, rand2);

        BigInteger n = p.multiply(q);

        BigInteger p_1=p.subtract(new BigInteger("1"));
        BigInteger q_1=q.subtract(new BigInteger("1"));

        BigInteger z=p_1.multiply(q_1);

        while(true){
            BigInteger GCD = z.gcd(new BigInteger(""+pubkey));
            if(GCD.equals(BigInteger.ONE)){
                break;
            }
                pubkey ++;
        }

        BigInteger big_pubkey = new BigInteger(""+pubkey);
        BigInteger prvKey = big_pubkey.modInverse(z);
        System.out.println("public key " + pubkey+", "+n);
        System.out.println("private key " + prvKey+", "+n);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the message");
        String msg = sc.nextLine();

        byte[] bytes = msg.getBytes();

        for (int i=0; i<msg.length();i++){

            int asciiVal=bytes[i];
            BigInteger val = new BigInteger(String.valueOf(asciiVal));
            BigInteger cipherVal=val.modPow(big_pubkey,n);
            //System.out.println("ciphered text:"+cipherVal);

            BigInteger decypherVal = cipherVal.modPow(prvKey,n);
            int i_decypherVal = decypherVal.intValue();
            //System.out.println("plaint text: " + Character.toString((char)i_plainVal));

            System.out.println("\nMessage: " + msg.charAt(i) +
                    ", Ascii number: " + asciiVal+
                    "\nCyphered text: "+ cipherVal +
                                "\nDecyphered text: " + Character.toString((char)i_decypherVal));

        }
    }
}
