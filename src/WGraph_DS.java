import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *This class represents undirectional weighted graph.
 */
public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, HashMap<Integer,Edge>> map;
    private HashMap<Integer, node_info> nodes;
    private int edgeCount;
    private int modeCount;

    public WGraph_DS(){
        this.map=new HashMap<Integer, HashMap<Integer,Edge>>() ;
        this.nodes=new HashMap<Integer, node_info>();
        this.edgeCount=0;
        this.modeCount=0;
    }
    /**
     * return the node_info that belongs to the given key
     * @param key - the node_id
     * @return the node_info by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if(this.map.containsKey(key))
            return this.nodes.get(key);
        return null;
    }
    /**
     * This method checks if there is edge between 2 given node keys.
     * @param node1
     * @param node2
     * @return true iff there is edge between node1 and node 2, else false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
            if(node1==node2)
                return false;
            if(map.get(node1).containsKey(node2))
                return true;
        return false;
    }
    /**
     * This method return the edge between 2 given node keys
     * @param node1
     * @param node2
     * @return the edge between node1 and node2 if there is no edge return -1
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            return map.get(node1).get(node2).weight;
        }
        return -1;
    }
    /**
     * This method adds new node to the graph with the given key
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(!map.containsKey(key)) {
            node_info temp = new NodeInfo(key);
            HashMap<Integer, Edge> m = new HashMap<Integer, Edge>();
            this.map.put(key, m);
            this.nodes.put(key,temp);
            modeCount++;
        }
    }
    /**
     * This method connect an edge between node1 and node2 with given wight>=0
     * if there is already edge between node1 and node2 the method will update the weight
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(hasEdge(node1,node2)){
            map.get(node1).get(node2).setWeight(w);
            map.get(node2).get(node1).setWeight(w);
        }
        else if(node1!=node2) {
            Edge e = new Edge(getNode(node1), getNode(node2), w);
            Edge k = new Edge(getNode(node2), getNode(node1), w);
            map.get(node1).put(node2, e);
            map.get(node2).put(node1, k);
            edgeCount++;
            modeCount++;
        }
    }
    /**
     * This method return a collection of all the nodes in the graph
     * @return Collection<node_info> of all the nodes in the graph
     */
    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    /**
     * This method return a collection of all the nodes that connected with edges
     * to the given node_id
     * @param node_id
     * @return Collection<node_info> of all node_id neighbors
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
     * This method remove node with the given key from the graph.
     * This method passes over all the nodes in the graph and delete all the edges to the given node key
     * @param key
     * @return The data of the removed node
     */
    @Override
    public node_info removeNode(int key) {
        node_info temp =getNode(key);
        if (!this.map.containsKey(key))
            return null;
        Iterator<node_info> it=getV(key).iterator();
        while (it.hasNext())
        {
            map.get(it.next().getKey()).remove(key);
            edgeCount--;
        }
        this.map.remove(key);
        this.nodes.remove(key);
        modeCount++;
        return temp;
    }

    /**
     * This method remove an edge between given 2 keys (node1,node2) from the graph
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
     * This class represents vertex with meta data
     */
    private class NodeInfo implements node_info {

        private int key;
        private String info;
        private double tag;
        private HashMap<Integer, Edge> edges;

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
    }

    /**
     * this class represents an edge between 2 given nodes
     * an edge have src,dest and weight
     */
    private class Edge{
        private node_info src;
        private node_info dest;
        private double weight;

        public Edge(node_info src,node_info dest,double weight){
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

//    public static void main(String[] args) {
//        System.out.println("a");
//        weighted_graph g = new WGraph_DS();
//        g.addNode(0);
//        g.addNode(1);
//        g.addNode(2);
//        g.addNode(3);
//        System.out.println(g.hasEdge(0,1));
//        g.connect(0,1,1);
//        System.out.println(g.hasEdge(0,1));
//        System.out.println(g.getEdge(0,1));
//        g.removeEdge(0,1);
//        System.out.println(g.hasEdge(0,1));
//        g.connect(0,1,1);
//        g.connect(0,2,1);
//        g.connect(0,3,1);
//        g.connect(1,2,1);
//        g.removeNode(0);
//        System.out.println("a");
//    }
}
