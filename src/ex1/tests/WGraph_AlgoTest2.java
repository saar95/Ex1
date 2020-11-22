package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

class WGraph_AlgoTest2 {
    private static Random _rnd = null;

    @Test
    void init() {
        weighted_graph g=graph_create();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        Assertions.assertTrue(wga.getGraph().equals(g));
        weighted_graph g1=graph_create();
        g1.addNode(15);
        Assertions.assertFalse(wga.getGraph().equals(g1));
    }

    @Test
    void getGraph() {
        weighted_graph g=graph_create();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        Assertions.assertTrue(g.equals(wga.getGraph()));
        g.addNode(18);
        g.addNode(15);
        g.addNode(19);
        g.connect(15,19,5);
        Assertions.assertTrue(g.equals(wga.getGraph()));
        wga.getGraph().addNode(25);
        wga.getGraph().addNode(25);
        Assertions.assertTrue(g.equals(wga.getGraph()));
    }

    @Test
    void copy() {
        weighted_graph g=graph_create();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        weighted_graph g1 = wga.copy();
        weighted_graph_algorithms wga1 = new WGraph_Algo();
        wga1.init(g1);
        Assertions.assertTrue(wga.getGraph().equals(wga1.getGraph()));
        g.addNode(17);
        Assertions.assertFalse(wga.getGraph().equals(wga1.getGraph()));
        g.removeNode(17);
        Assertions.assertTrue(wga.getGraph().equals(wga1.getGraph()));
    }

    @Test
    void isConnected() {
        weighted_graph g=graph_create();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        Assertions.assertTrue(wga.isConnected());
        g.addNode(15);
        Assertions.assertFalse(wga.isConnected());
        g.connect(15,3,5);
        Assertions.assertTrue(wga.isConnected());
        g.removeNode(0);
        Assertions.assertTrue(wga.isConnected());
        g.removeEdge(2,5);
        Assertions.assertFalse(wga.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g=graph_create();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        Assertions.assertEquals(wga.shortestPathDist(0,8),12);
        g.connect(2,5,1);
        Assertions.assertEquals(wga.shortestPathDist(0,8),10);
        g.connect(2,5,6);
        g.removeNode(3);
        Assertions.assertEquals(wga.shortestPathDist(0,8),15);
        g.addNode(15);
        g.connect(0,15,1);
        g.connect(15,8,1);
        Assertions.assertEquals(wga.shortestPathDist(0,8),2);
    }

    @Test
    void shortestPath() {
        weighted_graph g=graph_create();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        Queue<node_info> q = new LinkedList<node_info>();
        g.connect(2,5,1);
        q.add(g.getNode(0));
        q.add(g.getNode(2));
        q.add(g.getNode(5));
        q.add(g.getNode(7));
        q.add(g.getNode(8));
        List<node_info> l = wga.shortestPath(0,8);
        Iterator<node_info> it = l.listIterator();
        while (!q.isEmpty()){
            Assertions.assertEquals(it.next().getKey(),q.poll().getKey());
        }
        g.connect(2,5,6);
        q.add(g.getNode(0));
        q.add(g.getNode(3));
        q.add(g.getNode(8));
        List<node_info> l1 = wga.shortestPath(0,8);
        Iterator<node_info> it1 = l1.listIterator();
        while (!q.isEmpty()){
            Assertions.assertEquals(it1.next().getKey(),q.poll().getKey());
        }
    }

    @Test
    void save() {
    }

    @Test
    void load() throws IOException {
        weighted_graph g=graph_create();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        weighted_graph g1=graph_create();
        g1.addNode(15);
        g.connect(15,0,8);
        g.connect(3,2,5);
        weighted_graph_algorithms wga1 = new WGraph_Algo();
        wga1.init(g1);
        Assertions.assertNotEquals(wga.getGraph(),wga1.getGraph());
        wga.save("new_graph");
        wga1.load("new_graph");
        Assertions.assertEquals(wga.getGraph(),wga1.getGraph());
    }

    /**
     *Method to create a graph.
     *Taken from Ex0 Boaz tests
     * @return
     */
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
}