/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudorsfgenerator;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author vicu
 */
public class PseudoRSFgenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Randomer r = new Randomer(8);
        char c[] = r.getRandomString();
        System.out.println(Arrays.toString(c));
       };
    }
    


class Randomer
{
    int BYTES;
    Randomer(int bytes)
    {
       BYTES = bytes;
    }
    public char[] getRandomString()
    {
        char[] seed = generateSeed();
        LFSR block = new LFSR(seed, BYTES);
        return block.getEntireSequence();
    }
    private char[] generateSeed()
    {
        char seed[] = new char[(int)(Math.log(BYTES)/Math.log(2.0))+1];
        for(int i = 0;i<seed.length;i++)
        {
            long clock1 = System.currentTimeMillis();
            long clock2 = System.nanoTime();
            AtomicLong at = new AtomicLong(clock2 % clock1);
            seed[i] = (char) (clock1 * clock2 % at.decrementAndGet());
        }
        return seed;
    }
}
//linear Feedback shifted register simulation for an vector of char's
class LFSR {
    private int OUT_BYTES= 0;
    private char[] state;
    private final int[] FUNCTION = new int[] {1, 31}; //linear function
    LFSR (char[] seed,int bytes)
    {
        OUT_BYTES = bytes;
        init_function(seed.length);
        init_state(seed);
    }
    private void init_function(int relative)//change function for smaller values
    {
        for(int i=0;i<FUNCTION.length;i++)
        {
            FUNCTION[i] = FUNCTION[i] % relative;
        }
    }
    //fill first state with seed
    private void init_state(char[] seed)
    {
        state = new char[seed.length];
        int seed_length = seed.length;
        System.arraycopy(seed, 0, state, 0, seed_length);
    }
    //simulate one clock
    public char nextOutput()
    {
        char top_byte = state[state.length-1];
        int func_length = FUNCTION.length; 
        //compute feedback value
        for(int i=0;i<func_length; i++)
        {
           top_byte  ^= state[state.length - FUNCTION[i]];
        }
        //shift ro right register
        for(int i = 0 ;i<state.length-1; i++)
        {
            state[i] = state[i+1];
        }   
        //initialise first byte
        state[state.length-1] = top_byte;
        return state[0];
    }
    //simultateOUT_BYTESclock and give entire sequence
    public char[] getEntireSequence()
    {
        char sequence[] = new char[OUT_BYTES];
        for (int i = 0; i<OUT_BYTES;i++)
        {
            sequence[i] = nextOutput();
        }
        return sequence;
    }
}