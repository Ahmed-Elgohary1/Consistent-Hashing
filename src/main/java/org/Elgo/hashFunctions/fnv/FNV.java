package org.Elgo.hashFunctions.fnv;

import org.Elgo.hashFunctions.HashFunction;

public class FNV implements HashFunction {
    /**
     * Helps convert a byte into its unsigned value
     */
    public static final int UNSIGNED_MASK = 0xff;

    /**
     * Helps convert integer to its unsigned value
     */
    public static final long UINT_MASK = 0xFFFFFFFFl;

    public static final long prime =16777619;


    public static int hash32(byte[] bytes,long seed) {

        long hash=2166136261L;

        for(byte cur:bytes){
            hash=(hash*prime) & UINT_MASK;
            hash=(hash ^ (cur & UNSIGNED_MASK))&UINT_MASK;
        }

        return (int) hash;
    }
@Override
    public  int hash32(String data,long seed){
        byte[] bytes = data.getBytes();

        return hash32(bytes,0);
    }




}
