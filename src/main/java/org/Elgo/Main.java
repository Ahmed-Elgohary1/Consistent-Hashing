package org.Elgo;

import org.Elgo.hashFunctions.HashFunction;
import org.Elgo.hashFunctions.fnv.FNV;
import org.Elgo.hashFunctions.murmur3.Murmur;

import static org.Elgo.hashFunctions.murmur3.Util.display;

public class Main {
    public static void main(String[] args) {

        HashFunction fnv=new FNV();
        System.out.println("Hash from murmur: " + display(fnv.hash32("Ahmed Elgohary",0)));
    }
}