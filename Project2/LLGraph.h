/*
 * A class to represent a graph.
 */

#include <iostream>
using namespace std;
 
/*
 * Edge
 */ 
struct Edge
{
    int to;
    int attr;           // attribute could be weight, distance, etc.
    struct Edge* next;
};
 
/*
 * Last Edge List or adjacency list
 */  
struct LEList
{
    struct Edge *head;
};
 
class LLGraph
{
    private:
        int INF;
        string INFs;
        struct LEList* le;  // Last Edge List

    public:
        int N;              // number of vertices
        /*
         * Constructors
         */
        LLGraph(){}
        LLGraph(int N)
        {
            this->INF = 999999999;
            this->INFs = "∞";
            this->N = N;
            this->le = new LEList[N];
            for (int i = 0; i < this->N; ++i)
                this->le[i].head = NULL;
        }

        /*
         * Destructors
         */
        ~LLGraph()
        {
            delete le;
        }

        /*
         * Creating New Edge
         */ 
        Edge* newEdge(int to, int attr)
        {
            Edge* e = new Edge;
            e->to = to;
            e->attr = attr;
            e->next = NULL;
            return e;
        }

        /*
         * Adding Undirected Edge (<->) to Graph 
         * with default attribute/weight value
         */
        void addUnDEdge(int from, int to)
        {
            // Adding the Edge to the destination vertix
            addEdge(from, to, 1);
            // Adding the Edge to the source vertix
            addEdge(to, from, 1);
        }

        /*
         * Adding Undirected Edge (<->) to Graph 
         * with a specific attribute/weight value
         */
        void addUnDEdge(int from, int to, int attr)
        {
            // Adding the Edge to the destination vertix
            addEdge(from, to, attr);
            // Adding the Edge to the source vertix
            addEdge(to, from, attr);
        }

        /*
         * Adding a directed Edge (->) to Graph 
         * with default attribute/weight value
         */
        void addEdge(int from, int to)
        {
            addEdge(from, to, 1);
        }

        /*
         * Adding a directed Edge (->) to Graph 
         * with a specific attribute/weight value
         */
        void addEdge(int from, int to, int attr)
        {
            //if(from != to && attr != this->INF)
            {
                
                Edge* e = newEdge(to, attr);
                e->next = this->le[from].head;
                le[from].head = e;
            }
        }

        /*
         * Getting Weight of a specific edge
         */
        int getAttr(int from, int to)
        {
            if(from == to) return 0; // link with itself.
            
            for(Edge* e = this->le[from].head; e != NULL; e = e->next)
            {
                if(e->to == to)
                    return e->attr;
            }
            return this->INF;
        }

        /*
         * Setting Weight of a specific edge
         */
        void setAttr(int from, int to, int attr)
        {
            bool done = false;
            if(from == to) return; // no link with itself.
            
            for(Edge* e = le[from].head; e != NULL; e = e->next)
            {
                if(e->to == to){
                    e->attr = attr;
                    done = true;
                }
            }
            if(!done){
                addUnDEdge(from, to, attr);
                done = true;
            }

            for(Edge* e = le[to].head; e != NULL; e = e->next)
            {
                if(e->to == from)
                    e->attr = attr;
            }
        }

        /*
         * Print the graph
         */
        void print()
        {
            print(false);
        }
        void print(bool all)
        {
            for (int i = 0; i < this->N; ++i)
            {
                Edge* e = this->le[i].head;
                cout << "V" << i + 1 << ": ";
                bool pv = false, has_child = false;

                while (e != NULL)
                {
                    if(all && !pv && i < e->to){
                        cout << i + 1 << "(0) - ";
                        pv = true;
                    }
                    cout << e->to + 1 << "(";
                    if(e->attr == this->INF)
                        cout << this->INFs;
                    else
                        cout << e->attr;
                    cout << ")";
                    e = e->next;
                    if(e != NULL) cout << " - ";
                    if(!has_child) has_child = true;
                }
                if(all && !pv)
                {
                    if(has_child)
                        cout << " - ";
                    cout << i + 1 << "(0)";
                    pv = true;
                }
                cout << endl;
            }
        }

        /*
         * Clone this Linked List Graph to another one
         */
        LLGraph* clone() 
        { 
            LLGraph* graph = new LLGraph(this->N);

            for (int i = 0; i < graph->N; ++i)
            {
                Edge* e = this->le[i].head;
                cloneEdgeReverse(graph, i, e);
            }

            return graph;
        }

        /* 
         * Recursive Function to clone all vertix's edges in reverse way
         */
        void cloneEdgeReverse(LLGraph* graph, int v, struct Edge* e)
        {
            // Base case  
            if (e == NULL) return;
         
            cloneEdgeReverse(graph, v, e->next);
            graph->addEdge(v, e->to, e->attr);
        }

        const LEList* getLastEdges() const
        {
            return le;
        }
};