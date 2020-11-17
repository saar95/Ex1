import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
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

    @Override
    public weighted_graph copy() {
        node_info temp=null;
        node_info temp2=null;
        if(wga!=null){
            weighted_graph temp_graph = new WGraph_DS();
            Iterator <node_info> it =this.wga.getV().iterator();
            while(it.hasNext()){
                wga.addNode(it.next().getKey());
            }
            Iterator <node_info> it2 = this.wga.getV().iterator();
            while (it2.hasNext()){
                temp=it2.next();
                Iterator <node_info> it3 = this.wga.getV(temp.getKey()).iterator();
                while (it3.hasNext()){
                    temp2=it3.next();
                    wga.connect(temp.getKey(),temp2.getKey(),this.wga.getEdge(temp.getKey(),temp2.getKey()));
                }
            }
            return temp_graph;
        }
        return null;
    }

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

    @Override
    public double shortestPathDist(int src, int dest) {
        Queue<node_info> q = new LinkedList<node_info>();
        node_info temp = null;
        node_info temp2 = null;
        if(src==dest)
            return 0;
        wga.getNode(src).setTag(0);
        q.add(wga.getNode(src));
        wga.getNode(src).setInfo("1");
        while (!q.isEmpty()){
            node_info temp_src=wga.getNode(q.peek().getKey());
            Iterator<node_info> it = this.wga.getV(q.peek().getKey()).iterator();
            while(it.hasNext()){
                temp=it.next();
                double temp_weight=temp_src.getTag() + wga.getEdge(q.peek().getKey(), temp.getKey());
                if(temp.getInfo()!="1") {
                    q.add(temp);
                    temp.setInfo("1");
                }
                if(temp_weight<temp.getTag()) {
                       temp.setTag(temp_weight);
                       temp.setInfo("1");
                       refactor(temp);
                }
            }
            q.remove();
        }
        if(wga.getNode(dest).getTag()==Integer.MAX_VALUE)
            return -1;
        double a=wga.getNode(dest).getTag();
        resInfo(wga);
        return a;

    }
    private void refactor(node_info temp) {
        Iterator <node_info> neighbors = wga.getV(temp.getKey()).iterator();
        node_info temp1 =null;
        while (neighbors.hasNext()){
            temp1=neighbors.next();
            if(temp1.getTag()>temp.getTag()+wga.getEdge(temp.getKey(), temp1.getKey())){
                temp1.setTag(temp.getTag()+wga.getEdge(temp.getKey(), temp1.getKey()));
                refactor(temp1);
            }
        }
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> l = new ArrayList<node_info>();
        Queue<node_info> q = new LinkedList<node_info>();
        node_info temp = null;
        node_info temp2 = null;
        if(src==dest) {
            l.add(wga.getNode(src));
            return l;
        }
        wga.getNode(src).setTag(0);
        q.add(wga.getNode(src));
        wga.getNode(src).setInfo("1");
        while (!q.isEmpty()){
            node_info temp_src=wga.getNode(q.peek().getKey());
            Iterator<node_info> it = this.wga.getV(q.peek().getKey()).iterator();
            while(it.hasNext()){
                temp=it.next();
                double temp_weight=temp_src.getTag() + wga.getEdge(q.peek().getKey(), temp.getKey());
                if(temp.getInfo()!="1") {
                    q.add(temp);
                    temp.setInfo("1");
                }
                if(temp_weight<temp.getTag()) {
                    temp.setTag(temp_weight);
                    temp.setInfo("1");
                    refactor(temp);
                }
            }
            q.remove();
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

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
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

    public static void main(String[] args) {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,12);
        g.connect(0,5,3);
        g.connect(1,2,5);
        g.connect(1,3,2);
        g.connect(2,4,8);
        g.connect(3,5,1);
        g.removeNode(3);
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
        //System.out.println(wga.shortestPathDist(0,4));
        List<node_info> l = wga.shortestPath(0,6);
        System.out.println("a");
    }
}
