package ex1.src;
import java.io.*;
import java.util.*;
import java.io.Serializable;

/**
 * This class represents few methods that can be used on an undirectional weighted graph
 *
 */
public class WGraph_Algo implements weighted_graph_algorithms,Serializable {
    private weighted_graph wga;

    public WGraph_Algo(){

    }

    @Override
    public void init(weighted_graph g) {
    this.wga=g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.wga;
    }

    /**
     * This method create a deep copy of the given graph by creating new graph
     * run over the "old" graph and add all his nodes and edges the the new graph
     * copy the edge count and mode count as well
     * @return Deep copied graph
     */
    @Override
    public weighted_graph copy() {
        node_info temp=null;
        node_info temp2=null;
        if(wga!=null){
            weighted_graph temp_graph = new WGraph_DS();
            Iterator <node_info> it =this.wga.getV().iterator();
            while(it.hasNext()){
                temp_graph.addNode(it.next().getKey());
            }
            Iterator <node_info> it2 = this.wga.getV().iterator();
            while (it2.hasNext()){
                temp=it2.next();
                Iterator <node_info> it3 = this.wga.getV(temp.getKey()).iterator();
                while (it3.hasNext()){
                    temp2=it3.next();
                    temp_graph.connect(temp.getKey(),temp2.getKey(),this.wga.getEdge(temp.getKey(),temp2.getKey()));
                }
            }
            return temp_graph;
        }
        return null;
    }

    /**
     * This method starts at the first node in the graph, changes his tag to 1 than move all over his neighbors
     * and changes there tags to 1 than moves all over the neighbors than set the first neighbor as the first node
     * and move over his neighbors (changing their tags to 1) and so on
     * If all the nodes in the graph has 1 in their tags return true else return false
     * @return True iff there is a valid path from every node to each other node in the graph else false.
     */
    @Override
    public boolean isConnected() {
        int nodes = wga.nodeSize();
        if(nodes==0||nodes==1)
            return true;
        Queue<node_info> q = new LinkedList<node_info>();
        boolean ans=true;
        node_info temp = null;
        node_info temp2 = null;
        node_info temp3 = null;
        Iterator<node_info> it = wga.getV().iterator();
        temp=it.next();
        q.add(temp);
        temp.setTag(1);
        while(!q.isEmpty()){
            temp2=q.poll();
            Iterator<node_info> it1 = wga.getV(temp2.getKey()).iterator();
            while(it1.hasNext()){
                temp2=it1.next();
                if(temp2.getTag()==Integer.MAX_VALUE){
                    q.add(temp2);
                    temp2.setTag(1);
                }
            }
        }
        Iterator<node_info> it2 = wga.getV().iterator();
        while (it2.hasNext()){
            temp3=it2.next();
            if(temp3.getTag()==Integer.MAX_VALUE)
                ans=false;
        }
        resInfo(wga);
        return ans;
    }

