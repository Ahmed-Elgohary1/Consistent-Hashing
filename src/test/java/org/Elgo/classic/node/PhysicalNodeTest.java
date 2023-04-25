package org.Elgo.classic.node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalNodeTest {

    Node dummy1=new PhysicalNode();
    Node dummy2=new PhysicalNode("Hamoksha");


    @Test
    void getKey() {

        System.out.println(dummy1.getKey());
        System.out.println(dummy2.getKey());
    }

    @Test
    void testToString() {
        System.out.println(dummy1.toString());
        System.out.println(dummy2.toString());

    }
}