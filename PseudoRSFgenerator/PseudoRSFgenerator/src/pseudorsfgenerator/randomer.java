/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudorsfgenerator;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author vicu
 */

class Randomer
{
    int BYTES;
    Randomer(int bytes)
    {
       BYTES = bytes + 1;
    }
    public byte[] getRandomString()
    {
        byte[] seed = generateSeed();
        LFSR block = new LFSR(seed, BYTES);
        return block.getEntireSequence();
    }
    private byte[] generateSeed()
    {
        byte seed[] = new byte[(int)(Math.log(BYTES)/Math.log(2.0))+1];
        for(int i = 0;i<seed.length;i++)
        {
            long clock1 = System.currentTimeMillis();
            long clock2 = System.nanoTime();
            AtomicLong at = new AtomicLong(clock2 % clock1);
            seed[i] = (byte) (clock1 * clock2 % at.decrementAndGet());
        }
        return seed;
    }
}
//linear Feedback shifted register simulation for an vector of byte's
class LFSR {
    private int OUT_BYTES= 0;
    private byte[] state;
    private final int[] FUNCTION = new int[] {0}; //linear function
    LFSR (byte[] seed,int bytes)
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
    private void init_state(byte[] seed)
    {
        state = new byte[seed.length];
        int seed_length = seed.length;
        System.arraycopy(seed, 0, state, 0, seed_length);
    }
    //simulate one clock
    public byte nextOutput()
    {
        byte top_byte = state[state.length-1];
        int func_length = FUNCTION.length; 
        //compute feedback value
        for(int i=0;i<func_length; i++)
        {
           top_byte  ^= state[ FUNCTION[i]];
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
    public byte[] getEntireSequence()
    {
        byte sequence[] = new byte[OUT_BYTES];
        for (int i = 0; i<OUT_BYTES-1;i++)
        {
            sequence[i] = nextOutput();
        }
        return sequence;
    }
}