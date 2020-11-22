package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WGraph_DSTest2 {
    private static Random _rnd = null;

    @Test
    void getNode() {
        weighted_graph g=graph_create();
        g.getNode(5).setTag(1);
        g.getNode(5).setInfo("1");
        Assertions.assertEquals(g.getNode(5).getTag(),1);
        Assertions.assertEquals(g.getNode(5).getInfo(),"1");
        g.addNode(17);
        g.getNode(17).setInfo("333");
        Assertions.assertEquals(g.getNode(17).getInfo(),"333");
        g.removeNode(17);
        g.getNode(17);
    }

    @Test
    void hasEdge() {
    weighted_graph g=graph_create();
    Assertions.assertTrue(g.hasEdge(1,4));
    Assertions.assertFalse(g.hasEdge(0,4));
    Assertions.assertTrue(g.hasEdge(9,10));
    Assertions.assertTrue(g.hasEdge(7,8));
    Assertions.assertTrue(g.hasEdge(8,3));
    Assertions.assertTrue(g.hasEdge(4,2));
    Assertions.assertFalse(g.hasEdge(8,0));
    Assertions.assertFalse(g.hasEdge(5,9));
    Assertions.assertFalse(g.hasEdge(11,3));
    }

    @Test
    void getEdge() {
        weighted_graph g=graph_create();
        Assertions.assertEquals(g.getEdge(5,6),3);
        Assertions.assertEquals(g.getEdge(8,3),9);
        Assertions.assertEquals(g.getEdge(3,8),9);
        Assertions.assertEquals(g.getEdge(9,10),2);
        Assertions.assertEquals(g.getEdge(1,10),-1);
        Assertions.assertEquals(g.getEdge(3,5),-1);
        Assertions.assertEquals(g.getEdge(10,6),-1);
    }

    @Test
    void addNode() {
        weighted_graph g=graph_create();
        Assertions.assertEquals(g.nodeSize(),11);
        g.addNode(13);
        g.addNode(15);
        Assertions.assertEquals(g.nodeSize(),13);
        g.addNode(13);
        g.addNode(15);
        Assertions.assertEquals(g.nodeSize(),13);
        g.addNode(20);
        Assertions.assertEquals(g.nodeSize(),14);
        g.removeNode(20);
        Assertions.assertEquals(g.nodeSize(),13);
    }

    @Test
    void connect() {
        weighted_graph g=graph_create();
        Assertions.assertTrue(g.hasEdge(6,9));
        Assertions.assertTrue(g.hasEdge(5,7));
        g.addNode(15);
        g.addNode(16);
        g.addNode(17);
        g.connect(15,16,1);
        Assertions.assertTrue(g.hasEdge(15,16));
        Assertions.assertFalse(g.hasEdge(15,17));
        g.connect(16,17,4);
        Assertions.assertTrue(g.hasEdge(16,17));
        Assertions.assertFalse(g.hasEdge(15,17));
        Assertions.assertEquals(g.getEdge(15,16),1);
        g.removeNode(16);
        Assertions.assertFalse(g.hasEdge(15,16));
        g.addNode(30);
        g.addNode(31);
        g.connect(30,31,5);
        assertEquals(g.getEdge(30,31),5);
        g.connect(30,31,10);
        assertEquals(g.getEdge(31,30),10);
    }

    @Test
    void getV() {
        weighted_graph g=graph_create();
        Queue<node_info> q = new LinkedList<node_info>();
        for (int i=0;i<g.nodeSize();i++){
            q.add(g.getNode(i));
        }
        Iterator<node_info> it = g.getV().iterator();
        while(!q.isEmpty()){
            Assertions.assertEquals(q.poll().getKey(),it.next().getKey());
        }
    }

    @Test
    void testGetV() {
        weighted_graph g=graph_create();
        Queue<node_info> q = new LinkedList<node_info>();
        q.add(g.getNode(2));
        q.add(g.getNode(6));
        q.add(g.getNode(7));
        Iterator<node_info> it = g.getV(5).iterator();
        while (!q.isEmpty()){
            Assertions.assertEquals(q.poll().getKey(),it.next().getKey());
        }
        q.add(g.getNode(0));
        q.add(g.getNode(4));
        q.add(g.getNode(5));
        Iterator<node_info> it1 = g.getV(2).iterator();
        while (!q.isEmpty()){
            Assertions.assertEquals(q.poll().getKey(),it1.next().getKey());
        }
    }

    @Test
    void removeNode() {
        weighted_graph g=graph_create();
        assertEquals(g.nodeSize(),11);
        g.addNode(13);
        g.addNode(15);
        assertEquals(g.nodeSize(),13);
        g.addNode(15);
        assertEquals(g.nodeSize(),13);
        g.removeNode(15);
        assertEquals(g.nodeSize(),12);
        g.removeNode(9);
        assertEquals(g.nodeSize(),11);
        g.removeNode(20);
        g.removeNode(21);
        assertEquals(g.nodeSize(),11);
    }

    @Test
    void removeEdge() {
        weighted_graph g=graph_create();
        assertEquals(g.edgeSize(),12);
        g.removeEdge(9,10);
        assertEquals(g.edgeSize(),11);
        g.removeNode(2);
        assertEquals(g.edgeSize(),8);
        g.connect(5,8,3);
        assertEquals(g.edgeSize(),9);
        g.connect(0,1,2);
        assertEquals(g.edgeSize(),9);
        g.removeNode(0);
        assertEquals(g.edgeSize(),7);
        g.addNode(20);
        assertEquals(g.edgeSize(),7);
        g.connect(20,3,4);
        assertEquals(g.edgeSize(),8);
        g.connect(20,3,4);
        assertEquals(g.edgeSize(),8);
    }

    @Test
    void nodeSize() {
        weighted_graph g=graph_create();
        Assertions.assertEquals(g.nodeSize(),11);
        g.addNode(5);
        g.addNode(8);
        Assertions.assertEquals(g.nodeSize(),11);
        g.addNode(18);
        g.addNode(18);
        Assertions.assertEquals(g.nodeSize(),12);
        g.removeNode(20);
        Assertions.assertEquals(g.nodeSize(),12);
        g.removeNode(3);
        g.removeNode(3);
        Assertions.assertEquals(g.nodeSize(),11);
    }

    @Test
    void edgeSize() {
        weighted_graph g=graph_create();
        Assertions.assertEquals(g.edgeSize(),12);
        g.removeNode(9);
        Assertions.assertEquals(g.edgeSize(),10);
        g.removeEdge(7,8);
        Assertions.assertEquals(g.edgeSize(),9);
        g.removeEdge(7,8);
        Assertions.assertEquals(g.edgeSize(),9);
        g.connect(7,8,5);
        Assertions.assertEquals(g.edgeSize(),10);
        g.addNode(15);
        g.addNode(20);
        g.connect(15,20,4);
        g.connect(15,20,2);
        Assertions.assertEquals(g.edgeSize(),11);
    }

    @Test
    void getMC() {
        weighted_graph g=graph_create();
        Assertions.assertEquals(g.getMC(),23);
        g.addNode(99);
        g.addNode(99);
        Assertions.assertEquals(g.getMC(),24);
        g.removeEdge(9,10);
        g.removeEdge(9,10);
        Assertions.assertEquals(g.getMC(),25);
        g.removeNode(5);
        Assertions.assertEquals(g.getMC(),29);
    }

    public static weighted_graph graph_create(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.addNode(10);
        g.connect(0,1,2);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(1,4,3);
        g.connect(2,4,4);
        g.connect(2,5,6);
        g.connect(3,8,9);
        g.connect(5,6,3);
        g.connect(5,7,5);
        g.connect(6,9,1);
        g.connect(7,8,2);
        g.connect(9,10,2);
        return g;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        weighted_graph g = graph_creator(1000000,10000000,1);
        long endTime = System.currentTimeMillis();
        long runtime=endTime-startTime;
        if(runtime<40000)
            System.out.println("Runtime test succeed");
        else
            System.out.println("Runtime test failed");

    }
    /**
     *Method to create a graph.
     *Taken from Ex0 Boaz tests
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            g.connect(i,j,1);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an  Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}