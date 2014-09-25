/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudorsfgenerator;

/**
 *
 * @author vicu
 */
public class PseudoRSFgenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        

    }
    
}

class Randomer
{
    int BYTES;
    Randomer(int bytes)
    {
        
    }
}

class LFSR {
    private int BYTES = 0;
    private char[] state;
    private final int[] FUNCTION = {4, 9, 22, 31};
    LFSR (char[] seed,int bytes)
    {
        BYTES = bytes;
        init_function();
        init_state(seed);
    }
    private void init_function()
    {
        for(int i=0;i<4;i++)
        {
            FUNCTION[i] = FUNCTION[i] % BYTES;
        }
    }
    private void init_state(char[] seed)
    {
        state = new char[BYTES];
        int seed_pos = 0;
        int seed_length = seed.length;
        for(int pos = 0; pos < BYTES; pos++)
        {
            state[pos] = seed[seed_pos];
            seed_pos++;
            if(seed_pos == seed_length)
                seed_pos = 0;
        }
    }
    
    public char nextOutput()
    {
        char top_byte = state[BYTES-1];
        for(int i=0;i<4; i++)
        {
           top_byte  ^= state[FUNCTION[i]];
        }
        for(int i = 0 ;i<BYTES-2; i++)
        {
            state[i] = state[i+1];
        }   
        state[BYTES-1] = top_byte;
        return state[0];
    }
    public char[] getEntireSequence()
    {
        char sequence[] = new char[BYTES];
        for (int i = 0; i< BYTES ;i++)
        {
            sequence[i] = nextOutput();
        }
        return sequence;
    }
}