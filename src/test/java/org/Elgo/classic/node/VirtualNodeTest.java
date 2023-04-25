package org.Elgo.classic.node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtualNodeTest {


    Node dummy1=new PhysicalNode();
    Node dummy2=new PhysicalNode("Hamoksha");

    VirtualNode vNode1 = new VirtualNode(dummy1,1);
    VirtualNode vNode2 = new VirtualNode(dummy1,2);

    VirtualNode vNode3 = new VirtualNode(dummy2,1);
    VirtualNode vNode4 = new VirtualNode(dummy2,2);
    @Test
    void getKey() {
        System.out.println(vNode1.getKey());
        System.out.println(vNode2.getKey());
        System.out.println(vNode3.getKey());
        System.out.println(vNode4.getKey());
    }

    @Test
    void isVirtualOf() {
        System.out.println(vNode1.isVirtualOf(dummy1));
        System.out.println(vNode2.isVirtualOf(dummy2));
        System.out.println(vNode3.isVirtualOf(dummy1));
        System.out.println(vNode4.isVirtualOf(dummy2));
    }

    @Test
    void getVirtualIndex() {
        System.out.println(vNode1.getVirtualIndex());
    }

    @Test
    void getPhysicalNode() {

        System.out.println( vNode1.getPhysicalNode().toString());
    }

    @Test
    void testToString() {
        System.out.println(vNode1.toString());
    }
}