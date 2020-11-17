import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest2 {
    public static weighted_graph graphBuild(int ver,int edge) {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < ver - 1; i++) {
            g.addNode(i);
            g.connect(i,i+1,1);
        }
        return g;
    }

    @Test
    void getNode() {
    }

    @Test
    void hasEdge() {
    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
    }

    @Test
    void connect() {
    }

    @Test
    void getV() {
    }

    @Test
    void testGetV() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }
}