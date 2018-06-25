public class Planet{
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  static final double G = 6.67e-11;

  public Planet(double xP, double yP, double xV,
                double yV, double m, String img) {
                  xxPos = xP;
                  yyPos = yP;
                  xxVel = xV;
                  yyVel = yV;
                  mass = m;
                  imgFileName = img;
                } ;
  public Planet(Planet p) {
    xxPos = p.xxPos;
    yyPos = p.yyPos;
    xxVel = p.xxVel;
    yyVel = p.yyVel;
    mass = p.mass;
    imgFileName = p.imgFileName;
  }

  public double calcDistance(Planet p2) {
    double distance = 0,
           distanceX = 0,
           distanceY = 0;
    distanceX = Math.pow((this.xxPos - p2.xxPos), 2);
    distanceY = Math.pow((this.yyPos - p2.yyPos), 2);
    distance = Math.pow((distanceX + distanceY), 0.5);
    return distance;
  }

  public double calcForceExertedBy(Planet p2) {
    double force = 0;
    force = G * this.mass * p2.mass / Math.pow(this.calcDistance(p2), 2);
    return force;
  }

  public double calcForceExertedByX(Planet p2) {
    double forceX = 0,
           distanceX = 0;
           distanceX = p2.xxPos - this.xxPos;
           forceX = calcForceExertedBy(p2) * distanceX / calcDistance(p2);
           return forceX;
  }

  public double calcForceExertedByY(Planet p2) {
    double forceY = 0,
           distanceY = 0;
           distanceY = p2.yyPos - this.yyPos;
           forceY = calcForceExertedBy(p2) * distanceY / calcDistance(p2);
           return forceY;
  }

  public double calcNetForceExertedByX(Planet[] ps) {
    int len = ps.length;
    double netForceX = 0;
    for (int i = 0; i < len; i = i + 1) {
      if (this.equals(ps[i])) {
        continue;
      }
      netForceX = netForceX + this.calcForceExertedByX(ps[i]);
    }
    return netForceX;
  }

  public double calcNetForceExertedByY(Planet[] ps) {
    int len = ps.length;
    double netForceY = 0;
    for (int i = 0; i < len; i = i + 1) {
      if (this.equals(ps[i])) {
        continue;
      }
      netForceY = netForceY + this.calcForceExertedByY(ps[i]);
    }
    return netForceY;

  }

  public void update(double dt, double fX, double fY) {
    double aX, aY;
    aX = fX / this.mass;
    aY = fY / this.mass;
    xxVel = xxVel + dt * aX;
    yyVel = yyVel + dt * aY;
    xxPos = xxPos + dt * xxVel;
    yyPos = yyPos + dt * yyVel;
  }

  public void draw() {
    double x = this.xxPos;
    double y = this.yyPos;
    String image = "images/" + this.imgFileName;
    StdDraw.picture(x, y, image);
  }
}
