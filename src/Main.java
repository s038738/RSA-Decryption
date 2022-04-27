import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class Main {

        public static void main(String args[]) throws IOException {


            Scanner input = new Scanner(System.in);
            boolean mainLoop = true;

            int choice;
            while(true){
                System.out.println("\nMain Menu");
                System.out.print("1.) ALL \n");
                System.out.print("2.) Encryption.\n");
                System.out.print("3.) Decryption.\n");
                System.out.print("\nEnter Your Menu Choice: ");

                choice = input.nextInt();


                switch(choice){

                    case 1:
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Enter text: ");
                        String msg = sc.nextLine();
                        System.out.println("Enter P value: ");
                        BigInteger p = sc.nextBigInteger();
                        System.out.println("IEnter Q value: ");
                        BigInteger q = sc.nextBigInteger();


                        FileWriter myWriter = new FileWriter("Ciphered_Value.txt");

                        BigInteger n = p.multiply(q);

                        var e = BigInteger.valueOf(0x10001);
                        System.out.println("P Value: " + p + "\nQ Value: " + q);
                        var phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
                        //System.out.println(phi);

                        var d = e.modInverse(phi);
                        System.out.println("Public key: " + e + "\nn Value: " + n);


                        byte[] bytes = msg.getBytes();

                        int i_decypherVal = 0;
                        for (int i = 0; i < msg.length(); i++) {

                            int asciiVal = bytes[i];
                            BigInteger val = new BigInteger(String.valueOf(asciiVal));
                            BigInteger cipherVal = val.modPow(e, n);

                            BigInteger decypherVal = cipherVal.modPow(d, n);
                            i_decypherVal = decypherVal.intValue();

                            System.out.println("\nMessage char : " + msg.charAt(i) +
                                    "\nAscii number: " + asciiVal +
                                    "\nCyphered text: " + cipherVal +
                                    "\nDeciphered text: " + Character.toString((char) i_decypherVal)+
                            "\nDeciphered value: " + i_decypherVal);
                            myWriter.write(String.valueOf(cipherVal) + " ");

                        }
                        myWriter.close();

                        BigInteger [] bigInteger = new BigInteger[bytes.length];
                        String actual = Files.readString(Path.of("Ciphered_Value.txt"));
                        String [] numbers  = actual.split(" ");
                        for (int i = 0;i<numbers.length;i++){
                            bigInteger[i]=new BigInteger(numbers[i]);
                        }

                        byte [] bytes2 = new byte[bigInteger.length];
                        for (int i = 0; i <bytes2.length;i++){
                            bytes2[i]=bigInteger[i].modPow(d,n).byteValue();
                        }
                        String str = new String(bytes2);
                        System.out.println("\nDeciphered text: "+str);

                        break;
                    case 2:
                        sc = new Scanner(System.in);
                        System.out.println("Enter text: ");
                        msg = sc.nextLine();
                        System.out.println("Enter P value: ");
                        p = sc.nextBigInteger();
                        System.out.println("IEnter Q value: ");
                        q = sc.nextBigInteger();

                        n = p.multiply(q);

                        e = BigInteger.valueOf(0x10001);
                        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

                        System.out.println("Public key: " + e + "\nn Value: " + n);

                        bytes = msg.getBytes();

                        for (int i = 0; i < msg.length(); i++) {

                            int asciiVal = bytes[i];
                            BigInteger val = new BigInteger(String.valueOf(asciiVal));
                            BigInteger cipherVal = val.modPow(e, n);

                            System.out.println("\nMessage: " + msg.charAt(i) +
                                    "\nCyphered text: " + cipherVal);
                        }
                        break;
                    case 3:
                        sc = new Scanner(System.in);
                        System.out.println("Enter text: ");
                        msg = sc.nextLine();
                        System.out.println("Enter P value: ");
                        p = sc.nextBigInteger();
                        System.out.println("IEnter Q value: ");
                        q = sc.nextBigInteger();

                        n = p.multiply(q);
                        e = BigInteger.valueOf(0x10001);

                        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

                        d = e.modInverse(phi);
                        System.out.println("Public key: " + e + "\nn Value: " + n);

                        int i = Integer.parseInt(msg);
                        BigInteger bigInteger1 = BigInteger.valueOf(i);
                        bigInteger1 = bigInteger1.modPow(d,n);
                        int ans = bigInteger1.intValue();
                        System.out.println("\nDeciphered Char: " + Character.toString((char) ans));

                        break;
                    default :
                        System.out.println("This is not a valid Menu Option! Please Select Another");
                        break;

                }

            }

        }
}