    /**
     * This method run over all the nodes connected between the src and dest given and all their neighbors
     * and changing the tags of the nodes according to this calculation:
     * my tag = my neighbor's smallest tag+the edge between me and my neighbor.
     * The calculation using PriorityQueue to find the lowest weight neighbor.
     * returns the shortest path consider edge's weight
     * If there is no vailed path return -1;
     * @param src - start node
     * @param dest - end (target) node
     * @return double-the shortest path between src to dest
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        node_info temp = null;
        double tempWeight=0;
       if(src==dest)
            return 0;

        PriorityQueue<node_info> q = new PriorityQueue<>();
        q.add(wga.getNode(src));
        wga.getNode(src).setTag(0);
        while (!q.isEmpty()) {
             temp = q.peek();
            if (temp.getInfo() == "") {
                temp.setInfo("1");
                if (temp.getKey() == dest) break;
                Iterator<node_info> it = wga.getV(temp.getKey()).iterator();
                while (it.hasNext()) {
                    node_info n = it.next();
                    if (n.getInfo() == "") {
                         tempWeight = wga.getEdge(temp.getKey(), n.getKey());
                        if (tempWeight != -1 && tempWeight + temp.getTag() < n.getTag()) {
                            n.setTag(tempWeight + temp.getTag());
                            if (!q.contains(n)) q.add(n);
                        }
                    }
                }
            }
            q.poll();
        }
        double weight=wga.getNode(dest).getTag();
        if(weight!=Integer.MAX_VALUE) {
            resInfo(wga);
            return weight;
        }
        resInfo(wga);
        return -1;
    }

    /**
     * This method using the same algo as shortestPathDist to determine the nodes tags.
     * Run over the shortestPath from dest to src according to this calculation:
     * searching for my neighbor who has the tag of mine-the edge between us
     * @param src - start node
     * @param dest - end (target) node
     * @return List<ex1.src.node_info> of the shortest patch nodes
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> l = new ArrayList<node_info>();
        node_info temp = null;
        double tempWeight=0;
        if(src==dest)
            return null;
        PriorityQueue<node_info> q = new PriorityQueue<>();
        q.add(wga.getNode(src));
        wga.getNode(src).setTag(0);
        while (!q.isEmpty()) {
            temp = q.peek();
            if (temp.getInfo() == "") {
                temp.setInfo("1");
                if (temp.getKey() == dest) break;
                Iterator<node_info> it = wga.getV(temp.getKey()).iterator();
                while (it.hasNext()) {
                    node_info n = it.next();
                    if (n.getInfo() == "") {
                        tempWeight = wga.getEdge(temp.getKey(), n.getKey());
                        if (tempWeight != -1 && tempWeight + temp.getTag() < n.getTag()) {
                            n.setTag(tempWeight + temp.getTag());
                            if (!q.contains(n)) q.add(n);
                        }
                    }
                }
            }
            q.poll();
        }
        if(wga.getNode(dest).getTag()==Integer.MAX_VALUE)
            return null;
        l.add(wga.getNode(dest));
        listMaker(wga.getNode(dest),l);
        Stack<node_info> s = new Stack<node_info>();
            for(int i=0;i<l.size();){
                s.push(l.remove(i));
        }
            while(!s.isEmpty())
                l.add(s.pop());
            resInfo(wga);
        return l;
    }
    private void listMaker(node_info dest ,List<node_info> l) {
        Iterator <node_info> ni = wga.getV(dest.getKey()).iterator();
        while(ni.hasNext()){
        node_info temp = ni.next();
        if(dest.getTag()==temp.getTag()+wga.getEdge(dest.getKey(), temp.getKey()))
            {
                l.add(temp);
                listMaker(temp, l);
            }
        }
    }
    /**
     * This method save the weighted graph given to a file with the given name
     * return true if the save succeed else false
     * Note: I was helped by https://www.geeksforgeeks.org/serialization-in-java/
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        try {
            //Saving of object in a file
            FileOutputStream file1 = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(file1);

            // Method for serialization of object
            out.writeObject(wga);
            out.close();
            file1.close();

            System.out.println("Object has been serialized");
            return true;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return false;
        }
    }

    /**
     * This method load from given file weighted graph
     * return true if the load succeed else false
     * Note: I was helped by https://www.geeksforgeeks.org/serialization-in-java/
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        try
        {
            FileInputStream file1 = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(file1);
            this.wga = (WGraph_DS)in.readObject();
            in.close();
            file1.close();
            return true;
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            return false;
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
            return false;
        }
    }




    private void resInfo(weighted_graph wga){
        Iterator <node_info> it = wga.getV().iterator();
        node_info temp =null;
        while (it.hasNext()){
            temp=it.next();
            temp.setTag(Integer.MAX_VALUE);
            temp.setInfo("");
        }
    }
    public static void main(String[] args) throws IOException {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,11);
        g.connect(0,5,3);
        g.connect(1,2,5);
        g.connect(1,3,2);
        g.connect(2,4,8);
        g.connect(3,5,1);
        g.removeNode(2);
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        double c = wga.shortestPathDist(0,4);
        List <node_info> l = wga.shortestPath(0,4);


//        weighted_graph g1 = new WGraph_DS();
//        g1.addNode(0);
//        g1.addNode(1);
//        g1.addNode(2);
//        g1.addNode(3);
//        g1.addNode(4);
//        g1.addNode(5);
//        g1.connect(0,1,12);
//        g1.connect(0,5,3);
//        g1.connect(1,2,5);
//        g1.connect(1,3,2);
//        g1.connect(2,4,8);
//        g1.connect(3,5,1);
//        System.out.println(g.equals(g1));
        System.out.println("a");
    }
}
