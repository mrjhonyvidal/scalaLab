package playground;

/**
 * Created by Jhony Vidal on 24-May-20.
 */
public class JavaPlayground {
    // We use Class Level functionality by accessing Person Eyes without instance of Person class
    public static void main(String args[]){
        System.out.println(Person.N_EYES);
    }
}

class Person {
    public static final int N_EYES = 2;
}
