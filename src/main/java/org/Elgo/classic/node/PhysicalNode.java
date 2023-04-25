package org.Elgo.classic.node;

import java.util.UUID;

public class PhysicalNode implements Node{

    private final String key;

   public PhysicalNode(String key){
        this.key=key;
    }
    public PhysicalNode(){
        String randomId = UUID.randomUUID().toString().replace("-", "");
        this.key=randomId;
    }

    @Override
    public String getKey(){
   return key;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PhysicalNode [key:");
        builder.append(key);
        builder.append("]");
        return builder.toString();
    }
}
