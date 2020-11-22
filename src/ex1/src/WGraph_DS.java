package ex1.src;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *This class represents undirectional weighted graph with 2 Hashmaps
 * 1-For the edges class 2-for all the nodes in the given graph
 * with edge count for the number of edges and with mode count for the number of changes in the graph.
 */
public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, HashMap<Integer, edge>> map;
    private HashMap<Integer, node_info> nodes;
    private int edgeCount;
    private int modeCount;

    public WGraph_DS(){
        this.map=new HashMap<Integer, HashMap<Integer, edge>>() ;
        this.nodes=new HashMap<Integer, node_info>();
        this.edgeCount=0;
        this.modeCount=0;
    }
    /**
     * This method check if there is a node with the given key in the graph
     * If the node exist in the graph the method return him
     * If the node does not exist exist in the graph the method return null
     * @param key - the node_id
     * @return the ex1.src.node_info by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if(this.map.containsKey(key))
            return this.nodes.get(key);
        return null;
    }
    /**
     * This method checks if the given node2 key exist in the node1 edges hashmap - return true
     * if the node2 key does not exist in the node1 edges hashmap - return false
     * @param node1
     * @param node2
     * @return true iff there is edge between node1 and node 2, else false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(this.nodes.containsKey(node1)&&this.nodes.containsKey(node2)) {
            if (node1 == node2)
                return false;
            if (map.get(node1).containsKey(node2))
                return true;
        }
        return false;

    }
    /**
     * This method checks if there is edge between the given keys.
     * if there is edge between node1 and node2 the method returns the weight of the edge
     * by checking in the edges hashmap
     * if there is no edge between the given nodes return -1
     * @param node1
     * @param node2
     * @return the weight of the edge between node1 and node2 if there is no edge return -1
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            return map.get(node1).get(node2).weight;
        }
        return -1;
    }
    /**
     * This method checks if there is already a node in the graph with the given key
     * If there isnt the method creates new node with the given key and add him to the graph
     * also add the given node to the edges map and nodes map
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(!map.containsKey(key)) {
            node_info temp = new NodeInfo(key);
            HashMap<Integer, edge> m = new HashMap<Integer, edge>();
            this.map.put(key, m);
            this.nodes.put(key,temp);
            modeCount++;
        }
    }
    /**
     * This method checks if there is an edge between the given node keys
     * If there is an edge between them update the new edge weight in the nodes edge's hashmap
     * If there isnt an edge the method creates new edge between them and edit each node edge's hashmap
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(this.nodes.containsKey(node1)&&this.nodes.containsKey(node2)) {
            if (hasEdge(node1, node2)) {
                map.get(node1).get(node2).setWeight(w);
                map.get(node2).get(node1).setWeight(w);
            } else if (node1 != node2) {
                edge e = new edge(getNode(node1), getNode(node2), w);
                edge k = new edge(getNode(node2), getNode(node1), w);
                map.get(node1).put(node2, e);
                map.get(node2).put(node1, k);
                edgeCount++;
                modeCount++;
            }
        }
    }
    /**
     * This method return a collection of all the nodes in the graph
     * @return Collection<ex1.src.node_info> of all the nodes in the graph
     */
    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    /**
     * This method build a new hashmap with all the given key node neighbors
     * and return the new hashmap as Collection<ex1.src.node_info>
     * @param node_id
     * @return Collection<ex1.src.node_info> of all node_id neighbors
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        int count=0;
        HashMap<Integer, node_info> temp = new HashMap<Integer, node_info>();
        Iterator<Integer> it = map.get(node_id).keySet().iterator();
        while(it.hasNext())
            temp.put(count++,getNode(it.next()));
        return temp.values();
    }
    /**
     * This method checks if there is a node with the given key in the graph
     * If there is the method run over all the nodes in the graph and delete
     * the given key node as neighbor from all of them.
     * At the end remove the given key node from the edges hashmap and the nodes hashmap
     * and returns the node data
     * If there isnt a node with the given key in the graph return null
     * @param key
     * @return The data of the removed node
     */
    @Override
    public node_info removeNode(int key) {
        if (!this.map.containsKey(key))
            return null;
        node_info temp=getNode(key);
        Iterator<node_info> it=getV(key).iterator();
        while (it.hasNext())
        {
            map.get(it.next().getKey()).remove(key);
            edgeCount--;
            modeCount++;

        }
        this.map.remove(key);
        this.nodes.remove(key);
        modeCount++;
        return temp;
    }

    /**
     * This method checks if there is an edge between the given nodes keys
     * If there is edge between the given nodes remove from each edges hashmaps the edge
     * If there isnt an edge between them do nothing.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1,node2)) {
            this.map.get(node1).remove(node2);
            this.map.get(node2).remove(node1);
            edgeCount--;
            modeCount++;
        }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeCount;
    }

    @Override
    public int getMC() {
        return modeCount;
    }
    /**
     * This method run over the given graph nodes,edges,edge's weight and checks
     * if all the data is same as the graph the method works for.
     * If all the data is the same return true else return false
     * @param o
     * @return true if the two graphs equal in all the graph data.
     */
    @Override
    public boolean equals(Object o) {
        boolean ans=true;
        node_info temp= null;
        node_info temp2=null;
        node_info tempN= null;
        node_info tempN2= null;
        double edge=0;
        double edge2=0;

        if ((o instanceof WGraph_DS)==false)
            return false;
        WGraph_DS tempGraph = (WGraph_DS) o;

        if(nodeSize()!= tempGraph.nodeSize())
            return false;

        if (edgeCount != tempGraph.edgeCount)
            return false;
        Iterator<node_info> it=this.nodes.values().iterator();
        Iterator<node_info> it1=tempGraph.getV().iterator();
        while (it.hasNext() && it1.hasNext())
        {
             temp= it.next();
             temp2= it1.next();
            if (temp.getTag()!=temp2.getTag()){
                if((temp.getInfo().equals(temp2.getInfo()))==false||temp.getKey()!=temp2.getKey())
                    return false;
            }
            else
            {
                Iterator<node_info> itr=tempGraph.getV(temp.getKey()).iterator();
                Iterator<node_info> itr1=getV(temp2.getKey()).iterator();
                while (itr.hasNext()==true && itr1.hasNext()==true)
                {
                     tempN= itr.next();
                     tempN2= itr1.next();
                    if (  temp.getKey()!=temp2.getKey()){
                        if(temp.getTag()!=temp2.getTag()||(temp.getInfo().equals(temp2.getInfo()))==false)
                            return false;
                    }
                        edge=getEdge(temp.getKey(), tempN.getKey());
                        edge2=tempGraph.getEdge(temp2.getKey(), tempN2.getKey());
                    if (edge!=edge2)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * This class represents vertex with key as "name" hashmap as edge collection
     * and info,tag as data
     * The node used as vertex in the graph classes above
     */
    private class NodeInfo implements node_info,Serializable,Comparable<node_info> {

        private int key;
        private String info;
        private double tag;
        private HashMap<Integer, edge> edges;

    public NodeInfo(int key){
            this.key=key;
            this.info="";
            this.tag=Integer.MAX_VALUE;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info=s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public int compareTo(node_info o) {
            if (this.getTag() < o.getTag()) return -1;
            else if (this.getTag() > o.getTag()) return 1;
            return 0;
        }
    }


    /**
     * this class represents an edge between 2 given nodes
     * every edge has src node,dest node and weight.
     */
    private class edge implements Serializable{
        private node_info src;
        private node_info dest;
        private double weight;

        public edge(node_info src, node_info dest, double weight){
            this.dest=dest;
            this.weight=weight;
        }
        public double getWeight(node_info dest){
            return this.weight;
        }
        public void setWeight(double weight){
            this.weight=weight;
        }
        public node_info getDest(){
            return this.dest;
        }
        public void setDest(node_info dest){
            this.dest=dest;
        }
    }
}
