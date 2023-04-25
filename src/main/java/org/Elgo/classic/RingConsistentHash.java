package org.Elgo.classic;

import org.Elgo.classic.node.Node;
import org.Elgo.classic.node.PhysicalNode;
import org.Elgo.classic.node.VirtualNode;
import org.Elgo.hashFunctions.HashFunction;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RingConsistentHash {
    private String name;
    private HashFunction hashFunction;


    private final ReadWriteLock mutex = new ReentrantReadWriteLock(true);
    private final SortedMap<Long, VirtualNode> ring = new TreeMap<Long, VirtualNode>();

    RingConsistentHash(String name, HashFunction hashFunction) {
        this.name = name;
        this.hashFunction = hashFunction;
    }

    public boolean addNode(final PhysicalNode physicalNode, final int virtualNodeCount) {
        boolean addOper = false;

        if (mutex.writeLock().tryLock()) {

            try {
                int existingVirtualNodeCount = getVirtualNodeCount(physicalNode);
                for (int iter = 0; iter < virtualNodeCount; iter++) {
                    final VirtualNode virtualNode =
                            new VirtualNode( physicalNode, iter + existingVirtualNodeCount);
                    long hash = hashFunction.hash32(virtualNode.getKey(),0);
                    ring.put(hash, virtualNode);

                    addOper = true; // overwrite away
                }
            }
            finally {
                mutex.writeLock().unlock();
            }
        }
        return addOper;
    }


    public boolean removeNode(final PhysicalNode physicalNode) {
        boolean removeOper = false;

        if (mutex.writeLock().tryLock()) {
            try {
                Iterator<Long> iterator = ring.keySet().iterator();
                while (iterator.hasNext()) {
                    final Long key = iterator.next();
                    final VirtualNode virtualNode = ring.get(key);
                    if (virtualNode.isVirtualOf(physicalNode)) {
                        iterator.remove();
                        removeOper = true; // overwrite away
                    }
                }
            } finally {
                mutex.writeLock().unlock();
            }
        }
        return removeOper;
    }

    public Node chooseNode(final String key) {
        Node node = null;
        if (mutex.readLock().tryLock()) {
            try {
                if (ring.isEmpty()) {
                    return node;
                }
                // a. compute incoming key's hash
                final Long hash = Long.valueOf(hashFunction.hash32(key,0));

                // b. look for all virtual nodes with hashValue >= hash
                final SortedMap<Long, VirtualNode> tailMap = ring.tailMap(hash);

                // c. if not empty, select the hash for the first virtual node. Else, for wrap-around case,
                // pick the first hash (lowest hash value) from the ring
                final Long nodeHash = !tailMap.isEmpty() ? tailMap.firstKey() : ring.firstKey();

                // d. lookup hash->virtualNode->physicalNode
                node = ring.get(nodeHash).getPhysicalNode();
            } finally {
                mutex.readLock().unlock();
            }
        }
        return node;
    }

            /**
             * "Compute" total number of virtual nodes in the ring.
             */
            public int getTotalVirtualNodeCount () {
                int totalVirtualNodeCount = Integer.MIN_VALUE;
                if (mutex.readLock().tryLock()) {
                    try {
                        totalVirtualNodeCount = ring.values().size();
                    } finally {
                        mutex.readLock().unlock();
                    }
                }
                return totalVirtualNodeCount;
            }

            /**
             * "Compute" total number of physical nodes in the ring.
             */
            public int getTotalPhysicalNodeCount () {
                int physicalNodeSize = Integer.MIN_VALUE;
                if (mutex.readLock().tryLock()) {
                    try {
                        final Set physicalNodes = new HashSet<>();
                        for (VirtualNode virtualNode : ring.values()) {
                            physicalNodes.add(virtualNode.getPhysicalNode());
                        }
                        physicalNodeSize = physicalNodes.size();
                    } finally {
                        mutex.readLock().unlock();
                    }
                }
                return physicalNodeSize;
            }

            /**
             * "Compute" total number of virtual nodes in the ring belonging to/associated with the given
             * physicalNode.
             */
            public int getVirtualNodeCount (PhysicalNode pNode){
                int vNodeCount = Integer.MIN_VALUE;
                if (mutex.readLock().tryLock()) {
                    vNodeCount = 0;
                    try {
                        for (final VirtualNode virtualNode : ring.values()) {
                            if (virtualNode.isVirtualOf(pNode)) {
                                vNodeCount++;
                            }
                        }
                    } finally {
                        mutex.readLock().unlock();
                    }
                }
                return vNodeCount;
            }

        }
