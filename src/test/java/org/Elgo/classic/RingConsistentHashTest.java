package org.Elgo.classic;

import org.Elgo.classic.node.Node;
import org.Elgo.classic.node.PhysicalNode;
import org.Elgo.hashFunctions.murmur3.Murmur;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RingConsistentHashTest {
    PhysicalNode dummy1=new PhysicalNode("1");
    PhysicalNode dummy2=new PhysicalNode("2");

     RingConsistentHash ringConsistentHash=new RingConsistentHash("ring",new Murmur());

    @Test
    void addNode() {
        System.out.println(ringConsistentHash.addNode(dummy1,5));
        System.out.println(ringConsistentHash.addNode(dummy2,5));
    }

    @Test
    void removeNode() {
        System.out.println(ringConsistentHash.addNode(dummy1,5));
        System.out.println(ringConsistentHash.addNode(dummy2,5));

        System.out.println(ringConsistentHash.removeNode(dummy1));

    }

    @Test
    void chooseNode() {
        System.out.println(ringConsistentHash.addNode(dummy1,5));
        System.out.println(ringConsistentHash.addNode(dummy2,5));
        for(int i=0;i<20;i++) {
            System.out.println(ringConsistentHash.chooseNode( UUID.randomUUID().toString().replace("-", "")).toString());
        }
    }
}