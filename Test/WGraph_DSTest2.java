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

    @org.junit.jupiter.api.Test
    void getNode() {
    }

    @org.junit.jupiter.api.Test
    void hasEdge() {
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
    }

    @org.junit.jupiter.api.Test
    void addNode() {
    }

    @org.junit.jupiter.api.Test
    void connect() {
    }

    @org.junit.jupiter.api.Test
    void getV() {
    }

    @org.junit.jupiter.api.Test
    void testGetV() {
    }

    @org.junit.jupiter.api.Test
    void removeNode() {
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
    }

    @org.junit.jupiter.api.Test
    void nodeSize() {
    }

    @org.junit.jupiter.api.Test
    void edgeSize() {
    }

    @org.junit.jupiter.api.Test
    void getMC() {
    }
}