// (x1,y1)--------------
//    |                 |
//    |                 |
//    |     District    |
//    |                 |
//    |                 |
//     --------------(x2,y2)
public class District {
    public String name;
    public double x1,y1,x2,y2;

    public District(String name, double x1, double y1, double x2, double y2) {
        this.name = name;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}