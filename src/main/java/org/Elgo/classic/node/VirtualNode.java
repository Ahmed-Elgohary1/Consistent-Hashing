package org.Elgo.classic.node;

import jakarta.validation.constraints.NotNull;

public class VirtualNode implements Node{

    private final String key;
    private final Node physicalNode;
    private int virtualIndex;

    public VirtualNode(@NotNull Node physicalNode, @NotNull int virtualIndex){
        this.physicalNode=physicalNode;
        this.virtualIndex=virtualIndex;

        this.key=physicalNode.getKey()+"_"+virtualIndex;
    }
    @Override
    public String getKey(){
        return this.key;
    }

    public Boolean isVirtualOf(final Node physicalNode){
        Boolean check;
        check= this.physicalNode.getKey().equals(physicalNode.getKey());
        return check;
    }



    public int getVirtualIndex() {
        return virtualIndex;
    }

    public Node getPhysicalNode() {
        return physicalNode;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VirtualNode [key=");
        builder.append(getKey());
        builder.append(", ");
        builder.append(physicalNode);
        builder.append(", virtualNodeIndex=");
        builder.append(virtualIndex);
        builder.append("]");
        return builder.toString();
    }
}
