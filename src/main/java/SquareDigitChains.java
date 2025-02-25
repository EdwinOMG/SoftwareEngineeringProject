import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class SquareDigitChains {
    private int number;
    public void printSquareDigitChains() {
        ArrayList<Integer>storeChain = new ArrayList<Integer>();
        //arraylist that adds the numbers in the chain
        ArrayList<Integer>storeDigits = new ArrayList<Integer>();
        //arraylist that stores the digits that will be added to each other
        Set<Integer> chainEnd=new HashSet<>();
        int digits=0;
        storeChain.add(number);
        if (number>10){
            digits=number%10;
            //keeps going until there are no more digits to store
            storeDigits.add(digits);
            number=/10;
            for(int i=0; i<storeDigits.size(); i++){
                number+=storeDigits.get(i);
                //adds all the digits
            }
            storeChain.add(number);
            for(int i:storeChain){
                if(!chainEnd.add(i)){
                    System.out.println(storeChain);
                    //hashsets can't hold the same element twice
                }
            }

        } else {
            number=math.pow(2, number);
            storeChain.add(number);
            //immediately squares number if its less than 2 digits
        }
    }
}
