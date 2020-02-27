package co.raveblue.teacherassistant;

public class XYValue {

    private double x;
    private String y;
    private String z;
    private String v;
    private String w;
    private String u;

    public XYValue(String u, String v, String w, double x, String y, String z) {
        this.u = u;
        this.v = v;
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }
}